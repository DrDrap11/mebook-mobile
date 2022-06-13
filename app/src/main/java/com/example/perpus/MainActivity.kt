package com.example.perpus

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.perpus.ui.buku.ActivityScanQRBuku
import com.example.perpus.ui.buku.FragmentBuku
import com.example.perpus.ui.home.FragmentHome
import com.example.perpus.ui.peminjaman.FragmentPeminjaman
import com.example.perpus.ui.pengembalian.FragmentPengembalian
import com.example.perpus.ui.pengunjung.FragmentPengunjung
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    private var lastPressedTime: Long = 0
    private val PERIOD = 2000

    private var idUser = ""
    lateinit var items : MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref: SharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE)
        idUser = pref.getString("code", null).toString()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout= findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener(this)
        val header: View = navView.getHeaderView(0)
        val txtnama = header.findViewById<View>(R.id.tvName) as TextView
        val tvUsername = header.findViewById<View>(R.id.tvNik) as TextView
        txtnama.text = pref.getString("nama", null)
        tvUsername.text = pref.getString("username", null)

        try {
            if (intent.getStringExtra("intent").toString() == "1") {
                var fragment: Fragment? = null
                supportActionBar?.title = "Riwayat Peminjaman"
                fragment = FragmentPeminjaman()

                if (fragment != null) {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.nav_host_fragment, fragment)
                    ft.commit()
                }
            }else if (intent.getStringExtra("intent").toString() == "0") {
                var fragment: Fragment? = null
                supportActionBar?.title = "Riwayat Peminjaman"
                fragment = FragmentPeminjaman()

                if (fragment != null) {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.nav_host_fragment, fragment)
                    ft.commit()
                }
            }else if (intent.getStringExtra("intent").toString() == "2") {
                var fragment: Fragment? = null
                supportActionBar?.title = "Riwayat Pengembalian"
                fragment = FragmentPengembalian()

                if (fragment != null) {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.nav_host_fragment, fragment)
                    ft.commit()
                }
            }
        }catch (exceptio: Exception) {

        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            when (event.action) {
                KeyEvent.ACTION_DOWN -> {
                    if (event.downTime - lastPressedTime < PERIOD) {
                        val baIntent = Intent(Intent.ACTION_MAIN)
                        baIntent.addCategory(Intent.CATEGORY_HOME)
                        baIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(baIntent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Tekan sekali lagi untuk keluar.",
                            Toast.LENGTH_SHORT
                        ).show()
                        lastPressedTime = event.eventTime
                    }
                    return true
                }
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        items = menu.findItem(R.id.action_favorite)
        if (items != null) {
            items.isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_favorite) {
            val i = Intent(this, ActivityScanQRBuku::class.java)
            startActivity(i)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_home -> {
                items.isVisible = false
                supportActionBar?.title = "Home"
                fragment = FragmentHome()
            }
            R.id.nav_pengunjung -> {
                items.isVisible = false
                supportActionBar?.title = "Pengunjung"
                fragment = FragmentPengunjung()
            }
            R.id.nav_gallery -> {
                items.isVisible = true
                supportActionBar?.title = "Buku"
                fragment = FragmentBuku()
            }
            R.id.pengobatan -> {
                items.isVisible = false
                supportActionBar?.title = "Riwayat Peminjaman"
                fragment = FragmentPeminjaman()
            }
            R.id.promo -> {
                items.isVisible = false
                supportActionBar?.title = "Riwayat Pengembalian"
                fragment = FragmentPengembalian()
            }
            R.id.scanqr -> {
                openScanner()
            }
            R.id.logOut -> {
                dialogSignOut()
            }
        }
        if (fragment != null) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.nav_host_fragment, fragment)
            ft.commit()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun dialogSignOut() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_signout)

        val aksi = dialog.findViewById<RelativeLayout>(R.id.signout)
        dialog.show()

        aksi.setOnClickListener {

            val pref: SharedPreferences = getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
            )
            pref.edit().clear().commit()

            val intent = Intent(this@MainActivity, CloseActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

            dialog.dismiss()
        }
    }

    private fun openScanner() {
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                permissionsCheck()
            }
        } else {
            val intent = Intent(this, ActivityScanQR::class.java)
            startActivity(intent)
        }
    }

    private fun permissionsCheck(): Boolean {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.CAMERA
                )) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.INTERNET
                    ),
                    99
                )

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.INTERNET
                    ),
                    99
                )
            }
            return false
        } else {
            return true
        }
    }

//    private fun postPengunjung () {
//        val api = InitRetrofit().getInitInstance()
//        api.postPengunjung(idUser).enqueue(object : Callback<ResponsePostPengunjung> {
//            override fun onResponse(
//                    call: Call<ResponsePostPengunjung>,
//                    response: Response<ResponsePostPengunjung>
//            ) {
//                if (response.isSuccessful) {
//                    val jsonresponse = response.body()?.payload
//                }
//            }
//
//            override fun onFailure(call: Call<ResponsePostPengunjung>, error: Throwable) {
//                Log.e("android1", "errornya ${error.message}")
//            }
//        })
//    }
}