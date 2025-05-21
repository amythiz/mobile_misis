package ru.rinchino.api_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.rinchino.api_app.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!! // безопасный биндинг, которая всегда не null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false) // инициализируем биндинг
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGroup1.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_group1Fragment)
            // переход к фрагменту
        }

        binding.buttonGroup2.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_group2Fragment)

            // переход к фрагменту
        }

        binding.buttonGroup3.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_group3Fragment)

            // переход к фрагменту
        }

        binding.buttonGroup4.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_group4Fragment)

            // переход к фрагменту
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}