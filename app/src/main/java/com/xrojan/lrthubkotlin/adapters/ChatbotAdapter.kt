package com.xrojan.lrthubkotlin.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.constants.ASK_MESSAGE_MODE
import com.xrojan.lrthubkotlin.repository.entities.AskMessage
import kotlinx.android.synthetic.main.item_chatbot_message_bot.view.*


/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class ChatbotAdapter(private val context: Context,
                     private val items: MutableList<AskMessage>) : RecyclerView.Adapter<ChatbotAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rlBotView: RelativeLayout = view.rl_chatbot
        val rlUserView: RelativeLayout = view.rl_user
        val tvMessageBot: TextView = view.tv_message_bot
        val tvTimestampBot: TextView = view.tv_timestamp_bot
        val tvMessageUser: TextView = view.tv_message_user
        val tvTimestampUser: TextView = view.tv_timestamp_user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ChatbotAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chatbot_message_bot, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val askMessage: AskMessage = items[position]

        if (askMessage.sender_mode == ASK_MESSAGE_MODE.CHATBOT_SENDER) {
            holder.rlUserView.visibility = View.GONE
            holder.rlBotView.visibility = View.VISIBLE
            holder.tvMessageBot.text = askMessage.message
            holder.tvTimestampBot.text = askMessage.timestamp
        } else {
            holder.rlUserView.visibility = View.VISIBLE
            holder.rlBotView.visibility = View.GONE
            holder.tvMessageUser.text = askMessage.message
            holder.tvTimestampUser.text = askMessage.timestamp
        }
    }

}