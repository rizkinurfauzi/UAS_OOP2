package com.rizkihanip.oop2.Rizki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizkihanip.oop2.R
import com.rizkihanip.oop2.activity.Constant
import com.rizkihanip.oop2.activity.RoomDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val db by lazy { RoomDB(this) }
    lateinit var absenAdapter: AbsenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListener()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            absenAdapter.setData(db.absenDao().getAbs())
            withContext(Dispatchers.Main) {
                absenAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupView (){
        supportActionBar!!.apply {
            title = "ABSENSI MAHASISWA"
        }
    }

    private fun setupListener(){
        button_create.setOnClickListener {
            intentEdit(Constant.TYPE_CREATE, 0)
        }
    }

    private fun setupRecyclerView () {

        absenAdapter = AbsenAdapter(
            arrayListOf(),
            object : AbsenAdapter.OnAdapterListener {
                override fun onClick(absen: Absen) {
                    intentEdit(Constant.TYPE_READ, absen.id)
                }

                override fun onUpdate(absen: Absen) {
                    intentEdit(Constant.TYPE_UPDATE, absen.id)
                }

                override fun onDelete(absen: Absen) {
                    deleteAlert(absen)
                }

            })

        list_nama.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = absenAdapter
        }

    }

    private fun intentEdit(intent_type: Int, absen_id: Int) {
        startActivity(
            Intent(this, EditActivity::class.java)
                .putExtra("intent_type", intent_type)
                .putExtra("absen_id", absen_id)
        )

    }

    private fun deleteAlert(absen: Absen){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${absen.nama}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.absenDao().deleteAbsen(absen)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }

        dialog.show()
    }
}