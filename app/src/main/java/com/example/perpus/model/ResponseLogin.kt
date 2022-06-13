package com.example.perpus.model

data class ResponseLogin (
    var status:Boolean,
    var message: String = "",
    var payload : PayloadLogin
)