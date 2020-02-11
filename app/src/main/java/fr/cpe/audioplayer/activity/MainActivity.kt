package fr.cpe.audioplayer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.ActivityMainBinding
import fr.cpe.audioplayer.fragment.TrackControlFragment
import fr.cpe.audioplayer.fragment.AudioFileListFragment
import fr.cpe.audioplayer.model.TrackAction

const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

class MainActivity : AppCompatActivity(), TrackControlFragment.OnFragmentInteractionListener {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        // Display the fragments
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.audio_list_fragment_container, AudioFileListFragment())
        transaction.replace(R.id.track_control_fragment_container, TrackControlFragment())
        transaction.commit()

    }

    override fun onFragmentInteraction(action: TrackAction) {
        // TODO not implemented
    }
}
