package com.xrojan.lrthubkotlin.ui.profile

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.constants.HTTP
import com.xrojan.lrthubkotlin.fragments.BaseFragment
import com.xrojan.lrthubkotlin.repository.entities.UserProfile
import com.xrojan.lrthubkotlin.ui.main.MainViewModel
import com.xrojan.lrthubkotlin.viewmodel.UserViewModel
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.settings_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import android.support.v7.app.AppCompatActivity



/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class ProfileFragment : BaseFragment() {
    private val userViewModel: UserViewModel = App.injectUserViewModel()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            initComponents()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    }

    private fun initComponents() {
        btn_verify.isEnabled = false
        subscribe(userViewModel.getUserLocalData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe {
                    if (it.isNotEmpty()) {
//                        Log.d(tag, "LAMAN: " + it[0].token + it[0].id)
                        onSuccessFetchLocal(it[0].token, it[0].id)
                    }
                })

        // Subscribe for User Details


    }

    private fun onSuccessFetchLocal(token: String, uid: Int) {
        subscribe(userViewModel.getUserDetail(token, uid)
        !!.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    Log.d(tag, it.request.result[0].user.username)
                    when (it.request.statusCode) {
                        HTTP.OK -> {
                            showUserDetail(it)
                            if (it.request.result.isNotEmpty()) {
                                // show details and show update button
                                doAsync {
                                    uiThread {
//                                        toast("Profile OK, show update button")
                                        btn_verify.isEnabled = true
                                        btn_verify.text = "UPDATE PROFILE"
                                    }
                                }
                            } else {
                                // show add verify button
                                doAsync {
                                    uiThread {
//                                        toast("Profile no result, Show add verify button")
                                        btn_verify.isEnabled = true
                                        btn_verify.text = "VERIFY PROFILE"
                                    }
                                }
                            }
                        }

                        HTTP.FORBIDDEN -> {

                        }

                        HTTP.SERVICE_UNAVAILABLE -> {

                        }
                    }
                }, {
                    // onFailedLogin()
                    Log.d(tag, it.message)
                    doAsync {
                        uiThread {
                            toast("FAILED RETRIEVING USER")
                        }
                    }
                }))
    }

    private fun showUserDetail(data: UIDataArray<List<UserProfile>>) {
        doAsync {
            uiThread {
                tv_profile_name.text = data.request.result[0].user.username
                tv_profile_email.text = data.request.result[0].user.email
                tv_birthdate.text = "Birthday: " + data.request.result[0].birthDate
                tv_mobile_no.text = "Mobile no: " + data.request.result[0].mobileNumber
                tv_address.text = "Address: " + data.request.result[0].address
                tv_children_count.text = "Children count: " + data.request.result[0].childrenCount.toString()
                tv_gender.text = "Gender: " + data.request.result[0].gender.name
                tv_nationality.text = "Nationality: " + data.request.result[0].nationality.name
                tv_marital_status.text = "Marital status: " + data.request.result[0].maritalStatus.name
                tv_employment_status.text = "Employment status: " + data.request.result[0].employmentStatus.name
            }
        }
    }

//    override fun onDetach() {
//        super.onDetach()
//        (activity as AppCompatActivity).supportActionBar!!.show()
//    }

}
