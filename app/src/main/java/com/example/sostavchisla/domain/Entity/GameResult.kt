package com.example.sostavchisla.domain.Entity

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions : Int,
    val gameSettings: GameSettings
)