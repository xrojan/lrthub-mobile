package com.xrojan.lrthubkotlin.ui.chatbot

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xrojan.lrthubkotlin.App
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.adapters.ChatbotAdapter
import com.xrojan.lrthubkotlin.constants.ASK_MESSAGE_MODE
import com.xrojan.lrthubkotlin.fragments.BaseFragment
import com.xrojan.lrthubkotlin.repository.entities.AskMessage
import com.xrojan.lrthubkotlin.viewmodel.ChatbotViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.chatbot_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class ChatbotFragment : BaseFragment() {
    private val chatbotViewModel: ChatbotViewModel = App.injectChatbotViewModel()
    private lateinit var chatbotAdapter: ChatbotAdapter
    private lateinit var askMessages: MutableList<AskMessage>

    companion object {
        fun newInstance() = ChatbotFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chatbot_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeSubscriptions()
    }

    private fun initComponents() {
        // Setup recycler view
        askMessages = ArrayList()
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.stackFromEnd = true
        linearLayoutManager.isSmoothScrollbarEnabled = true
        rv_chatbot_messaging.layoutManager = linearLayoutManager

        chatbotAdapter = ChatbotAdapter(context!!, askMessages)
        rv_chatbot_messaging.adapter = chatbotAdapter

        //  Init listeners
        bt_send.setOnClickListener { onSendClick() }

        // Initial chat
        updateMessageStack(serializeAskMessage("Hi, I am AskBot of LRTHub. How may I help you?", ASK_MESSAGE_MODE.CHATBOT_SENDER))
    }

    private fun onSendClick() {
        val queryMessage = et_message.text.toString()
        if (!queryMessage.isEmpty()) {
            et_message.text.clear()
            tv_chatbot_speaking.visibility = View.VISIBLE

            updateMessageStack(serializeAskMessage(queryMessage, ASK_MESSAGE_MODE.USER_SENDER))
            subscribe(chatbotViewModel.queryChatbot(queryMessage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.single())
                    .doOnComplete {
                        doAsync {
                            uiThread {
                                tv_chatbot_speaking.visibility = View.GONE
                            }
                        }
                    }
                    .subscribe({
                        var answer = it.answer.answer
                        if (it.answer.answer == "No good match found in KB.") {
                            answer = "Sorry, I can't seem to recognize your question. Please try to be more specific."
                        }
                        updateMessageStack(serializeAskMessage(answer, ASK_MESSAGE_MODE.CHATBOT_SENDER))

                    }, {
                        Log.e(tag, it.message)
                        onFailedChatBotResponse()
                    }))
        }
    }

    private fun serializeAskMessage(question: String, askMessageMode: ASK_MESSAGE_MODE): AskMessage {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy KK:mm a")
        val millisInString = dateFormat.format(Date())
        return AskMessage(question, millisInString, askMessageMode)
    }

    private fun updateMessageStack(data: AskMessage) {
        doAsync {
            uiThread {
                askMessages.add(data)
                chatbotAdapter.notifyDataSetChanged()
                rv_chatbot_messaging.smoothScrollToPosition(askMessages.size)
            }
        }
    }

    private fun onFailedChatBotResponse() {
        tv_chatbot_speaking.visibility = View.GONE
    }
}