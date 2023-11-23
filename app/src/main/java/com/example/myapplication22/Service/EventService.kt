package com.example.myapplication22.Service



import com.example.myapplication22.Models.Task
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface EventService {

    data class EventResponse(
        @SerializedName("event")
        val evenement: Task
    )

    data class EventsResponse(
        @SerializedName("events")
        val evenements: List<Task>
    )

    data class TaskBody(val title: String, val time: String,
                       val  date:String,val description: String)


    @POST("Event/add")
    fun addPost(@Body taskBody: TaskBody): Call<EventResponse>

    @GET("Event/findByDate/{date}")
    fun getEventsByDate(@Path("date") date: String): Call<EventsResponse>

    @DELETE("Event/delete/{eventId}")
    fun deleteEvent(@Path("eventId") eventId: String): Call<Void>


}