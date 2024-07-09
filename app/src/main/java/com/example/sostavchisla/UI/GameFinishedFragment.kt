package com.example.sostavchisla.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.sostavchisla.R
import com.example.sostavchisla.databinding.FragmentGameFinishedBinding
import com.example.sostavchisla.domain.Entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var binding: FragmentGameFinishedBinding
    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        bindViews()
    }

    private fun setupClickListeners(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.buttonRetry.setOnClickListener{
            retryGame()
        }
    }

    private fun bindViews() {

        binding.emojiResult.setImageResource(getSmileResId())
        binding.tvRequiredAnswers.text = String.format(
            getString(R.string.required_score),
            gameResult.gameSettings.minCountOfRightAnswers.toString()
        )

        binding.tvScoreAnswers.text = String.format(
            getString(R.string.score_answers),
            gameResult.countOfRightAnswers.toString()
        )

        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.required_percentage),
            gameResult.gameSettings.minPercentOfRightAnswers.toString()
        )
        binding.tvScorePercentage.text = String.format(
            getString(R.string.score_percentage),
            getPercentOfRightAnswer().toString()
        )

    }

    private fun getSmileResId() : Int{
        return if (gameResult.winner){
            R.drawable.ic_smile
        }else{
            R.drawable.nid
        }
    }

    private fun getPercentOfRightAnswer() = with(gameResult){
        if (countOfRightAnswers == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble())*100).toInt()
        }
    }


    private fun parseArgs() {
        gameResult = requireArguments().getSerializable(KEY_GAME_RESULT) as GameResult
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    companion object{

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}