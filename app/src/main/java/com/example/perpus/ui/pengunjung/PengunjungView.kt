package com.example.perpus.ui.pengunjung

import com.example.perpus.model.PayloadPengunjungPersonal

interface PengunjungView {
    fun onSuccessPengunjung(payloadPengunjungPersonal: ArrayList<PayloadPengunjungPersonal>?)
    fun onErrorResponse()
}