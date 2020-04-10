package fr.cpe.audioplayer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.FragmentCurrentTrackBinding
import fr.cpe.audioplayer.viewmodel.PlaylistViewModel

class CurrentTrackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentCurrentTrackBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_track, container, false)

        binding.apply {
            track = PlaylistViewModel.currentTrackViewModel

            playPauseTrack.setOnClickListener {
                PlaylistViewModel.togglePlay();
            }

            previousTrack.setOnClickListener {
                PlaylistViewModel.prev()
            }

            nextTrack.setOnClickListener {
                PlaylistViewModel.next()
            }

            return playingTrackRoot
        }
    }
}
