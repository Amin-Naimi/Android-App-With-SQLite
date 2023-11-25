package com.example.pfeapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EtudiantsDBHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION) {
    public val db = writableDatabase
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    //companion means static

    companion object {
        // If you change the database schema, you must increment the database version.
        public val DATABASE_VERSION = 1
        public val DATABASE_NAME = "etudiants.db"

        public val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DBContract.EtudiantEntry.TABLE_NAME} (" +
                    "${DBContract.EtudiantEntry.COLUMN_USER_ID} INTEGER PRIMARY KEY," +
                    "${DBContract.EtudiantEntry.COLUMN_NAME_NOM} TEXT," +
                    "${DBContract.EtudiantEntry.COLUMN_NAME_PRENOM} TEXT," +
                    "${DBContract.EtudiantEntry.COLUMN_NAME_PHONE} TEXT," +
                    "${DBContract.EtudiantEntry.COLUMN_NAME_EMAIL} TEXT," +
                    "${DBContract.EtudiantEntry.COLUMN_NAME_LOGIN} TEXT," +
                    "${DBContract.EtudiantEntry.COLUMN_NAME_MDP} TEXT," +
                    " _id INTEGER)"

        public val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.EtudiantEntry.TABLE_NAME
    }

}