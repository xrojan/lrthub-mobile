package com.xrojan.lrthubkotlin.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.activities.BaseActivity
import com.xrojan.lrthubkotlin.constants.HTTP
import com.xrojan.lrthubkotlin.repository.entities.User
import com.xrojan.lrthubkotlin.ui.main.MainActivity
import com.xrojan.lrthubkotlin.viewmodel.UserViewModel
import com.xrojan.lrthubkotlin.viewmodel.data.UIData
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class SignupActivity : BaseActivity() {
    private val tag = SignupActivity::class.java.simpleName
    private val userViewModel: UserViewModel = App.injectUserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        if (savedInstanceState == null) {
            initComponents()
        }

    }

    private fun initComponents() {
        pb_register_loading.visibility = View.GONE

        bt_register.setOnClickListener {
            if (isValid()) {
                // Register User
                registerUser(et_register_username.text.toString(), et_register_password.text.toString(), et_register_email.text.toString())
                toast("Registering user")

            }
        }
    }

    private fun registerUser(username: String, password: String, email: String) {
        pb_register_loading.visibility = View.VISIBLE
        subscribe(userViewModel.registerUser(username, password, email)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    when (it.request.statusCode) {
                        HTTP.CREATED -> {
                            onSuccessRegister(it, username, password)
                            toast("Successfully registered")
                        }

                        HTTP.FORBIDDEN -> {

                        }

                        HTTP.SERVICE_UNAVAILABLE -> {

                        }

                        HTTP.BAD_REQUEST -> {
                            onFailedRegister(it.request.message, true)
                        }
                    }
                }, {
                    onFailedRegister("", false)
                    showError(tag, it.message.toString())
                }))
    }

    private fun onFailedRegister(message: String, withMessage: Boolean) {
        doAsync {
            uiThread {
                pb_register_loading.visibility = View.GONE
                if (withMessage) {
                    toast(getString(R.string.invalid_request))
                }
            }
        }
    }

    private fun onSuccessRegister(data: UIData<User>, username: String, password: String) {
        Log.e(tag, data.request.result.toString())
        doAsync {
            uiThread {
//                pb_register_loading.visibility = View.GONE
                loginUser(username, password)
            }
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

    private fun onSuccessLogin(it: UIData<User>) {
        Log.e(tag, it.request.result.toString())
        doAsync {
            uiThread {
                pb_register_loading.visibility = View.GONE
            }
        }

        // Start new activity intent on login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun onFailedLogin() {
        Log.e(tag, getString(R.string.invalid_request))
        doAsync {
            uiThread {
                pb_register_loading.visibility = View.GONE
                Toast.makeText(applicationContext, getString(R.string.invalid_request), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid(): Boolean {
        if (et_register_username.text.toString().trim().isEmpty()) {
            et_register_username.error = getString(R.string.empty_text)
            return false
        } else if (et_register_username.text.toString().trim().length < 8) {
            et_register_username.error = getString(R.string.short_text)
            return false
        } else if (et_register_email.text.toString().trim().isEmpty()) {
            et_register_email.error = getString(R.string.empty_text)
            return false
        } else if (et_register_email.text.toString().trim().length < 8) {
            et_register_email.error = getString(R.string.short_text)
            return false
        } else if (et_register_password.text.toString().trim().isEmpty()) {
            et_register_password.error = getString(R.string.empty_text)
            return false
        } else if (et_register_password.text.toString().trim().length < 8) {
            et_register_password.error = getString(R.string.short_text)
            return false
        }
        return true
    }

}
