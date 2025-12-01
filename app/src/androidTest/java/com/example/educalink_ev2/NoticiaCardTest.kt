package com.example.educalink_ev2

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.educalink_ev2.ui.screens.NoticiaCard
import org.junit.Rule
import org.junit.Test

class NoticiaCardTest {
    @get:Rule val rule = createComposeRule()

    @Test
    fun noticiaCard_muestra_texto() {
        rule.setContent { NoticiaCard("Titulo Test", "Cuerpo Test") }
        rule.onNodeWithText("TITULO TEST").assertIsDisplayed() // Recuerda que lo ponemos uppercase en la UI
        rule.onNodeWithText("Cuerpo Test").assertIsDisplayed()
    }
}