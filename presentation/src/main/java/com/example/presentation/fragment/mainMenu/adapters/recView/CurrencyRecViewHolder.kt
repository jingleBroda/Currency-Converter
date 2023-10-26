package com.example.presentation.fragment.mainMenu.adapters.recView

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.presentationModel.Country
import com.example.domain.presentationModel.CountryHelper
import com.example.presentation.R
import com.example.presentation.databinding.CurrencyRecViewItemBinding
import java.lang.NumberFormatException
import kotlin.properties.Delegates

class CurrencyRecViewHolder(
    private val item:CurrencyRecViewItemBinding,
    private val listener: (pairCurrency:String)-> Unit
):RecyclerView.ViewHolder(item.root), View.OnClickListener {
    private var coefficientConvertCurrency by Delegates.notNull<Float>()
    private lateinit var mainCurrency : Country
    private lateinit var secondCurrency : Country
    private lateinit var encryptedPairCurrency: String

    fun bind(content: Pair<String, Float>){
        encryptedPairCurrency = content.first
        mainCurrency = CountryHelper.findCountryInCurrency(encryptedPairCurrency.substring(0, 3))
        secondCurrency = CountryHelper.findCountryInCurrency(encryptedPairCurrency.substring(3, 6))
        coefficientConvertCurrency = content.second
        item.swapButton.setOnClickListener(this)
        item.openChart.setOnClickListener(this)
        initItemUI()
    }

    private fun initItemUI(){
        with(item){
            when(mainCurrency){
                Country.Europe ->
                    selectedCurrencyFlag.setImageResource(R.drawable.flag_of_europe)
                Country.USA ->
                    selectedCurrencyFlag.setImageResource(R.drawable.flag_of_the_usa)
                Country.Kazakhstan ->
                    selectedCurrencyFlag.setImageResource(R.drawable.flag_of_kazakhstan)
                Country.Russia ->
                    selectedCurrencyFlag.setImageResource(R.drawable.flag_of_russia)
            }
            when(secondCurrency){
                Country.Europe ->
                    otherCurrencyFlag.setImageResource(R.drawable.flag_of_europe)
                Country.USA ->
                    otherCurrencyFlag.setImageResource(R.drawable.flag_of_the_usa)
                Country.Kazakhstan ->
                    otherCurrencyFlag.setImageResource(R.drawable.flag_of_kazakhstan)
                Country.Russia ->
                    otherCurrencyFlag.setImageResource(R.drawable.flag_of_russia)
            }
            selectedCurrencyNumb.hint = mainCurrency.currency
            selectedCurrencyNumbTextInputEditText.setText("1")
            selectedCurrencyNumbTextInputEditText.doAfterTextChanged { value->
                val convertValue = try {
                    String.format("%.4f", (value.toString().toFloat() * coefficientConvertCurrency))
                }
                catch(e : NumberFormatException){
                    null
                }
                otherCurrencyNumbTextInputEditText.setText(convertValue ?: "ERROR")
            }
            otherCurrencyNumb.hint = secondCurrency.currency
            otherCurrencyNumbTextInputEditText.setText(
                String.format("%.4f", coefficientConvertCurrency)
            )
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.swapButton->{
                coefficientConvertCurrency = 1 / coefficientConvertCurrency
                mainCurrency = secondCurrency.also {
                    secondCurrency = mainCurrency
                }
                initItemUI()
            }

            R.id.openChart-> {
                v.tag = encryptedPairCurrency
                listener.invoke(v.tag as String)
            }
        }
    }
}