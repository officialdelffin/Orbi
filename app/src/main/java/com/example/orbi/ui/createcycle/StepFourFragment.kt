package com.example.orbi.ui.createcycle


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.orbi.databinding.FragmentStepFourBinding


// O Fragmento do quarto passo focado exclusivamente nas repetições :
class StepFourFragment : Fragment() {


    private var _binding: FragmentStepFourBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateCycleViewModel by activityViewModels()


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?


    ): View {


        _binding = FragmentStepFourBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        setupNumberPicker()


    }


    private fun setupNumberPicker() {


        // Mínimo de 1 sessão (rodar uma vez) até 20 repetições seguidas :
        binding.pickerRepetitions.minValue = 1
        binding.pickerRepetitions.maxValue = 20


        // Resgata o valor atual da ViewModel :
        binding.pickerRepetitions.value = viewModel.formState.value.repetitions


        // Atualiza a ViewModel instantaneamente ao rolar o seletor :
        binding.pickerRepetitions.setOnValueChangedListener { _, _, newVal ->


            viewModel.updateRepetitions(newVal)


        }


    }


    override fun onDestroyView() {


        super.onDestroyView()
        _binding = null


    }


}