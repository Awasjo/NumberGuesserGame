package com.awas.awas_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.awas.awas_final.database.Score
import com.awas.awas_final.database.ScoreRepository
import com.awas.awas_final.databinding.ActivityScoreBinding
import kotlinx.coroutines.launch
class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding
    private val TAG = this.javaClass.canonicalName

    private lateinit var scoreAdapter: ScoreAdpater
    private lateinit var scoreRepository: ScoreRepository
    private val _noteList: MutableList<Score> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        scoreAdapter = ScoreAdpater(_noteList)
        scoreRepository = ScoreRepository(this.application)

        try {
            //get data from DB and show it //this is a background process, the collect function will provide us list of notes. it is the same as noteList in this case. its standard to use it
            lifecycle.coroutineScope.launch {
                scoreRepository.allNotes?.collect() {
                    Log.d(TAG, "onCreate: data received ${it}")

                    _noteList.clear()
                    _noteList.addAll(it)
                    scoreAdapter.notifyDataSetChanged()
                }
            }


        } catch (ex: Exception) {
            Log.e(TAG, "Failed to fetch data", ex)
        }

        binding.rvScore.layoutManager = LinearLayoutManager(this)
        binding.rvScore.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.rvScore.adapter = scoreAdapter
    }

}