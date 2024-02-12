package com.example.minesweeper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minesweeper.room.GameDao

class ViewModelFactory(private val dao: GameDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GameDao::class.java).newInstance(dao)
    }
}