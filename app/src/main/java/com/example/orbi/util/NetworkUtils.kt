package com.example.orbi.util


// Importações :
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


// Objeto utilitário para isolar a regra de verificação do sistema Android :
object NetworkUtils {


    // Verifica se o dispositivo possui conexão ativa (Wi-Fi ou Dados Móveis) :
    fun isInternetAvailable(context: Context): Boolean {


        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false


        return when {


            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false


        }


    }


}