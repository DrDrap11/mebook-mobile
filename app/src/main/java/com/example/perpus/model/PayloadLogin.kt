package com.example.perpus.model

data class PayloadLogin(
    val id: String,
    val nim: String,
    val nama: String,
    val kelamin: String,
    val agama: String,
    val tempat_lahir: String,
    val tanggal_lahir: String,
    val alamat: String,
    val no_telp: String,
    val password: String,
    val token: String
)