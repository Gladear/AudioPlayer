package fr.cpe.audioplayer.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import fr.cpe.audioplayer.utils.TimeFormatter

data class AudioFile(
    val filePath: String,
    val title: String,
    var album: String,
    val artist: String,
    var duration: Int = 0,
    var genre: String = "",
    var image: Drawable? = null

) {
    val durationText: String
        get() = TimeFormatter.formatDuration(duration)
}