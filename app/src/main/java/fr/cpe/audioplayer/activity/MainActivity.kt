package fr.cpe.audioplayer.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.factory.AudioFileFactory
import fr.cpe.audioplayer.fragment.AudioFileListAdapter
import fr.cpe.audioplayer.fragment.AudioFileListFragment
import fr.cpe.audioplayer.fragment.TrackControlFragment
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.model.Playlist
import fr.cpe.audioplayer.model.TrackAction
import fr.cpe.audioplayer.service.PlayerService

const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

class MainActivity : AppCompatActivity(), TrackControlFragment.OnTrackControlInteractionListener,
    AudioFileListAdapter.OnAudioFileInteractionListener {

    private var audioFileList: AudioFileListFragment? = null
    private var trackControl: TrackControlFragment? = null

    private var audioService: PlayerService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Display the fragments
        val transaction = supportFragmentManager.beginTransaction()

        audioFileList = AudioFileListFragment()
        trackControl = TrackControlFragment()

        transaction.replace(R.id.audio_list_fragment_container, audioFileList!!)
        transaction.replace(R.id.track_control_fragment_container, trackControl!!)
        transaction.commit()

        val bound = bindService(
            Intent(this, PlayerService::class.java),
            object : ServiceConnection {
                override fun onNullBinding(name: ComponentName?) {
                    Toast.makeText(this@MainActivity, "Null binding", Toast.LENGTH_SHORT).show()
                }

                override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                    audioService = (binder as PlayerService.PlayerBinder).getService()
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    audioService = null
                }
            },
            Context.BIND_AUTO_CREATE
        )

        if (!bound) {
            Toast.makeText(this, R.string.error_start_service, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAudioFileInteraction(position: Int, audioFile: AudioFile) {
        trackControl!!.apply {
            track = audioFile
            playing = true
        }

        val audioFiles = AudioFileFactory.getAudioFiles(this).toList()
        val playlist = Playlist(audioFiles)
        playlist.position = position

        audioService?.play(playlist)
    }

    override fun onTrackControlInteraction(action: TrackAction) {
        when (action) {
            TrackAction.PLAY -> {
                trackControl!!.playing = true

                audioService?.resume()
            }
            TrackAction.PAUSE -> {
                trackControl!!.playing = false

                audioService?.pause()
            }
            TrackAction.PREV -> {
                audioService?.prev()
            }
            TrackAction.NEXT -> {
                audioService?.next()
            }
        }
    }
}
