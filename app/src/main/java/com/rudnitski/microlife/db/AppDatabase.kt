package com.rudnitski.microlife.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.ListenableWorker
import androidx.work.workDataOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate

@Database(
    entities = [Habit::class, User::class, HabitsJournal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class
AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun userDao(): UserDao
    abstract fun habitsJournalDao(): HabitsJournalDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "microlife")
                    .addCallback(HabitDatabaseCallback(scope, context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class HabitDatabaseCallback(
        private val scope: CoroutineScope,
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    try {
                        val filename = workDataOf("my_filename" to "habits.json").getString("my_filename")
                        Log.e("corotine", "Error seeding database - no valid filename: $filename")
                        if (filename != null) {
                            Log.e("corotine", "Error seeding database - no valid filename: $filename != null!!!!!")
                            context.applicationContext.assets.open(filename).use { inputStream ->
                                JsonReader(inputStream.reader()).use { jsonReader ->
                                    Log.e("corotine", "Error seeding database - no valid : here")
                                    val habitType = object : TypeToken<List<Habit>>() {}.type
                                    Log.e("corotine", "Error seeding database - no valid : habitType")
                                    val habitList: List<Habit> = Gson().fromJson(jsonReader, habitType)
                                    Log.e("corotine", "Error seeding database - no valid filename: " + habitList.size)

                                    database.habitDao().insertAll(habitList)
                                    database.userDao().insertUser(User(1, Gender.FEMALE))

                                    Log.e("corotine", "Error seeding database -success")
                                    ListenableWorker.Result.success()
                                }
                            }
                        } else {
                            Log.e("corotine", "Error seeding database -failure one ")
                            ListenableWorker.Result.failure()
                        }
                    } catch (ex: Exception) {
                        Log.e("corotine", "Error seeding database -failure two ", ex)
                        ListenableWorker.Result.failure()
                    }
                }
            }
        }
    }
}