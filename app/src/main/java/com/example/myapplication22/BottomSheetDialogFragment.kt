import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication22.Adapters.swapGestor
import com.example.myapplication22.Models.Task
import com.example.myapplication22.R
import com.example.myapplication22.Service.ApiService
import com.example.myapplication22.Service.EventService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBottomSheetDialogFragment(private val date: String) : BottomSheetDialogFragment() {

    private lateinit var taskList: List<Task>

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.bottom_sheet_layout, container, false)

        val swipeGesture = object : swapGestor(this@MyBottomSheetDialogFragment) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTask = taskList[viewHolder.absoluteAdapterPosition]

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        deleteItem(swipedTask._id!!)
                        Log.d("swap", "Left")
                    }
                    ItemTouchHelper.RIGHT -> {
                        // Handle right swipe if needed
                        Log.d("swap", "Right")
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        // Set up RecyclerView
        val recyclerView: RecyclerView = rootView.findViewById(R.id.taskRecyclerView)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Add TaskItemDecoration to apply spacing between items
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.vertical_spacing)
        recyclerView.addItemDecoration(TaskItemDecoration(requireContext(), verticalSpacing))

        // Fetch tasks from API based on the provided date
        fetchTasksFromApi()

        return rootView
    }

    private fun fetchTasksFromApi() {
        Log.d("date",date)
        ApiService.EVENTSERVICE.getEventsByDate(date).enqueue(object : Callback<EventService.EventsResponse> {
            override fun onResponse(call: Call<EventService.EventsResponse>, response: Response<EventService.EventsResponse>) {
                if (response.isSuccessful) {
                    val eventResponse = response.body()
                    taskList = eventResponse?.evenements ?: emptyList()
                    updateRecyclerView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "error Loading Data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<EventService.EventsResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "error Loading Data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateRecyclerView() {
        val recyclerView: RecyclerView? = view?.findViewById(R.id.taskRecyclerView)
        recyclerView?.adapter = TaskAdapter(this, taskList)
    }

    private fun deleteItem(itemId: String) {


        ApiService.EVENTSERVICE.deleteEvent(itemId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Successfully deleted the item
                    Log.d("Delete", "Item deleted successfully")
                    dismiss()
                } else {
                    // Handle error response
                    Log.e("Delete", "Error deleting item. Status code: ${response.code()}")
                    // Add any error handling logic
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle network failure
                Log.e("Delete", "Network failure while deleting item. Error: ${t.message}")
                // Add any error handling logic
            }
        })
    }

}
