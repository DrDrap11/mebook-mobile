package com.example.perpus.ui.buku

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.perpus.MainActivity
import com.example.perpus.R
import com.example.perpus.databinding.ActivityQrcodeBinding
import com.example.perpus.model.ResponseBukuDetail
import com.example.perpus.model.ResponsePostPeminjaman
import com.example.perpus.network.InitRetrofit
import com.example.perpus.utils.TmpData
import me.dm7.barcodescanner.zxing.ZXingScannerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityScanQRBuku : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var binding: ActivityQrcodeBinding? = null
    private lateinit var mZingview : ZXingScannerView
    private var idUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
//        supportActionBar!!.hide()

        val pref: SharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()
        mZingview = findViewById(R.id.qrView)

    }

    public override fun onResume() {
        super.onResume()
        Log.i("QRCODE : ", "2")
        mZingview.setResultHandler(this@ActivityScanQRBuku)
        mZingview.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        Log.i("QRCODE : ", "3")
        mZingview.stopCamera()
        mZingview.stopCameraPreview()
    }

    override fun handleResult(rawResult: Result?) {
        val execute = object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                Log.v("TAG", rawResult?.text.toString())
                Log.v("TAG", rawResult?.barcodeFormat.toString())


//        Toast.makeText(this, rawResult?.text.toString()+" - "+rawResult?.barcodeFormat.toString(), Toast.LENGTH_LONG).show()
                mZingview.stopCameraPreview()

                return null
            }

            override fun onPostExecute(result: Void?) {
//                Toast.makeText(this@ActivityScanQRBuku, rawResult?.text.toString(), Toast.LENGTH_LONG).show()
//                postPengunjung(rawResult?.text.toString())
                mZingview.stopCamera()
                mZingview.stopCameraPreview()
                getBuku(rawResult?.text.toString())
            }

            override fun onPreExecute() {
                super.onPreExecute()
                binding?.pb?.visibility = View.VISIBLE
            }


        }.execute()
    }

    private fun getBuku(qr: String) {
        val api = InitRetrofit().getInitInstance()
        api.getQRBuku(qr).enqueue(object : Callback<ResponseBukuDetail> {
            override fun onResponse(call: Call<ResponseBukuDetail>, response: Response<ResponseBukuDetail>) {
                Log.d("LogQR", response.toString())
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if(jsonresponse?.qrcode == qr) {
                        if (jsonresponse.stok_buku!!.toInt() > 0) {
                            if (TmpData.buku.contains(jsonresponse.id!!)) {
                                TmpData.buku.remove(jsonresponse.id)
                            } else {
                                TmpData.buku!!.add(jsonresponse.id!!)
                                dialogSetuju()
                            }
                        } else {
                            mZingview.setResultHandler(this@ActivityScanQRBuku)
                            mZingview.startCamera()
                            Toast.makeText(this@ActivityScanQRBuku, "Stok Buku Kosong", Toast.LENGTH_LONG).show()
                            binding?.pb?.visibility = View.GONE
                        }
                    }else{
                        mZingview.setResultHandler(this@ActivityScanQRBuku)
                        mZingview.startCamera()
                        Toast.makeText(this@ActivityScanQRBuku, "Tidak ditemukan", Toast.LENGTH_LONG).show()
                        binding?.pb?.visibility = View.GONE
                    }
                }else{
                    mZingview.setResultHandler(this@ActivityScanQRBuku)
                    mZingview.startCamera()
                    Toast.makeText(this@ActivityScanQRBuku, "Tidak ditemukan", Toast.LENGTH_LONG).show()
                    binding?.pb?.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ResponseBukuDetail>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
                finish()
            }
        })
    }

    fun postPeminjaman(){
        val api = InitRetrofit().getInitInstance()
        api.postPeminjaman(idUser, TmpData.buku).enqueue(object :
                Callback<ResponsePostPeminjaman> {
            @SuppressLint("CommitPrefEdits")
            override fun onResponse(call: Call<ResponsePostPeminjaman>, response: Response<ResponsePostPeminjaman>) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    TmpData.buku.clear()
                    TmpData.kategori = ""

                    val i = Intent(this@ActivityScanQRBuku, MainActivity::class.java)
                    i.putExtra("intent", "1")
                    startActivity(i)
                } else {
                    Toast.makeText(this@ActivityScanQRBuku, "Gagal meminjam", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponsePostPeminjaman>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }

    private fun dialogSetuju() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_setuju)

        val pinjam = dialog.findViewById<Button>(R.id.btnPinjam)
        val batal = dialog.findViewById<Button>(R.id.btnBatal)

        dialog.show()

        pinjam.setOnClickListener {
            postPeminjaman()
            dialog.dismiss()
        }

        batal.setOnClickListener {
            mZingview.setResultHandler(this@ActivityScanQRBuku)
            mZingview.startCamera()
            binding?.pb?.visibility = View.GONE
            TmpData.buku.clear()
            TmpData.kategori = ""
            dialog.dismiss()
        }
    }

//    private fun dialogBuku(payload: PayloadBukuDetail?) {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(R.layout.dialog_buku_qr)
//
//        val btnBatal = dialog.findViewById<Button>(R.id.btnBatal)
//        val btnPinjam = dialog.findViewById<Button>(R.id.btnPinjam)
//        val ivBuku = dialog.findViewById<ImageView>(R.id.ivBuku)
//        val tvJudulBuku = dialog.findViewById<TextView>(R.id.tvJudulBuku)
//
//        dialog.show()
//
//        tvJudulBuku.text = payload?.judul_buku
////        Picasso.with(this@ActivityScanQRBuku).load("${InitRetrofit().getFolderImg()}${payload?.gambar}").into(ivBuku)
//
//        btnBatal.setOnClickListener {
//            mZingview.setResultHandler(this@ActivityScanQRBuku)
//            mZingview.startCamera()
//            binding?.pb?.visibility = View.GONE
//            dialog.dismiss()
//        }
//
//        btnPinjam.setOnClickListener {
//
//        }
//    }
}