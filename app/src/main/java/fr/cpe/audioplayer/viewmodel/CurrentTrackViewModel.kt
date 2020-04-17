package fr.cpe.audioplayer.viewmodel

import androidx.databinding.BaseObservable
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.utils.TimeFormatter

class CurrentTrackViewModel : BaseObservable() {
    var audioFile: AudioFile? = null
        set(value) {
            field = value
            notifyChange()
        }

    val displayName: String
        get() {
            var displayName = audioFile!!.title

            if (audioFile!!.artist.isNotEmpty()) {
                displayName += " - ${audioFile!!.artist}"
            }

            return displayName
        }

    val present: Boolean
        get() {
            return audioFile != null
        }

    var isPlaying = false
        set(value) {
            field = value
            notifyChange()
        }

    var currentPosition = 0
        set(value) {
            field = value
            notifyChange()
        }

    val currentPositionText: String
        get() = TimeFormatter.formatDuration(currentPosition)
}