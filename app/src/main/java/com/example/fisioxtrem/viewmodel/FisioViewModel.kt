package com.example.fisioxtrem.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fisioxtrem.model.Centre
import com.example.fisioxtrem.model.Cita
import com.example.fisioxtrem.model.Professional
import com.example.fisioxtrem.model.Promocio
import com.example.fisioxtrem.model.Servei
import com.example.fisioxtrem.model.Usuari

/**
 * Single source of truth for the demo. Data is in memory (no backend, no database yet)
 * and the login is a simulated one, see the README.
 */
class FisioViewModel : ViewModel() {

    val centres = listOf(
        Centre(1, "FisioXtrem Barcelona", "C/ Gran Via 123", "933000000"),
        Centre(2, "FisioXtrem Badalona", "Av. Martí Pujol 45", "934000000"),
        Centre(3, "FisioXtrem Granollers", "Carrer Colòmbia 47", "935000000")
    )

    val serveis = listOf(
        Servei(1, "Fisioteràpia avançada", "Tractament de lesions musculars", 50.0),
        Servei(2, "Podologia clínica", "Cura del peu i biomecànica", 45.0, "Extra: +35€ plantilles"),
        Servei(3, "Osteopatia", "Tractament holístic del cos", 55.0),
        Servei(4, "Nutrició esportiva", "Dietes personalitzades", 40.0)
    )

    val professionals = listOf(
        Professional(1, "Laura Pérez", "Fisioteràpia", 1),
        Professional(2, "Marc Soler", "Podologia", 1),
        Professional(3, "Anna Vidal", "Osteopatia", 2),
        Professional(4, "Jordi Ferrer", "Fisioteràpia", 3)
    )

    val promos = listOf(
        Promocio("Pack 5 sessions", "Aconsegueix 5 sessions pel preu de 4.", "PROMO5X4"),
        Promocio("Black Friday", "20% de descompte en la primera visita.", "BLACKFISIO"),
        Promocio("Targeta Regal", "Regala salut als teus familiars.", "GIFT2024")
    )

    /** Appointments confirmed in this session. */
    val cites = mutableStateListOf<Cita>()

    var usuariLogejat by mutableStateOf<Usuari?>(null)
        private set

    /** Simulated login: any non-blank email plus the demo password. */
    fun login(email: String, pass: String): Boolean {
        val valid = email.isNotBlank() && pass == DEMO_PASSWORD
        usuariLogejat = if (valid) Usuari(email.trim()) else null
        return valid
    }

    fun logout() {
        usuariLogejat = null
    }

    fun afegirCita(cita: Cita) {
        cites.add(cita)
    }

    companion object {
        const val DEMO_PASSWORD = "1234"
    }
}
