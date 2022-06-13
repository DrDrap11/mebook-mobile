package com.example.perpus.model

data class ResponseBuku (
    var status:Boolean,
    var message: String = "",
    var payload: ArrayList<PayloadBuku>? = null
)