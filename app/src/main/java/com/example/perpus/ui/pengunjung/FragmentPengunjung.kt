package com.example.perpus.ui.pengunjung

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perpus.R
import com.example.perpus.adapter.PengunjungAdapter
import com.example.perpus.databinding.FragmentPeminjamanBinding
import com.example.perpus.model.*

class FragmentPengunjung : Fragment(R.layout.fragment_pengunjung), PengunjungView {
    private var bindings: FragmentPeminjamanBinding? = null
    private lateinit var pengunjungPresenter: PengunjungPresenter
    private lateinit var pengunjungAdapter: PengunjungAdapter
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
        pengunjungPresenter = PengunjungPresenter(this)

        bindings!!.swlayout.setOnRefreshListener {
            bindings!!.swlayout.isRefreshing = false
            pengunjungPresenter.getResponse(idUser)
        }
    }

    override fun onStart() {
        super.onStart()
        pengunjungPresenter.getResponse(idUser)
    }

    override fun onSuccessPengunjung(payloadPengunjungPersonal: ArrayList<PayloadPengunjungPersonal>?) {
        pengunjungAdapter = PengunjungAdapter(requireContext(), payloadPengunjungPersonal)
        bindings?.recycler?.adapter = pengunjungAdapter
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
}