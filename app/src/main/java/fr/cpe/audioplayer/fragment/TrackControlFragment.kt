package fr.cpe.audioplayer.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.FragmentTrackControlBinding
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.model.TrackAction
import fr.cpe.audioplayer.viewmodel.PlayingTrackViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TrackControlFragment.OnTrackControlInteractionListener] interface
 * to handle interaction events.
 */
class TrackControlFragment : Fragment() {
    private var binding: FragmentTrackControlBinding? = null
    private var listener: OnTrackControlInteractionListener? = null

    var track: AudioFile?
        get() = binding!!.track!!.audioFile
        set(value) {
            binding!!.track!!.audioFile = value
        }

    var playing: Boolean
        get() = binding!!.track!!.playing
        set(value) {
            binding!!.track!!.playing = value
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_track_control, container, false)
        binding!!.track = PlayingTrackViewModel()

        val view = binding!!.frameLayout

        // Add listeners to buttons
        view.findViewById<ImageButton>(R.id.previous_track).setOnClickListener {
            listener?.onTrackControlInteraction(TrackAction.PREV)
        }

        view.findViewById<ImageButton>(R.id.next_track).setOnClickListener {
            listener?.onTrackControlInteraction(TrackAction.NEXT)
        }

        view.findViewById<ImageButton>(R.id.play_pause_track).setOnClickListener {
            listener?.onTrackControlInteraction(
                if (track != null && playing) TrackAction.PAUSE
                else TrackAction.PLAY
            )
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTrackControlInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnTrackControlInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnTrackControlInteractionListener {
        fun onTrackControlInteraction(action: TrackAction)
    }
}
