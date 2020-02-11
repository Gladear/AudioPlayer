package fr.cpe.audioplayer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.AudioFileListFragmentBinding
import fr.cpe.audioplayer.model.AudioFile

class AudioFileListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AudioFileListFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_audio_file_list, container, false)
        binding.audioFileList.layoutManager = LinearLayoutManager(binding.root.context)

        binding.audioFileList.adapter = AudioFileListAdapter(
            listOf(
                AudioFile(
                    "Stairway to Heaven",
                    "/sdcard/Music/stairway-to-heaven.mp3",
                    "Led Zeppelin",
                    "Led Zeppelin IV",
                    "Rock Prog",
                    1974,
                    480
                ),
                AudioFile(
                    "Highway Star",
                    "/sdcard/Music/highway-star.mp3",
                    "Deep Purple",
                    "Machine Head",
                    "Classic Rock",
                    1972,
                    326
                ),
                AudioFile(
                    "Master of Puppet",
                    "/sdcard/Music/master-of-puppets.mp3",
                    "Metallica",
                    "Master of Puppets",
                    "Thrash Metal",
                    1972,
                    507
                )
            )
        )

        return binding.root
    }
}