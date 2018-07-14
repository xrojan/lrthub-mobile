package com.xrojan.lrthubkotlin.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xrojan.lrthubkotlin.R
import com.xrojan.lrthubkotlin.repository.entities.FeedbackConversation
import kotlinx.android.synthetic.main.item_feedback_conversation.view.*

/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

class FeedbackAdapter(
        val context: Context,
        val items: List<FeedbackConversation>) : RecyclerView.Adapter<FeedbackAdapter.ViewHolder>() {

    var onItemClick: (Int) -> Unit = {}

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIncidentSubject: TextView = view.tv_incident_subject
        val tvIncidentOtherDetails: TextView = view.tv_other_details
        val tvIncidentDate: TextView = view.tv_incident_date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feedback_conversation, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Assign click listener
        holder.itemView.setOnClickListener {
            onItemClick(holder.adapterPosition)
        }

        // Render details
        val feedbackConversation: FeedbackConversation = items[position]

        holder.tvIncidentSubject.text = feedbackConversation.incidentSubject
        holder.tvIncidentOtherDetails.text = feedbackConversation.otherDetails
        holder.tvIncidentDate.text = "INCIDENT DATE: " + feedbackConversation.incidentDate
    }

    override fun getItemCount(): Int {
        return items.size
    }
}