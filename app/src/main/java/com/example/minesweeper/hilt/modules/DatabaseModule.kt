package com.example.minesweeper.hilt.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.minesweeper.room.dao.GameDao
import com.example.minesweeper.room.GameDatabase
import com.example.minesweeper.room.dao.ScoreDao
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
        return gameDatabase.getGameDao()
    }

    @Provides
    fun provideScoreDao(gameDatabase: GameDatabase): ScoreDao {
        return gameDatabase.getScoreDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) : GameDatabase {
        return Room.databaseBuilder(
            appContext,
            GameDatabase::class.java,
            "game_database"
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO score_table VALUES(1, 0, 0)")
                }
            }
        ).build()
    }
}
