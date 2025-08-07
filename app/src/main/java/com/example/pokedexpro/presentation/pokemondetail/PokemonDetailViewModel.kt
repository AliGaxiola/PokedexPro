package com.example.pokedexpro.presentation.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexpro.data.remote.RetrofitInstance
import com.example.pokedexpro.data.remote.toDomain
import com.example.pokedexpro.domain.model.PokemonDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel: ViewModel() {

    private val _pokemon = MutableStateFlow<PokemonDetail?>(null)
    val pokemon: StateFlow<PokemonDetail?> = _pokemon

    fun loadPokemon(id: Int, nombre: String){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPokemonDetail(nombre.lowercase())
                _pokemon.value = response.toDomain(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}