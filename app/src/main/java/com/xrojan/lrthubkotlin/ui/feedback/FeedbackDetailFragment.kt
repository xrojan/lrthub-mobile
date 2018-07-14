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
import org.jetbrains.anko.toast
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
    private var feedbackId = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.feedback_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            initComponents()
        }
    }

    fun show(manager: FragmentManager?, tag: String?, feedbackId: Int) {
        super.show(manager, tag)
        this.feedbackId = feedbackId
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

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
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
        if (feedbackId == -1) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val millisInString = dateFormat.format(Date())
            et_incident_date.setText(millisInString.toString())
        } else {
            bt_submit.visibility = View.GONE
        }
    }

    private fun onSuccessFetchLocalData(token: String, uid: Int) {
        this.token = token
        this.uid = uid

        if (feedbackId != -1) {
            subscribe(feedbackViewModel.getFeedbackConversation(token, feedbackId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.single())
                    .subscribe({
                        when (it.request.statusCode) {
                            HTTP.OK -> {
                                onSuccessRetrieveDetails(it)
                            }
                        }
                    }, {
                        Log.e(tag, it.message)
                    }))
        }
    }

    private fun onSuccessSendFeedback(data: Request<FeedbackConversation>) {
        subscriptions.clear()
        dismiss()
    }

    private fun onSuccessRetrieveDetails(data: UIData<FeedbackConversation>) {
        doAsync {
            uiThread {
                bt_cancel.text = "BACK"

                val feedbackConversation = data.request.result
                et_incident_subject.setText(feedbackConversation.incidentSubject)
                et_incident_subject.isEnabled = false
                et_incident_date.setText(feedbackConversation.incidentDate)
                et_incident_date.isEnabled = false
                et_fullname.setText(feedbackConversation.fullName)
                et_fullname.isEnabled = false
                et_address.setText(feedbackConversation.address)
                et_address.isEnabled = false
                et_contact_number.setText(feedbackConversation.contactNumber)
                et_contact_number.isEnabled = false
                et_employee_name.setText(feedbackConversation.employeeName)
                et_employee_name.isEnabled = false
                et_other_details.setText(feedbackConversation.otherDetails)
                et_other_details.isEnabled = false
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