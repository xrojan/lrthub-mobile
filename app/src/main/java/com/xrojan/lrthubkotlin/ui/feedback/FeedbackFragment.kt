package com.xrojan.lrthubkotlin.ui.feedback

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.adapters.FeedAdapter
import com.xrojan.lrthubkotlin.adapters.FeedbackAdapter
import com.xrojan.lrthubkotlin.constants.HTTP
import com.xrojan.lrthubkotlin.fragments.BaseFragment
import com.xrojan.lrthubkotlin.repository.entities.FeedbackConversation
import com.xrojan.lrthubkotlin.ui.main.MainViewModel
import com.xrojan.lrthubkotlin.viewmodel.FeedbackViewModel
import com.xrojan.lrthubkotlin.viewmodel.UserViewModel
import com.xrojan.lrthubkotlin.viewmodel.data.UIDataArray
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feedback_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.uiThread

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

class FeedbackFragment : BaseFragment() {
    private val feedbackViewModel: FeedbackViewModel = App.injectFeedbackViewModel()
    private val userViewModel: UserViewModel = App.injectUserViewModel()

    companion object {
        fun newInstance() = FeedbackFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.feedback_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            initComponents()
        }
    }

    private fun initComponents() {
        // Init listeners
        bt_create_new.setOnClickListener { showFeedbackDetail() }
        bt_create_new_main.setOnClickListener { showFeedbackDetail() }

        subscribe(userViewModel.getUserLocalData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe {
                    if (it.isNotEmpty()) {
                        onSuccessFetchLocal(it[0].token, it[0].id)
                    }
                })
    }

    private fun showFeedbackDetail() {
        val feedbackDetailFragment = FeedbackDetailDialogFragment()
        feedbackDetailFragment.show(activity!!.fragmentManager, feedbackDetailFragment::class.java.simpleName)
        Log.e(tag, "Hello")
    }

    private fun onSuccessFetchLocal(token: String, uid: Int) {
        subscribe(feedbackViewModel.getFeedbackConversations(token)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe({
                    when (it.request.statusCode) {
                        HTTP.OK -> {
                            onSuccessFeedbackConversations(it)
                        }

                        HTTP.FORBIDDEN -> {

                        }
                    }
                }, {
                    Log.e(tag, it.message)
                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeSubscriptions()
    }

    private fun onSuccessFeedbackConversations(data: UIDataArray<List<FeedbackConversation>>) {
        doAsync {
            uiThread {
                rv_feedback_conversations.layoutManager = LinearLayoutManager(activity)
                val feedbackAdapter: FeedbackAdapter = FeedbackAdapter(context!!, data.request.result)
                feedbackAdapter.onItemClick = {
                    val feedbackDetailDialogFragment = FeedbackDetailDialogFragment()
                    feedbackDetailDialogFragment.show(activity!!.fragmentManager, feedbackDetailDialogFragment::class.java.simpleName, data.request.result[it].id)
                }
                rv_feedback_conversations.adapter = feedbackAdapter
            }
        }
    }
}
