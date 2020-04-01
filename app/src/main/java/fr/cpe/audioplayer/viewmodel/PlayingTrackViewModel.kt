package fr.cpe.audioplayer.viewmodel

import androidx.databinding.BaseObservable
import fr.cpe.audioplayer.model.AudioFile

class PlayingTrackViewModel : BaseObservable() {
    var audioFile: AudioFile? = null
        set(value) {
            field = value
            notifyChange()
        }

    var isPlaying = false
        set(value) {
            if (audioFile == null) {
                throw IllegalStateException("No audio track")
            }

            field = value
            notifyChange()
        }

    val present: Boolean
        get() {
            return audioFile != null
        }

    val displayName: String
        get() {
            var displayName = audioFile!!.title

            if (audioFile!!.artist.isNotEmpty()) {
                displayName += " - ${audioFile!!.artist}"
            }

            return displayName
        }
}