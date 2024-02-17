Functional Requirements:
  - Game board: Grid-based board consisting of cells.
  - Mines: Game should randomly place certain number of mines on board.
  - Cell Reveal: Players should be able to click on cells to reveal what's underneath.
  - Win condition: Players win the game when all non mine cells are revealed.
  - Lose condition: Game ends if the player click on a cell containing a mine.
  - Timer: Timer to track the time taken by the player.
  - Restart Option: Players should be able to restart the game at any time, either from the beginning or from the current state.
  - Save/Load Game: The game might allow players to save their progress and resume it later.
  - Playing condition 1: First click on a cell should never be mine.

Nonfunctional Requirements:
  - Performance: Game should be responsive and run smoothly. Meaning fast loading time, quick response from the player's action
  - Reliability: The game should be stable and free from crashes or unexpected errors. It should handle unexpected inputs gracefully and recover from errors without losing game progress.

Important Articles:
  - https://proandroiddev.com/debugging-the-recomposition-in-jetpack-compose-16e92cbc9c6
  - "Donot hole skipping" in compose: https://www.jetpackcompose.app/articles/donut-hole-skipping-in-jetpack-compose
  - https://developer.android.com/jetpack/compose/side-effects
