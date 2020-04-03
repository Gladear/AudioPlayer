package fr.cpe.audioplayer.factory

import android.content.Context
import android.provider.MediaStore
import fr.cpe.audioplayer.model.AudioFile
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class AudioFileFactory {
    companion object {
        private var cache: ArrayList<AudioFile>? = null

        private var key = "9827a7f23811360556bb3ea33f67e949"

        private var lastfmapi =
            "https://ws.audioscrobbler.com/2.0/?method=track.getinfo&track=YOUR_TITLE&artist=YOUR_ARTIST&api_key=YOUR_API_KEY&format=json"
        //  https://ws.audioscrobbler.com/2.0/?method=track.getinfo&track=money&artist=pink%20floyd&api_key=9827a7f23811360556bb3ea33f67e949&format=json

        fun getAudioFiles(context: Context): Sequence<AudioFile> {
            if (cache != null) {
                return cache!!.asSequence()
            }

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
                    cache = ArrayList(it.count)

                    while (it.moveToNext()) {
                        val filepath = it.getString(0)
                        val title = it.getString(1)
                        val album = it.getString(2)
                        val artist = it.getString(3)

                        val audioFile = AudioFile(
                            filepath,
                            title,
                            album ?: "",
                            artist ?: ""
                        )

                        cache!!.add(audioFile)

                        yield(audioFile)
                    }

                    it.close()
                }
            }
        }

        fun getDetailsAudioFile(song: AudioFile): AudioFile {

            try {
                if (song.title == "")
                    throw IllegalAccessError("the song has no title")
                else
                    lastfmapi = lastfmapi.replace("YOUR_TITLE", song.title)

                if (song.artist == "")
                    throw IllegalAccessError("the song has no artiste")
                else
                    lastfmapi = lastfmapi.replace("YOUR_ARTIST", song.artist)

                lastfmapi = lastfmapi.replace("YOUR_API_KEY", key)

                var url: URL = URL(lastfmapi)
                var c: HttpURLConnection
                c = url.openConnection() as HttpURLConnection
                c.requestMethod = "GET"
                c.doInput = true

                var strReader: InputStreamReader = InputStreamReader(c.inputStream)
                var strdata: String = BufferedReader(strReader).readLine()
                strReader.close()

                var jsonData: JSONObject = JSONObject(strdata)
                var result: JSONObject = jsonData.getJSONObject("track")
                val album = result.getJSONObject("album")

                song.album = album.getString("title")
                song.genre = result.getJSONObject("toptags").getJSONArray("tag").getJSONObject(0)
                    .getString("name")
                song.duration = result.getInt("duration")
            } catch (e: IOException) {

            }
            catch (e: JSONException){

            }
            return song

        }
    }
}
