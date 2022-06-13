package com.example.perpus.model

import com.google.gson.annotations.SerializedName

data class ResponsePostPengunjung (
    var status:Boolean,
    var message: String = "",
    var payload : String = ""
)