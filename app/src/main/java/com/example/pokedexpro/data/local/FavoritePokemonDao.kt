package com.example.pokedexpro.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDao {
    @Query("SELECT * FROM favorite_pokemon ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<FavoritePokemon>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pokemon WHERE id = :pokemonId)")
    suspend fun isFavorite(pokemonId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(pokemon: FavoritePokemon)

    @Delete
    suspend fun removeFavorite(pokemon: FavoritePokemon)

    @Query("DELETE FROM favorite_pokemon WHERE id = :pokemonId")
    suspend fun removeFavoriteById(pokemonId: Int)

    @Query("SELECT * FROM favorite_pokemon WHERE id = :pokemonId")
    suspend fun getFavoriteById(pokemonId: Int): FavoritePokemon?
}