package com.luminor.infrastructure.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luminor.infrastructure.db.ApiEntity
import com.luminor.infrastructure.db.DbEntity
import com.luminor.infrastructure.utils.passwordEncryption


@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
) : DbEntity, ApiEntity<User> {
    override fun asDbEntity(): User {
        return this.copy(password = password.passwordEncryption())
    }
}
