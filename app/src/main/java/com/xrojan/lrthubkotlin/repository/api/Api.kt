package com.xrojan.lrthubkotlin.repository.api

import com.xrojan.lrthubkotlin.credentials.Credentials.Companion.apiKey
import com.xrojan.lrthubkotlin.repository.entities.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by Joshua de Guzman on 10/07/2018.
 */

interface UserApi {
    @FormUrlEncoded
    @POST("/api/v1/token/generate")
    fun loginUser(@Field("username") username: String,
                  @Field("password") password: String): Observable<Request<User>>

    @FormUrlEncoded
    @POST("/api/v1/users/create/")
    fun registerUser(@Field("username") username: String,
                     @Field("password") password: String,
                     @Field("email") email: String): Observable<Request<User>>

    @GET("/api/v1/users/profile/")
    fun getUserDetail(
            @Header("Api-Key") apiKey: String,
            @Header("Authorization") token: String,
            @Query("user_id") userId: Int): Observable<RequestArray<List<UserProfile>>>

    @GET("/api/v1/users/profile/options/gender/")
    fun getGenders(
            @Header("Api-Key") apiKey: String,
            @Header("Authorization") token: String): Observable<RequestArray<List<Gender>>>

}

interface FeedApi {
    @GET("/api/v1/feeds/all/")
    fun getFeeds(
            @Header("Api-Key") apiKey: String,
            @Query("search") search: String): Observable<RequestArray<List<Feed>>>

    @GET("/api/v1/feeds/")
    fun getFeeds(
            @Header("Api-Key") apiKey: String,
            @Query("is_featured") isFeatured: Boolean): Observable<RequestArray<List<Feed>>>
}

interface ChatbotApi {
    @POST("https://lrthub.azurewebsites.net/qnamaker/knowledgebases/43941e99-7626-464a-997d-e49e2b598eb3/generateAnswer")
    fun queryChatbot(@Header("Authorization") authorization: String,
                     @Header("Content-Type") contentType: String,
                     @Body question: Question): Observable<Answers>
}

interface FeedbackApi {
    @GET("/api/v1/feedback/")
    fun getFeedbackConversations(@Header("Authorization") authorization: String): Observable<RequestArray<List<FeedbackConversation>>>

    @GET("/api/v1/feedback/{id}/")
    fun getFeedbackConversation(@Header("Authorization") authorization: String,
                                @Header("Api-Key") apiKey: String,
                                @Path("id") id: Int): Observable<Request<FeedbackConversation>>

    @FormUrlEncoded
    @POST("/api/v1/feedback/create/")
    fun sendFeedbackConversation(@Header("Authorization") authorization: String,
                                 @Header("Api-Key") apiKey: String,
                                 @Field("full_name") fullName: String,
                                 @Field("address") address: String,
                                 @Field("contact_number") contactNumber: String,
                                 @Field("employee_name") employeeName: String,
                                 @Field("incident_date") incidentDate: String,
                                 @Field("incident_subject") incidentSubject: String,
                                 @Field("other_details") otherDetails: String,
                                 @Field("sender_id") senderId: Int,
                                 @Field("receiver_id") receiverId: Int): Observable<Request<FeedbackConversation>>
}

interface AdApi {
    @GET("/api/v1/ads/")
    fun getAds(@Header("Api-Key") apiKey: String): Observable<RequestArray<List<Ad>>>
}

interface TrainCheckApi {
    @GET("/api/v1/traincheck/")
    fun getTrainCheckFeeds(@Header("Api-Key") apiKey: String): Observable<Request<List<TrainCheckHistory>>>
}