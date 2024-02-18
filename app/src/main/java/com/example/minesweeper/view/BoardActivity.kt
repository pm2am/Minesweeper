package com.example.minesweeper.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minesweeper.ui.screen.Board
import com.example.minesweeper.ui.screen.MainScreen
import com.example.minesweeper.ui.screen.ScoreScreen
import com.example.minesweeper.ui.theme.MinesweeperTheme
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
            val viewModel: BoardViewModel = hiltViewModel()
            val navController = rememberNavController()
            MinesweeperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MinesApp(viewModel = viewModel, navController = navController)
                }
            }
        }
    }
}

@Composable
fun MinesApp(viewModel: BoardViewModel, navController: NavHostController) {
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
            Board(viewModel = viewModel)
        }
        composable(ScreenRoute.Score.name) {
            ScoreScreen(viewModel = viewModel)
        }
    }
}
