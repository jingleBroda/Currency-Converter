package com.example.domain.presentationModel

import java.lang.NullPointerException

enum class Country(val currency : String){
    Europe("EUR"),
    USA("USD"),
    Kazakhstan("KZT"),
    Russia("RUB"),
}

object CountryHelper{
    /**
     * Поиск Country по заданному currency.
     * @param currency Валюта страны
     */
    fun findCountryInCurrency(currency : String) : Country {
        var result:Country? = null
        for(country in Country.values()){
            if(country.currency == currency){
                result = country
                break
            }
        }
        return if(result == null) throw NullPointerException()
        else result
    }

    /**
     *
     * Создает строку, содержащую пары валют, куда входит selectedCountry.
     * Пары разделены запятой.
     *
     * Пример созданной строки для selectedCountry = Country.Europe: EURUSD,EURKZT,EURRUB
     *
     * @param selectedCountry объект Country, для которого будет создана строка.
     */
    fun createPairs(selectedCountry:Country) : String {
        var pairs = ""
        val allCountry = Country.values()
        allCountry.forEach { country ->
            if(country != selectedCountry){
                val mergeCountry = selectedCountry.currency +  country.currency + ","
                pairs += mergeCountry
            }
        }
        return pairs
    }
}
