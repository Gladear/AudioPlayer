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
import fr.cpe.audioplayer.fragment.AudioFileListFragment
import fr.cpe.audioplayer.fragment.TrackControlFragment
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.service.PlayerService
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        // Display the fragments
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.audio_list_fragment_container, AudioFileListFragment())
        transaction.replace(R.id.track_control_fragment_container, TrackControlFragment())
        transaction.commit()

        val bound = bindService(
            Intent(this, PlayerService::class.java),
            object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                }
            },
            Context.BIND_AUTO_CREATE
        )

        if (!bound) {
            Toast.makeText(this, R.string.error_start_service, Toast.LENGTH_SHORT).show()
        }
    }
}
