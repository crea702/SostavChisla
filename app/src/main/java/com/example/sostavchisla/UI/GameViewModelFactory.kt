package com.example.sostavchisla.UI

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sostavchisla.domain.Entity.Level

class GameViewModelFactory(
    private val level: Level,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GameVieModel::class.java)) {
            return GameVieModel(application, level) as T
        } else{
            throw RuntimeException(" Unknown class $modelClass")
        }
    }
}