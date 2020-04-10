package fr.cpe.audioplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.fragment.TrackDetailsFragment
import fr.cpe.audioplayer.fragment.TrackControlFragment

class CurrentTrackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_track)
    }
}
