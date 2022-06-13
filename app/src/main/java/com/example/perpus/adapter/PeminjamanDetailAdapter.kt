package com.example.perpus.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.perpus.R
import com.example.perpus.model.PayloadPeminjamanDetail
import com.example.perpus.network.InitRetrofit
import com.example.perpus.ui.buku.DetailBukuActivity
import com.squareup.picasso.Picasso


class PeminjamanDetailAdapter : RecyclerView.Adapter<PeminjamanDetailAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadPeminjamanDetail>? = null

    constructor(context: Context, data: ArrayList<PayloadPeminjamanDetail>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_peminjaman_detail, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder as MyViewHolder, position)
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJudulBuku:TextView = itemView.findViewById(R.id.tvJudulBuku)
        var tvKategori:TextView = itemView.findViewById(R.id.tvKategori)
        var tvPengarang:TextView = itemView.findViewById(R.id.tvPengarang)
        var ivGambar:ImageView = itemView.findViewById(R.id.ivGambar)
    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvJudulBuku.text = dataList?.get(position)!!.judul_buku!!
        holder.tvKategori.text = dataList?.get(position)!!.kategori!!
        holder.tvPengarang.text = dataList?.get(position)!!.pengarang!!

        val api = InitRetrofit().getFolderImg()
        Picasso.with(c).load("$api${dataList?.get(position)!!.gambar!!}").resize(100, 150).into(holder.ivGambar)

        holder.itemView.setOnClickListener{
            var intent = Intent(it.context, DetailBukuActivity::class.java)
            intent.putExtra("id", dataList?.get(position)!!.id.toString()!!)
            it.context.startActivity(intent)
        }
    }
}