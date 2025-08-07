package com.example.pokedexpro.domain.model

data class PokemonDetail(
    val name: String,
    val imageUrl: String,
    val weight: Int,
    val height: Int,
    val types: List<String>
)
