package com.example.pokedexpro.presentation.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokedexpro.ui.theme.TypeColors

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

        val mainColor = TypeColors[p.types.firstOrNull()] ?: MaterialTheme.colorScheme.primary

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(mainColor, Color.White)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(Modifier.height(32.dp))

                Image(
                    painter = rememberAsyncImagePainter(p.imageUrl),
                    contentDescription = p.name,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = p.name,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(Modifier.height(16.dp))

                        Text("Altura: ${p.height}", style = MaterialTheme.typography.bodyLarge)
                        Text("Peso: ${p.weight}", style = MaterialTheme.typography.bodyLarge)

                        Spacer(Modifier.height(16.dp))

                        Text("Tipos:", style = MaterialTheme.typography.bodyLarge)

                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            p.types.forEach { type ->
                                val chipColor =
                                    TypeColors[type] ?: MaterialTheme.colorScheme.secondary
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = chipColor,
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                ) {
                                    Text(
                                        text = type.uppercase(),
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        ),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    } ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}