package com.example.perpus.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.perpus.R
import com.example.perpus.databinding.FragmentHomeBinding
import com.example.perpus.model.ResponsePengunjung
import com.example.perpus.network.InitRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentHome : Fragment(R.layout.fragment_home) {
    private var bindings: FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        bindings = binding

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            bindings!!.swlayout.visibility = View.VISIBLE
            loadData()
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData () {
        val api = InitRetrofit().getInitInstance()
        api.getPengunjung().enqueue(object : Callback<ResponsePengunjung> {
            override fun onResponse(
                    call: Call<ResponsePengunjung>,
                    response: Response<ResponsePengunjung>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (jsonresponse !=null) {
                        bindings!!.tvHariIni.text = jsonresponse.hari_ini
                        bindings!!.tvKemarin.text = jsonresponse.kemarin
                        bindings!!.tvTotal.text = jsonresponse.total
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePengunjung>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }

}