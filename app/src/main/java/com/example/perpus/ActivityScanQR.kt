package com.example.perpus

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.perpus.databinding.ActivityQrcodeBinding
import com.example.perpus.model.ResponsePostPengunjung
import com.example.perpus.network.InitRetrofit
import me.dm7.barcodescanner.zxing.ZXingScannerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityScanQR : AppCompatActivity(), ZXingScannerView.ResultHandler {
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
        mZingview.setResultHandler(this@ActivityScanQR)
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
//                absensiPresenter.postAbsensi(rawResult?.text.toString(), "hadir", "")
//                Toast.makeText(this@ActivityScanQR, rawResult?.text.toString(), Toast.LENGTH_LONG).show()
                postPengunjung(rawResult?.text.toString())
            }

            override fun onPreExecute() {
                super.onPreExecute()
                binding?.pb?.visibility = View.VISIBLE
            }


        }.execute()
    }

    private fun postPengunjung(qr: String) {
        val api = InitRetrofit().getInitInstance()
        api.postPengunjung(idUser, qr).enqueue(object : Callback<ResponsePostPengunjung> {
            override fun onResponse(
                call: Call<ResponsePostPengunjung>,
                response: Response<ResponsePostPengunjung>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    Toast.makeText(this@ActivityScanQR, "Berhasil", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponsePostPengunjung>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
                finish()
            }
        })
    }


}