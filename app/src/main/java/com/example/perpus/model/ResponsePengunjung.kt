package com.example.perpus.model

data class ResponsePengunjung (
    var status:Boolean,
    var message: String = "",
    var payload : PayloadPengunjung
)