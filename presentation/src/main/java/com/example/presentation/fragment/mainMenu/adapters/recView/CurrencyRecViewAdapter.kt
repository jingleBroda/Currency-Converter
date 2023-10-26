package com.example.presentation.fragment.mainMenu.adapters.recView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.presentation.R
import com.example.presentation.databinding.CurrencyRecViewItemBinding
import com.example.presentation.fragment.mainMenu.adapters.recView.diffUtils.CurrencyDiffUtils
import com.example.presentation.utils.CustomRecViewAdapter

class CurrencyRecViewAdapter(
    private var items:List<Pair<String, Float>>,
    private val listener: (pairCurrency:String)-> Unit
): CustomRecViewAdapter<CurrencyRecViewHolder, List<Pair<String, Float>>>(), View.OnClickListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRecViewHolder =
        CurrencyRecViewHolder(
            CurrencyRecViewItemBinding.inflate(LayoutInflater.from(parent.context)),
            listener
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CurrencyRecViewHolder, position: Int) =
        holder.bind(items[position])

    override fun update(newList: List<Pair<String, Float>>) {
        val diffUtil = CurrencyDiffUtils(items, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        items = newList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun getItemList(): List<Pair<String, Float>> = items

    override fun onClick(v: View) {
        when(v.id) {
            R.id.openChart-> listener.invoke(v.tag as String)
        }
    }
}