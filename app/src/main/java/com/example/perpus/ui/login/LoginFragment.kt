package com.example.perpus.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.perpus.MainActivity
import com.example.perpus.R
import com.example.perpus.databinding.LayoutLoginBinding
import com.example.perpus.model.ResponseLogin
import com.example.perpus.network.InitRetrofit
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment(R.layout.layout_login) {
    private var bindings: LayoutLoginBinding? = null

    private var user: String = ""
    private var pass: String = ""
    private var strToken: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = LayoutLoginBinding.bind(view)
        bindings = binding

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
            strToken = token.toString()
            Log.d("TAG", token.toString())
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        bindings!!.login.setOnClickListener {
            user = bindings!!.etUsername.text.toString()
            pass = bindings!!.etPassword.text.toString()

            when {
                user == "" -> {
                    bindings!!.etUsername.error = "Tidak boleh kosong"
                }
                pass == "" -> {
                    bindings!!.etPassword.error = "Tidak boleh kosong"
                }
                else -> {
                    bindings!!.loading.visibility = View.VISIBLE
                    proccess(1)
                    loadData()
                }
            }
        }

    }

    fun loadData(){
        val api = InitRetrofit().getInitInstance()
        api.login(user, pass, strToken).enqueue(object :
                Callback<ResponseLogin> {
            @SuppressLint("CommitPrefEdits")
            override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
            ) {
                if (response.isSuccessful) {
                    val jsonresponse = response.body()?.payload
                    if (user == jsonresponse?.nim) {
                        requireActivity().getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE)
                            .edit()
                            .putString("code", jsonresponse.id)
                            .putString("username", jsonresponse.nim)
                            .putString("password", jsonresponse.password)
                            .putString("nama", jsonresponse.nama)
                            .apply()
                        val myIntent = Intent(activity, MainActivity::class.java)
                        startActivity(myIntent)
                        activity?.finish()
                        bindings!!.loading.visibility = View.GONE
                    } else {
                        proccess(2)
                        Toast.makeText(
                            activity,
                            "Login Gagal, Periksa kembali NIM dan Password",
                            Toast.LENGTH_SHORT
                        ).show()
                        bindings!!.loading.visibility = View.GONE
                    }
                } else {
                    proccess(2)
                    Toast.makeText(
                        activity,
                        "Login Gagal, Periksa kembali NIM dan Passwords",
                        Toast.LENGTH_SHORT
                    ).show()
                    bindings!!.loading.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, error: Throwable) {
                Log.e("android1", "errornya ${error.message}")
                proccess(2)
            }
        })
    }

    private fun moveToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, fragment.javaClass.simpleName).addToBackStack(null)
            .commit()
    }

    private fun proccess (sts:Int) {
        if (sts == 1) {
            bindings!!.login.visibility = View.GONE
            bindings!!.loading.visibility = View.VISIBLE
        }else{
            bindings!!.login.visibility = View.VISIBLE
            bindings!!.loading.visibility = View.GONE
        }
    }
}

