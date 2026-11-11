package com.example.fisioxtrem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fisioxtrem.viewmodel.FisioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServeisScreen(vm: FisioViewModel) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Cerca tractament...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {}

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            val filtrats = vm.serveis.filter { it.nom.contains(query, ignoreCase = true) }
            items(filtrats) { s ->
                Card(
                    Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(s.nom, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(s.detall, style = MaterialTheme.typography.bodyMedium)
                        s.extra?.let {
                            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
                        }
                        Spacer(Modifier.height(8.dp))
                        Badge(containerColor = MaterialTheme.colorScheme.primary) {
                            Text(
                                "${s.preu} €",
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
