package com.example.currency_converter.di.module.nestedPresentationModules

import com.example.presentation.fragment.currencyChart.CurrencyChartFragment
import com.example.presentation.fragment.mainMenu.MainMenuFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributesMainMenuFragment(): MainMenuFragment
    @ContributesAndroidInjector
    abstract fun contributesCurrencyChartFragment(): CurrencyChartFragment
}