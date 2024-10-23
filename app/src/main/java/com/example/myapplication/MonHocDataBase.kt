package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MonHocDataBase(context: Context) : SQLiteOpenHelper(context, DATABASE, null, VERSION) {
    companion object{
        const val DATABASE = "SQ123.db"
        const val VERSION = 1
        const val TABLE = "tab123"
        const val ID = "id"
        const val MA = "ma"
        const val NAME = "name"
        const val LYTHUYET = "lythuyet"
        const val THUCHANH = "thuchanh"
        const val CREATE_TABLE =
            " CREATE TABLE " + TABLE + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MA + " TEXT, " +
                    NAME + " TEXT, " +
                    LYTHUYET + " INTEGER, " +
                    THUCHANH + " INTEGER " + ");"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun getAllMonHoc(): MutableList<MonHoc>{
        val db = readableDatabase
        val cursor = db.query(TABLE, null,null,null,null,null,null,null)
        val list = mutableListOf<MonHoc>()
        if(cursor.moveToFirst()){
            do {
                val monhoc = MonHoc()
                monhoc.id = cursor.getInt(0)
                monhoc.ma = cursor.getString(1)
                monhoc.name = cursor.getString(2)
                monhoc.lythuyet = cursor.getInt(3)
                monhoc.thuchanh = cursor.getInt(4)
                list.add(monhoc)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }
    fun insertMonHoc(monHoc: MonHoc){
        val db = writableDatabase
        val values = ContentValues()
        values.put(MA, monHoc.ma)
        values.put(NAME, monHoc.name)
        values.put(LYTHUYET, monHoc.lythuyet)
        values.put(THUCHANH, monHoc.thuchanh)
        db.insert(TABLE, null, values)
    }
    fun deleteMonHoc(id: Int){
        val db = writableDatabase
        db.delete(TABLE, ID + " = ? ", arrayOf(id.toString()))
        db.close()
    }
}