package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua de Guzman on 14/07/2018.
 */

data class FeedbackConversation(
        @SerializedName("id")
        val id: Int,
        @SerializedName("sender_id")
        val senderId: Int,
        @SerializedName("sender")
        val sender: FeedbackUser,
        @SerializedName("receiver_id")
        val receiverId: Int,
        @SerializedName("receiver")
        val receiver: FeedbackUser,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("employee_name")
        val employeeName: String,
        @SerializedName("contact_number")
        val contactNumber: String,
        @SerializedName("incident_subject")
        val incidentSubject: String,
        @SerializedName("incident_date")
        val incidentDate: String,
        @SerializedName("other_details")
        val otherDetails: String,
        @SerializedName("is_closed")
        val isClosed: Boolean,
        @SerializedName("updated_on")
        val updatedOn: String
)

data class FeedbackUser(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("email")
        val email: String
)