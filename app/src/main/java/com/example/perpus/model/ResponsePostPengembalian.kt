package com.example.perpus.model

data class ResponsePostPengembalian (
    var status:Boolean,
    var message: String = "",
    var payload : String = ""
)