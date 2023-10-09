package com.example.dartoli.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.annotation.Nullable


class MyDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1 // Datenbank Version
        private const val DATABASE_NAME = "DartoliDatabase" // Datenbank Name
        private const val TABLE_NAME = "PlayerTable" // Tabellen Name

        //All the Columns names
        private const val KEY_ID = "_id"
        private const val KEY_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT);"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addPlayer(name: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_NAME, name)
        val result = db.insert(TABLE_NAME, null, cv)
        db.close()
    }

    fun readAllData(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun updateData(row_id: String, name: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_NAME, name)
        val result = db.update(TABLE_NAME, cv, "_id=?", arrayOf(row_id)).toLong()
    }

    fun deleteOneRow(row_id: String) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "_id=?", arrayOf(row_id)).toLong()
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
}