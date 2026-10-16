package com.example.orbi.ui.theme


// Importações :
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


// Centralizando a família de fontes do aplicativo para facilitar alterações futuras :
val OrbiFontFamily = FontFamily.Default


// Definindo a escala tipográfica com padrões de tamanho legíveis e organizados :
val Typography = Typography(


    // Configurando o estilo para títulos grandes e destaques nas telas :
    headlineLarge = TextStyle(


        fontFamily = OrbiFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp


    ),


    // Configurando o estilo para títulos de seções e cartões :
    titleLarge = TextStyle(


        fontFamily = OrbiFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp


    ),


    // Configurando o estilo para textos de leitura, parágrafos e descrições :
    bodyLarge = TextStyle(


        fontFamily = OrbiFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp


    ),


    // Configurando o estilo para textos de botões, legendas e rótulos pequenos :
    labelMedium = TextStyle(


        fontFamily = OrbiFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp


    )


)