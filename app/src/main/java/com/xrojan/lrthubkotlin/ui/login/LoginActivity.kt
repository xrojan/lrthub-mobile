package com.xrojan.lrthubkotlin.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.activities.BaseActivity
import com.xrojan.lrthubkotlin.constants.HTTP
import com.xrojan.lrthubkotlin.viewmodel.data.UIData
import com.xrojan.lrthubkotlin.repository.entities.User
import com.xrojan.lrthubkotlin.ui.main.MainActivity
import com.xrojan.lrthubkotlin.viewmodel.UserViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class LoginActivity : BaseActivity() {
    private val tag = LoginActivity::class.java.simpleName
    private val userViewModel: UserViewModel = App.injectUserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            initComponents()
        }
    }

    private fun initComponents() {
        bt_login.setOnClickListener {
            pb_loading.visibility = View.VISIBLE
            loginUser(et_username.text.toString(), et_password.text.toString())
        }

        tv_sign_up.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }

    private fun loginUser(username: String, password: String) {
        subscribe(userViewModel.loginUser(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    when (it.request.statusCode) {
                        HTTP.OK -> {
                            onSuccessLogin(it)
                        }

                        HTTP.FORBIDDEN -> {

                        }

                        HTTP.SERVICE_UNAVAILABLE -> {

                        }
                    }
                }, {
                    onFailedLogin()
                    showError(tag, it.message.toString())
                }))
    }

    private fun onSuccessLogin(data: UIData<User>) {
        Log.e(tag, data.request.result.toString())
        doAsync {
            uiThread {
                pb_loading.visibility = View.GONE
            }
        }

        // Start new activity intent on login
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onFailedLogin() {
        Log.e(tag, getString(R.string.login_invalid))
        doAsync {
            uiThread {
                pb_loading.visibility = View.GONE
                Toast.makeText(applicationContext, getString(R.string.login_invalid), Toast.LENGTH_SHORT).show()
            }
        }
    }
}