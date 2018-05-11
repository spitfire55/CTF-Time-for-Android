package re.spitfy.ctftime.view.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlin.math.roundToInt

class StandingsItemDecoration(
        private val padding: Int,
        private val background: Paint
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
            outRect: Rect?,
            view: View?,
            parent: RecyclerView?,
            state: RecyclerView.State?
    ) {
        outRect?.right = padding
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        val childCount = parent?.childCount
        val lm = parent?.layoutManager
        if(childCount != null && lm != null) {
            for(i in 0..childCount) {
                val child = parent.getChildAt(i)
                var right = (lm.getDecoratedRight(child) + child.translationX).roundToInt()
                if( i == childCount - 1)
                    right = Math.max(right, parent.width)

                c?.drawRect(
                        child.right + child.translationX,
                        0f, right.toFloat(),
                        parent.height.toFloat(),
                        background
                )
            }
        }
    }
}