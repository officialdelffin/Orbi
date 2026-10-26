package com.example.orbi.ui.createcycle


// Importações :
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.orbi.R
import com.example.orbi.databinding.ActivityCreateCycleBinding
import dagger.hilt.android.AndroidEntryPoint


// Moldura principal que gerencia o assistente de criação passo a passo :
@AndroidEntryPoint
class CreateCycleActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCreateCycleBinding


    private val viewModel: CreateCycleViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Inicializa o ViewBinding :
        binding = ActivityCreateCycleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupSystemBars()
        setupViewPager()
        setupButtons()


    }


    // Mantém a imersão visual da tela com fundo creme :
    private fun setupSystemBars() {


        window.statusBarColor = ContextCompat.getColor(this, R.color.orbi_cream)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.orbi_cream)


        WindowInsetsControllerCompat(window, window.decorView).apply {


            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true


        }


    }


    // Configura o paginador central :
    private fun setupViewPager() {


        // Bloqueia o gesto de deslizar (swipe) para obrigar o usuário a usar os botões validados :
        binding.viewPager.isUserInputEnabled = false


        // Configura o paginador central :
        private fun setupViewPager() {


            // Bloqueia o gesto de deslizar (swipe) para obrigar o usuário a usar os botões validados :
            binding.viewPager.isUserInputEnabled = false


            // Injeta o adaptador recém-criado :
            val pagerAdapter = CreateCyclePagerAdapter(this)
            binding.viewPager.adapter = pagerAdapter


        }


    }


    // Configura o comportamento da barra de navegação inferior :
    private fun setupButtons() {


        // Fecha a tela cancelando a criação :
        binding.btnClose.setOnClickListener {


            finish()


        }


        // Retrocede um passo na criação :
        binding.btnBack.setOnClickListener {


            if (binding.viewPager.currentItem > 0) {


                binding.viewPager.currentItem -= 1
                updateUI()


            }


        }


        // Avança um passo ou aciona o salvamento no final :
        binding.btnNext.setOnClickListener {


            if (binding.viewPager.currentItem < 4) {


                binding.viewPager.currentItem += 1
                updateUI()


            } else {


                // Aciona a lógica de comunicação com o Firebase construída anteriormente :
                viewModel.saveCycle()


            }


        }


    }


    // Atualiza a barra de progresso e os textos dos botões com base no passo atual :
    private fun updateUI() {


        val currentStep = binding.viewPager.currentItem + 1
        binding.progressBar.progress = currentStep
        binding.btnBack.isEnabled = currentStep > 1


        if (currentStep == 5) {


            binding.btnNext.text = "Salvar Ciclo"


        } else {


            binding.btnNext.text = "Próximo"


        }


    }


}