package com.example.orbi.ui.createcycle


// Importações :
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.orbi.R
import com.example.orbi.databinding.ActivityCreateCycleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
        observeViewModel()


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


        // Injeta o adaptador recém-criado :
        val pagerAdapter = CreateCyclePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter


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


        validateStep(binding.viewPager.currentItem, viewModel.formState.value)


    }


    // Observa as mudanças no formulário em tempo real :
    private fun observeViewModel() {


        lifecycleScope.launch {


            viewModel.formState.collect { state ->


                // 1. Valida as regras de negócio de cada tela :
                validateStep(binding.viewPager.currentItem, state)


                // 2. Proteção de UI durante a operação assíncrona (Salvando no NoSQL) :
                if (state.isSaving) {


                    binding.btnNext.isEnabled = false
                    binding.btnNext.text = "Salvando..."
                    binding.btnBack.isEnabled = false
                    binding.btnClose.isEnabled = false


                } else if (binding.viewPager.currentItem == 4) {


                    // Restaura o botão caso a tela ainda esteja no último passo e não esteja salvando :
                    binding.btnNext.text = "Salvar Ciclo"
                    binding.btnClose.isEnabled = true


                }


                // 3. Exibe um alerta visual se o banco de dados NoSQL retornar alguma falha :
                state.errorMessage?.let { erro ->


                    android.widget.Toast.makeText(this@CreateCycleActivity, erro, android.widget.Toast.LENGTH_LONG).show()


                }


                // 4. Encerra o assistente e retorna para a tela inicial assim que o sucesso for confirmado na nuvem :
                if (state.isSuccess) {


                    finish()


                }


            }


        }


    }


    // Gerencia as regras de bloqueio do botão "Próximo" para cada etapa :
    private fun validateStep(position: Int, state: CycleFormState) {


        binding.btnNext.isEnabled = when (position) {


            0 -> state.title.trim().length >= 3
            1 -> state.selectedDays.isNotEmpty()
            2 -> state.focusMinutes > 0
            3 -> state.repetitions > 0
            else -> true


        }


    }


}