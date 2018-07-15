package com.xrojan.lrthubkotlin.repository.entities

import com.google.gson.annotations.SerializedName

data class UserProfile(
        @SerializedName("id")
        val id: Int,

        @SerializedName("user")
        val user: User,

        @SerializedName("gender")
        val gender: Gender,

        @SerializedName("gender_id")
        val genderId: Int,

        @SerializedName("profile_image")
        val profileImage: String,

        @SerializedName("mobile_no")
        val mobileNumber: String,

        @SerializedName("birth_date")
        val birthDate: String,

        @SerializedName("address")
        val address: String,

        @SerializedName("children_count")
        val childrenCount: Int,

        @SerializedName("salary")
        val salary: Int,

        @SerializedName("no_of_cars")
        val noOfCars: Int,

        @SerializedName("nationality_id")
        val nationalityId: Int,

        @SerializedName("nationality")
        val nationality: Nationality,

        @SerializedName("marital_status_id")
        val maritalStatusId: Int,

        @SerializedName("marital_status")
        val maritalStatus: MaritalStatus,

        @SerializedName("employment_status")
        val employmentStatus: EmploymentStatus,

        @SerializedName("employment_status_id")
        val employmentStatusId: Int

//        @SerializedName("disabilities")
//        val disabilities:

)

data class Gender (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
)


data class Nationality (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
)

data class MaritalStatus (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
)

data class EmploymentStatus (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
)
