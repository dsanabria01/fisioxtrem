package com.example.fisioxtrem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fisioxtrem.ui.screens.CentresScreen
import com.example.fisioxtrem.ui.screens.CitaScreen
import com.example.fisioxtrem.ui.screens.CitesScreen
import com.example.fisioxtrem.ui.screens.LoginScreen
import com.example.fisioxtrem.ui.screens.PromocionsScreen
import com.example.fisioxtrem.ui.screens.ServeisScreen
import com.example.fisioxtrem.ui.theme.FisioXtremTheme
import com.example.fisioxtrem.viewmodel.FisioViewModel

private object Routes {
    const val LOGIN = "login"
    const val CENTRES = "centres"
    const val SERVEIS = "serveis"
    const val CITA = "cita"
    const val CITES = "cites"
    const val PROMOS = "promos"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FisioXtremTheme {
                val navController = rememberNavController()
                val vm: FisioViewModel = viewModel()
                val backStack by navController.currentBackStackEntryAsState()
                val rutaActual = backStack?.destination?.route

                Scaffold { padding ->
                    Row(modifier = Modifier.fillMaxSize().padding(padding)) {
                        if (vm.usuariLogejat != null) {
                            NavigationRail(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                Spacer(Modifier.height(16.dp))
                                NavItem(rutaActual, Routes.CENTRES, Icons.Default.LocationOn, "Centres", navController)
                                NavItem(rutaActual, Routes.SERVEIS, Icons.Default.List, "Serveis", navController)
                                NavItem(rutaActual, Routes.CITA, Icons.Default.DateRange, "Cita", navController)
                                NavItem(rutaActual, Routes.CITES, Icons.Default.CheckCircle, "Cites", navController)
                                NavItem(rutaActual, Routes.PROMOS, Icons.Default.Star, "Promos", navController)

                                Spacer(modifier = Modifier.weight(1f))

                                NavigationRailItem(
                                    selected = false,
                                    onClick = {
                                        vm.logout()
                                        navController.navigate(Routes.LOGIN) { popUpTo(0) }
                                    },
                                    icon = { Icon(Icons.Default.ExitToApp, null) },
                                    label = { Text("Sortir") }
                                )
                            }
                        }

                        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                            NavHost(navController = navController, startDestination = Routes.LOGIN) {
                                composable(Routes.LOGIN) {
                                    LoginScreen(vm) {
                                        navController.navigate(Routes.CENTRES) {
                                            popUpTo(Routes.LOGIN) { inclusive = true }
                                        }
                                    }
                                }
                                composable(Routes.CENTRES) { CentresScreen(vm) }
                                composable(Routes.SERVEIS) { ServeisScreen(vm) }
                                composable(Routes.CITA) {
                                    CitaScreen(vm) { navController.navigate(Routes.CITES) }
                                }
                                composable(Routes.CITES) { CitesScreen(vm) }
                                composable(Routes.PROMOS) { PromocionsScreen(vm) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NavItem(actual: String?, ruta: String, icon: ImageVector, label: String, nav: NavHostController) {
    NavigationRailItem(
        selected = actual == ruta,
        onClick = { nav.navigate(ruta) },
        icon = { Icon(icon, null) },
        label = { Text(label) }
    )
}
