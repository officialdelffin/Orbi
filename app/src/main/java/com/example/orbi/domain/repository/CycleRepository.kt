package com.example.orbi.domain.repository


// Importações :
import com.example.orbi.domain.model.Cycle
import kotlinx.coroutines.flow.Flow


// Contrato que define as operações possíveis com os ciclos no banco de dados :
interface CycleRepository {


    // Salva um novo ciclo na coleção do usuário logado :
    suspend fun createCycle(cycle: Cycle): Result<Unit>


    // Busca a lista de ciclos do usuário atualizada em tempo real :
    fun getUserCycles(): Flow<List<Cycle>>


}