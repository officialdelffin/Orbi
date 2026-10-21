package com.example.orbi.di


// Importações :
import com.example.orbi.data.repository.AuthRepositoryImpl
import com.example.orbi.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// Ensina o Hilt a injetar a instância do Firebase :
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {


    // Fornece a instância única do FirebaseAuth para todo o aplicativo :
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {


        return FirebaseAuth.getInstance()


    }


}


// Conecta a interface do repositório com a sua implementação :
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {


    // Informa ao Hilt qual implementação usar quando alguém pedir o AuthRepository :
    @Binds
    @Singleton
    abstract fun bindAuthRepository(


        authRepositoryImpl: AuthRepositoryImpl


    ): AuthRepository


}