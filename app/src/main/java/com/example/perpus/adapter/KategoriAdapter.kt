package com.example.perpus.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.perpus.R
import com.example.perpus.model.PayloadKategori
import com.example.perpus.utils.TmpData
import com.example.perpus.utils.ViewData

class KategoriAdapter : RecyclerView.Adapter<KategoriAdapter.MyViewHolder> {

    lateinit var view: ViewData
    var c: Context? = null
    var dataList: ArrayList<PayloadKategori>? = null


    constructor(context: Context, view: ViewData, data: ArrayList<PayloadKategori>?) {
        this.c = context
        this.dataList = data
        this.view = view
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_kategori, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder as MyViewHolder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNamaKategori:TextView = itemView.findViewById(R.id.tvNamaKategori)
        var cardKategori:CardView = itemView.findViewById(R.id.cardKategori)

    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvNamaKategori.text = dataList?.get(position)!!.nama_kategori!!

        if (TmpData.kategori == dataList?.get(position)!!.id_kategori!!) {
            holder.cardKategori.setCardBackgroundColor(Color.parseColor("#C5C5C5"))
        }else{
            holder.cardKategori.setCardBackgroundColor(Color.parseColor("#ffffff"))
        }

        holder.itemView.setOnClickListener {
            view.sendData(dataList?.get(position)!!.id_kategori!!)
        }
    }
}