package com.example.fisioxtrem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fisioxtrem.ui.formatData
import com.example.fisioxtrem.ui.formatHora
import com.example.fisioxtrem.viewmodel.FisioViewModel

@Composable
fun CitesScreen(vm: FisioViewModel) {
    Column(Modifier.padding(16.dp)) {
        Text("Les meves cites", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        if (vm.cites.isEmpty()) {
            Text("Encara no tens cap cita.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(vm.cites) { c ->
                    Card(
                        Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                "${formatData(c.dataMillis)} · ${formatHora(c.hora, c.minut)}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(c.centre.nom, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "${c.professional.nom} · ${c.professional.especialitat}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
