package fr.cpe.audioplayer.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import fr.cpe.audioplayer.model.AudioFile

object PlaylistViewModel : BaseObservable() {
    const val PROPERTY_CURRENT_TRACK = 1
    const val PROPERTY_TRACKS = 2
    const val PROPERTY_PLAYING = 3

    var position = 0
        set(value) {
            field = value

            if (position == -1) {
                isPlaying = false
            }

            notifyPropertyChanged(PROPERTY_CURRENT_TRACK)
        }

    var isPlaying = false
        set(value) {
            field = value
            notifyPropertyChanged(PROPERTY_PLAYING)
        }

    var tracks: List<AudioFile> = emptyList()
        set(value) {
            field = value
            position = -1
            notifyPropertyChanged(PROPERTY_TRACKS)
        }

    val currentTrack: AudioFile?
        @Bindable
        get() = if (position < 0) null else tracks[position]

    val currentTrackViewModel = CurrentTrackViewModel()

    fun pause() {
        isPlaying = false
    }

    fun resume() {
        isPlaying = true
    }

    fun togglePlay() {
        if (isPlaying) {
            pause()
        } else {
            resume()
        }
    }

    fun prev() {
        position = if (position > 0) position - 1 else 0
    }

    fun hasNext(): Boolean {
        return position < tracks.size - 1
    }

    fun next() {
        if (!hasNext()) {
            position = -1
        } else {
            position += 1
        }
    }
}