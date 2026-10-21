package com.example.orbi.domain.repository


// Importações :
import com.google.firebase.auth.FirebaseUser


// Contrato que define as ações de autenticação sem expor como são feitas :
interface AuthRepository {


    // Retorna o usuário atual logado, se existir :
    fun getCurrentUser(): FirebaseUser?


    // Realiza o login no Firebase usando o token gerado pelo Google :
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>


}