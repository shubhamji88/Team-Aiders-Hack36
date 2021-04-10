package com.example.healthmonitor.network.Response


import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("field1")
    val field1: String?,
    @SerializedName("field2")
    val field2: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("last_entry_id")
    val lastEntryId: Int?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)