package com.example.perpus.ui.buku

import android.util.Log
import com.example.perpus.model.ResponseBuku
import com.example.perpus.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BukuPresenter {
    var view: BukuView

    constructor(view: BukuView) {
        this.view = view
    }

    fun getResponse(judul:String, kategori:String) {
        val api = InitRetrofit().getInitInstance()
        api.getBuku(kategori,judul).enqueue(object : Callback<ResponseBuku> {
            override fun onResponse(
                    call: Call<ResponseBuku>,
                    response: Response<ResponseBuku>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload

                    if (jsonresponse !=null) {
                          view.onSuccessBuku(jsonresponse)
                    } else {
                        view.onErrorResponse()
                    }
                }else{
                    view.onErrorResponse()
                }
            }

            override fun onFailure(call: Call<ResponseBuku>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}