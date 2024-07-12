package com.example.sostavchisla.domain.repository

import com.example.sostavchisla.domain.Entity.GameSettings
import com.example.sostavchisla.domain.Entity.Level
import com.example.sostavchisla.domain.Entity.Question

interface GameRepository {

    fun generateQuestions(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}