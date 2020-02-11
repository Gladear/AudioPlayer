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

    fun addAll(files: Collection<AudioFile>): Boolean {
        val size = audioFileList.size
        val result = audioFileList.addAll(files)

        notifyItemRangeInserted(size, files.size)
        return result
    }

    inner class ViewHolder(binding: AudioFileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val viewModel = AudioFileViewModel()

        init {
            binding.model = viewModel
        }
    }
}