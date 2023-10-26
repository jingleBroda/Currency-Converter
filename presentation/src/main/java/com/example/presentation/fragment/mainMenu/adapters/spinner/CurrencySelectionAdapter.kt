package com.example.presentation.fragment.mainMenu.adapters.spinner

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.annotation.DrawableRes
import com.example.domain.presentationModel.Country
import com.example.presentation.R
import com.example.presentation.databinding.ActiveCurrencySelectedItemBinding

class CurrencySelectionAdapter : SpinnerAdapter {
    private val items = listOf(
        SpinnerCurrencyData(Country.Europe, R.drawable.flag_of_europe),
        SpinnerCurrencyData(Country.USA, R.drawable.flag_of_the_usa),
        SpinnerCurrencyData(Country.Russia, R.drawable.flag_of_russia),
        SpinnerCurrencyData(Country.Kazakhstan, R.drawable.flag_of_kazakhstan),
    )
    override fun registerDataSetObserver(observer: DataSetObserver?) = Unit

    override fun unregisterDataSetObserver(observer: DataSetObserver?) = Unit

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Country = items[position].country

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true

    override fun getItemViewType(position: Int): Int = Adapter.IGNORE_ITEM_VIEW_TYPE

    override fun getViewTypeCount(): Int = 1

    override fun isEmpty(): Boolean = items.isEmpty()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        getCustomView(position, parent)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        parent.setBackgroundColor(parent.context.getColor(R.color.white))
        return getCustomView(position, parent)
    }


   private fun getCustomView(position: Int, parent: ViewGroup): View {
        val binding = ActiveCurrencySelectedItemBinding.inflate(LayoutInflater.from(parent.context))
        with(binding){
            spinnerCurencyFlag.setImageResource(items[position].imgCurrency)
            spinnerCurrencyName.text = items[position].country.currency
        }
        return binding.root
   }

   private data class SpinnerCurrencyData(
       val country: Country,
       @DrawableRes
       val imgCurrency:Int,
   )
}