package com.example.perpus.model

data class ResponseBukuDetail (
    var status:Boolean,
    var message: String = "",
    var payload : PayloadBukuDetail
)