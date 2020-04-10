package fr.cpe.audioplayer.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.FragmentAudioFileListBinding
import fr.cpe.audioplayer.factory.AudioFileFactory
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.viewmodel.PlaylistViewModel
import kotlin.concurrent.thread

class AudioFileListFragment : Fragment(), AudioFileListAdapter.OnAudioFileInteractionListener {
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

    private val adapter = AudioFileListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAudioFileListBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_audio_file_list,
                container,
                false
            )

        val audioFileList = binding.audioFileList
        audioFileList.layoutManager = LinearLayoutManager(binding.root.context)
        audioFileList.adapter = adapter
        adapter.listener = this

        loadAudioFiles()

        return binding.root
    }

    override fun onAudioFileInteraction(position: Int, audioFile: AudioFile) {
        val tracks = AudioFileFactory.getAudioFiles(context!!).toList()

        PlaylistViewModel.tracks = tracks
        PlaylistViewModel.position = position
        PlaylistViewModel.isPlaying = true
    }

    override fun onDetach() {
        super.onDetach()
        adapter.listener = null
    }

    private fun loadAudioFiles() {
        // Check for required permission
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(context!!)
                    .setMessage(R.string.dialog_permission_read_external_storage)
                    .setPositiveButton(R.string.dialog_permission_read_external_storage_positive) { _, _ ->
                        loadAudioFiles()
                    }
                    .setNegativeButton(R.string.dialog_permission_read_external_storage_negative) { _, _ ->
                        // Do nothing
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            // Do file-related operations

            val audioFiles = AudioFileFactory.getAudioFiles(context!!)

            for (audioFile in audioFiles) {
                adapter.add(audioFile)
            }

            thread(start = true) {
                for ((index, audioFile) in audioFiles.withIndex()) {
                    AudioFileFactory.getDetailsAudioFile(audioFile)
                    activity!!.runOnUiThread { adapter.notifyItemChanged(index) }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // file-related task you need to do.
                    loadAudioFiles()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}