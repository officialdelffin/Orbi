package com.example.orbi.domain.model


// Importações :
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


// Representando o modelo de dados puro para uma página do onboarding :
data class OnboardingPage(


    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int


)