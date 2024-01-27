import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Compose: ${Greeting().greet()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    null,
                )
            }
            FluentUIExample()
        }
    }
}

@Composable
fun LoadingBarExample() {
    var loading by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf("") }
    var cor = rememberCoroutineScope()
    var spinnerRotation by remember { mutableStateOf(0f) }
    val rotationTransition = rememberInfiniteTransition()
    val rotation by rotationTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0f at 0
                360f at 1000
            },
            repeatMode = RepeatMode.Restart
        )
    )
    spinnerRotation = rotation

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        item {
            Button(
                onClick = {
                    loading = true
                    cor.launch {
                        withContext(Dispatchers.Default) {
                            performHeavyTaskInBackground { calculatedResult ->
                                result = "Resultado: $calculatedResult"
                            }
                        }
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Iniciar tarea pesada")
            }
            // Spinner
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .padding(16.dp)
                    .rotate(spinnerRotation)
            )

            if (loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(8.dp)
                )
            }

            if (result.isNotEmpty()) {
                Text(result, modifier = Modifier.padding(8.dp))
            }
        }

    }
}

@Composable
fun FluentUIExample() {
    var backgroundColor by remember { mutableStateOf(Color.Red) }

    // Animation for continuously changing background color
    val colorTransition = rememberInfiniteTransition()
    val color by colorTransition.animateColor(
        initialValue = Color.Green,
        targetValue = Color.Yellow,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    backgroundColor = color



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        LoadingBarExample()
        // Other UI elements can be added here for a fluent UI
    }
}

fun performHeavyTaskInBackground(): String {
    // Simular un cálculo aritmético pesado
    var j = 1.0
    for (i in 0 until 100000000) {
        j += 1
        j *= (j + 1)
        j /= (j - 1)
    }
    j += (0..100).random()
    return "finished $j"
}

suspend fun performHeavyTaskInBackground(resultCallback: (String) -> Unit) {
    // Simular un cálculo aritmético pesado
    var j = 1.0
    for (i in 0 until 10000000) {
        j += (0..10).random()
        j *= (j + 1)
        j /= (j - 1)
    }
    resultCallback("$j")
}


fun print(string: String) {
    Logger.v(string, null, "LOG TAG VIDEO")
}
