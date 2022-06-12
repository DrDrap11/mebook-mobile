package com.example.perpus.ui.buku

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perpus.MainActivity
import com.example.perpus.R
import com.example.perpus.adapter.BukuAdapter
import com.example.perpus.adapter.KategoriAdapter
import com.example.perpus.databinding.FragmentBukuBinding
import com.example.perpus.model.PayloadBuku
import com.example.perpus.model.PayloadKategori
import com.example.perpus.model.ResponseLogin
import com.example.perpus.model.ResponsePostPeminjaman
import com.example.perpus.network.InitRetrofit
import com.example.perpus.ui.kategori.KategoriPresenter
import com.example.perpus.ui.kategori.KategoriView
import com.example.perpus.utils.TmpData
import com.example.perpus.utils.ViewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FragmentBuku : Fragment(R.layout.fragment_buku), BukuView, KategoriView, ViewData {
    private var bindings: FragmentBukuBinding? = null

    private lateinit var bukuPresenter: BukuPresenter
    private lateinit var bukuAdapter: BukuAdapter
    private lateinit var kategoriPresenter: KategoriPresenter
    private lateinit var kategoriAdapter: KategoriAdapter

    private var idUser = ""
    private var strTglPeminjaman = ""
    private var strTglPengembalian = ""
    var myCalendar: Calendar? = null
    var date: DatePickerDialog.OnDateSetListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBukuBinding.bind(view)
        bindings = binding

        binding!!.recycler.isFocusable = false
        val pref: SharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)

        idUser = pref.getString("code", null).toString()
        bukuPresenter = BukuPresenter(this)
        kategoriPresenter = KategoriPresenter(this)

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            bindings!!.swlayout.visibility = View.VISIBLE

            bindings!!.search.text.clear()
            bindings!!.search.text.toString() == ""
            TmpData.buku.clear()
            TmpData.kategori = ""

            kategoriPresenter.getResponse()
            bukuPresenter.getResponse(bindings!!.search.text.toString(), TmpData.kategori)
        }

        bindings!!.btnSearch.setOnClickListener {
            bukuPresenter.getResponse(bindings!!.search.text.toString(), TmpData.kategori)
        }

        bindings!!.btnPinjam.setOnClickListener {
            if (TmpData.buku.isEmpty()) {
                Toast.makeText(requireContext(), "Harap pilih buku yang akan dipinjam dengan cara tekan dan tahan item buku", Toast.LENGTH_LONG).show()
            }else{
//                Toast.makeText(requireContext(), TmpData.buku.toString(), Toast.LENGTH_LONG).show()
                dialogSetuju()
//                val fm = activity?.supportFragmentManager
//                val p = DialogPinjamBuku()
//                fm?.let { it1 -> p.show(it1, "") }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindings!!.search.text.clear()
        TmpData.buku.clear()
        TmpData.kategori = ""
        bindings!!.search.text.toString() == ""
        kategoriPresenter.getResponse()
        bukuPresenter.getResponse(bindings!!.search.text.toString(), TmpData.kategori)

    }

    override fun onSuccessBuku(payloadBuku: ArrayList<PayloadBuku>?) {
        bukuAdapter = BukuAdapter(requireContext(), payloadBuku)
        bindings?.recycler?.adapter = bukuAdapter
        bindings?.recycler?.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onSuccessKategori(payloadKategori: ArrayList<PayloadKategori>?) {
        kategoriAdapter = KategoriAdapter(requireContext(), this, payloadKategori)
        bindings?.recyclerkategori?.adapter = kategoriAdapter
        bindings?.recyclerkategori?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,
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

    override fun sendData(data: String) {
        TmpData.kategori = data
        kategoriPresenter.getResponse()
        bukuPresenter.getResponse(bindings!!.search.text.toString(), data)
    }

    fun postPeminjaman(){
        val api = InitRetrofit().getInitInstance()
        api.postPeminjaman(idUser, TmpData.buku).enqueue(object :
                Callback<ResponsePostPeminjaman> {
            @SuppressLint("CommitPrefEdits")
            override fun onResponse(
                    call: Call<ResponsePostPeminjaman>,
                    response: Response<ResponsePostPeminjaman>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    bindings!!.search.text.clear()
                    TmpData.buku.clear()
                    TmpData.kategori = ""
                    bindings!!.search.text.toString() == ""
                    kategoriPresenter.getResponse()
                    bukuPresenter.getResponse(bindings!!.search.text.toString(), TmpData.kategori)

                    val i = Intent(requireContext(), MainActivity::class.java)
                    i.putExtra("intent", "1")
                    startActivity(i)
                } else {
                    Toast.makeText(
                            activity,
                            "Gagal meminjam",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponsePostPeminjaman>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
            }
        })
    }
//
    private fun dialogSetuju() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_setuju)

        val pinjam = dialog.findViewById<Button>(R.id.btnPinjam)
        val batal = dialog.findViewById<Button>(R.id.btnBatal)
//        val tvTglPeminjaman = dialog.findViewById<TextView>(R.id.tvTglPeminjaman)
//        val tvTglPengembalian = dialog.findViewById<TextView>(R.id.tvTglPengembalian)

        dialog.show()

//        tvTglPeminjaman.setOnClickListener {
//            DatePickerDialog(
//                requireContext(), date, myCalendar!!
//                    .get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
//                myCalendar!!.get(Calendar.DAY_OF_MONTH)
//            ).show()
//
//            myCalendar = Calendar.getInstance()
//            date =
//                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
//                    myCalendar!!.set(Calendar.YEAR, year)
//                    myCalendar!!.set(Calendar.MONTH, monthOfYear)
//                    myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//                    val myFormat = "yyyy-MM-dd"
//                    val sdf = SimpleDateFormat(myFormat, Locale.US)
//                    strTglPeminjaman = sdf.format(myCalendar!!.time)
//                    tvTglPeminjaman.text = strTglPeminjaman
//                }
//        }
//
//        tvTglPeminjaman.setOnClickListener {
//            val myFormat = "yyyy-MM-dd"
//            val sdf = SimpleDateFormat(myFormat, Locale.US)
//            strTglPeminjaman = sdf.format(myCalendar!!.time)
//            tvTglPengembalian.text = strTglPengembalian
//            Log.d("jam", sdf.format(myCalendar!!.time))
//        }


        pinjam.setOnClickListener {
            postPeminjaman()
            dialog.dismiss()
        }

        batal.setOnClickListener {
            dialog.dismiss()
        }
    }

}