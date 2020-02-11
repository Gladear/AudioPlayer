package fr.cpe.audioplayer.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import fr.cpe.audioplayer.model.AudioFile

class AudioFileViewModel: BaseObservable() {
    var audioFile: AudioFile? = null
        set(value) {
            field = value
            notifyChange()
        }

    val artist: String
        @Bindable
        get() = audioFile!!.artist

    val title: String
        @Bindable
        get() = audioFile!!.title

    val album: String
        @Bindable
        get() = audioFile!!.album

    val duration: String
        @Bindable
        get() = audioFile!!.durationText
}