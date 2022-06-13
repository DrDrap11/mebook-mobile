package com.example.perpus.model

import com.google.gson.annotations.SerializedName

data class ResponsePengunjung (
    var status:Boolean,
    var message: String = "",
    var payload : PayloadPengunjung
)