package com.example.orbi.ui.onboarding


// Importações :
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.orbi.R
import com.example.orbi.databinding.ActivityOnboardingBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


// Activity responsavel pela experiencia de onboarding do usuario :
@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {


    private lateinit var binding: ActivityOnboardingBinding


    private val viewModel: OnboardingViewModel by viewModels()


    private lateinit var adapter: OnboardingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Inflando a visualizacao principal com ViewBinding :
        binding = ActivityOnboardingBinding.inflate(layoutInflater)


        setContentView(binding.root)


        // Inicializando os componentes de UI e observadores :
        setupViewPager()


        setupClickListeners()


        observeViewModel()


    }


    // Configurando o ViewPager2 e conectando ao TabLayoutMediator :
    private fun setupViewPager() {


        adapter = OnboardingAdapter(viewModel.pages)
        binding.vpOnboarding.adapter = adapter


        // Escutando a transicao de paginas :
        binding.vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {


                super.onPageSelected(position)
                viewModel.updatePage(position)


            }


        })


    }


    // Observando a pagina ativa emitida pelo StateFlow da ViewModel :
    private fun observeViewModel() {


        lifecycleScope.launch {


            repeatOnLifecycle(Lifecycle.State.STARTED) {


                viewModel.currentPage.collect { position ->


                    // Controlando a visibilidade do botao voltar :
                    binding.btnBack.visibility = if (position > 0) View.VISIBLE else View.GONE


                    // Atualizando o texto do botao no ultimo passo :
                    val isLastPage = position == viewModel.pages.size - 1


                    binding.btnNext.text = if (isLastPage) {


                        getString(R.string.btn_start)


                    } else {


                        getString(R.string.btn_next)


                    }


                }


            }


        }


    }


    // Configurando os eventos de clique dos botoes :
    private fun setupClickListeners() {


        binding.btnNext.setOnClickListener {


            val current = binding.vpOnboarding.currentItem


            if (current < viewModel.pages.size - 1) {


                binding.vpOnboarding.currentItem = current + 1


            } else {


                // Finalizando a navegacao do onboarding :
                finishOnboarding()


            }


        }


        binding.btnBack.setOnClickListener {


            val current = binding.vpOnboarding.currentItem


            if (current > 0) {


                binding.vpOnboarding.currentItem = current - 1


            }


        }


    }


    // Trata o encerramento do onboarding para avançar no app :
    private fun finishOnboarding() {


        // Redirecionamento para a tela de Login nos proximos passos :


    }


}