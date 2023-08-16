package com.awas.awas_final.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors

@Database(entities = [Score::class], version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun noteDAO(): ScoreDAO?

    companion object {
        @Volatile
        private var db: AppDB? = null

        fun getDB(context: Context): AppDB? { //function to return the instance
            if (db == null) {
                //create the data base instance only if it is null

                db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "db_score" //this is the database name we set
                )
                    .fallbackToDestructiveMigration()
                    .build()

            }
            //otherwise , return already existing instance (not creating more than one db)
            return db
        }

        //for using the background threads for data base operations
        private const val NUMBER_OF_THREADS = 4
        val dataBaseWriExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS) //allows u to run concurrent tasks in the background
    }
}
