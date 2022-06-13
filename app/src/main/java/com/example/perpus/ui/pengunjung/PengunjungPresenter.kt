package com.example.perpus.ui.pengunjung

import android.util.Log
import com.example.perpus.model.PayloadPengunjungPersonal
import com.example.perpus.model.ResponseKategori
import com.example.perpus.model.ResponsePengunjungPersonal
import com.example.perpus.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengunjungPresenter {
    var view: PengunjungView

    constructor(view: PengunjungView) {
        this.view = view
    }

    fun getResponse(idmhs : String) {
        val api = InitRetrofit().getInitInstance()
        api.getRiwayatPengunjung(idmhs).enqueue(object : Callback<ResponsePengunjungPersonal> {
            override fun onResponse(call: Call<ResponsePengunjungPersonal>, response: Response<ResponsePengunjungPersonal>) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload

                    if (jsonresponse !=null) {
                          view.onSuccessPengunjung(jsonresponse as ArrayList<PayloadPengunjungPersonal>?)
                    } else {
                        view.onErrorResponse()
                    }
                }else{
                    view.onErrorResponse()
                }
            }

            override fun onFailure(call: Call<ResponsePengunjungPersonal>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}