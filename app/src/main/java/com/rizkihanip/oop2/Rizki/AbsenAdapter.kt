package com.rizkihanip.oop2.Rizki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizkihanip.oop2.R
import kotlinx.android.synthetic.main.adapter_main.view.*

class AbsenAdapter (var abs: ArrayList<Absen>, var listener: OnAdapterListener) :
    RecyclerView.Adapter<AbsenAdapter.AbsenViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenViewHolder {
        return AbsenViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_main,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = abs.size

    override fun onBindViewHolder(holder: AbsenViewHolder, position: Int) {
        val absen = abs[position]
        holder.view.text_nama.text = absen.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(absen)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(absen)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(absen)
        }
    }

    class AbsenViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Absen>) {
        abs.clear()
        abs.addAll(newList)
    }

    interface OnAdapterListener {
        fun onClick(absen: Absen)
        fun onUpdate(absen: Absen)
        fun onDelete(absen: Absen)
    }
}