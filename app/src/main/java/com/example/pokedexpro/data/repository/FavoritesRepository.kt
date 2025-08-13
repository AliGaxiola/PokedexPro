package com.example.pokedexpro.data.repository

import com.example.pokedexpro.data.local.FavoritePokemonDao
import com.example.pokedexpro.data.local.toFavoritePokemon
import com.example.pokedexpro.data.local.toPokemon
import com.example.pokedexpro.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepository(
    private val dao: FavoritePokemonDao
){
    fun getAllFavorites(): Flow<List<Pokemon>> {
        return dao.getAllFavorites().map { favoritesList ->
            favoritesList.map { it.toPokemon() }
        }
    }

    suspend fun isFavorite(pokemonId: Int): Boolean {
        return dao.isFavorite(pokemonId)
    }

    suspend fun toggleFavorite(pokemon: Pokemon) {
        if (dao.isFavorite(pokemon.id)) {
            dao.removeFavoriteById(pokemon.id)
        } else {
            dao.addFavorite(pokemon.toFavoritePokemon())
        }
    }

    suspend fun addFavorite(pokemon: Pokemon) {
        dao.addFavorite(pokemon.toFavoritePokemon())
    }

    suspend fun removeFavorite(pokemon: Pokemon) {
        dao.removeFavoriteById(pokemon.id)
    }
}