package com.example.pokedexpro.data.remote

import com.example.pokedexpro.domain.model.PokemonDetail

data class PokemonDetailResponse(
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>
)

data class TypeSlot(
    val type: Type
)

data class Type(
    val name: String
)

//Mapper
fun PokemonDetailResponse.toDomain(id: Int): PokemonDetail {
    val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"

    return PokemonDetail(
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = imageUrl,
        weight = weight,
        height = height,
        types = types.map { it.type.name }
    )
}
