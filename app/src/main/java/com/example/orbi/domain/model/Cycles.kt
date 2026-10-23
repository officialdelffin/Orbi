package com.example.orbi.domain.model


// Representa a estrutura de dados de um ciclo de foco no banco NoSQL :
data class Cycle(


    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String? = null,
    val activeDays: List<Int> = emptyList(),
    val focusDurationMinutes: Int = 25,
    val breakDurationMinutes: Int = 5,
    val repetitions: Int = 4,
    val createdAt: Long = System.currentTimeMillis()


)