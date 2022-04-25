package com.example.testforskb_lab.data.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.testforskb_lab.data.SQLite.*
import javax.inject.Inject
import javax.inject.Provider

class LocalRepository @Inject constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableUsers = "CREATE TABLE " + TABLE_NAME_USERS + " (" +
                USERS_COL_ID + " VARCHAR(256), " +
                USERS_COL_EMAIL + " VARCHAR(256), " +
                USERS_COL_IMG_URL + " VARCHAR(256))"
        db?.execSQL(createTableUsers)

        val createTableConferences = "CREATE TABLE " + TABLE_NAME_LOCAL_REPOSITORIES + " (" +
                LOCAL_REPOSITORIES_FULL_NAME + " VARCHAR(256), " +
                LOCAL_REPOSITORIES_OWNER + " VARCHAR(256), " +
                LOCAL_REPOSITORIES_IMAGE_OWNER + " VARCHAR(256), " +
                LOCAL_REPOSITORIES_DESCRIPTION + " VARCHAR(256), " +
                LOCAL_REPOSITORIES_FORKS + " VARCHAR(256), " +
                LOCAL_REPOSITORIES_WATCHERS + " VARCHAR(256), " +
                LOCAL_REPOSITORIES_CREATED_AT + " VARCHAR(256), " +
                LOCAL_REPOSITORIES_ID_SAVED_USERS + " VARCHAR(256))"
        db?.execSQL(createTableConferences)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}