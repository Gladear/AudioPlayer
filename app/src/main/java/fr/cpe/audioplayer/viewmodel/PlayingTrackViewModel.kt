package fr.cpe.audioplayer.viewmodel

import androidx.databinding.BaseObservable
import fr.cpe.audioplayer.model.AudioFile
import java.lang.IllegalStateException

class PlayingTrackViewModel : BaseObservable() {
    var audioFile: AudioFile? = null
        set(value) {
            field = value
            notifyChange()
        }

    var playing = false
        set(value) {
            if (audioFile == null) {
                throw IllegalStateException("No audio track")
            }

            field = value
            notifyChange()
        }
}