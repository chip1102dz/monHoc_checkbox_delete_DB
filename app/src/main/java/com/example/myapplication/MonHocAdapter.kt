package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBinding

class MonHocAdapter : RecyclerView.Adapter<MonHocAdapter.MonHocViewHolder>() {
    class MonHocViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)
    private var list = mutableListOf<MonHoc>()
    private var listCheck = mutableListOf<MonHoc>()

    fun getList(): MutableList<MonHoc> {
        return listCheck
    }
    fun setData(list: MutableList<MonHoc>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonHocViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context))
        return MonHocViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MonHocViewHolder, position: Int) {
        val monHoc = list[position]
        holder.binding.tvMa.text = monHoc.ma
        holder.binding.tvName.text = monHoc.name
        holder.binding.tvLythuyet.text = monHoc.lythuyet.toString()
        holder.binding.tvThuchanh.text = monHoc.thuchanh.toString()
        holder.binding.checkbox.isChecked = listCheck.contains(monHoc)
        holder.binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                listCheck.add(monHoc)
            }else{
                listCheck.remove(monHoc)
            }
        }

    }
}