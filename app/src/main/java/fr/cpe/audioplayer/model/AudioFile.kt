package fr.cpe.audioplayer.model

import fr.cpe.audioplayer.utils.TimeFormatter

data class AudioFile(
    val filePath: String,
    val title: String,
    val artist: String,
    var album: String,
    var duration: Int = 0,
    var genre: String = ""

) {
    val durationText: String
        get() = TimeFormatter.formatDuration(duration)
}