package com.example.perpus.model

import com.google.gson.annotations.SerializedName

data class ResponsePengembalian (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadPengembalian>? = null
)