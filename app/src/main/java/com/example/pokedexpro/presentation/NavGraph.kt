package com.example.pokedexpro.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokedexpro.presentation.pokemondetail.PokemonDetailScreen
import com.example.pokedexpro.presentation.pokemonlist.PokemonListScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "pokemon_list",
        modifier = modifier
    ){
        composable("pokemon_list") {
            PokemonListScreen(navController)
        }

        composable("pokemon_detail/{pokemonName}"){ backStackEntry ->
            val name = backStackEntry.arguments?.getString("pokemonName") ?: ""
            PokemonDetailScreen(pokemonName = name)
        }
    }
}