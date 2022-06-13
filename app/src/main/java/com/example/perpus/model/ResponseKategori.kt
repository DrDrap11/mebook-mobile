package com.example.perpus.model

data class ResponseKategori (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadKategori>? = null
)