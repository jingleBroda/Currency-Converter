package com.example.presentation.fragment.mainMenu.adapters.recView.diffUtils

import androidx.recyclerview.widget.DiffUtil

class CurrencyDiffUtils(
    private val oldList:List<Pair<String, Float>>,
    private val newList:List<Pair<String, Float>>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].first == newList[newItemPosition].first

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = when {
        oldList[oldItemPosition].first != newList[newItemPosition].first ->{
            false
        }

        oldList[oldItemPosition].second != newList[newItemPosition].second ->{
            false
        }

        else -> true
    }
}