package com.example.perpus.model

data class ResponsePengunjungPersonal(
        val message: String,
        val payload: List<PayloadPengunjungPersonal>,
        val status: Boolean
)