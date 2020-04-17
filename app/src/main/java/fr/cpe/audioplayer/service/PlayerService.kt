package fr.cpe.audioplayer.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import androidx.databinding.Observable
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.viewmodel.PlaylistViewModel


class PlayerService : Service(), MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener
//    , MediaPlayer.OnCompletionListener
{
    private val binder: Binder = PlayerBinder()
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var timer: CountDownTimer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        mediaPlayer.setOnPreparedListener(this)
//        mediaPlayer.setOnCompletionListener(this)

        PlaylistViewModel.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    PlaylistViewModel.PROPERTY_CURRENT_TRACK -> {
                        PlaylistViewModel.currentTrack?.let {
                            playFile(it)
                        } ?: run {
                            pause()
                        }
                    }
                    PlaylistViewModel.PROPERTY_PLAYING -> {
                        if (PlaylistViewModel.isPlaying) {
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
        val viewModel = PlaylistViewModel.currentTrackViewModel

        if (PlaylistViewModel.isPlaying) {
            mediaPlayer.start()
            viewModel.audioFile?.duration = mediaPlayer.duration
            startTimer()
        }
    }

//    override fun onCompletion(player: MediaPlayer) {
//        PlaylistViewModel.next()
//    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    private fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            timer?.cancel()
        }
    }

    private fun resume() {
        mediaPlayer.start()
        startTimer()
    }

    private fun startTimer() {
        val viewModel = PlaylistViewModel.currentTrackViewModel

        timer = object :
            CountDownTimer((mediaPlayer.duration - mediaPlayer.currentPosition).toLong(), 1000L) {
            override fun onFinish() {
                // Move to next song
                // Workaround while onCompletionListener doesn't work
                PlaylistViewModel.next()
            }

            override fun onTick(millisUntilFinished: Long) {
                if (PlaylistViewModel.isPlaying) {
                    viewModel.currentPosition = mediaPlayer.currentPosition
                }
            }
        }
        timer!!.start()
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