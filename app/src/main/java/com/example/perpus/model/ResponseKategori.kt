package com.example.perpus.model

import com.google.gson.annotations.SerializedName

data class ResponseKategori (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadKategori>? = null
)