package com.example.perpus.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.perpus.R
import com.example.perpus.model.PayloadBuku
import com.example.perpus.network.InitRetrofit
import com.example.perpus.ui.buku.DetailBukuActivity
import com.example.perpus.utils.TmpData
import com.squareup.picasso.Picasso

class BukuAdapter : RecyclerView.Adapter<BukuAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadBuku>? = null

    constructor(context: Context, data: ArrayList<PayloadBuku>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_buku, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder as MyViewHolder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJudul:TextView = itemView.findViewById(R.id.tvJudul)
        var tvKategori:TextView = itemView.findViewById(R.id.tvKategori)
        var tvPengarang:TextView = itemView.findViewById(R.id.tvPengarang)
        var tvStok:TextView = itemView.findViewById(R.id.tvStok)
        var ivGambar:ImageView = itemView.findViewById(R.id.img)
        var cardBuku: CardView = itemView.findViewById(R.id.cardBuku)

    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvJudul.text = dataList?.get(position)!!.judul_buku!!
        holder.tvKategori.text = dataList?.get(position)!!.nama_kategori!!
        holder.tvPengarang.text = dataList?.get(position)!!.pengarang!!
        holder.tvStok.text = "Stok Buku ${dataList?.get(position)!!.stok_buku!!}"

        val api = InitRetrofit().getFolderImg()
        Picasso.with(c).load("$api${dataList?.get(position)!!.gambar!!}").resize(100, 150).into(holder.ivGambar)

        if (TmpData.buku.contains(dataList?.get(position)!!.id!!)) {
            holder.cardBuku.setCardBackgroundColor(Color.parseColor("#C5C5C5"))
        }else{
            holder.cardBuku.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        holder.itemView.setOnLongClickListener(OnLongClickListener {
            val p: Int = dataList?.get(position)!!.id!!.toInt()

            if (dataList?.get(position)!!.stok_buku!!.toInt() > 0) {
                if (TmpData.buku.contains(dataList?.get(position)!!.id!!)) {
                    holder.cardBuku.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                    TmpData.buku.remove(dataList?.get(position)!!.id)
                } else {
                    holder.cardBuku.setCardBackgroundColor(Color.parseColor("#C5C5C5"))
                    TmpData.buku!!.add(dataList?.get(position)!!.id!!)
                }
            } else {
                Toast.makeText(c, "Stok Buku Kosong", Toast.LENGTH_LONG).show()
            }
            println("LongClick: $p")
            true // returning true instead of false, works for me
        })

        holder.itemView.setOnClickListener {
            val i = Intent(c, DetailBukuActivity::class.java)
            i.putExtra("id", dataList?.get(position)!!.id!!)
            c!!.startActivity(i)
        }

    }
}