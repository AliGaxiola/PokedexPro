package com.example.pokedexpro.presentation.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexpro.data.remote.RetrofitInstance
import com.example.pokedexpro.data.remote.toPokemon
import com.example.pokedexpro.domain.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PokemonListViewModel : ViewModel() {

    private val _allPokemons = MutableStateFlow<List<Pokemon>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            combine(_allPokemons, _searchQuery){ pokemons, query ->
                if(query.isBlank()){
                    pokemons
                } else {
                    pokemons.filter { pokemon ->
                        pokemon.name.contains(query, ignoreCase = true)
                    }
                }
            }.collect { filteredList ->
                _pokemonList.value = filteredList
            }
        }
    }

    fun loadPokemons(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getPokemonList(151, 0)
                val pokemonList  = response.results.map { it.toPokemon() }
                _allPokemons.value = pokemonList
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch(){
        _searchQuery.value = ""
    }
}