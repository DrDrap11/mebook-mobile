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
import com.example.perpus.model.PayloadPengembalian
import com.example.perpus.ui.peminjaman.DialogPeminjamanDetail
import com.example.perpus.ui.pengembalian.DialogPengembalianDetail


class PengembalianAdapter : RecyclerView.Adapter<PengembalianAdapter.MyViewHolder> {

    var c: Context? = null
    var dataList: ArrayList<PayloadPengembalian>? = null

    constructor(context: Context, data: ArrayList<PayloadPengembalian>?) {
        this.c = context
        this.dataList = data
    }

    @NonNull
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(c).inflate(R.layout.item_pengembalian, p0, false)
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
        var tvTglPengembalian:TextView = itemView.findViewById(R.id.tvTglPengembalian)
        var tvStatus:TextView = itemView.findViewById(R.id.tvStatus)
        var tvDenda:TextView = itemView.findViewById(R.id.tvDenda)
        var frame:FrameLayout = itemView.findViewById(R.id.frame)

    }

    private fun populateItemRows(holder: MyViewHolder, position: Int) {
        holder.tvJumlah.text = "Jumlah Buku ${dataList?.get(position)!!.pengembalian!!}"
        holder.tvTglPinjam.text = dataList?.get(position)!!.tgl_peminjaman!!
        holder.tvTglKembali.text = dataList?.get(position)!!.tgl_kembali!!
        holder.tvTglPengembalian.text = dataList?.get(position)!!.tgl_pengembalian!!
        holder.tvDenda.text = dataList?.get(position)!!.denda!!


        when {
            dataList?.get(position)!!.status_kembali!! == "0" -> {
                holder.tvStatus.text = "Diproses Oleh Admin"
                holder.tvStatus.setTextColor(Color.parseColor("#E03B24"))
                holder.frame.setBackgroundColor(Color.parseColor("#E03B24"))
            }
            dataList?.get(position)!!.status_kembali!! == "1" -> {
                holder.tvStatus.text = "DiKonfirmasi Oleh Admin"
                holder.tvStatus.setTextColor(Color.parseColor("#64A338"))
                holder.frame.setBackgroundColor(Color.parseColor("#64A338"))
            }
        }

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("id_pinjam", dataList?.get(position)!!.id_pengembalian!!)
            bundle.putString("tgl_pinjam", dataList?.get(position)!!.tgl_peminjaman!!)
            bundle.putString("tgl_kembali", dataList?.get(position)!!.tgl_kembali!!)
            bundle.putString("status_pinjam", dataList?.get(position)!!.status_kembali!!)
            val fm = (c as AppCompatActivity).supportFragmentManager
            val p = DialogPengembalianDetail()
            p.arguments = bundle
            p.show(fm, "")
        }
    }
}