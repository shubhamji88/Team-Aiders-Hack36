package com.example.healthmonitor.network.Response


import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("channel")
    val channel: Channel?,
    @SerializedName("feeds")
    val feeds: List<Feed?>?
)