package com.example.perpus.ui.peminjaman

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perpus.R
import com.example.perpus.adapter.PeminjamanAdapter
import com.example.perpus.databinding.FragmentPeminjamanBinding
import com.example.perpus.model.*

class FragmentPeminjaman : Fragment(R.layout.fragment_peminjaman), PeminjamanView {
    private var bindings: FragmentPeminjamanBinding? = null
    private lateinit var peminjamanPresenter: PeminjamanPresenter
    private lateinit var peminjamanAdapter: PeminjamanAdapter
    private var idUser = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPeminjamanBinding.bind(view)
        bindings = binding

        binding!!.recycler.isFocusable = false
        val pref: SharedPreferences = requireActivity().getSharedPreferences(
            "login",
            Context.MODE_PRIVATE
        )

        idUser = pref.getString("code", null).toString()
        peminjamanPresenter = PeminjamanPresenter(this)

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            peminjamanPresenter.getResponse(idUser)
        }
    }

    override fun onStart() {
        super.onStart()
        peminjamanPresenter.getResponse(idUser)
    }

    override fun onSuccessPeminjaman(payloadPeminjaman: ArrayList<PayloadPeminjaman>?) {
        peminjamanAdapter = PeminjamanAdapter(requireContext(), payloadPeminjaman)
        bindings?.recycler?.adapter = peminjamanAdapter
        bindings?.recycler?.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
        )
    }


    override fun onErrorResponse() {
        Toast.makeText(
            requireContext(),
            "Data Tidak Ditemukan",
            Toast.LENGTH_SHORT
        ).show()
    }

//    fun postPeminjaman(){
//        val api = InitRetrofit().getInitInstance()
//        api.postPeminjaman(idUser, TmpData.buku).enqueue(object :
//                Callback<ResponsePostPeminjaman> {
//            @SuppressLint("CommitPrefEdits")
//            override fun onResponse(
//                    call: Call<ResponsePostPeminjaman>,
//                    response: Response<ResponsePostPeminjaman>
//            ) {
//                if (response.isSuccessful) {
//                    val jsonresponse = response.body()?.payload
//                    bindings!!.search.text.clear()
//                    TmpData.buku.clear()
//                    TmpData.kategori = ""
//                    bindings!!.search.text.toString() == ""
//                    kategoriPresenter.getResponse()
//                    bukuPresenter.getResponse(bindings!!.search.text.toString(), TmpData.kategori)
//                } else {
//                    Toast.makeText(
//                            activity,
//                            "Gagal meminjam",
//                            Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponsePostPeminjaman>, error: Throwable) {
//                Log.e("android1", "errornya ${error.message}")
//            }
//        })
//    }

//    private fun dialogSetuju() {
//        val dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(R.layout.dialog_setuju)
//
//        val pinjam = dialog.findViewById<Button>(R.id.btnPinjam)
//        val batal = dialog.findViewById<Button>(R.id.btnBatal)
//
//        dialog.show()
//
//        pinjam.setOnClickListener {
//            postPeminjaman()
//            dialog.dismiss()
//        }
//
//        batal.setOnClickListener {
//            dialog.dismiss()
//        }
//    }

}