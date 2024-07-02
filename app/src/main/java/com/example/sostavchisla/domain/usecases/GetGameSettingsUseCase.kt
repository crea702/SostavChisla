package com.example.sostavchisla.domain.usecases

import com.example.sostavchisla.domain.Entity.GameSettings
import com.example.sostavchisla.domain.Entity.Level
import com.example.sostavchisla.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level) : GameSettings {
        return repository.getGameSettings(level)
    }
}