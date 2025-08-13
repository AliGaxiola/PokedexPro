package com.example.pokedexpro.presentation.pokemondetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexpro.data.local.PokedexDatabase
import com.example.pokedexpro.data.remote.RetrofitInstance
import com.example.pokedexpro.data.remote.toDomain
import com.example.pokedexpro.data.repository.FavoritesRepository
import com.example.pokedexpro.domain.model.Pokemon
import com.example.pokedexpro.domain.model.PokemonDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _pokemon = MutableStateFlow<PokemonDetail?>(null)
    val pokemon: StateFlow<PokemonDetail?> = _pokemon

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val favoritesRepository: FavoritesRepository

    init {
        val database = PokedexDatabase.getDatabase(application)
        favoritesRepository = FavoritesRepository(database.favoritePokemonDao())
    }

    fun loadPokemon(id: Int, nombre: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getPokemonDetail(nombre.lowercase())
                _pokemon.value = response.toDomain(id)

                _isFavorite.value = favoritesRepository.isFavorite(id)

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite() {
        val currentPokemon = _pokemon.value ?: return

        viewModelScope.launch {
            try {
                val pokemonBasic = Pokemon(
                    id = extractIdFromDetail(currentPokemon),
                    name = currentPokemon.name,
                    imageUrl = currentPokemon.imageUrl
                )

                favoritesRepository.toggleFavorite(pokemonBasic)

                _isFavorite.value = !_isFavorite.value

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun extractIdFromDetail(pokemon: PokemonDetail): Int {
        return pokemon.imageUrl
            .substringAfterLast("/")
            .substringBeforeLast(".")
            .toIntOrNull() ?: 0
    }
}