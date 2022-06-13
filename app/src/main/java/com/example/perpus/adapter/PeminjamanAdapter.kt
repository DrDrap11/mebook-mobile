package com.example.perpus.adapter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.perpus.R
import com.example.perpus.model.PayloadPeminjaman
import com.example.perpus.ui.peminjaman.DialogPeminjamanDetail


class PeminjamanAdapter : RecyclerView.Adapter<PeminjamanAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadPeminjaman>? = null

    constructor(context: Context, data: ArrayList<PayloadPeminjaman>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_peminjaman, p0, false)
            return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        populateItemRows(holder as MyViewHolder, position)

    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJumlah:TextView = itemView.findViewById(R.id.tvJumlah)
        var tvTglPinjam:TextView = itemView.findViewById(R.id.tvTglPinjam)
        var tvTglKembali:TextView = itemView.findViewById(R.id.tvTglKembali)
        var tvStatus:TextView = itemView.findViewById(R.id.tvStatus)
        var tvAlasan:TextView = itemView.findViewById(R.id.tvAlasan)
        var frame:FrameLayout = itemView.findViewById(R.id.frame)

    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvTglPinjam.text = dataList?.get(position)!!.tgl_peminjaman!!
        holder.tvTglKembali.text = dataList?.get(position)!!.tgl_kembali!!
        holder.tvJumlah.text = "Jumlah Buku ${dataList?.get(position)!!.peminjaman!!}"
        holder.tvAlasan.text = dataList?.get(position)!!.alasan!!


        when {
            dataList?.get(position)!!.status_pinjam!! == "0" -> {
                holder.tvStatus.text = "Diproses"
                holder.tvStatus.setTextColor(Color.parseColor("#E03B24"))
                holder.frame.setBackgroundColor(Color.parseColor("#E03B24"))
            }
            dataList?.get(position)!!.status_pinjam!! == "1" -> {
                holder.tvStatus.text = "DiKonfirmasi"
                holder.tvStatus.setTextColor(Color.parseColor("#64A338"))
                holder.frame.setBackgroundColor(Color.parseColor("#64A338"))
            }
            dataList?.get(position)!!.status_pinjam!! == "2" -> {
                holder.tvStatus.text = "Dikembalikan"
                holder.tvStatus.setTextColor(Color.parseColor("#3865A3"))
                holder.frame.setBackgroundColor(Color.parseColor("#3865A3"))
            }
            dataList?.get(position)!!.status_pinjam!! == "5" -> {
                holder.tvStatus.text = "Dibatalkan"
                holder.tvStatus.setTextColor(Color.parseColor("#E03B24"))
                holder.frame.setBackgroundColor(Color.parseColor("#E03B24"))
            }
        }

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("id_pinjam", dataList?.get(position)!!.id_peminjaman!!)
            bundle.putString("tgl_pinjam", dataList?.get(position)!!.tgl_peminjaman!!)
            bundle.putString("tgl_kembali", dataList?.get(position)!!.tgl_kembali!!)
            bundle.putString("status_pinjam", dataList?.get(position)!!.status_pinjam!!)
            val fm = (c as AppCompatActivity).supportFragmentManager
            val p = DialogPeminjamanDetail()
            p.arguments = bundle
            p.show(fm, "")
        }
    }
}