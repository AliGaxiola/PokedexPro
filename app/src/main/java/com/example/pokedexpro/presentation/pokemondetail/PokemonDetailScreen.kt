package com.example.pokedexpro.presentation.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun PokemonDetailScreen(
    id: Int,
    pokemonName: String,
    viewModel: PokemonDetailViewModel = viewModel()
) {

    val pokemon by viewModel.pokemon.collectAsState()

    LaunchedEffect(pokemonName) {
        viewModel.loadPokemon(id, pokemonName)
    }

    pokemon?.let { p ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(p.imageUrl),
                contentDescription = p.name,
                modifier = Modifier.size(200.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(text = "Name: ${p.name}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Height: ${p.height}")
            Text(text = "Weight: ${p.weight}")
            Text(text = "Types: ${p.types.joinToString(", ")}")
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}