package com.example.orbi.ui.main


// Importações :
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orbi.domain.model.Cycle
import com.example.orbi.domain.repository.CycleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


// Gerencia o estado da tela principal conectando-se ao banco de dados :
@HiltViewModel
class MainViewModel @Inject constructor(


    repository: CycleRepository


) : ViewModel() {


    // Cria um fluxo de dados (Flow) que escuta os ciclos do usuário em tempo real e o stateIn garante que a lista sobreviva a rotações de tela e otimize a memória :
    val cyclesState: StateFlow<List<Cycle>> = repository.getUserCycles()


        .stateIn(


            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()


        )


}