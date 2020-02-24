package fr.cpe.audioplayer.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.AudioFileItemBinding
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.viewmodel.AudioFileViewModel

class AudioFileListAdapter : RecyclerView.Adapter<AudioFileListAdapter.ViewHolder>() {

    private val audioFileList: ArrayList<AudioFile> = ArrayList()
    var listener: OnAudioFileInteractionListener? = null

    fun add(file: AudioFile): Boolean {
        val size = audioFileList.size
        val result = audioFileList.add(file)

        notifyItemInserted(size)
        return result
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: AudioFileItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.audio_file_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val audioFile = audioFileList[position]

        holder.viewModel.audioFile = audioFile

        holder.itemView.setOnClickListener {
            listener?.onAudioFileInteraction(position, audioFile)
        }
    }

    override fun getItemCount(): Int {
        return audioFileList.size
    }

    inner class ViewHolder(binding: AudioFileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val viewModel = AudioFileViewModel()

        init {
            binding.track = viewModel
        }
    }

    interface OnAudioFileInteractionListener {
        fun onAudioFileInteraction(position: Int, audioFile: AudioFile)
    }
}