package com.example.myapplication22


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication22.Models.Task
import com.example.myapplication22.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : DialogFragment() {

    private lateinit var binding: FragmentTaskDetailsBinding

    companion object {
        const val ARG_TASK = "task"

        fun newInstance(task: Task): TaskDetailsFragment {
            val fragment = TaskDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_TASK, task)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = arguments?.getParcelable<Task>(ARG_TASK)
        task?.let {
            // Set task details using View Binding
            binding.titleTextView.text = it.title
            binding.TimeTextView.text = it.time
            binding.dateTextView.text = it.date
            binding.descriptionTextView.text = it.description
        }
    }
}
