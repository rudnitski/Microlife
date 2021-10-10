package com.rudnitski.microlife.di

import android.content.Context
import com.rudnitski.microlife.db.AppDatabase
import com.rudnitski.microlife.db.HabitDao
import com.rudnitski.microlife.db.HabitsJournalDao
import com.rudnitski.microlife.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context, scope: CoroutineScope): AppDatabase {
        return AppDatabase.getDatabase(context, scope)
    }

    @Provides
    fun provideHabitDao(appDatabase: AppDatabase): HabitDao {
        return appDatabase.habitDao()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideHabitsJournalDao(appDatabase: AppDatabase): HabitsJournalDao {
        return appDatabase.habitsJournalDao()
    }
}