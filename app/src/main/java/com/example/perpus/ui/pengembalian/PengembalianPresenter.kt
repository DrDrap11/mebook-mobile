package com.example.perpus.ui.pengembalian

import android.util.Log
import com.example.perpus.model.ResponsePengembalian
import com.example.perpus.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengembalianPresenter {
    var view: PengembalianView

    constructor(view: PengembalianView) {
        this.view = view
    }

    fun getResponse(id:String) {
        val api = InitRetrofit().getInitInstance()
        api.getPengembalian(id).enqueue(object : Callback<ResponsePengembalian> {
            override fun onResponse(
                    call: Call<ResponsePengembalian>,
                    response: Response<ResponsePengembalian>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                        view.onSuccessPengembalian(jsonresponse)
                    } else {
                        view.onErrorResponse()
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePengembalian>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}