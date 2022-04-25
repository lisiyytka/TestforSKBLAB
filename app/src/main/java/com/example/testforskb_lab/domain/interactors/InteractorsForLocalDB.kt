package com.example.testforskb_lab.domain.interactors

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import com.example.testforskb_lab.data.SQLite.*
import com.example.testforskb_lab.data.repository.LocalRepository
import com.example.testforskb_lab.domain.model.OwnerConstructor
import com.example.testforskb_lab.domain.model.RepositoriesConstructor
import com.example.testforskb_lab.domain.modelForLocalDB.ReposForLocal
import com.example.testforskb_lab.domain.modelForLocalDB.UserForLocal
import javax.inject.Inject

class InteractorsForLocalDB @Inject constructor(private val localRepository: LocalRepository){

    fun insertUser(userForLocal: UserForLocal) {
        val db = localRepository.writableDatabase
        val cv = ContentValues()
        cv.put(USERS_COL_ID, userForLocal.id)
        cv.put(USERS_COL_EMAIL, userForLocal.email)
        cv.put(USERS_COL_IMG_URL, userForLocal.photoUrl)
        db.insert(TABLE_NAME_USERS, null, cv)
    }

    fun insertSavedRepos(reposForLocal: ReposForLocal) {
        val db = localRepository.writableDatabase
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
        var db = localRepository.readableDatabase
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
        val db = localRepository.writableDatabase
        val values = arrayOf(idUser, nameRepos)
        db.delete(
            TABLE_NAME_LOCAL_REPOSITORIES,
            "$LOCAL_REPOSITORIES_ID_SAVED_USERS = ? AND $LOCAL_REPOSITORIES_FULL_NAME = ?",
            values
        )
        db.close()
    }

    fun deleteSavedRepos(idUser: String) {
        val db = localRepository.writableDatabase
        val value = arrayOf(idUser)
        db.delete(TABLE_NAME_LOCAL_REPOSITORIES, "$LOCAL_REPOSITORIES_ID_SAVED_USERS = ?", value)
        db.close()
    }

    @SuppressLint("Range")
    fun getUser(): UserForLocal {
        val user = UserForLocal()
        val db = localRepository.readableDatabase
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
        val db = localRepository.writableDatabase
        val value = arrayOf(idUser)
        db.delete(TABLE_NAME_USERS, "$USERS_COL_ID = ?", value)
        db.close()
    }
}