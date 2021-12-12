package com.example.testforskb_lab.SQLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.testforskb_lab.SQLite.modelsForLocal.ReposForLocal
import com.example.testforskb_lab.SQLite.modelsForLocal.UserForLocal
import com.example.testforskb_lab.models.OwnerConstructor
import com.example.testforskb_lab.models.RepositoriesConstructor

val DATABASE_NAME = "LocaleDataBase"
val TABLE_NAME_USERS = "Users"
val TABLE_NAME_LOCAL_REPOSITORIES = "LocalRepositories"
val USERS_COL_ID = "Id"
val LOCAL_REPOSITORIES_FULL_NAME = "FullName"
val LOCAL_REPOSITORIES_OWNER = "Owner"
val LOCAL_REPOSITORIES_IMAGE_OWNER = "ImageOwner"
val LOCAL_REPOSITORIES_DESCRIPTION = "Description"
val LOCAL_REPOSITORIES_FORKS = "Forks"
val LOCAL_REPOSITORIES_WATCHERS = "Watchers"
val LOCAL_REPOSITORIES_CREATED_AT = "CreatedAt"
val LOCAL_REPOSITORIES_ID_SAVED_USERS = "IdSavedUsers"

class SQLiteHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableUsers = "CREATE TABLE " + TABLE_NAME_USERS + " (" +
                USERS_COL_ID + " VARCHAR(256))"
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

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertUser(userForLocal: UserForLocal) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(USERS_COL_ID, userForLocal.id)
        db.insert(TABLE_NAME_USERS, null, cv)
    }

    fun insertSavedRepos(reposForLocal: ReposForLocal) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(LOCAL_REPOSITORIES_FULL_NAME, reposForLocal.full_name)
        cv.put(LOCAL_REPOSITORIES_OWNER, reposForLocal.owner)
        cv.put(LOCAL_REPOSITORIES_IMAGE_OWNER, reposForLocal.imageOwner)
        cv.put(LOCAL_REPOSITORIES_DESCRIPTION, reposForLocal.description)
        cv.put(LOCAL_REPOSITORIES_FORKS, reposForLocal.forks)
        cv.put(LOCAL_REPOSITORIES_WATCHERS, reposForLocal.watchers)
        cv.put(LOCAL_REPOSITORIES_CREATED_AT, reposForLocal.created_at)
        cv.put(LOCAL_REPOSITORIES_ID_SAVED_USERS, reposForLocal.id_saved_user)
        db.insert(TABLE_NAME_LOCAL_REPOSITORIES, null, cv)
    }

    @SuppressLint("Range")
    fun getMyRepos(id: String): ArrayList<RepositoriesConstructor> {
        val listMyRepos: ArrayList<RepositoriesConstructor> = ArrayList()
        var db = this.readableDatabase
        var query =
            "SELECT * FROM $TABLE_NAME_LOCAL_REPOSITORIES WHERE $LOCAL_REPOSITORIES_ID_SAVED_USERS = ?"
        val value = arrayOf(id)
        var result = db.rawQuery(query, value)
        if (result.moveToFirst()) {
            do {
                var owner = OwnerConstructor(
                    result.getString(result.getColumnIndex(LOCAL_REPOSITORIES_OWNER)).toString(),
                    result.getString(result.getColumnIndex(LOCAL_REPOSITORIES_IMAGE_OWNER))
                        .toString()
                )
                val repos = RepositoriesConstructor()
                repos.full_name =
                    result.getString(result.getColumnIndex(LOCAL_REPOSITORIES_FULL_NAME)).toString()
                repos.description =
                    result.getString(result.getColumnIndex(LOCAL_REPOSITORIES_DESCRIPTION))
                        .toString()
                repos.forks =
                    result.getString(result.getColumnIndex(LOCAL_REPOSITORIES_FORKS)).toString()
                repos.watchers =
                    result.getString(result.getColumnIndex(LOCAL_REPOSITORIES_WATCHERS)).toString()
                repos.created_at =
                    result.getString(result.getColumnIndex(LOCAL_REPOSITORIES_CREATED_AT))
                        .toString()
                repos.owner = owner
                listMyRepos.add(repos)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return listMyRepos
    }

    fun deleteReposFromLocalRepositoryies(idUser: String, nameRepos: String) {
        val db = this.writableDatabase
        val values = arrayOf(idUser, nameRepos)
        db.delete(
            TABLE_NAME_LOCAL_REPOSITORIES,
            "$LOCAL_REPOSITORIES_ID_SAVED_USERS = ? AND $LOCAL_REPOSITORIES_FULL_NAME = ?",
            values
        )
        db.close()
    }

    fun deleteSavedRepos(idUser: String) {
        val db = this.writableDatabase
        val value = arrayOf(idUser)
        db.delete(TABLE_NAME_LOCAL_REPOSITORIES, "$LOCAL_REPOSITORIES_ID_SAVED_USERS = ?", value)
        db.close()
    }
}