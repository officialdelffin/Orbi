package com.example.orbi.ui.timer


// Importações :
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.orbi.R
import com.example.orbi.databinding.ActivityTimerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


// Tela responsável por rodar o cronômetro do Pomodoro :
@AndroidEntryPoint
class TimerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityTimerBinding
    private val viewModel: TimerViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Inflando o layout XML e conectando os elementos visuais :
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupSystemBars()
        setupListeners()


        // Recebe o ID do ciclo clicado e manda a ViewModel buscar no banco :
        val cycleId = intent.getStringExtra("CYCLE_ID")
        cycleId?.let { viewModel.loadCycle(it) }


        observeViewModel()
        observeSoundEvents()


    }


    // Observa o estado da ViewModel em tempo real :
    private fun observeViewModel() {


        lifecycleScope.launch {


            viewModel.uiState.collect { state ->


                state.cycle?.let { cycle ->


                    // 1. Atualiza o relógio e a Barra de Progresso :
                    binding.tvTimeLeft.text = viewModel.getFormattedTime()


                    // Calcula a porcentagem para preencher o anel (CircularProgressIndicator) :
                    if (state.totalTimeInMillis > 0) {


                        val progress = ((state.timeLeftInMillis.toFloat() / state.totalTimeInMillis.toFloat()) * 100).toInt()
                        binding.progressTimer.progress = progress


                    }


                    // 2. Define o visual baseado no estado (Foco ou Pausa) :
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


                    // 3. Atualiza o único botão central alternando a imagem entre Play e Pause :
                    if (state.isPlaying) {


                        binding.btnPause.setBackgroundResource(R.drawable.ic_pause)


                    } else {


                        binding.btnPause.setBackgroundResource(R.drawable.ic_play)


                    }


                    // 4. Renderiza as bolinhas dinâmicas :
                    renderDots(cycle.repetitions, state.currentSession, state.isFocusMode)


                }


            }


        }


    }


    // Fica escutando os eventos de som disparados pela ViewModel :
    private fun observeSoundEvents() {


        lifecycleScope.launch {


            viewModel.soundEvent.collect {


                playNotificationSound()


            }


        }


    }


    // Toca o som padrão de notificação do celular do usuário :
    private fun playNotificationSound() {


        try {


            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(applicationContext, uri)
            ringtone.play()


        } catch (e: Exception) {


            e.printStackTrace()


        }


    }


    // Desenha as bolinhas de progresso conforme a legenda do aplicativo :
    private fun renderDots(totalSessions: Int, currentSession: Int, isFocusMode: Boolean) {


        binding.layoutDotsContainer.removeAllViews()


        for (i in 1..totalSessions) {


            val dot = View(this).apply {


                // Tamanho da bolinha :
                val size = (12 * resources.displayMetrics.density).toInt()


                layoutParams = LinearLayout.LayoutParams(size, size).apply {


                    marginEnd = (6 * resources.displayMetrics.density).toInt()


                }


                // Definindo as cores com base no estado :
                val bgDrawable = GradientDrawable().apply {


                    shape = GradientDrawable.OVAL


                    when {


                        // Bolinhas passadas ficam sólidas :
                        i < currentSession -> setColor(ContextCompat.getColor(this@TimerActivity, R.color.orbi_dark_brown))

                        // Bolinha atual muda de cor dependendo se é foco (Marrom) ou intervalo (Verde) :
                        i == currentSession -> {


                            if (isFocusMode) {


                                setColor(ContextCompat.getColor(this@TimerActivity, R.color.orbi_dark_brown))


                            } else {


                                setColor(ContextCompat.getColor(this@TimerActivity, R.color.orbi_olive))


                            }


                        }

                        // Bolinhas futuras ficam brancas (pendentes) :
                        else -> setColor(Color.WHITE)


                    }


                }


                background = bgDrawable


            }


            binding.layoutDotsContainer.addView(dot)


        }


    }


    // Configura os cliques :
    private fun setupListeners() {


        binding.btnBack.setOnClickListener { finish() }

        // Como agora só existe um botão, conectamos ele à função toggleTimer :
        binding.btnPause.setOnClickListener { viewModel.toggleTimer() }
        binding.btnSkip.setOnClickListener { viewModel.skipToNextPhase() }
        binding.btnRestartCycle.setOnClickListener { viewModel.restartCycle() }


    }


    // Mantém a imersão visual da tela e impede que ela apague :
    private fun setupSystemBars() {


        window.statusBarColor = ContextCompat.getColor(this, R.color.orbi_cream)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.orbi_cream)

        // Mantém a tela sempre ligada :
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        WindowInsetsControllerCompat(window, window.decorView).apply {


            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true


        }


    }


}