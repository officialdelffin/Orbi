package com.example.orbi.ui.createcycle


// Importações :
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.orbi.R
import com.example.orbi.databinding.FragmentStepTwoBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch


// O Fragmento do segundo passo (Seleção dos Dias da Semana) :
class StepTwoFragment : Fragment() {

    private var _binding: FragmentStepTwoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateCycleViewModel by activityViewModels()


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?


    ): View {


        _binding = FragmentStepTwoBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModel()


    }


    // Vincula cada botão a um número de 1 a 7 (Domingo a Sábado) :
    private fun setupClickListeners() {


        val dayButtons = mapOf(


            1 to binding.btnDay1,
            2 to binding.btnDay2,
            3 to binding.btnDay3,
            4 to binding.btnDay4,
            5 to binding.btnDay5,
            6 to binding.btnDay6,
            7 to binding.btnDay7


        )


        dayButtons.forEach { (dayInt, button) ->


            button.setOnClickListener {


                viewModel.toggleDay(dayInt)


            }


        }


    }


    // Observa o StateFlow e pinta os botões automaticamente se o dia estiver no Conjunto (Set) :
    private fun observeViewModel() {


        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.formState.collect { state ->


                updateButtonVisuals(1, binding.btnDay1, state.selectedDays)
                updateButtonVisuals(2, binding.btnDay2, state.selectedDays)
                updateButtonVisuals(3, binding.btnDay3, state.selectedDays)
                updateButtonVisuals(4, binding.btnDay4, state.selectedDays)
                updateButtonVisuals(5, binding.btnDay5, state.selectedDays)
                updateButtonVisuals(6, binding.btnDay6, state.selectedDays)
                updateButtonVisuals(7, binding.btnDay7, state.selectedDays)
            }


        }


    }


    // Aplica a cor preenchida (Olive) se selecionado, ou vazado (Cream) se não selecionado :
    private fun updateButtonVisuals(dayInt: Int, button: MaterialButton, selectedDays: Set<Int>) {


        val isSelected = selectedDays.contains(dayInt)
        val context = requireContext()


        if (isSelected) {


            button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.orbi_olive))
            button.setTextColor(ContextCompat.getColor(context, R.color.orbi_cream))


        } else {


            button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.transparent))
            button.setTextColor(ContextCompat.getColor(context, R.color.orbi_dark_brown))
        }


    }


    override fun onDestroyView() {


        super.onDestroyView()
        _binding = null


    }


}