package fr.cpe.audioplayer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.FragmentTrackControlBinding
import fr.cpe.audioplayer.model.Playlist
import fr.cpe.audioplayer.viewmodel.PlayingTrackViewModel

class TrackControlFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTrackControlBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_track_control, container, false)

        Playlist.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    Playlist.PROPERTY_CURRENT_TRACK -> {
                        binding.track!!.audioFile = Playlist.currentTrack
                    }
                    Playlist.PROPERTY_PLAYING -> {
                        binding.track!!.isPlaying = Playlist.isPlaying
                    }
                }
            }
        })

        // Add listeners to buttons
        binding.apply {
            track = PlayingTrackViewModel()

            previousTrack.setOnClickListener {
                Playlist.prev()
            }

            nextTrack.setOnClickListener {
                Playlist.next()
            }

            playPauseTrack.setOnClickListener {
                if (Playlist.isPlaying) {
                    Playlist.pause()
                } else {
                    Playlist.resume()
                }
            }

            return frameLayout
        }

    }
}
