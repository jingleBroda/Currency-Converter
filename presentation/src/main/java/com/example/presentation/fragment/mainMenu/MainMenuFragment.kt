package com.example.presentation.fragment.mainMenu

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.presentationModel.Country
import com.example.presentation.R
import com.example.presentation.activity_contract.internetConnection
import com.example.presentation.databinding.FragmentMainMenuBinding
import com.example.presentation.fragment.mainMenu.adapters.recView.CurrencyRecViewAdapter
import com.example.presentation.fragment.mainMenu.adapters.spinner.CurrencySelectionAdapter
import com.example.presentation.utils.GridSpacingItemDecoration
import com.example.presentation.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainMenuFragment : DaggerFragment(R.layout.fragment_main_menu) {
    private lateinit var binding : FragmentMainMenuBinding
    private val currencySelectionAdapter = CurrencySelectionAdapter()
    private lateinit var recViewAdapter : CurrencyRecViewAdapter
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    private val viewModel by viewModels<MainMenuViewModel> {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMenuBinding.bind(view)
        startLoading(country = Country.Europe, firstLoad = true)
    }

    private fun startLoading(country: Country, firstLoad:Boolean){
        with(binding){
            if(!firstLoad) {
                progressBar.visibility = View.VISIBLE
                currencyLayout.root.visibility = View.GONE
                errorMessage.visibility = View.GONE
            }

            if(internetConnection().isInternetConnected()) {
                viewModel.getRatesForCurrencies(country) {
                    progressBar.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                    currencyLayout.root.visibility = View.VISIBLE
                    if(firstLoad) {
                        recViewAdapter = CurrencyRecViewAdapter(it.data.toList()) { pairCurrency->
                            findNavController().navigate(
                                MainMenuFragmentDirections
                                    .actionMainMenuFragmentToCurrencyChartFragment(pairCurrency),
                                navOptions{
                                    anim {
                                        enter = androidx.appcompat.R.anim.abc_tooltip_enter
                                        exit = androidx.appcompat.R.anim.abc_tooltip_exit
                                        popEnter = androidx.appcompat.R.anim.abc_popup_enter
                                        popExit = androidx.appcompat.R.anim.abc_popup_exit
                                    }
                                }
                            )
                        }
                        initCurrencyLayout()
                    }
                    else{
                        recViewAdapter.update(it.data.toList())
                    }
                }
            }
            else {
                progressBar.visibility = View.GONE
                currencyLayout.root.visibility = View.GONE
                errorMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun initCurrencyLayout() {
        with(binding.currencyLayout) {
            currencySelection.adapter = currencySelectionAdapter
            currencySelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    startLoading(
                        country = currencySelectionAdapter.getItem(position),
                        firstLoad = false
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            currencyRecView.layoutManager = GridLayoutManager(requireActivity(), 1)
            currencyRecView.addItemDecoration(GridSpacingItemDecoration())
            currencyRecView.adapter = recViewAdapter
        }
    }
}