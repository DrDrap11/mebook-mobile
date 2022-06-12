package com.example.perpus.ui.detailpeminjaman

import com.example.perpus.model.PayloadPeminjamanDetail

interface PeminjamanDetailView {
    fun onSuccessPeminjamanDetail(payloadPeminjamanDetail: ArrayList<PayloadPeminjamanDetail>?)
    fun onErrorResponse()
}