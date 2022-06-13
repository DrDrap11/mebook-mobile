package com.example.perpus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.perpus.R
import com.example.perpus.model.PayloadKategori
import com.example.perpus.model.PayloadPengunjungPersonal
import com.example.perpus.ui.buku.BukuView
import com.example.perpus.utils.TmpData
import com.example.perpus.utils.ViewData

class PengunjungAdapter : RecyclerView.Adapter<PengunjungAdapter.MyViewHolder> {

    lateinit var view: ViewData
    var c: Context? = null
    var dataList: ArrayList<PayloadPengunjungPersonal>? = null


    constructor(context: Context, data: ArrayList<PayloadPengunjungPersonal>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_riwayat_pengunjung, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvHari:TextView = itemView.findViewById(R.id.tvHari)
        var tvTgl:TextView = itemView.findViewById(R.id.tvTgl)

    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvHari.text = dataList?.get(position)!!.hari!!
        holder.tvTgl.text = dataList?.get(position)!!.tanggal!!
    }
}