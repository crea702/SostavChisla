package com.example.sostavchisla.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sostavchisla.R
import com.example.sostavchisla.databinding.FragmentGameBinding
import com.example.sostavchisla.domain.Entity.GameResult
import com.example.sostavchisla.domain.Entity.GameSettings
import com.example.sostavchisla.domain.Entity.Level


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var level: Level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvOption1.setOnClickListener{
            launchGameFinishedFragment(
                GameResult(true,
                0,
                0,
                GameSettings(0,0,0,0)
                )
            )
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()

    }

    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    companion object{

        private const val KEY_LEVEL = "level"
        const val NAME ="GameFragment"

        fun newInstance(level: Level): GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}