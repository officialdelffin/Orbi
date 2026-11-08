package com.example.orbi.ui.timer


// Importações :
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbi.domain.model.Cycle
import com.example.orbi.domain.repository.CycleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


// Adicionamos a variável totalTimeInMillis para a barra de progresso saber o limite :
data class TimerUiState(


    val cycle: Cycle? = null,
    val isFocusMode: Boolean = true,
    val currentSession: Int = 1,
    val timeLeftInMillis: Long = 0L,
    val totalTimeInMillis: Long = 0L,
    val isPlaying: Boolean = false


)


// Classe que guarda o estado exato da tela do cronômetro :
@HiltViewModel
class TimerViewModel @Inject constructor(


    private val repository: CycleRepository


) : ViewModel() {


    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    // Evento único para disparar o som (SharedFlow não guarda estado, apenas avisa a Activity na hora exata) :
    private val _soundEvent = MutableSharedFlow<Unit>()
    val soundEvent: SharedFlow<Unit> = _soundEvent.asSharedFlow()

    private var timerJob: Job? = null


    // Busca o ciclo no NoSQL e prepara a tela para o primeiro período de foco :
    fun loadCycle(cycleId: String) {


        viewModelScope.launch {


            val cycle = repository.getCycleById(cycleId)


            if (cycle != null) {


                val focusMillis = cycle.focusDurationMinutes * 60 * 1000L
                _uiState.update { it.copy(


                    cycle = cycle,
                    isFocusMode = true,
                    currentSession = 1,
                    timeLeftInMillis = focusMillis,
                    totalTimeInMillis = focusMillis,
                    isPlaying = false


                )}


            }


        }


    }


    // Alterna entre iniciar e pausar o cronômetro :
    fun toggleTimer() {


        if (_uiState.value.isPlaying) pauseTimer() else startTimer()


    }


    // Inicia a contagem regressiva :
    private fun startTimer() {


        _uiState.update { it.copy(isPlaying = true) }


        timerJob = viewModelScope.launch {


            while (_uiState.value.timeLeftInMillis > 0 && _uiState.value.isPlaying) {


                delay(1000L) // Aguarda exatamente 1 segundo :
                _uiState.update { it.copy(timeLeftInMillis = it.timeLeftInMillis - 1000L) }


            }


            if (_uiState.value.timeLeftInMillis <= 0L) {


                skipToNextPhase()


            }


        }


    }


    // Pausa a contagem regressiva :
    fun pauseTimer() {


        _uiState.update { it.copy(isPlaying = false) }
        timerJob?.cancel()


    }


    // Pula para a próxima fase de forma automática e dispara o som :
    fun skipToNextPhase() {


        // Descobre se o cronômetro estava rodando antes de pular a fase :
        val wasPlaying = _uiState.value.isPlaying

        pauseTimer()


        val currentState = _uiState.value
        val cycle = currentState.cycle ?: return


        if (currentState.isFocusMode) {


            // Entrando no Intervalo :
            val breakMillis = cycle.breakDurationMinutes * 60 * 1000L
            _uiState.update { it.copy(


                isFocusMode = false,
                timeLeftInMillis = breakMillis,
                totalTimeInMillis = breakMillis


            )}

            triggerSound()

            // Se estava rodando, inicia o intervalo automaticamente :
            if (wasPlaying) startTimer()


        } else {


            // Voltando para o Foco (Avança a Sessão) :
            val nextSession = currentState.currentSession + 1


            if (nextSession <= cycle.repetitions) {


                val focusMillis = cycle.focusDurationMinutes * 60 * 1000L
                _uiState.update { it.copy(


                    isFocusMode = true,
                    currentSession = nextSession,
                    timeLeftInMillis = focusMillis,
                    totalTimeInMillis = focusMillis


                )}

                triggerSound()

                // Se estava rodando, inicia o próximo foco automaticamente :
                if (wasPlaying) startTimer()


            } else {


                // Fim do ciclo (Toca o som de fim e volta para o início pausado) :
                triggerSound()
                loadCycle(cycle.id)


            }


        }


    }


    // Emite o evento de som para a Activity escutar :
    private fun triggerSound() {


        viewModelScope.launch {


            _soundEvent.emit(Unit)


        }


    }


    // Reinicia o ciclo inteiro :
    fun restartCycle() {


        pauseTimer()


        val cycleId = _uiState.value.cycle?.id


        if (cycleId != null) {


            loadCycle(cycleId)


        }


    }


    // Formata os milissegundos brutos para o formato MM:SS (ex: 25:00) :
    fun getFormattedTime(): String {


        val minutes = (_uiState.value.timeLeftInMillis / 1000) / 60
        val seconds = (_uiState.value.timeLeftInMillis / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)


    }


}