package io.github.mattiejas.crypto_tracker.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import io.github.mattiejas.crypto_tracker.DBContract
import io.github.mattiejas.crypto_tracker.models.CoinModel

/**
 * Created by matthias on 08/01/2018.
 */

class CoinsDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // Not sure yet what to do when the database gets upgraded
        TODO("not implemented")
    }

    @Throws(SQLiteConstraintException::class)
    fun insertCoin(coin: CoinModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.CoinEntry.COLUMN_SYMBOL, coin.symbol)
        values.put(DBContract.CoinEntry.COLUMN_NAME, coin.name)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.CoinEntry.TABLE_NAME, null, values)
        println(newRowId)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteCoin(coinSymbol: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.CoinEntry.COLUMN_SYMBOL + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(coinSymbol)
        // Issue SQL statement.
        db.delete(DBContract.CoinEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readCoin(coinSymbol: String): ArrayList<CoinModel> {
        val users = ArrayList<CoinModel>()
        val db = writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + DBContract.CoinEntry.TABLE_NAME + " WHERE "
                    + DBContract.CoinEntry.COLUMN_SYMBOL + "='" + coinSymbol + "'", null)
        } catch (e: SQLiteException) {
            // Create table if it does not exist yet
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.CoinEntry.COLUMN_NAME))

                users.add(CoinModel(coinSymbol, name))
                cursor.moveToNext()
            }
        }
        cursor.close()

        return users
    }

    fun readAllUsers(): ArrayList<CoinModel> {
        val users = ArrayList<CoinModel>()
        val db = writableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from " + DBContract.CoinEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            // Create if table does not exist yet
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var coinSymbol: String
        var name: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                coinSymbol = cursor.getString(cursor.getColumnIndex(DBContract.CoinEntry.COLUMN_SYMBOL))
                name = cursor.getString(cursor.getColumnIndex(DBContract.CoinEntry.COLUMN_NAME))

                users.add(CoinModel(coinSymbol, name))
                cursor.moveToNext()
            }
        }
        cursor.close()

        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "CryptoTracker.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.CoinEntry.TABLE_NAME + " (" +
                        DBContract.CoinEntry.COLUMN_SYMBOL + " VARCHAR PRIMARY KEY," +
                        DBContract.CoinEntry.COLUMN_NAME + " VARCHAR);"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.CoinEntry.TABLE_NAME
    }
}