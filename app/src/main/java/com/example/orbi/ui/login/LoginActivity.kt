package com.example.orbi.ui.login


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.orbi.R
import com.example.orbi.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pintando a barra de status topo e navegação rodapé com a cor oliva:
        window.statusBarColor = ContextCompat.getColor(this, R.color.orbi_olive)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.orbi_olive)


        // Avisando o sistema que o fundo agora é escuro, forçando os ícones a ficarem brancos:
        WindowInsetsControllerCompat(window, window.decorView).apply {


            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false


        }

        // Aqui colocaremos o clique do binding.btnGoogleLogin


    }


}