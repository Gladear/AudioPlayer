package fr.cpe.audioplayer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.fragment.AudioFileListAdapter
import fr.cpe.audioplayer.fragment.AudioFileListFragment
import fr.cpe.audioplayer.fragment.TrackControlFragment
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.model.TrackAction

const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

class MainActivity : AppCompatActivity(), TrackControlFragment.OnTrackControlInteractionListener,
    AudioFileListAdapter.OnAudioFileInteractionListener {

    private var audioFileList: AudioFileListFragment? = null
    private var trackControl: TrackControlFragment? = null

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
    }

    override fun onAudioFileInteraction(position: Int, audioFile: AudioFile) {
        trackControl!!.apply {
            track = audioFile
            playing = true
        }
    }

    override fun onTrackControlInteraction(action: TrackAction) {
        when (action) {
            TrackAction.PLAY -> {
                trackControl!!.playing = true
            }
            TrackAction.PAUSE -> {
                trackControl!!.playing = false
            }
            TrackAction.PREV -> {

            }
            TrackAction.NEXT -> {

            }
        }
    }
}
