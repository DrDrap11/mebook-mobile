package com.example.perpus.ui.pengembalian

import com.example.perpus.model.PayloadPengembalian

interface PengembalianView {
    fun onSuccessPengembalian(payloadPengembalian: ArrayList<PayloadPengembalian>?)
    fun onErrorResponse()
}