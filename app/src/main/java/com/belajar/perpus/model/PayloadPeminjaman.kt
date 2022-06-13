package com.example.perpus.model

data class PayloadPeminjaman(
    val id: String,
    val tgl_peminjaman: String,
    val tgl_kembali: String,
    val id_mahasiswa: String,
    val peminjaman: String,
    val id_peminjaman: String,
    val status_pinjam: String,
    val alasan: String
)