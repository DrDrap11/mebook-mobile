package com.example.perpus.model

data class ResponsePeminjamanDetail (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadPeminjamanDetail>? = null
)