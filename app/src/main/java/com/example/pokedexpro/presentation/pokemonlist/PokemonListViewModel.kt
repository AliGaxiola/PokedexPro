package com.example.pokedexpro.presentation.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexpro.data.remote.PokemonResult
import com.example.pokedexpro.data.remote.RetrofitInstance
import com.example.pokedexpro.data.remote.toPokemon
import com.example.pokedexpro.domain.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    fun loadPokemons() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPokemonList(limit = 20, offset = 0)
                _pokemonList.value = response.results.map { it.toPokemon() }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}