package com.example.orbi.ui.createcycle


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


// Guarda todas as informações que o usuário preencher em cada tela do assistente :
data class CycleFormState(


    val title: String = "",
    val description: String = "",
    val selectedDays: Set<Int> = emptySet(),
    val focusMinutes: Int = 25,
    val breakMinutes: Int = 5,
    val repetitions: Int = 4,
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null


)


// Gerencia o estado do formulário e a comunicação com a camada de dados NoSQL :
@HiltViewModel
class CreateCycleViewModel @Inject constructor(


    private val repository: CycleRepository


) : ViewModel() {


    // Estado reativo que os fragmentos do fluxo vão observar e modificar :
    private val _formState = MutableStateFlow(CycleFormState())
    val formState: StateFlow<CycleFormState> = _formState.asStateFlow()


    // Atualiza o título digitado no Passo 1 :
    fun updateTitle(title: String) {


        _formState.update { it.copy(title = title) }


    }


    // Atualiza a descrição digitada no Passo 1 :
    fun updateDescription(description: String) {


        _formState.update { it.copy(description = description) }


    }


    // Adiciona ou remove um dia da semana no Passo 2 :
    fun toggleDay(day: Int) {


        _formState.update { currentState ->


            val currentDays = currentState.selectedDays.toMutableSet()


            if (currentDays.contains(day)) {


                currentDays.remove(day)


            } else {


                currentDays.add(day)


            }


            currentState.copy(selectedDays = currentDays)


        }


    }


    // Atualiza o tempo de foco no Passo 3 :
    fun updateFocusMinutes(minutes: Int) {


        _formState.update { it.copy(focusMinutes = minutes) }


    }


    // Atualiza apenas as repetições no Passo 4 :
    fun updateRepetitions(reps: Int) {


        _formState.update { it.copy(repetitions = reps) }


    }


    // Atualiza apenas o tempo de pausa no Passo 5 :
    fun updateBreakMinutes(minutes: Int) {


        _formState.update { it.copy(breakMinutes = minutes) }


    }


    // Pega todos os dados do estado atual e envia para o banco NoSQL no Passo final :
    fun saveCycle() {


        val currentState = _formState.value


        // Validação final de segurança para garantir a integridade dos dados na nuvem :
        if (currentState.title.isBlank() || currentState.selectedDays.isEmpty()) {


            _formState.update { it.copy(errorMessage = "Preencha os campos obrigatórios") }
            return


        }


        viewModelScope.launch {


            // Avisa a interface que o processo de salvamento começou :
            _formState.update { it.copy(isSaving = true, errorMessage = null) }


            // Constrói o objeto final do ciclo com base nos passos preenchidos :
            val newCycle = Cycle(
                title = currentState.title.trim(),
                description = currentState.description.trim().takeIf { it.isNotEmpty() },
                activeDays = currentState.selectedDays.toList().sorted(),
                focusDurationMinutes = currentState.focusMinutes,
                breakDurationMinutes = currentState.breakMinutes,
                repetitions = currentState.repetitions
            )


            // Chama a implementação do repositório para persistir na nuvem :
            val result = repository.createCycle(newCycle)


            result.onSuccess {


                _formState.update { it.copy(isSaving = false, isSuccess = true) }


            }.onFailure { error ->


                _formState.update { it.copy(isSaving = false, errorMessage = error.message) }


            }


        }


    }


}