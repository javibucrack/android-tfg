package com.example.asistelo.decorator

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase decorador que personaliza los RecyclerView
 */
class SimpleItemDecoration(context: Context, space: Int = 10) : RecyclerView.ItemDecoration() {

    private val spacePx = dpToPx(context, space)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spacePx
        outRect.top = spacePx
    }

    fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}

