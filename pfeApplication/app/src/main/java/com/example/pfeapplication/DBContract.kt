package com.example.pfeapplication

import android.provider.BaseColumns

object DBContract {

    class EtudiantEntry: BaseColumns {
        companion object{
            val TABLE_NAME = "etudiant"
            val COLUMN_USER_ID = "userid"
            val COLUMN_NAME_NOM = "nom"
            val COLUMN_NAME_PRENOM = "prenom"
            val COLUMN_NAME_PHONE = "phone"
            val COLUMN_NAME_EMAIL = "email"
            val COLUMN_NAME_LOGIN = "login"
            val COLUMN_NAME_MDP = "password"
            val COLUMN_NAME__ID = "_id"

        }
    }
}