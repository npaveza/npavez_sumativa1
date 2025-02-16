package com.example.npavez_sumativa1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "userdata.db"
        private const val DATABASE_VERSION = 1

        //nombres, tabla y columnas
        const val TABLE_USUARIOS = "userdata"
        const val COLUMN_CORREO = "email"
        const val COLUMN_CONTRA = "passwword"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTablaQuery = """
            CREATE TABLE $TABLE_USUARIOS (
            $COLUMN_CORREO TEXT PRIMARY KEY,
            $COLUMN_CONTRA TEXT NOT NULL
            )
        """.trimIndent()
        if (db != null) {
            db.execSQL(createTablaQuery)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        }
        onCreate(db)
    }
}