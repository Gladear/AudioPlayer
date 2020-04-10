package fr.cpe.audioplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.fragment.CurrentTrackFragment

class CurrentTrackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_playing_track)

        // Display the fragments
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.playing_track_container, CurrentTrackFragment())
        transaction.commit()
    }
}
