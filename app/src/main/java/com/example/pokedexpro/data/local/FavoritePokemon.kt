package com.example.pokedexpro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexpro.domain.model.Pokemon

@Entity(tableName = "favorite_pokemon")
data class FavoritePokemon(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String
)

fun Pokemon.toFavoritePokemon(): FavoritePokemon {
    return FavoritePokemon(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )
}

fun FavoritePokemon.toPokemon(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )
}