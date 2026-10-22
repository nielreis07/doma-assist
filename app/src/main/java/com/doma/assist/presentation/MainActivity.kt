package com.doma.assist.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.doma.assist.presentation.theme.DomaAssistTheme
import java.util.Locale

private const val STATUS_READY = "Pronto para ajudar"
private const val STATUS_STARTING = "Iniciando voz..."

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    val context = LocalContext.current
    val audioHelper = remember(context) { AudioHelper(context) }

    var status by remember {
        mutableStateOf(STATUS_STARTING)
    }

    var ttsReady by remember {
        mutableStateOf(false)
    }

    var tts by remember {
        mutableStateOf<TextToSpeech?>(null)
    }

    var bluetoothConnected by remember {
        mutableStateOf(audioHelper.isBluetoothConnected())
    }

    // Detecção dinâmica: avisa quando um fone Bluetooth conecta ou desconecta
    DisposableEffect(audioHelper) {
        val callback = audioHelper.registerCallback {
            val connectedNow = audioHelper.isBluetoothConnected()

            if (connectedNow != bluetoothConnected) {
                bluetoothConnected = connectedNow
                status = if (connectedNow) {
                    "Fone Bluetooth conectado"
                } else {
                    "Fone Bluetooth desconectado"
                }
            }
        }

        onDispose {
            audioHelper.unregisterCallback(callback)
        }
    }

    DisposableEffect(context) {
        var speech: TextToSpeech? = null

        speech = TextToSpeech(context) { result ->
            val engine = speech

            if (result == TextToSpeech.SUCCESS && engine != null) {
                val languageResult = engine.setLanguage(
                    Locale.forLanguageTag("pt-BR")
                )

                val languageAvailable =
                    languageResult != TextToSpeech.LANG_MISSING_DATA &&
                            languageResult != TextToSpeech.LANG_NOT_SUPPORTED

                engine.setOnUtteranceProgressListener(
                    object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {}

                        override fun onDone(utteranceId: String?) {
                            status = STATUS_READY
                        }

                        @Deprecated("Deprecated in Java")
                        override fun onError(utteranceId: String?) {
                            status = "Erro ao reproduzir o aviso"
                        }

                        override fun onError(utteranceId: String?, errorCode: Int) {
                            status = "Erro ao reproduzir o aviso"
                        }
                    }
                )

                ttsReady = languageAvailable

                status = if (languageAvailable) {
                    STATUS_READY
                } else {
                    "Idioma de voz indisponível"
                }
            } else {
                ttsReady = false
                status = "Falha ao iniciar a voz"
            }
        }

        tts = speech

        onDispose {
            speech?.stop()
            speech?.shutdown()
            tts = null
            ttsReady = false
        }
    }

    fun speak(message: String) {
        val engine = tts

        if (ttsReady && engine != null) {
            val result = engine.speak(
                message,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "doma_assist_${System.currentTimeMillis()}"
            )

            status = if (result == TextToSpeech.SUCCESS) {
                "Reproduzindo aviso..."
            } else {
                "Não foi possível reproduzir o aviso"
            }
        } else {
            status = "Voz indisponível. Verifique o mecanismo de voz."
        }
    }

    DomaAssistTheme {
        AppScaffold {
            val listState = rememberTransformingLazyColumnState()
            val transformationSpec = rememberTransformationSpec()

            ScreenScaffold(
                scrollState = listState,
                edgeButton = {
                    EdgeButton(
                        onClick = {
                            speak(
                                "Doma Assist. Estou aqui para ajudar."
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                MaterialTheme.colorScheme.secondaryContainer,
                            contentColor =
                                MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text("Ajuda")
                    }
                }
            ) { contentPadding ->
                TransformingLazyColumn(
                    contentPadding = contentPadding,
                    state = listState
                ) {
                    item {
                        ListHeader(
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(
                                    this,
                                    transformationSpec
                                ),
                            transformation = SurfaceTransformation(
                                transformationSpec
                            )
                        ) {
                            Text("Doma Assist")
                        }
                    }

                    item {
                        Text(
                            text = status,
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(
                                    this,
                                    transformationSpec
                                )
                        )
                    }

                    item {
                        Button(
                            onClick = {
                                speak(
                                    "Olá! Este é o Doma Assist. " +
                                            "Estou aqui para ajudar você durante o trabalho."
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(
                                    this,
                                    transformationSpec
                                ),
                            transformation = SurfaceTransformation(
                                transformationSpec
                            )
                        ) {
                            Text("Ouvir aviso")
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                speak(
                                    "Atenção. Em uma emergência, procure um local " +
                                            "seguro e siga as orientações da equipe."
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(
                                    this,
                                    transformationSpec
                                ),
                            transformation = SurfaceTransformation(
                                transformationSpec
                            )
                        ) {
                            Text("Alerta de segurança")
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                status = describeAudioOutput(audioHelper)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(
                                    this,
                                    transformationSpec
                                ),
                            transformation = SurfaceTransformation(
                                transformationSpec
                            )
                        ) {
                            Text("Verificar áudio")
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                status = if (openBluetoothSettings(context)) {
                                    "Abrindo configurações de Bluetooth"
                                } else {
                                    "Configurações de Bluetooth indisponíveis"
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(
                                    this,
                                    transformationSpec
                                ),
                            transformation = SurfaceTransformation(
                                transformationSpec
                            )
                        ) {
                            Text("Conectar fone")
                        }
                    }
                }
            }
        }
    }
}

private fun describeAudioOutput(audioHelper: AudioHelper): String {
    if (!audioHelper.hasAudioOutputFeature()) {
        return "Saída de áudio não identificada"
    }

    val hasSpeaker = audioHelper.isSpeakerAvailable()
    val hasBluetooth = audioHelper.isBluetoothConnected()

    return when {
        hasSpeaker && hasBluetooth ->
            "Alto-falante e Bluetooth disponíveis"

        hasSpeaker ->
            "Alto-falante disponível"

        hasBluetooth ->
            "Áudio Bluetooth disponível"

        else ->
            "Nenhuma saída de áudio identificada"
    }
}

/**
 * Leva o usuário às configurações de Bluetooth para parear um fone.
 * Retorna false se o relógio não tiver essa tela.
 */
private fun openBluetoothSettings(context: Context): Boolean {
    val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    return try {
        context.startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    DomaAssistTheme {
        Text("Doma Assist")
    }
}