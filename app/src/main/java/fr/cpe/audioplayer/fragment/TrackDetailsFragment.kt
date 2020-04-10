package fr.cpe.audioplayer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.FragmentTrackDetailsBinding
import fr.cpe.audioplayer.viewmodel.PlaylistViewModel

class TrackDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTrackDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_track_details, container, false)

        binding.track = PlaylistViewModel.currentTrackViewModel

        return binding.playingTrackRoot
    }
}
