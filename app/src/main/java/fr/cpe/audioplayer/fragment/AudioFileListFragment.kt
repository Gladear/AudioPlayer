package fr.cpe.audioplayer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.FragmentAudioFileListBinding

class AudioFileListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAudioFileListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_audio_file_list, container, false)
        binding.audioFileList.layoutManager = LinearLayoutManager(binding.root.context)

        return binding.root
    }
}