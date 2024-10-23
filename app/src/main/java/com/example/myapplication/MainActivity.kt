package com.example.myapplication

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var rcv: RecyclerView
    lateinit var adapter: MonHocAdapter
    lateinit var db: MonHocDataBase
    var list = mutableListOf<MonHoc>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rcv = binding.rcv
        adapter = MonHocAdapter()
        db = MonHocDataBase(this)
        rcv.adapter = adapter
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        binding.btnNhapMon.setOnClickListener {
            addMon()
        }
        binding.delete.setOnClickListener {
            val listCheck = adapter.getList()
            listCheck.forEach { monHoc ->
                deteMon(monHoc.id)
            }
        }

        getAllMon()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun deteMon(id: Int) {
        db.deleteMonHoc(id)
        getAllMon()
    }

    private fun getAllMon() {
        list = db.getAllMonHoc()
        adapter.setData(list)
    }

    private fun addMon() {
        val ma = binding.edtMa.text.toString()
        val name = binding.edtName.text.toString()
        val lythuyet = binding.edtLythuyet.text.toString()
        val thuchanh = binding.edtThuchanh.text.toString()

        val lyThuyet = lythuyet.toIntOrNull() ?: 0
        val thucHanh = thuchanh.toIntOrNull() ?: 0
        if(TextUtils.isEmpty(ma)||TextUtils.isEmpty(name)||TextUtils.isEmpty(lythuyet)||TextUtils.isEmpty(thuchanh)){
            Toast.makeText(this, " HAY NHAP DU THONG TIN !" , Toast.LENGTH_SHORT).show()
            return
        }
        if(!checkHoa(name)){
            Toast.makeText(this, " HAY NHAP CHU HOA !" , Toast.LENGTH_SHORT).show()
            return
        }
        if(!checkSo(lythuyet)||!checkSo(thuchanh)){
            Toast.makeText(this, " HAY NHAP SO1 !" , Toast.LENGTH_SHORT).show()
            return
        }
        if(checkSo(name)){
            Toast.makeText(this, " HAY NHAP CHU HOA !" , Toast.LENGTH_SHORT).show()
            return
        }
        val monhoc = MonHoc(ma = ma, name = name, lythuyet = lyThuyet, thuchanh = thucHanh)
        db.insertMonHoc(monhoc)

        binding.edtMa.setText("")
        binding.edtName.setText("")
        binding.edtLythuyet.setText("")
        binding.edtThuchanh.setText("")

        getAllMon()
    }

    fun checkHoa(str: String): Boolean{
        return str.equals(str.uppercase())
    }
    fun checkChu(str: String): Boolean{
        return str.all { it.isLetter() }
    }
    fun checkSo(str: String): Boolean{
        return str.matches("\\d+".toRegex())
    }
    fun checkHoaDau(str: String): Boolean{
        return str[0].isUpperCase()
    }
}