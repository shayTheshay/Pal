package com.example.pal.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_table")
data class Cat(
    val min_weight: Float,
    val max_weight: Float,
    val length: String,
    val origin: String,
    @PrimaryKey
    val name: String,
    val image_link: String,
    val min_life_expectancy: Float,
    val max_life_expectancy: Float,
    val grooming: Int,
    val children_friendly: Int,
    val other_pets_friendly: Int,
    val shedding: Int,
    val playfulness: Int,
    val family_friendly: Int,
    val general_health: Int,
    val meowing: Int,
    val stranger_friendly: Int,
    val intelligence: Int
) {}


