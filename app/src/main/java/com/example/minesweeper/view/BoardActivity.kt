package com.example.minesweeper.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.minesweeper.ui.screen.BoardScreen
import com.example.minesweeper.ui.screen.MainScreen
import com.example.minesweeper.ui.screen.ScoreScreen
import com.example.minesweeper.ui.theme.MinesweeperTheme
import com.example.minesweeper.utils.LogComposition
import com.example.minesweeper.utils.TAG
import com.example.minesweeper.viewmodel.BoardViewModel
import dagger.hilt.android.AndroidEntryPoint

enum class ScreenRoute {
    Main,
    Board,
    Score
}

@AndroidEntryPoint
class BoardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MinesweeperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MinesApp(navController = navController)
                }
            }
        }
    }
}

@Composable
fun MinesApp(navController: NavHostController) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        currentBackStackEntry?.let { navBackStackEntry ->
            if (navBackStackEntry.destination.route == ScreenRoute.Main.name) {
//                viewModel.resumeOrNotGame()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Main.name,
        modifier = Modifier.padding(4.dp)
        ) {
        composable(ScreenRoute.Main.name) {
            MainScreen(
                onBoardClicked = {
                    navController.navigate(ScreenRoute.Board.name)
                },
                onScoreClicked = {
                    navController.navigate(ScreenRoute.Score.name)
                }
            )
        }
        composable(ScreenRoute.Board.name) {
            BoardScreen()
        }
        composable(ScreenRoute.Score.name) {
            ScoreScreen()
        }
    }
}
