package com.example.orbi.ui.createcycle


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.orbi.databinding.FragmentStepThreeBinding


// O Fragmento do terceiro passo com seletores de tempo versáteis :
class StepThreeFragment : Fragment() {


    private var _binding: FragmentStepThreeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateCycleViewModel by activityViewModels()


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?


    ): View {


        _binding = FragmentStepThreeBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        setupNumberPickers()


    }


    private fun setupNumberPickers() {


        // Configura os limites numéricos para a rotação :
        binding.pickerHours.minValue = 0
        binding.pickerHours.maxValue = 12


        binding.pickerMinutes.minValue = 0
        binding.pickerMinutes.maxValue = 59


        // Resgata o valor atual da ViewModel (padrão é 25 min) para as roletas não resetarem se o usuário voltar a tela :
        val currentTotalMinutes = viewModel.formState.value.focusMinutes
        binding.pickerHours.value = currentTotalMinutes / 60
        binding.pickerMinutes.value = currentTotalMinutes % 60


        // Atualiza a ViewModel instantaneamente ao rolar a coluna das horas :
        binding.pickerHours.setOnValueChangedListener { _, _, _ ->


            sendTimeToViewModel()


        }


        // Atualiza a ViewModel instantaneamente ao rolar a coluna dos minutos :
        binding.pickerMinutes.setOnValueChangedListener { _, _, _ ->


            sendTimeToViewModel()


        }


    }


    // Converte a escolha visual num formato único (total de minutos) e injeta na ViewModel :
    private fun sendTimeToViewModel() {


        val hours = binding.pickerHours.value
        val minutes = binding.pickerMinutes.value
        val totalMinutes = (hours * 60) + minutes
        viewModel.updateFocusMinutes(totalMinutes)


    }


    override fun onDestroyView() {


        super.onDestroyView()
        _binding = null


    }


}