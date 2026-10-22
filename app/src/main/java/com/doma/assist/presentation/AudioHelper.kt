package com.doma.assist.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager

/**
 * Consulta e monitora as saídas de áudio do dispositivo (alto-falante e Bluetooth).
 */
class AudioHelper(private val context: Context) {

    private val audioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun hasAudioOutputFeature(): Boolean =
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)

    fun audioOutputAvailable(type: Int): Boolean {
        if (!hasAudioOutputFeature()) return false

        return audioManager
            .getDevices(AudioManager.GET_DEVICES_OUTPUTS)
            .any { it.type == type }
    }

    fun isSpeakerAvailable(): Boolean =
        audioOutputAvailable(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER)

    fun isBluetoothConnected(): Boolean =
        audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) ||
                audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_SCO)

    /**
     * Registra um callback que avisa quando dispositivos de áudio são
     * conectados ou removidos. Guarde o retorno para chamar [unregisterCallback].
     */
    fun registerCallback(onDevicesChanged: () -> Unit): AudioDeviceCallback {
        val callback = object : AudioDeviceCallback() {
            override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>?) {
                onDevicesChanged()
            }

            override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>?) {
                onDevicesChanged()
            }
        }

        // handler = null: os eventos chegam na thread principal
        audioManager.registerAudioDeviceCallback(callback, null)
        return callback
    }

    fun unregisterCallback(callback: AudioDeviceCallback) {
        audioManager.unregisterAudioDeviceCallback(callback)
    }
}