package com.example.presentation.utils

import androidx.recyclerview.widget.RecyclerView

abstract class CustomRecViewAdapter <VH: RecyclerView.ViewHolder, ITEM: List<*>> :
    RecyclerView.Adapter<VH>() {
    abstract fun update(newList: ITEM)
    abstract fun getItemList(): ITEM
}