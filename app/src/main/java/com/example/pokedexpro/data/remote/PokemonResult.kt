package com.example.pokedexpro.data.remote

import com.example.pokedexpro.domain.model.Pokemon

fun PokemonResult.toPokemon(): Pokemon {
    val id = url.trimEnd('/').split("/").last().toInt()
    val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

    return Pokemon(
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = imageUrl
    )
}