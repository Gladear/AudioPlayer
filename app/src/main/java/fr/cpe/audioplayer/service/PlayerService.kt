package fr.cpe.audioplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.model.Playlist


class PlayerService : Service(), MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener {
    private val binder: Binder = PlayerBinder()
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var playlist: Playlist? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun play(list: Playlist) {
        playlist = list
        playFile(list.next())
    }

    fun resume() {
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun prev() {
        playlist?.let {
            playFile(it.prev())
        }
    }

    fun next() {
        playlist?.let {
            playFile(it.next())
        }
    }

    private fun playFile(audioFile: AudioFile) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(audioFile.filePath)
        mediaPlayer.prepareAsync()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnCompletionListener(this)
    }

    override fun onDestroy() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    override fun onPrepared(player: MediaPlayer) {
        mediaPlayer.start()
    }

    override fun onCompletion(player: MediaPlayer) {
        playFile(playlist!!.next())
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    inner class PlayerBinder : Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }
}