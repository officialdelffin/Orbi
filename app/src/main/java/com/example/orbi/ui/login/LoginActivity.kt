package com.example.orbi.ui.login


// Importações :
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
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


        // Inicializa o ViewBinding :
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setupSystemBars()
        setupClickListeners()


    }


    // Configura as cores da barra de status e navegação para manter a imersão :
    private fun setupSystemBars() {


        window.statusBarColor = ContextCompat.getColor(this, R.color.orbi_olive)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.orbi_olive)


        // Define os ícones do sistema bateria, hora como brancos para dar contraste com o fundo escuro :
        WindowInsetsControllerCompat(window, window.decorView).apply {


            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false


        }


    }


    // Configura os eventos de clique da tela :
    private fun setupClickListeners() {


        // Ação do botão visual de voltar :
        binding.btnBackLogin.setOnClickListener {


            executeBackNavigation()


        }

        // Ação do gesto nativo de voltar do aparelho (arrastar a tela ou botão inferior) :
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {


            override fun handleOnBackPressed() {


                executeBackNavigation()


            }


        })


    }


    // Centraliza a navegação de retorno para o Onboarding com as animações ajustadas :
    private fun executeBackNavigation() {


        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_up)


    }


}