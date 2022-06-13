package com.example.perpus.ui.buku

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.perpus.databinding.ActivityDetailBukuBinding
import com.example.perpus.model.ResponseBukuDetail
import com.example.perpus.network.InitRetrofit
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBukuActivity : AppCompatActivity() {
    private var binding: ActivityDetailBukuBinding? = null
    private var ids = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBukuBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        ids = intent.getStringExtra("id").toString()
        getResponse(ids)
    }

    fun getResponse(id:String) {
        val api = InitRetrofit().getInitInstance()
        val apiImg = InitRetrofit().getFolderImg()
        api.getBukuDetail(id).enqueue(object : Callback<ResponseBukuDetail> {
            override fun onResponse(
                    call: Call<ResponseBukuDetail>,
                    response: Response<ResponseBukuDetail>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                        binding!!.pb1.visibility = View.GONE
                        binding!!.tvJudulBuku.text = jsonresponse.judul_buku
                        binding!!.tvKategori.text = jsonresponse.nama_kategori
                        binding!!.tvStok.text = "Stok Buku ${jsonresponse.stok_buku}"
                        binding!!.tvPenerbit.text = jsonresponse.penerbit
                        binding!!.tvTahunTerbit.text = jsonresponse.tahun_terbit
                        binding!!.tvPengarang.text = jsonresponse.pengarang
                        binding!!.tvBahasa.text = jsonresponse.bahasa
                        binding!!.tvJumlahHalaman.text = jsonresponse.jumlah_halaman
                        binding!!.tvSinopsis.text = jsonresponse.sinopsis
                        Picasso.with(this@DetailBukuActivity).load("$apiImg${jsonresponse.gambar}").into(binding!!.ivFoto)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBukuDetail>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}