package com.example.orbi.ui.createcycle


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.orbi.databinding.FragmentStepFiveBinding


// O Fragmento do quinto e último passo definindo o tempo de pausa :
class StepFiveFragment : Fragment() {


    private var _binding: FragmentStepFiveBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateCycleViewModel by activityViewModels()


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?


    ): View {


        _binding = FragmentStepFiveBinding.inflate(inflater, container, false)
        return binding.root


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        setupNumberPickers()


    }


    private fun setupNumberPickers() {


        // Limites de tempo de pausa :
        binding.pickerHours.minValue = 0
        binding.pickerHours.maxValue = 12


        binding.pickerMinutes.minValue = 0
        binding.pickerMinutes.maxValue = 59


        // Resgata o valor atual da ViewModel :
        val currentBreakMinutes = viewModel.formState.value.breakMinutes
        binding.pickerHours.value = currentBreakMinutes / 60
        binding.pickerMinutes.value = currentBreakMinutes % 60


        // Listeners para atualizar a ViewModel :
        val pickerListener = android.widget.NumberPicker.OnValueChangeListener { _, _, _ ->


            sendTimeToViewModel()


        }


        binding.pickerHours.setOnValueChangedListener(pickerListener)
        binding.pickerMinutes.setOnValueChangedListener(pickerListener)


    }


    private fun sendTimeToViewModel() {


        val hours = binding.pickerHours.value
        val minutes = binding.pickerMinutes.value
        val totalMinutes = (hours * 60) + minutes


        viewModel.updateBreakMinutes(totalMinutes)


    }


    override fun onDestroyView() {


        super.onDestroyView()
        _binding = null


    }


}