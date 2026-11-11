package com.example.fisioxtrem.model

data class Centre(val id: Int, val nom: String, val adreca: String, val telefon: String)

data class Servei(
    val id: Int,
    val nom: String,
    val detall: String,
    val preu: Double,
    val extra: String? = null
)

data class Professional(val id: Int, val nom: String, val especialitat: String, val centreId: Int)

data class Promocio(val titol: String, val descripcio: String, val codi: String)

/** Logged-in user. The password is never kept after the login check. */
data class Usuari(val email: String)

/**
 * A confirmed appointment.
 * [dataMillis] is the date at UTC midnight, as returned by the Material 3 date picker.
 */
data class Cita(
    val centre: Centre,
    val professional: Professional,
    val dataMillis: Long,
    val hora: Int,
    val minut: Int
)
