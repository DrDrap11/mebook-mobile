package com.example.perpus.ui.pengembalian

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perpus.R
import com.example.perpus.adapter.PengembalianAdapter
import com.example.perpus.databinding.FragmentPeminjamanBinding
import com.example.perpus.model.*

class FragmentPengembalian : Fragment(R.layout.fragment_peminjaman), PengembalianView {
    private var bindings: FragmentPeminjamanBinding? = null
    private lateinit var pengembalianPresenter: PengembalianPresenter
    private lateinit var pengembalianAdapter: PengembalianAdapter
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
        pengembalianPresenter = PengembalianPresenter(this)

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            pengembalianPresenter.getResponse(idUser)
        }
    }

    override fun onStart() {
        super.onStart()
        pengembalianPresenter.getResponse(idUser)
    }

    override fun onSuccessPengembalian(payloadPengembalian: ArrayList<PayloadPengembalian>?) {
        pengembalianAdapter = PengembalianAdapter(requireContext(), payloadPengembalian)
        bindings?.recycler?.adapter = pengembalianAdapter
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