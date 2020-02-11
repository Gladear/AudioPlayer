package fr.cpe.audioplayer.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.model.TrackAction

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AudioControlFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class AudioControlFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    var playing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_audio_control, container, false)

        // Add listeners to buttons
        view.findViewById<ImageButton>(R.id.previous_track).setOnClickListener {
            listener?.onFragmentInteraction(TrackAction.PREV)
        }

        view.findViewById<ImageButton>(R.id.next_track).setOnClickListener {
            listener?.onFragmentInteraction(TrackAction.NEXT)
        }

        view.findViewById<ImageButton>(R.id.play_pause_track).setOnClickListener {
            listener?.onFragmentInteraction(
                if (playing) TrackAction.PAUSE
                else TrackAction.PLAY
            )
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
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
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(action: TrackAction)
    }
}
