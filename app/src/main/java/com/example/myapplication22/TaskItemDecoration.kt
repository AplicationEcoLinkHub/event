import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TaskItemDecoration(private val context: Context, private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount ?: 0 - 1) {
            outRect.bottom = verticalSpacing // Définir la marge inférieure pour tous les éléments sauf le dernier
        }
    }
}
