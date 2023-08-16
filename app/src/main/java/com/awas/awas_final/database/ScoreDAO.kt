package com.awas.awas_final.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDAO {

    @Insert
    fun insertNote(note:Score?)

    @Query("SELECT * FROM table_scores ORDER BY name DESC")//the table name is in the entity class
    fun getAllNotes(): Flow<List<Score>>

}