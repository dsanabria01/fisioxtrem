package com.example.fisioxtrem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fisioxtrem.model.Cita
import com.example.fisioxtrem.ui.formatData
import com.example.fisioxtrem.ui.formatHora
import com.example.fisioxtrem.viewmodel.FisioViewModel

private const val MILLIS_PER_DAY = 86_400_000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaScreen(vm: FisioViewModel, onCitaConfirmada: () -> Unit) {
    var centreSel by remember { mutableStateOf(vm.centres.first()) }
    val professionalsDelCentre = vm.professionals.filter { it.centreId == centreSel.id }
    // Reset the professional whenever the centre changes
    var profSel by remember(centreSel) { mutableStateOf(professionalsDelCentre.firstOrNull()) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var horaTriada by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Only today or later (the picker works in UTC midnight)
    val avui = remember { System.currentTimeMillis().let { it - it % MILLIS_PER_DAY } }
    val dateState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long) = utcTimeMillis >= avui
        }
    )
    val timeState = rememberTimePickerState()

    Column(Modifier.padding(24.dp).verticalScroll(rememberScrollState())) {
        Text("Demanar Cita", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        DropdownMenuBox("Selecciona Centre", vm.centres.map { it.nom }, centreSel.nom) {
            centreSel = vm.centres[it]
            error = null
        }

        Spacer(Modifier.height(8.dp))

        if (professionalsDelCentre.isEmpty()) {
            Text("No hi ha professionals disponibles en aquest centre.", style = MaterialTheme.typography.bodyMedium)
        } else {
            DropdownMenuBox(
                "Professional desitjat",
                professionalsDelCentre.map { it.nom },
                profSel?.nom.orEmpty()
            ) { profSel = professionalsDelCentre[it] }
        }

        Spacer(Modifier.height(24.dp))

        OutlinedButton(onClick = { showDatePicker = true }, Modifier.fillMaxWidth()) {
            Icon(Icons.Default.DateRange, null)
            Spacer(Modifier.width(8.dp))
            Text(dateState.selectedDateMillis?.let { "Data: ${formatData(it)}" } ?: "Seleccionar Data")
        }

        OutlinedButton(onClick = { showTimePicker = true }, Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text(if (horaTriada) "Hora: ${formatHora(timeState.hour, timeState.minute)}" else "Seleccionar Hora")
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = { TextButton(onClick = { showDatePicker = false }) { Text("D'acord") } }
            ) { DatePicker(state = dateState) }
        }

        if (showTimePicker) {
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        horaTriada = true
                        showTimePicker = false
                    }) { Text("D'acord") }
                },
                title = { Text("Tria l'hora") },
                text = { TimePicker(state = timeState) }
            )
        }

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 16.dp))
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                val professional = profSel
                val data = dateState.selectedDateMillis
                when {
                    professional == null -> error = "Aquest centre no té professionals disponibles."
                    data == null -> error = "Selecciona una data."
                    !horaTriada -> error = "Selecciona una hora."
                    else -> {
                        error = null
                        vm.afegirCita(Cita(centreSel, professional, data, timeState.hour, timeState.minute))
                        onCitaConfirmada()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("CONFIRMAR CITA")
        }
    }
}

@Composable
fun DropdownMenuBox(label: String, options: List<String>, selected: String, onSelect: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text(selected)
            Icon(Icons.Default.ArrowDropDown, null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            options.forEachIndexed { index, s ->
                DropdownMenuItem(text = { Text(s) }, onClick = { onSelect(index); expanded = false })
            }
        }
    }
}
