package com.example.pfeapplication

import android.database.Cursor
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleCursorAdapter

class ListeEtudiant : AppCompatActivity() {

    private var dbHelper: EtudiantsDBHelper? = null

    lateinit var listview : ListView

    fun getDbHelper(): EtudiantsDBHelper? {
        if (dbHelper == null) {
            dbHelper = EtudiantsDBHelper(applicationContext)
        }
        return dbHelper
    }

    private var adapter: SimpleCursorAdapter? = null


    fun getAdapter(): SimpleCursorAdapter? {
        if (adapter == null) {

            val db = getDbHelper()?.db
            var cursor: Cursor? = null
            try {
                if (db != null) {
                    cursor = db.rawQuery(
                        "select * from ${DBContract.EtudiantEntry.TABLE_NAME}", null
                    )
                }
            } catch (e: SQLiteException) {
                if (db != null) {
                    db.execSQL(EtudiantsDBHelper.SQL_CREATE_ENTRIES)
                }
            }

            adapter = SimpleCursorAdapter(
                this,
                R.layout.ligne_etudiant,
                cursor,
                arrayOf(
                    DBContract.EtudiantEntry.COLUMN_NAME_NOM,
                    DBContract.EtudiantEntry.COLUMN_NAME_PRENOM
                ),
                intArrayOf(R.id.nometud, R.id.preetud),
                0
            )
        }
        return adapter!!
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_etudiant)
        listview = findViewById<ListView> (R.id.idlistetu)
        listview.adapter = getAdapter()
    }
}