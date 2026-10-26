package com.example.orbi.ui.createcycle


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.orbi.databinding.FragmentStepOneBinding


// O Fragmento do primeiro passo (Título e Descrição) :
class StepOneFragment : Fragment() {


    private var _binding: FragmentStepOneBinding? = null
    private val binding get() = _binding!!


    // Conecta-se à mesma instância da ViewModel usada na CreateCycleActivity :
    private val viewModel: CreateCycleViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        _binding = FragmentStepOneBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        setupInputs()


    }


    private fun setupInputs() {


        // Atualiza o estado da ViewModel em tempo real enquanto o usuário digita o título :
        binding.etTitle.doAfterTextChanged { text ->


            viewModel.updateTitle(text.toString())


        }


        // Atualiza o estado da ViewModel em tempo real enquanto o usuário digita a descrição :
        binding.etDesc.doAfterTextChanged { text ->


            viewModel.updateDescription(text.toString())


        }


    }


    override fun onDestroyView() {


        super.onDestroyView()
        _binding = null


    }


}