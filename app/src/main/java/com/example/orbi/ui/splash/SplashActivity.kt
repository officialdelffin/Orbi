package com.example.orbi.ui.splash


// Importações :
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.orbi.R
import com.example.orbi.ui.main.MainActivity
import com.example.orbi.ui.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {


    private val viewModel: SplashViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Define a cor de fundo nativa para evitar tela branca durante o roteamento :
        window.decorView.setBackgroundColor(ContextCompat.getColor(this, R.color.orbi_olive))


        observeViewModel()
        viewModel.verifyUserSession()


    }


    // Observa a decisão da ViewModel e realiza a navegação limpa :
    private fun observeViewModel() {


        lifecycleScope.launch {


            viewModel.destination.collect { destination ->


                when (destination) {


                    is SplashDestination.Main -> {


                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()


                    }


                    is SplashDestination.Onboarding -> {


                        startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
                        finish()


                    }


                    null -> {


                        // Aguardando verificação no repositório :


                    }


                }


            }


        }


    }


}