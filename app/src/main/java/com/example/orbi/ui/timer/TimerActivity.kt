package com.example.orbi.ui.timer


// Importações :
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.orbi.R
import com.example.orbi.databinding.ActivityTimerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TimerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTimerBinding
    private val viewModel: TimerViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSystemBars()
        setupListeners()


        // Recebe o ID do ciclo clicado e manda a ViewModel buscar no banco :
        val cycleId = intent.getStringExtra("CYCLE_ID")
        cycleId?.let { viewModel.loadCycle(it) }
        observeViewModel()


    }


    private fun observeViewModel() {


        lifecycleScope.launch {


            viewModel.uiState.collect { state ->


                state.cycle?.let { cycle ->


                    // 1. Atualiza o relógio (ex: 25:00) :
                    binding.tvTimeLeft.text = viewModel.getFormattedTime()


                    // 2. Define os textos baseados no estado (Foco ou Pausa) :
                    if (state.isFocusMode) {


                        binding.tvProgressStatus.text = "Foco"
                        binding.tvTimerLabel.text = "Período de\nfoco"
                        binding.tvCurrentStatusDesc.text = "Você está no foco do período ${state.currentSession} com ${cycle.repetitions} períodos de foco"
                        binding.progressTimer.setIndicatorColor(ContextCompat.getColor(this@TimerActivity, R.color.orbi_dark_brown))


                    } else {


                        binding.tvProgressStatus.text = "Intervalo"
                        binding.tvTimerLabel.text = "Período de\nintervalo"
                        binding.tvCurrentStatusDesc.text = "Você está no intervalo do período ${state.currentSession} com ${cycle.repetitions} períodos de foco"
                        binding.progressTimer.setIndicatorColor(ContextCompat.getColor(this@TimerActivity, R.color.orbi_olive))


                    }


                    // O próximo passo será renderizar as bolinhas e fazer o relógio rodar!


                }


            }


        }


    }


    private fun setupSystemBars() {


        window.statusBarColor = ContextCompat.getColor(this, R.color.orbi_cream)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.orbi_cream)


        WindowInsetsControllerCompat(window, window.decorView).apply {


            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true


        }


    }


    private fun setupListeners() {


        binding.btnBack.setOnClickListener { finish() }


    }


}