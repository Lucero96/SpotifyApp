package dev.layala.spotifyapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.layala.spotifyapp.presentation.auth.LoginScreen
import dev.layala.spotifyapp.presentation.auth.RegisterScreen
import dev.layala.spotifyapp.presentation.home.HomeScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val SEARCH = "search"
    const val LIBRARY = "library"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.REGISTER) { RegisterScreen(navController) }

        // Home real con bottom bar
        composable(Routes.HOME) { HomeScreen(navController) }

        // Pantallas de relleno para que funcione la bottom bar ya
        composable(Routes.SEARCH) {
            // Puedes crear una pantalla Search real luego
            androidx.compose.material3.Scaffold { padding ->
                Text("Search screen", modifier = androidx.compose.ui.Modifier.padding(padding))
            }
        }
        composable(Routes.LIBRARY) {
            androidx.compose.material3.Scaffold { padding ->
                Text("Your Library screen", modifier = androidx.compose.ui.Modifier.padding(padding))
            }
        }
    }
}
