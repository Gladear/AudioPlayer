package fr.cpe.audioplayer.factory

import android.app.Activity
import android.provider.MediaStore
import fr.cpe.audioplayer.model.AudioFile

class AudioFileFactory {
    companion object {
        fun getAudioFileList(activity: Activity): List<AudioFile> {
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST
            )

            val cursor = activity.contentResolver.query(uri, projection, null, null, null)
            val audioFiles = ArrayList<AudioFile>()

            cursor?.let {
                while (it.moveToNext()) {
                    val filepath = it.getString(0)
                    val title = it.getString(1)
                    val album = it.getString(2)
                    val artist = it.getString(3)

                    audioFiles.add(
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

            return audioFiles
        }
    }
}