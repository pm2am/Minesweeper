package com.example.minesweeper.hilt.modules

import android.content.Context
import androidx.room.Room
import com.example.minesweeper.room.GameDao
import com.example.minesweeper.room.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideGameDao(gameDatabase: GameDatabase): GameDao {
        return gameDatabase.getDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) : GameDatabase {
        return Room.databaseBuilder(
            appContext,
            GameDatabase::class.java,
            "game_database"
        ).build()
    }
}
