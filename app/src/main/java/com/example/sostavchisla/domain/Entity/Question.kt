package com.example.sostavchisla.domain.Entity

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
)
