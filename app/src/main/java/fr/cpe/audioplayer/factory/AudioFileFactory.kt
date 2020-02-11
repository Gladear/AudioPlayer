package fr.cpe.audioplayer.factory

import android.content.Context
import android.provider.MediaStore
import fr.cpe.audioplayer.model.AudioFile

class AudioFileFactory {
    companion object {
        fun getAudioFiles(context: Context): Sequence<AudioFile> {
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST
            )

            return sequence {
                val cursor = context.contentResolver.query(uri, projection, null, null, null)

                cursor?.let {
                    while (it.moveToNext()) {
                        val filepath = it.getString(0)
                        val title = it.getString(1)
                        val album = it.getString(2)
                        val artist = it.getString(3)

                        yield(
                            AudioFile(
                                filepath,
                                title,
                                album,
                                artist
                            )
                        )
                    }

                    it.close()
                }
            }
        }
    }
}