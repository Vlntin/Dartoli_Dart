package com.example.dartoli.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.dartoli.model.CountingPlayerResults
import com.example.dartoli.model.Match
import com.example.dartoli.model.Player
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CountingPlayerDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1 // Datenbank Version
        private const val DATABASE_NAME = "DartoliDatabase4" // Datenbank Name
        private const val TABLE_NAME = "CountingPlayerResultTable" // Tabellen Name

        //All the Columns names
        private const val KEY_ID = "_id"
        private const val KEY_PLAYER_ID = "player_id"
        private const val KEY_DATE = "date"
        private const val KEY_RANK = "rank"
        private const val KEY_AVERAGE = "average"
        private const val KEY_THROW_AMOUNT = "throw_amount"
        private const val KEY_THROWS = "throws"
        private const val KEY_DOUBLE_TRIES = "double_tries"
        private const val KEY_DOUBLE_HITS_AMOUNT = "double_hits_amount"
        private const val KEY_DOUBLE_HITS = "double_hits"
        private const val KEY_FINISHES = "finishes"
        private const val KEY_NEEDED_DARTS_TO_WIN = "needed_darts_to_win"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_PLAYER_ID + " INTEGER, " + KEY_DATE + " TEXT, " + KEY_RANK + " INTEGER, " + KEY_AVERAGE + " TEXT, " + KEY_THROW_AMOUNT +
                " INTEGER, " + KEY_THROWS + " TEXT, " + KEY_DOUBLE_TRIES + " INTEGER, " + KEY_DOUBLE_HITS_AMOUNT + " INTEGER, " + KEY_DOUBLE_HITS + " INTEGER, " + KEY_FINISHES + " TEXT, " + KEY_NEEDED_DARTS_TO_WIN + " TEXT);"
        Log.v("query", query)
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addCountingPlayerResult(result: CountingPlayerResults) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_PLAYER_ID, result.player_id)
        cv.put(KEY_DATE, result.date)
        cv.put(KEY_RANK, result.rank)
        cv.put(KEY_AVERAGE, result.average.toString())
        cv.put(KEY_THROW_AMOUNT, result.throw_amount)
        cv.put(KEY_THROWS, Gson().toJson(result.throws))
        cv.put(KEY_DOUBLE_TRIES, result.double_tries)
        cv.put(KEY_DOUBLE_HITS_AMOUNT, result.double_hits_amount)
        cv.put(KEY_DOUBLE_HITS, Gson().toJson(result.double_hits))
        cv.put(KEY_FINISHES, Gson().toJson(result.finishes))
        cv.put(KEY_NEEDED_DARTS_TO_WIN, Gson().toJson(result.needed_darts_to_win))

        val result = db.insert(TABLE_NAME, null, cv)

        db.close()
    }

    @SuppressLint("Range")
    fun readAllResults(): ArrayList<CountingPlayerResults> {
        var results_list: ArrayList<CountingPlayerResults> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    val player_id = cursor.getInt(cursor.getColumnIndex(KEY_PLAYER_ID))
                    val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                    val rank = cursor.getInt(cursor.getColumnIndex(KEY_RANK))
                    val average = cursor.getString(cursor.getColumnIndex(KEY_AVERAGE)).toDouble()
                    val throw_amount = cursor.getInt(cursor.getColumnIndex(KEY_THROW_AMOUNT))
                    val throws_string = cursor.getString(cursor.getColumnIndex(KEY_THROWS))
                    val double_tries = cursor.getInt(cursor.getColumnIndex(KEY_DOUBLE_TRIES))
                    val double_hits_amount = cursor.getInt(cursor.getColumnIndex(
                        KEY_DOUBLE_HITS_AMOUNT))
                    val double_hits_string = cursor.getString(cursor.getColumnIndex(KEY_DOUBLE_HITS))
                    val finishes_string = cursor.getString(cursor.getColumnIndex(KEY_FINISHES))
                    val needed_darts_to_win_string = cursor.getString(cursor.getColumnIndex(
                        KEY_NEEDED_DARTS_TO_WIN))



                    val collectionType: Type = object : TypeToken<ArrayList<Int?>?>() {}.type
                    val throws: ArrayList<Int> = Gson().fromJson(throws_string, collectionType)
                    val double_hits: ArrayList<Int> = Gson().fromJson(double_hits_string, collectionType)
                    val finishes: ArrayList<Int> = Gson().fromJson(finishes_string, collectionType)
                    val needed_darts_to_win: ArrayList<Int> = Gson().fromJson(needed_darts_to_win_string, collectionType)

                    results_list.add(CountingPlayerResults(id, player_id, date, rank, average, throw_amount, throws, double_tries, double_hits_amount, double_hits, finishes, needed_darts_to_win))

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return results_list
    }

    fun updateData(row_id: String, date: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_DATE, date)
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