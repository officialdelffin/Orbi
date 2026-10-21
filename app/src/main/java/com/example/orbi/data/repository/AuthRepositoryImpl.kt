package com.example.orbi.data.repository


// Importações :
import com.example.orbi.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


// Implementação real do repositório conectando diretamente com o Firebase :
class AuthRepositoryImpl @Inject constructor(


    private val auth: FirebaseAuth


) : AuthRepository {


    // Retorna o usuário que está salvo na sessão do Firebase :
    override fun getCurrentUser(): FirebaseUser? {


        return auth.currentUser


    }


    // Converte o token do Google em uma credencial e tenta logar no Firebase :
    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {


        return try {


            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            val user = authResult.user


            if (user != null) {


                Result.success(user)


            } else {


                Result.failure(Exception("Usuário não encontrado após o login"))


            }


        } catch (e: Exception) {


            Result.failure(e)


        }


    }


}