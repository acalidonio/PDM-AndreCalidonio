package com.acalidonio.aacccinco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acalidonio.aacccinco.ui.theme.Labo2PDMV3Theme
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object NameList

@Serializable
object SensorDetail
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labo2PDMV3Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                onNavigateToNames = { navController.navigate(NameList) },
                onNavigateToSensors = { navController.navigate(SensorDetail) }
            )
        }
        composable<NameList> {
            NameListScreen()
        }
        composable<SensorDetail> {
            //SensorScreen()
        }
    }
}

@Composable
fun HomeScreen(onNavigateToNames: () -> Unit, onNavigateToSensors: () -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Menú Principal", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onNavigateToNames,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
            ) {
                Text("Ver Lista de Nombres")
            }
            Button(
                onClick = onNavigateToSensors,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
            ) {
                Text("Ver Sensores")
            }
        }
    }
}

@Composable
fun NameListScreen() {
    var nameInput by remember { mutableStateOf("") }
    val namesList = remember { mutableStateListOf<String>() }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (nameInput.isNotBlank()) {
                        namesList.add(nameInput)
                        nameInput = ""
                    }
                }
            ) {
                Text(text = "Guardar")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Listado de nombres y\nposición en la lista",
                    fontSize = 18.sp
                )
                Button(
                    onClick = { namesList.clear() }
                ) {
                    Text("Limpiar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 4.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(namesList) { index, name ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = name, fontSize = 18.sp)
                            Text(text = "${index + 1}", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}