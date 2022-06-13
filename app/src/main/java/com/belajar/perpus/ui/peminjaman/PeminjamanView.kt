package com.example.perpus.ui.peminjaman

import com.example.perpus.model.PayloadPeminjaman

interface PeminjamanView {
    fun onSuccessPeminjaman(payloadPeminjaman: ArrayList<PayloadPeminjaman>?)
    fun onErrorResponse()
}