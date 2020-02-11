package fr.cpe.audioplayer.activity

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.databinding.DataBindingUtil
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.ActivityMainBinding
import fr.cpe.audioplayer.factory.AudioFileFactory
import fr.cpe.audioplayer.fragment.AudioControlFragment
import fr.cpe.audioplayer.fragment.AudioFileListFragment
import fr.cpe.audioplayer.model.TrackAction

const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

class MainActivity : AppCompatActivity(), AudioControlFragment.OnFragmentInteractionListener {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        // Display the fragments
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.audio_list_fragment_container, AudioFileListFragment())
        transaction.replace(R.id.audio_control_fragment_container, AudioControlFragment())
        transaction.commit()

        loadAudioFiles()
    }

    private fun loadAudioFiles() {
        // Check for required permission
        if (checkSelfPermission(
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
                AlertDialog.Builder(this)
                    .setMessage(R.string.dialog_permission_read_external_storage)
                    .setPositiveButton(R.string.dialog_permission_read_external_storage_positive) { _, _ ->
                        loadAudioFiles()
                    }
                    .setNegativeButton(R.string.dialog_permission_read_external_storage_negative) { _, _ ->
                        // Do nothing
                    }
                    .create()
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

            val audioFileList = AudioFileFactory.getAudioFileList(this)
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

    override fun onFragmentInteraction(action: TrackAction) {
        // TODO not implemented
    }
}
