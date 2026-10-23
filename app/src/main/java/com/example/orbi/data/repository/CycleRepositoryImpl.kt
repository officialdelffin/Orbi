package com.example.orbi.data.repository


// Importações :
import com.example.orbi.domain.model.Cycle
import com.example.orbi.domain.repository.CycleRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


// Implementação real conectando nossa aplicação ao banco NoSQL :
class CycleRepositoryImpl @Inject constructor(


    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth


) : CycleRepository {


    // Coleção principal onde os ciclos serão salvos na nuvem :
    private val collection = firestore.collection("cycles")


    // Salva o ciclo vinculando-o ao usuário autenticado :
    override suspend fun createCycle(cycle: Cycle): Result<Unit> {


        return try {


            val currentUser = auth.currentUser

            if (currentUser == null) {


                return Result.failure(Exception("Usuário não autenticado"))


            }


            // Gera um ID único no NoSQL para este novo documento :
            val documentId = collection.document().id

            // Prepara o objeto final injetando o ID do documento e do usuário :
            val cycleToSave = cycle.copy(
                id = documentId,
                userId = currentUser.uid
            )


            // Salva o documento na nuvem e aguarda a conclusão :
            collection.document(documentId).set(cycleToSave).await()

            Result.success(Unit)


        } catch (e: Exception) {


            Result.failure(e)


        }


    }


    // Busca os ciclos do usuário em tempo real :
    override fun getUserCycles(): Flow<List<Cycle>> = callbackFlow {


        val currentUser = auth.currentUser


        if (currentUser == null) {


            close(Exception("Usuário não autenticado"))
            return@callbackFlow


        }


        // Cria um ouvinte para a coleção filtrando apenas os ciclos deste usuário específico :
        val listenerRegistration = collection
            .whereEqualTo("userId", currentUser.uid)
            .addSnapshotListener { snapshot, error ->


                if (error != null) {


                    close(error)
                    return@addSnapshotListener


                }


                if (snapshot != null) {


                    // Converte os documentos NoSQL brutos para a nossa classe Cycle :
                    val cycles = snapshot.documents.mapNotNull { doc ->


                        doc.toObject(Cycle::class.java)


                    }

                    // Envia a lista atualizada para a ViewModel :
                    trySend(cycles)


                }


            }


        // Remove o ouvinte quando a observação for cancelada para economizar memória e rede :
        awaitClose {


            listenerRegistration.remove()


        }


    }


}