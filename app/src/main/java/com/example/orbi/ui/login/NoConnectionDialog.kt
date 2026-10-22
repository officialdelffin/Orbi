package com.example.orbi.ui.login


// Importações :
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.orbi.databinding.DialogNoConnectionBinding


// Fragmento de diálogo reutilizável para exibição de erro de rede :
class NoConnectionDialog : DialogFragment() {


    private var _binding: DialogNoConnectionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        _binding = DialogNoConnectionBinding.inflate(inflater, container, false)

        // Remove o fundo quadrado padrão do Android para revelar o nosso fundo arredondado :
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Fecha o pop-up para que o usuário possa interagir com a tela de login novamente :
        binding.btnRetryConnection.setOnClickListener {


            dismiss()


        }


    }


    override fun onDestroyView() {


        super.onDestroyView()
        _binding = null


    }


}