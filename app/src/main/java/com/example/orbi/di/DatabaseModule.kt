package com.example.orbi.di


// Importações :
import com.example.orbi.data.repository.CycleRepositoryImpl
import com.example.orbi.domain.repository.CycleRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


// Ensina o Hilt a injetar o banco de dados NoSQL :
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    // Fornece a instância única do banco de dados para todo o aplicativo :
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {


        return FirebaseFirestore.getInstance()


    }


}


// Conecta a interface do repositório de ciclos com a sua implementação :
@Module
@InstallIn(SingletonComponent::class)
abstract class CycleRepositoryModule {


    // Informa ao Hilt qual implementação usar quando a ViewModel pedir o CycleRepository :
    @Binds
    @Singleton
    abstract fun bindCycleRepository(


        cycleRepositoryImpl: CycleRepositoryImpl


    ): CycleRepository


}