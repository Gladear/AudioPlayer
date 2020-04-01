package fr.cpe.audioplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.databinding.Observable
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.model.Playlist


class PlayerService : Service(), MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener
//    , MediaPlayer.OnCompletionListener
{
    private val binder: Binder = PlayerBinder()
    private val mediaPlayer: MediaPlayer = MediaPlayer()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        mediaPlayer.setOnPreparedListener(this)
//        mediaPlayer.setOnCompletionListener(this)

        Playlist.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    Playlist.PROPERTY_CURRENT_TRACK -> {
                        Playlist.currentTrack?.let {
                            playFile(it)
                        } ?: run {
                            pause()
                        }
                    }
                    Playlist.PROPERTY_PLAYING -> {
                        if (Playlist.isPlaying) {
                            resume()
                        } else {
                            pause()
                        }
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    override fun onPrepared(player: MediaPlayer) {
        if (Playlist.isPlaying) {
            mediaPlayer.start()
        }
    }

//    override fun onCompletion(player: MediaPlayer) {
//        Playlist.next()
//    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    private fun pause() {
        mediaPlayer.pause()
    }

    private fun resume() {
        mediaPlayer.start()
    }

    private fun playFile(audioFile: AudioFile) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(audioFile.filePath)
        mediaPlayer.prepareAsync()
    }

    inner class PlayerBinder : Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }
}