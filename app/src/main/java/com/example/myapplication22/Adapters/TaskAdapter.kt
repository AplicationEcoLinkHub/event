import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication22.Models.Task
import com.example.myapplication22.R
import com.example.myapplication22.TaskDetailsFragment

class TaskAdapter(private val fragment: Fragment, private val taskList: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.titleTextView.text = task.title
        holder.TimeTextView.text = task.time
        holder.dateTextView.text = task.date
        holder.descriptionTextView.text = task.description


        // Set click listener to open the TaskDetailsFragment
        holder.itemView.setOnClickListener {
            val fragmentManager = fragment.requireActivity().supportFragmentManager
            val detailsFragment = TaskDetailsFragment.newInstance(task)
            detailsFragment.show(fragmentManager, TaskDetailsFragment::class.java.simpleName)
        }
    }
    interface OnSwipeListener {
        fun onSwipeLeft(position: Int)
        fun onSwipeRight(position: Int)
    }
    override fun getItemCount(): Int {
        return taskList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val TimeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }
}
