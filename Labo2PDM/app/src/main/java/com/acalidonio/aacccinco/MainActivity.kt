package com.acalidonio.aacccinco

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
            SensorScreen()
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

@Composable
fun SensorScreen() {
    val lightValues = useSensor(Sensor.TYPE_LIGHT)
    val gyroscopeValues = useSensor(Sensor.TYPE_GYROSCOPE)
    val proximityValues = useSensor(Sensor.TYPE_PROXIMITY)

    val lux = if (lightValues.isNotEmpty()) lightValues[0] else 0f

    val isDark = lux < 20f
    val backgroundColor = if (isDark) Color.Black else Color.White
    val textColor = if (isDark) Color.White else Color.Black

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Giroscopio",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = "X:${gyroscopeValues[0]}",
                fontSize = 18.sp,
                color = textColor
            )
            Text(
                text = "Y:${gyroscopeValues[1]}",
                fontSize = 18.sp,
                color = textColor
            )
            Text(
                text = "Z:${gyroscopeValues[2]}",
                fontSize = 18.sp,
                color = textColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sensor de Proximidad",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = "Distancia:${proximityValues[0]} cm",
                fontSize = 18.sp,
                color = textColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sensor de Luz",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = "Intensidad: $lux lx",
                fontSize = 18.sp,
                color = textColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (isDark) "Modo Oscuro Activado" else "Modo Claro Activado",
                color = if (isDark) Color.Yellow else Color.Gray
            )
        }
    }
}

@Composable
fun useSensor(sensorType: Int): List<Float> {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val sensor = sensorManager.getDefaultSensor(sensorType) ?: return emptyList()
    var sensorValues by remember { mutableStateOf(listOf(0f, 0f, 0f)) }

    DisposableEffect(sensorType) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.values?.let {
                    sensorValues = it.toList()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    return sensorValues
}