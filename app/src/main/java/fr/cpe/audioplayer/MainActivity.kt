package fr.cpe.audioplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import fr.cpe.audioplayer.databinding.ActivityMainBinding
import fr.cpe.audioplayer.fragment.AudioFileListFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = AudioFileListFragment()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
