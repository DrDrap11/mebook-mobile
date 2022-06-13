package com.example.perpus.model

data class ResponsePengembalian (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadPengembalian>? = null
)