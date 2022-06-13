package com.example.perpus.ui.pengembalian

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perpus.MainActivity
import com.example.perpus.R
import com.example.perpus.adapter.PeminjamanDetailAdapter
import com.example.perpus.model.PayloadPeminjamanDetail
import com.example.perpus.model.ResponsePostPeminjaman
import com.example.perpus.model.ResponsePostPengembalian
import com.example.perpus.network.InitRetrofit
import com.example.perpus.ui.detailpeminjaman.PeminjamanDetailPresenter
import com.example.perpus.ui.detailpeminjaman.PeminjamanDetailView
import com.example.perpus.utils.TmpData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class DialogPengembalianDetail : DialogFragment(), PeminjamanDetailView {

    private lateinit var peminjamanDetailPresenter: PeminjamanDetailPresenter
    private lateinit var peminjamanDetailAdapter: PeminjamanDetailAdapter
    private lateinit var recycler : RecyclerView
    private lateinit var btnKembalikan : Button
    private var idUser = ""
    private var idPinjam = ""
    private var tglPinjam = ""
    private var tglKembali = ""
    private var statusPinjam = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val android = inflater.inflate(R.layout.dialog_detail_pengembalian, container, false)
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val pref: SharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()

        peminjamanDetailPresenter = PeminjamanDetailPresenter(this)
        recycler = android.findViewById(R.id.recycler)
        btnKembalikan = android.findViewById(R.id.btnKembalikan)

        idPinjam = requireArguments().getString("id_pinjam").toString()
        tglPinjam = requireArguments().getString("tgl_pinjam").toString()
        tglKembali = requireArguments().getString("tgl_kembali").toString()
        statusPinjam = requireArguments().getString("status_pinjam").toString()

        peminjamanDetailPresenter.getResponseDetail(idPinjam!!)

//        if (statusPinjam == "1") {
//            btnKembalikan.visibility = View.VISIBLE
//        }else{
//            btnKembalikan.visibility = View.GONE
//        }

        btnKembalikan.setOnClickListener {
            dialogKembalikan()
        }
        return android
    }

    override fun onSuccessPeminjamanDetail(payloadPeminjamanDetail: ArrayList<PayloadPeminjamanDetail>?) {
        Log.d("datas", payloadPeminjamanDetail.toString())
        peminjamanDetailAdapter = PeminjamanDetailAdapter(requireContext(), payloadPeminjamanDetail)
        recycler?.adapter = peminjamanDetailAdapter
        recycler?.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
        )
    }

    override fun onErrorResponse() {

    }

    fun postPengembalian(){
        val api = InitRetrofit().getInitInstance()
        api.postPengembalian(idUser,tglPinjam,tglKembali,idPinjam ).enqueue(object :
                Callback<ResponsePostPengembalian> {
            override fun onResponse(
                    call: Call<ResponsePostPengembalian>,
                    response: Response<ResponsePostPengembalian>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    val i = Intent(requireContext(), MainActivity::class.java)
                    i.putExtra("intent", "2")
                    startActivity(i)
                    dialog?.cancel()
                } else {
                    Toast.makeText(
                            activity,
                            "Terjadi kesalahan, Gagal mengembalikan buku",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponsePostPengembalian>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }

    private fun dialogKembalikan() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_kembalikan)

        val pinjam = dialog.findViewById<Button>(R.id.btnPinjam)
        val batal = dialog.findViewById<Button>(R.id.btnBatal)

        dialog.show()

        pinjam.setOnClickListener {
            postPengembalian()
            dialog.dismiss()
        }

        batal.setOnClickListener {
            dialog.dismiss()
        }
    }


}