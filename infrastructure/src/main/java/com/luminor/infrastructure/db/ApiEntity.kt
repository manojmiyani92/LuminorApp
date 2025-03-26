package com.luminor.infrastructure.db


interface ApiEntity<D : DbEntity> {

    fun asDbEntity(): D
}