package fr.cpe.audioplayer.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import fr.cpe.audioplayer.R
import fr.cpe.audioplayer.databinding.AudioFileItemBinding
import fr.cpe.audioplayer.model.AudioFile
import fr.cpe.audioplayer.viewmodel.AudioFileViewModel

class AudioFileListAdapter(private val audioFileList: List<AudioFile>) :
    RecyclerView.Adapter<AudioFileListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.audio_file_item,
            parent,
            false
        ) as AudioFileItemBinding

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = audioFileList[position]
        holder.viewModel.audioFile = file
    }

    override fun getItemCount(): Int {
        return audioFileList.size
    }

    inner class ViewHolder(binding: AudioFileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val viewModel = AudioFileViewModel()

        init {
            binding.model = viewModel
        }
    }
}