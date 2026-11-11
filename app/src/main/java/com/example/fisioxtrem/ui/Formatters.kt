package com.example.fisioxtrem.ui

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/** Formats a date-picker value (UTC midnight) as dd/MM/yyyy. */
fun formatData(utcMillis: Long): String =
    SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("ca")).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(Date(utcMillis))

fun formatHora(hora: Int, minut: Int): String =
    String.format(Locale.ROOT, "%02d:%02d", hora, minut)
