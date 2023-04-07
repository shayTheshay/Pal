package com.example.pal.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    val name: String = "",
    val email: String = "",
    val phone: String? = "",
    val favorites: List<String> = listOf()
)