package xyz.dokup.stickyheadersample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.text.TextUtils
import android.view.View

/**
 * Created by e10dokup on 2018/02/20.
 */
class StickyLabelItemDecoration constructor(
        context: Context?,
        private val callback: Callback
) : RecyclerView.ItemDecoration() {

    private val textPaint = TextPaint()
    private val labelPadding: Int
    private val contentMargin: Int
    private val fontMetrics: Paint.FontMetrics

    init {
        val resource = context!!.resources

        textPaint.apply {
            typeface = Typeface.DEFAULT
            isAntiAlias = true
            textSize = resource.getDimension(R.dimen.sticky_label_font_size)
            color = ContextCompat.getColor(context, R.color.colorPrimary)
            textAlign = Paint.Align.LEFT
        }

        fontMetrics = textPaint.fontMetrics

        labelPadding = resource.getDimensionPixelSize(R.dimen.sticky_label_padding)
        contentMargin = resource.getDimensionPixelSize(R.dimen.sticky_label_content_margin)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.left = contentMargin
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val totalItemCount = state.itemCount
        val childCount = parent.childCount
        val fontCenter = (fontMetrics.ascent + fontMetrics.descent) / 2
        var previousInitial: String
        var initial = ""

        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)

            previousInitial = initial
            initial = callback.getInitial(position)

            if (TextUtils.isEmpty(initial) || previousInitial == initial) continue

            val viewCenter = (view.top + view.bottom) / 2
            val viewMiddle = view.height / 2
            var textY = Math.max(viewMiddle, viewCenter) - fontCenter
            if (position + 1 < totalItemCount) {
                val nextInitial = callback.getInitial(position + 1)
                if (nextInitial != initial && viewCenter < viewMiddle) {
                    textY -= viewMiddle - viewCenter
                }
            }
            c.drawText(initial, labelPadding.toFloat(), textY, textPaint)
        }
    }

    interface Callback {
        fun getInitial(position: Int): String
    }
}