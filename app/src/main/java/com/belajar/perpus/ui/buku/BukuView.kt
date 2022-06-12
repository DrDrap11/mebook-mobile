package com.example.perpus.ui.buku

import com.example.perpus.model.PayloadBuku

interface BukuView {
    fun onSuccessBuku(payloadBuku: ArrayList<PayloadBuku>?)
    fun onErrorResponse()
}