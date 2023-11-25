package com.example.pfeapplication

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var id: EditText
    lateinit var nom: EditText
    lateinit var prenom: EditText
    lateinit var tel: EditText
    lateinit var email: EditText
    lateinit var login: EditText
    lateinit var password: EditText
    lateinit var btnAnnuler: Button
    lateinit var btnValider: Button

    lateinit var etudiantDBHelper: EtudiantsDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = findViewById(R.id.identifinat)
        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        tel = findViewById(R.id.tel)
        email = findViewById(R.id.email)
        login = findViewById(R.id.login)
        password = findViewById(R.id.password)
        btnAnnuler = findViewById(R.id.annuler)
        btnValider = findViewById(R.id.valider)



        btnAnnuler.setOnClickListener {
            annuler()
        }
        btnValider.setOnClickListener {
            valider()
            addEtudiant()

        }

        etudiantDBHelper = EtudiantsDBHelper(this)
    }

    private fun annuler() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Attention")
        builder.setMessage("Voulez vous vraimant remettre à zero les champs")
        builder.setPositiveButton("oui",
            DialogInterface.OnClickListener { dialogInterface, i ->
                remmettreLeschampAzero()
            })
        builder.setNegativeButton("Non", null)
        builder.show()
    }
    private fun remmettreLeschampAzero(){
        id.setText("")
        nom.setText("")
        prenom.setText("")
        tel.setText("")
        email.setText("")
        login.setText("")
        password.setText("")

    }
    private fun valider(){

        val idText: String = id.text.toString()
        val nomText: String = nom.text.toString()
        val prenomText: String = prenom.text.toString()
        val telText: String = tel.text.toString()
        val emailText: String = email.text.toString()
        val loginText: String = login.text.toString()
        val passwordText: String = password.text.toString()
        var error = false

        if (idText.isEmpty() || nomText.isEmpty() || prenomText.isEmpty() || telText.isEmpty() || emailText.isEmpty() || loginText.isEmpty()||
            passwordText.isEmpty()) {
            error = true
        }


        if (error==true) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Attention")
            builder.setMessage("Tous les champs doivent etre remplis")
            builder.setPositiveButton("ok", null)

            builder.show()
        }
    }



    @Throws(SQLiteConstraintException::class)
    fun insertEtudiant(etudiant: EtudiantModel): Boolean {
        try {
            val db = etudiantDBHelper.writableDatabase
            val values = ContentValues()
            values.put(DBContract.EtudiantEntry.COLUMN_NAME__ID, etudiant.userid)
            values.put(DBContract.EtudiantEntry.COLUMN_NAME_NOM, etudiant.nom)
            values.put(DBContract.EtudiantEntry.COLUMN_NAME_PRENOM, etudiant.prenom)
            values.put(DBContract.EtudiantEntry.COLUMN_NAME_PHONE, etudiant.tel)
            values.put(DBContract.EtudiantEntry.COLUMN_NAME_EMAIL, etudiant.email)
            values.put(DBContract.EtudiantEntry.COLUMN_NAME_LOGIN, etudiant.login)
            values.put(DBContract.EtudiantEntry.COLUMN_NAME_MDP, etudiant.password)
            val newRowId = db.insertOrThrow(DBContract.EtudiantEntry.TABLE_NAME, null, values)
            Toast.makeText(this, "Nouvel ID de ligne : $newRowId", Toast.LENGTH_LONG).show()
            return true
        } catch (e: SQLiteConstraintException) {
            // Gérer l'erreur d'insertion
            Toast.makeText(this, "Erreur lors de l'insertion : ${e.message}", Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun addEtudiant() {
        val id_: Int = id.text.toString().toInt()
        val nom_ = nom.text.toString()
        val prenom_ = prenom.text.toString()
        val tel_ = tel.text.toString()
        val email_ = email.text.toString()
        val login_ = login.text.toString()
        val password_ = password.text.toString()

        val insertionReussie = insertEtudiant(
            EtudiantModel(userid= id_,nom = nom_, prenom = prenom_, tel = tel_, email = email_, login = login_, password = password_))



        if (insertionReussie) {
            val intent = Intent(this, ListeEtudiant::class.java)
            startActivity(intent)
        }
    }


    /*  @SuppressLint("Range")
      fun readAllUsers(): ArrayList<UserModel> {
          val users = ArrayList<UserModel>()
          val db = usersDBHelper.db
          var cursor: Cursor? = null
          try {
              cursor = db.rawQuery(
                  "select * from ${DBContract.UserEntry.TABLE_NAME}", null
              )
          } catch (e: SQLiteException) {
              db.execSQL(UsersDBHelper.SQL_CREATE_ENTRIES)
              return ArrayList()
          }

          var userid: String
          var name: String
          var age: String
          if (cursor!!.moveToFirst()) {
              while (!cursor.isAfterLast) {
                  userid = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_USER_ID))
                  name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                  age = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_AGE))
                  users.add(UserModel(userid, name, age))
                  cursor.moveToNext()
              }
          }
          return users
      }*/
}