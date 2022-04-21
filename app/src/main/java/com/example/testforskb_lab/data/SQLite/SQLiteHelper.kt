package com.example.testforskb_lab.data.SQLite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import com.example.testforskb_lab.domain.model.OwnerConstructor
import com.example.testforskb_lab.domain.model.RepositoriesConstructor

const val DATABASE_NAME = "LocaleDataBase"
const val TABLE_NAME_USERS = "Users"
const val TABLE_NAME_LOCAL_REPOSITORIES = "LocalRepositories"
const val USERS_COL_ID = "Id"
const val USERS_COL_IMG_URL = "ImgUrl"
const val USERS_COL_EMAIL = "Email"
const val LOCAL_REPOSITORIES_FULL_NAME = "FullName"
const val LOCAL_REPOSITORIES_OWNER = "Owner"
const val LOCAL_REPOSITORIES_IMAGE_OWNER = "ImageOwner"
const val LOCAL_REPOSITORIES_DESCRIPTION = "Description"
const val LOCAL_REPOSITORIES_FORKS = "Forks"
const val LOCAL_REPOSITORIES_WATCHERS = "Watchers"
const val LOCAL_REPOSITORIES_CREATED_AT = "CreatedAt"
const val LOCAL_REPOSITORIES_ID_SAVED_USERS = "IdSavedUsers"

class SQLiteHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

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

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertUser(userForLocal: UserForLocal) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(USERS_COL_ID, userForLocal.id)
        cv.put(USERS_COL_EMAIL, userForLocal.email)
        cv.put(USERS_COL_IMG_URL, userForLocal.photoUrl)
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

    @SuppressLint("Range")
    fun getUser(): UserForLocal {
        val list: MutableList<UserForLocal> = ArrayList()
        val user = UserForLocal()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME_USERS"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                user.id = result.getString(result.getColumnIndex(USERS_COL_ID)).toString()
                user.email = result.getString(result.getColumnIndex(USERS_COL_EMAIL)).toString()
                user.photoUrl = result.getString(result.getColumnIndex(USERS_COL_IMG_URL)).toString()
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return user
    }

    fun deleteUser(idUser: String) {
        val db = this.writableDatabase
        val value = arrayOf(idUser)
        db.delete(TABLE_NAME_USERS, "$USERS_COL_ID = ?", value)
        db.close()
    }
}