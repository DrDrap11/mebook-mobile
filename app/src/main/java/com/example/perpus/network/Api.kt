package com.example.perpus.network

import com.example.perpus.model.*
import retrofit2.Call
import retrofit2.http.*


interface Api {

    @FormUrlEncoded
    @POST("Login/login_mahasiswa")
    fun login(
        @Field("user") user: String,
        @Field("pass") pass: String,
        @Field("token") token: String
    ): Call<ResponseLogin>

    @GET("Buku/buku_filter")
    fun getBuku(
            @Query("k") kategori: String?,
            @Query("j") judul: String?
    ): Call<ResponseBuku>

    @GET("Buku/buku_detail/{id}")
    fun getBukuDetail(
            @Path("id") id: String?
    ): Call<ResponseBukuDetail>

    @GET("Kategori/kategori")
    fun getKategori(): Call<ResponseKategori>

//    @FormUrlEncoded
//    @POST("Peminjaman/peminjaman")
//    fun postPeminjaman(
//            @Field("id_mahasiswa") id_mahasiswa: String,
//            @Field("tgl_peminjaman") tgl_peminjaman: String,
//            @Field("tgl_pengembalian") tgl_pengembalian: String,
//            @Field("id_buku[]") id_buku : ArrayList<String>
//    ): Call<ResponsePostPeminjaman>

    @FormUrlEncoded
    @POST("Peminjaman/peminjaman")
    fun postPeminjaman(
            @Field("id_mahasiswa") id_mahasiswa: String,
            @Field("id_buku[]") id_buku : ArrayList<String>
    ): Call<ResponsePostPeminjaman>

    @GET("Peminjaman/{id}")
    fun getPeminjaman(
            @Path("id") id: String?
    ): Call<ResponsePeminjaman>

    @GET("Peminjaman/detail/{id}")
    fun getPeminjamanDetail(
            @Path("id") id: String?
    ): Call<ResponsePeminjamanDetail>

    @FormUrlEncoded
    @POST("Pengunjung/pengunjung")
    fun postPengunjung(
            @Field("id_mahasiswa") id_mahasiswa: String,
            @Field("qr") qr: String
    ): Call<ResponsePostPengunjung>

    @GET("Pengembalian/{id}")
    fun getPengembalian(
            @Path("id") id: String?
    ): Call<ResponsePengembalian>

    @FormUrlEncoded
    @POST("pengembalian/pengembalian")
    fun postPengembalian(
            @Field("id_mahasiswa") id_mahasiswa: String,
            @Field("tgl_peminjaman") tgl_peminjaman : String,
            @Field("tgl_kembali") tgl_kembali : String,
            @Field("pengembalian") pengembalian : String,
    ): Call<ResponsePostPengembalian>

    @GET("Pengunjung")
    fun getPengunjung(
    ): Call<ResponsePengunjung>

    @GET("pengunjung/pengunjunglist//{idmhs}")
    fun getRiwayatPengunjung(
            @Path("idmhs") idmhs: String?
    ): Call<ResponsePengunjungPersonal>

    @GET("Buku/bukuqr/{qr}")
    fun getQRBuku(
            @Path("qr") qr: String?
    ): Call<ResponseBukuDetail>
}