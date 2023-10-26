package com.example.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//Данный декоратор вынужденная мера, по скольку ресайклер на отрез отказался работать с LinerLayoutManager!!!!!!
class GridSpacingItemDecoration(
    private val spanCount: Int = 1,
    private val spacing: Int = 50,
    private val includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        if (includeEdge) {
            outRect.left =
                spacing - column * spacing / spanCount
            outRect.right =
                (column + 1) * spacing / spanCount
            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right =
                spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}