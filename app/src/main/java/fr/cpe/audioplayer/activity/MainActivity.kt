package fr.cpe.audioplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.ActivityMainBinding
import fr.cpe.audioplayer.fragment.AudioControlFragment
import fr.cpe.audioplayer.fragment.AudioFileListFragment
import fr.cpe.audioplayer.model.TrackAction

class MainActivity : AppCompatActivity(), AudioControlFragment.OnFragmentInteractionListener {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.audio_list_fragment_container, AudioFileListFragment())
        transaction.replace(R.id.audio_control_fragment_container, AudioControlFragment())
        transaction.commit()
    }

    override fun onFragmentInteraction(action: TrackAction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
