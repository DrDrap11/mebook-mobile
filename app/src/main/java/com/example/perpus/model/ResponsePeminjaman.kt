package com.example.perpus.model

import com.google.gson.annotations.SerializedName

data class ResponsePeminjaman (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadPeminjaman>? = null
)