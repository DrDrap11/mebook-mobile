package com.example.perpus.ui.kategori

import com.example.perpus.model.PayloadKategori

interface KategoriView {
    fun onSuccessKategori(payloadKategori: ArrayList<PayloadKategori>?)
    fun onErrorResponse()
}