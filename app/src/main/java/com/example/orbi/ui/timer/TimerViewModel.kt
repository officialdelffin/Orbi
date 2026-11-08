package com.example.orbi.ui.timer


// Importações :
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbi.domain.model.Cycle
import com.example.orbi.domain.repository.CycleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


// Classe que guarda o estado exato da tela do cronômetro :
data class TimerUiState(


    val cycle: Cycle? = null,
    val isFocusMode: Boolean = true, // true = Foco, false = Pausa
    val currentSession: Int = 1,
    val timeLeftInMillis: Long = 0L,
    val isPlaying: Boolean = false


)


@HiltViewModel
class TimerViewModel @Inject constructor(


    private val repository: CycleRepository


) : ViewModel() {


    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()


    // Busca o ciclo no NoSQL e prepara a tela para o primeiro período de foco :
    fun loadCycle(cycleId: String) {


        viewModelScope.launch {


            val cycle = repository.getCycleById(cycleId)


            if (cycle != null) {


                _uiState.update { it.copy(


                    cycle = cycle,
                    isFocusMode = true,
                    currentSession = 1,
                    timeLeftInMillis = cycle.focusDurationMinutes * 60 * 1000L // Converte minutos para milissegundos


                )}


            }


        }


    }


    // Formata os milissegundos brutos para o formato MM:SS (ex: 25:00) :
    fun getFormattedTime(): String {


        val minutes = (_uiState.value.timeLeftInMillis / 1000) / 60
        val seconds = (_uiState.value.timeLeftInMillis / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)


    }


}