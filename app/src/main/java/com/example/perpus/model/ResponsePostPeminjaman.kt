package com.example.perpus.model

data class ResponsePostPeminjaman (
    var status:Boolean,
    var message: String = "",
    var payload : String = ""
)