package com.xrojan.lrthubkotlin.ui.feedback

import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.constants.HTTP
import com.xrojan.lrthubkotlin.repository.entities.FeedbackConversation
import com.xrojan.lrthubkotlin.repository.entities.Request
import com.xrojan.lrthubkotlin.viewmodel.FeedbackViewModel
import com.xrojan.lrthubkotlin.viewmodel.UserViewModel
import com.xrojan.lrthubkotlin.viewmodel.data.UIData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feedback_dialog_fragment.*
import kotlinx.android.synthetic.main.feedback_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class FeedbackDetailDialogFragment : DialogFragment() {
    private val subscriptions = CompositeDisposable()
    private val feedbackViewModel: FeedbackViewModel = App.injectFeedbackViewModel()
    private val userViewModel: UserViewModel = App.injectUserViewModel()
    private var token: String = ""
    private var uid: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.feedback_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            initComponents()
        }
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        super.show(manager, tag)
    }

    override fun onStart() {
        super.onStart()
        setStyle(android.app.DialogFragment.STYLE_NO_FRAME, theme)
        dialog.window!!.setWindowAnimations(R.style.DialogAnimationRtlLtr)
        subscribe(userViewModel.getUserLocalData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    onSuccessFetchLocalData(it[0].token, it[0].id)
                }, {
                    Log.e(tag, it.message)
                }))
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    private fun initComponents() {
        bt_submit.setOnClickListener {
            if (isValid()) {
                subscribe(feedbackViewModel.sendFeedback(this.token, this.uid, et_fullname.text.toString(), et_address.text.toString(), et_contact_number.text.toString(), et_employee_name.text.toString(), et_incident_date.text.toString(), et_incident_subject.text.toString(), et_other_details.text.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.single())
                        .subscribe({
                            when (it.statusCode) {
                                HTTP.CREATED -> {
                                    onSuccessSendFeedback(it)
                                }

                                HTTP.BAD_REQUEST -> {
                                    //
                                }
                            }
                        }, {
                            Log.e(tag, it.message)
                        }))
            }
        }

        bt_cancel.setOnClickListener { dismiss() }

        // Init dates
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val millisInString = dateFormat.format(Date())
        et_incident_date.setText(millisInString.toString())
    }

    private fun onSuccessFetchLocalData(token: String, uid: Int) {
        this.token = token
        this.uid = uid
    }

    private fun onSuccessSendFeedback(data: Request<FeedbackConversation>) {
        doAsync {
            uiThread {
                // Navigate to detail view
            }
        }
    }

    private fun isValid(): Boolean {
        if (et_incident_subject.text.toString().trim().isEmpty()) {
            et_incident_subject.error = getString(R.string.empty_text)
            return false
            return false
        } else if (et_incident_date.text.toString().trim().isEmpty()) {
            et_incident_date.error = getString(R.string.empty_text)
            return false
        } else if (et_fullname.text.toString().trim().isEmpty()) {
            et_fullname.error = getString(R.string.empty_text)
            return false
        } else if (et_address.text.toString().trim().isEmpty()) {
            et_address.error = getString(R.string.empty_text)
            return false
        } else if (et_contact_number.text.toString().trim().isEmpty()) {
            et_contact_number.error = getString(R.string.empty_text)
            return false
        } else if (et_employee_name.text.toString().trim().isEmpty()) {
            et_employee_name.error = getString(R.string.empty_text)
            return false
        } else if (et_other_details.text.toString().trim().isEmpty()) {
            et_other_details.error = getString(R.string.empty_text)
            return false
        } else if (token.trim().isEmpty()) {
            return false
        } else if (uid == -1) {
            return false
        }

        return true
    }

    /**
     * Create disposable subscriptions
     * @param disposable
     */
    fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }
}