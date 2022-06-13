package com.example.perpus.model

data class PayloadPengembalian(
    val id: String,
    val tgl_peminjaman: String,
    val tgl_kembali: String,
    val tgl_pengembalian: String,
    val id_mahasiswa: String,
    val pengembalian: String,
    val id_pengembalian: String,
    val denda: String,
    val status_kembali: String
)