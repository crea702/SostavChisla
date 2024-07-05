package com.example.sostavchisla.domain.Entity

import java.io.Serializable

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions : Int,
    val gameSettings: GameSettings
): Serializable