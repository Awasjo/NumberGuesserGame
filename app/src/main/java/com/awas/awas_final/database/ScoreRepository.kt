package com.awas.awas_final.database

import android.app.Application
import kotlinx.coroutines.flow.Flow

class ScoreRepository(application: Application) {
    private val db : AppDB?
    private val noteDAO : ScoreDAO?
    var allNotes : Flow<List<Score>>?

    init {
        db = application?.let{AppDB.getDB(it)}
        noteDAO = application?.let {AppDB.getDB(application)?.noteDAO()}
        allNotes = noteDAO?.getAllNotes()
    }

    fun insertNote(newScore: Score){
        AppDB.dataBaseWriExecutor.execute{
            noteDAO?.insertNote(newScore)
        }
    }


}