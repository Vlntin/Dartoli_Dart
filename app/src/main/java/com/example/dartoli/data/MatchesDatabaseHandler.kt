package com.example.dartoli.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.dartoli.model.Match
import com.example.dartoli.model.Player
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MatchesDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1 // Datenbank Version
        private const val DATABASE_NAME = "DartoliDatabase13" // Datenbank Name
        private const val TABLE_NAME = "MatchesTable" // Tabellen Name

        //All the Columns names
        private const val KEY_ID = "_id"
        private const val KEY_GAME_ID = "game_id"
        private const val KEY_DATE = "date"
        private const val KEY_PLAYER_IDS = "player_ids"
        private const val KEY_WON_LEGS = "player_won_legs"
        private const val KEY_WON_SETS = "player_won_sets"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_NAME +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_GAME_ID + " INTEGER, " + KEY_DATE + " TEXT, " + KEY_PLAYER_IDS + " TEXT, " + KEY_WON_LEGS + " TEXT, " + KEY_WON_SETS + " TEXT);"
        Log.v("query", query)
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        onCreate(db)
    }

    fun addMatch(match: Match) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_GAME_ID, match.game_id)
        cv.put(KEY_DATE, match.date)
        cv.put(KEY_PLAYER_IDS, Gson().toJson(match.player_ids))
        cv.put(KEY_WON_LEGS, Gson().toJson(match.won_legs))
        Log.v("a", "d")
        cv.put(KEY_WON_SETS, Gson().toJson(match.won_sets))
        Log.v("a", "d")
        val result = db.insert(TABLE_NAME, null, cv)
        Log.v("a", "d")
        db.close()
    }

    @SuppressLint("Range")
    fun readAllMatches(): ArrayList<Match> {
        var matches_list: ArrayList<Match> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    val game_id = cursor.getInt(cursor.getColumnIndex(KEY_GAME_ID))
                    val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                    val player_ids = cursor.getString(cursor.getColumnIndex(KEY_PLAYER_IDS))
                    val won_legs = cursor.getString(cursor.getColumnIndex(KEY_WON_LEGS))
                    val won_sets = cursor.getString(cursor.getColumnIndex(KEY_WON_SETS))
                    val collectionType: Type = object : TypeToken<ArrayList<Int?>?>() {}.type
                    val player_ids_list: ArrayList<Int> = Gson().fromJson(player_ids, collectionType)
                    val won_legs_list: ArrayList<Int> = Gson().fromJson(won_legs, collectionType)
                    val won_sets_list: ArrayList<Int> = Gson().fromJson(won_sets, collectionType)
                    matches_list.add(Match(id, game_id, date, player_ids_list, won_legs_list, won_sets_list))

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return matches_list
    }

    @SuppressLint("Range")
    fun readAllMatchesWithPlayer(player: Player): ArrayList<Match> {
        var matches_list: ArrayList<Match> = ArrayList()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    val game_id = cursor.getInt(cursor.getColumnIndex(KEY_GAME_ID))
                    val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                    val player_ids = cursor.getString(cursor.getColumnIndex(KEY_PLAYER_IDS))
                    val won_legs = cursor.getString(cursor.getColumnIndex(KEY_WON_LEGS))
                    val won_sets = cursor.getString(cursor.getColumnIndex(KEY_WON_SETS))
                    val collectionType: Type = object : TypeToken<ArrayList<Int?>?>() {}.type
                    val player_ids_list: ArrayList<Int> = Gson().fromJson(player_ids, collectionType)
                    val won_legs_list: ArrayList<Int> = Gson().fromJson(won_legs, collectionType)
                    val won_sets_list: ArrayList<Int> = Gson().fromJson(won_sets, collectionType)
                    for (id in player_ids_list){
                        if (id == player.id) matches_list.add(Match(id, game_id, date, player_ids_list, won_legs_list, won_sets_list))
                    }
                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return matches_list
    }

    fun updateData(row_id: String, name: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_WON_LEGS, name)
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