package fr.cpe.audioplayer.model

import java.lang.IllegalStateException

class Playlist(private val trackList: List<AudioFile>) {
    private var pointer: Int = -1

    var position: Int
        get() = pointer + 1
        set(value) {
            pointer = value - 1
        }

    fun prev(): AudioFile {
        if (pointer > 0) {
            pointer -= 1
        }
        return trackList[pointer]
    }

    fun hasNext(): Boolean {
        return pointer < trackList.size - 1
    }

    fun next(): AudioFile {
        if (!hasNext()) {
            throw IllegalStateException("Playlist doesn't have a next track")
        }

        pointer += 1
        return trackList[pointer]
    }
}