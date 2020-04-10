package fr.cpe.audioplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.activity.CurrentTrackActivity
import fr.cpe.audioplayer.databinding.FragmentTrackControlBinding
import fr.cpe.audioplayer.viewmodel.PlaylistViewModel

class TrackControlFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTrackControlBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_track_control, container, false)

        PlaylistViewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    PlaylistViewModel.PROPERTY_CURRENT_TRACK -> {
                        binding.track!!.audioFile = PlaylistViewModel.currentTrack
                    }
                    PlaylistViewModel.PROPERTY_PLAYING -> {
                        binding.track!!.isPlaying = PlaylistViewModel.isPlaying
                    }
                }
            }
        })

        // Add listeners to buttons
        binding.apply {
            track = PlaylistViewModel.currentTrackViewModel

            playPauseTrack.setOnClickListener {
                PlaylistViewModel.togglePlay()
            }

            previousTrack.setOnClickListener {
                PlaylistViewModel.prev()
            }

            nextTrack.setOnClickListener {
                PlaylistViewModel.next()
            }

            trackControlRoot.setOnClickListener {
                val intent = Intent(activity!!, CurrentTrackActivity::class.java)
                startActivity(intent)
            }

            return trackControlRoot
        }
    }
}
