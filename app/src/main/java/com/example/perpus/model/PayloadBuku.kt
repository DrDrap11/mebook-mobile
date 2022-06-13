package com.example.perpus.model

data class PayloadBuku(
    val id: String,
    val judul_buku: String,
    val pengarang: String,
    val penerbit: String,
    val tahun_terbit: String,
    val gambar: String,
    val jumlah_halaman: String,
    val stok_buku: String,
    val bahasa: String,
    val sinopsis: String,
    val kategori: String,
    val id_kategori: String,
    val nama_kategori: String
)