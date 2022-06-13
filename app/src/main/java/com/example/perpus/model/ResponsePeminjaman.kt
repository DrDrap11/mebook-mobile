package com.example.perpus.model

data class ResponsePeminjaman (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadPeminjaman>? = null
)