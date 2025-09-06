package com.hiutin.jetpackcomposetodo.data.mappers

import com.hiutin.jetpackcomposetodo.common.extensions.toComposeColor
import com.hiutin.jetpackcomposetodo.common.extensions.toHex
import com.hiutin.jetpackcomposetodo.data.local.entities.CategoryEntity
import com.hiutin.jetpackcomposetodo.domain.models.Category

fun CategoryEntity.toModel(): Category {
    return Category(id, name, color.toComposeColor())
}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(id, name, color.toHex())
}
