package com.example.currency_converter.di.module.nestedPresentationModules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currency_converter.di.module.nestedPresentationModules.utils.ViewModelKey
import com.example.presentation.fragment.currencyChart.CurrencyChartViewModel
import com.example.presentation.fragment.mainMenu.MainMenuViewModel
import com.example.presentation.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainMenuViewModel::class)
    internal abstract fun bindMainMenuViewModel(mainMenuViewModel: MainMenuViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyChartViewModel::class)
    internal abstract fun bindCurrencyChartViewModel(currencyChartViewModel: CurrencyChartViewModel): ViewModel
}