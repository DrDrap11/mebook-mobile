package com.example.perpus.ui.detailpeminjaman

import android.util.Log
import com.example.perpus.model.ResponsePeminjamanDetail
import com.example.perpus.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeminjamanDetailPresenter {
    var view: PeminjamanDetailView

    constructor(view: PeminjamanDetailView) {
        this.view = view
    }

    fun getResponseDetail(id:String) {
        val api = InitRetrofit().getInitInstance()
        api.getPeminjamanDetail(id).enqueue(object : Callback<ResponsePeminjamanDetail> {
            override fun onResponse(
                    call: Call<ResponsePeminjamanDetail>,
                    response: Response<ResponsePeminjamanDetail>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                        view.onSuccessPeminjamanDetail(jsonresponse)
                    } else {
                        view.onErrorResponse()
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePeminjamanDetail>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
}