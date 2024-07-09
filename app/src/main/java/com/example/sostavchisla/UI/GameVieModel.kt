package com.example.sostavchisla.UI

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sostavchisla.R
import com.example.sostavchisla.domain.Entity.GameResult
import com.example.sostavchisla.domain.Entity.GameSettings
import com.example.sostavchisla.domain.Entity.Level
import com.example.sostavchisla.domain.Entity.Question
import com.example.sostavchisla.domain.GameRepositoryImpl

import com.example.sostavchisla.domain.usecases.GenerateQuestionsUseCase
import com.example.sostavchisla.domain.usecases.GetGameSettingsUseCase

class GameVieModel(application: Application) : AndroidViewModel(application){

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val context = application

    private val repository = GameRepositoryImpl

    private val generateQuestionsUseCase = GenerateQuestionsUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer: CountDownTimer? = null

    private val _formatedTime = MutableLiveData<String>()
    val formatedTime: LiveData<String>
        get() = _formatedTime

    private val _question = MutableLiveData<Question>()
    val question : LiveData<Question>
        get() = _question

    private var _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer : LiveData<Int>
        get() = _percentOfRightAnswer

    private var _progressAnswers = MutableLiveData<String>()
    val progressAnswers : LiveData<String>
        get() = _progressAnswers

    private var _enoughCountOfRightAnswer = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswer : LiveData<Boolean>
        get() = _enoughCountOfRightAnswer

    private var _enoughPercentOfRightAnswer = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswer : LiveData<Boolean>
        get() = _enoughPercentOfRightAnswer

    private val _minPercent = MutableLiveData<Int>()
    val  minPercent : LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val  gameResult : LiveData<GameResult>
        get() = _gameResult


     private var countOfRightAnswer = 0
     private var countOfQuestion = 0

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generatedQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generatedQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswer.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswer.toString(),
            gameSettings.minCountOfRightAnswers.toString()
        )
        _enoughCountOfRightAnswer.value = countOfRightAnswer >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswer.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers() : Int{
        if (countOfQuestion == 0) {
            return 0
        }
        return ((countOfRightAnswer / countOfQuestion.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswer++
        }
        countOfQuestion++
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
             timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(p0: Long) {
                _formatedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generatedQuestion() {
        _question.value = generateQuestionsUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(p0: Long) : String{
        val seconds = p0 / MILLIS_IN_SECONDS
        val minutes =  seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)

    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = enoughCountOfRightAnswer.value == true && enoughPercentOfRightAnswer.value == true,
            countOfRightAnswers = countOfRightAnswer,
            countOfQuestions = countOfQuestion,
            gameSettings = gameSettings


        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object{
        private const val MILLIS_IN_SECONDS =1000L
        private const val SECONDS_IN_MINUTES = 60
    }


}