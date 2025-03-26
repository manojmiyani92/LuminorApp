package com.luminor.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luminor.infrastructure.db.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: User)

    @Query("SELECT * FROM user where email=:email and password=:password limit 1")
    suspend fun loadUser(email: String, password: String): User?

    @Query("SELECT EXISTS (SELECT * FROM user where email=:email)")
    suspend fun checkUserExist(email: String): Boolean

    @Query("SELECT * FROM user where uid=:id limit 1")
    suspend fun loadUserById(id: Int): User?

}