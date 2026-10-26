package com.example.orbi.ui.createcycle


// Importações :
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


// Gerencia a exibição dos fragmentos dentro da Activity principal :
class CreateCyclePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {


    // Define que o assistente terá exatamente 5 passos :
    override fun getItemCount(): Int = 5


    // Instancia o fragmento correto com base na posição atual (0 a 4) :
    override fun createFragment(position: Int): Fragment {


        return when (position) {


            0 -> StepOneFragment()
            1 -> StepTwoFragment()
            2 -> StepThreeFragment()
            else -> Fragment()


        }


    }


}