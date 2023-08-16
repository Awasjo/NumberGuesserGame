package com.awas.awas_final.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_scores")
class Score(val name:String, val win:Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    override fun toString(): String {
        return "Score{" +
                ", name='" + name + '\'' +
                ", win='" + win + '\'' +
                '}'
    }
}