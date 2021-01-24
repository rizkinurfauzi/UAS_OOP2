package com.rizkihanip.oop2.Rizki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rizkihanip.oop2.R
import com.rizkihanip.oop2.activity.Constant
import com.rizkihanip.oop2.activity.RoomDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class EditActivity : AppCompatActivity() {

    private val db by lazy { RoomDB(this) }
    private var absenId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupLstener()
    }

    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        when (intentType()) {
            Constant.TYPE_CREATE -> {
                supportActionBar!!.title = "BUAT BARU"
                button_save.visibility = View.VISIBLE
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                supportActionBar!!.title = "DAFTAR HADIR"
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getAbsen()
            }
            Constant.TYPE_UPDATE -> {
                supportActionBar!!.title = "EDIT"
                button_save.visibility = View.GONE
                button_update.visibility = View.VISIBLE
                getAbsen()
            }
        }
    }

    private fun setupLstener(){
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.absenDao().addAbsen(
                    Absen(
                        0,
                        edit_nama.text.toString(),
                        edit_nim_kelas.text.toString()
                    )
                )
                finish()
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.absenDao().updateAbsen(
                    Absen(
                        absenId,
                        edit_nama.text.toString(),
                        edit_nim_kelas.text.toString()
                    )
                )
                finish()
            }
        }
    }

    private fun getAbsen(){
        absenId = intent.getIntExtra("absen_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val abs = db.absenDao().getAbsen(absenId).get(0)
            edit_nama.setText( abs.nama )
            edit_nim_kelas.setText( abs.nim_kelas )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun intentType(): Int {
        return intent.getIntExtra("intent_type", 0)
    }
    val ref :DatabaseReference = FirebaseDatabase.getInstance().getReference("absen")
}

