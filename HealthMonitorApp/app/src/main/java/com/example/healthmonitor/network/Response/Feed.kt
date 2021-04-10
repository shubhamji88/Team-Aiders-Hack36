package com.example.healthmonitor.network.Response


import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("entry_id")
    val entryId: Int?,
    @SerializedName("field1")
    val field1: String?,
    @SerializedName("field2")
    val field2: String?
)