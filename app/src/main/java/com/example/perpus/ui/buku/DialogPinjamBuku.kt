package com.example.perpus.ui.buku

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.perpus.R
import java.text.SimpleDateFormat
import java.util.*

class DialogPinjamBuku : DialogFragment() {

    var dataList = ArrayList<String>()
    var myCalendar: Calendar? = null
    var datePeminjaman: DatePickerDialog.OnDateSetListener? = null
    var datePengembalian: DatePickerDialog.OnDateSetListener? = null
    var strTglPeminjaman = ""
    var strTglPengembalian = ""
    var strId = ""

    private lateinit var tvTglPeminjaman:TextView
    private lateinit var tvTglPengembalian:TextView
    private lateinit var btnSetuju:Button
    private lateinit var btnBatal:Button
    private lateinit var pDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?,): View? {
        val android = inflater.inflate(R.layout.dialog_setuju, container, false)
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val pref: SharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        strId = pref.getString("code", null).toString()

        tvTglPeminjaman = android.findViewById(R.id.tvTglPeminjaman)
        tvTglPengembalian = android.findViewById(R.id.tvTglPengembalian)

        btnSetuju = android.findViewById(R.id.btnPinjam)
        btnBatal = android.findViewById(R.id.btnBatal)

        iniTgl()

        tvTglPeminjaman.setOnClickListener {
            DatePickerDialog(
                requireContext(), datePeminjaman, myCalendar!!
                    .get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        tvTglPengembalian.setOnClickListener {
            DatePickerDialog(
                requireContext(), datePengembalian, myCalendar!!
                    .get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnSetuju.setOnClickListener {
            when {
                strTglPeminjaman == "" -> {
                    Toast.makeText(requireContext(), "Pilih Tanggal terlebih dahulu", Toast.LENGTH_LONG).show()
                }
                strTglPengembalian == "" -> {
                    Toast.makeText(requireContext(), "Pilih Tanggal terlebih dahulu", Toast.LENGTH_LONG).show()
                }
                else -> {
//                    postPeminjaman()
//                    buatJadwal().execute()
                }
            }
        }

        btnBatal.setOnClickListener {
            dialog!!.cancel()
        }

        return android
    }

    fun updateTglPeminjaman () {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        strTglPeminjaman = sdf.format(myCalendar!!.time)
        tvTglPeminjaman.text = strTglPeminjaman
        Log.d("jam", sdf.format(myCalendar!!.time))
    }

    fun updateTglPengembalian () {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        strTglPengembalian = sdf.format(myCalendar!!.time)
        tvTglPengembalian.text = strTglPengembalian
        Log.d("jam", sdf.format(myCalendar!!.time))
    }

    fun iniTgl() {
        myCalendar = Calendar.getInstance()
        datePeminjaman =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateTglPeminjaman()
            }

        datePengembalian =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateTglPengembalian()
            }
    }

//    fun postPeminjaman(){
//        val api = InitRetrofit().getInitInstance()
//        api.postPeminjaman(strId, strTglPeminjaman,strTglPengembalian, TmpData.buku).enqueue(object :
//            Callback<ResponsePostPeminjaman> {
//            @SuppressLint("CommitPrefEdits")
//            override fun onResponse(
//                call: Call<ResponsePostPeminjaman>,
//                response: Response<ResponsePostPeminjaman>
//            ) {
//                if (response.isSuccessful) {
//                    val jsonresponse = response.body()?.payload
//                    TmpData.buku.clear()
//                    TmpData.kategori = ""
//
//                    val i = Intent(requireContext(), MainActivity::class.java)
//                    i.putExtra("intent", "1")
//                    startActivity(i)
//                } else {
//                    Toast.makeText(
//                        activity,
//                        "Gagal meminjam",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponsePostPeminjaman>, error: Throwable) {
//                Log.e("android1", "errornya ${error.message}")
//            }
//        })
//    }

//    fun addJadwal(jenis_terapi: ArrayList<String>) {
//        val api = InitRetrofit().getInitInstance()
//        api.addJadwal(strId, jenis_terapi,"$strTgl $strWaktu", strType).enqueue(object :
//            Callback<String> {
//            @SuppressLint("CommitPrefEdits")
//            override fun onResponse(
//                call: Call<String>,
//                response: Response<String>
//            ) {
//                if (response.isSuccessful) {
//                    dataList.clear()
//
//                }else{
//                    dialog?.cancel()
//                }
//            }
//
//            override fun onFailure(call: Call<String>, error: Throwable) {
//                dialog?.cancel()
//                Log.e("android1", "errornya ${error.message}")
//            }
//        })
//    }
//
//    inner class buatJadwal() : AsyncTask<Void, Void, String>() {
//        override fun doInBackground(vararg params: Void?): String? {
//            Log.d("Tag", "On doInBackground...")
//            addJadwal(dataList)
//            dialog?.cancel()
//            pDialog.dismiss()
//
//            return "You are at PostExecute"
//        }
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//            pDialog = ProgressDialog(requireContext())
//            pDialog.setMessage("Mohon tunggu...")
//            pDialog.isIndeterminate = false
//            pDialog.setCancelable(true)
//            pDialog.show()
//        }
//    }
}