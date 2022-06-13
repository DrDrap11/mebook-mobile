package com.example.perpus.ui.peminjaman

import android.util.Log
import com.example.perpus.model.ResponsePeminjaman
import com.example.perpus.model.ResponsePeminjamanDetail
import com.example.perpus.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeminjamanPresenter {
    var view: PeminjamanView

    constructor(view: PeminjamanView) {
        this.view = view
    }

    fun getResponse(id:String) {
        val api = InitRetrofit().getInitInstance()
        api.getPeminjaman(id).enqueue(object : Callback<ResponsePeminjaman> {
            override fun onResponse(
                    call: Call<ResponsePeminjaman>,
                    response: Response<ResponsePeminjaman>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                          view.onSuccessPeminjaman(jsonresponse)
                    } else {
                        view.onErrorResponse()
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePeminjaman>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}