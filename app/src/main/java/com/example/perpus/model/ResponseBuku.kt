package com.example.perpus.model

import com.google.gson.annotations.SerializedName

data class ResponseBuku (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadBuku>? = null
)