package com.example.presentation.fragment.currencyChart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.presentation.R
import com.example.presentation.activity_contract.internetConnection
import com.example.presentation.databinding.FragmentCurrencyChartBinding
import com.example.presentation.fragment.currencyChart.chartAdapter.ChartAdapter
import com.example.presentation.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CurrencyChartFragment : DaggerFragment(R.layout.fragment_currency_chart) {
    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    private val viewModel by viewModels<CurrencyChartViewModel> { viewModelFactory }
    private lateinit var binding:FragmentCurrencyChartBinding
    private val args: CurrencyChartFragmentArgs by navArgs()
    private lateinit var chartAdapter: ChartAdapter
    private lateinit var pairCurrency: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrencyChartBinding.bind(view)
        pairCurrency = args.pairCurrency
        with(binding) {
            if(internetConnection().isInternetConnected()) {
                viewModel.getChartItem(pairCurrency){ chartList, error ->
                    if(error == "") {
                        progressBar2.visibility = View.GONE
                        chartLayout.root.visibility = View.VISIBLE
                        chartAdapter = ChartAdapter(chartList)
                        chartLayout.currencyChart.chartAdapter = chartAdapter
                        chartLayout.nameChart.text = pairCurrency
                    }
                    else {
                        progressBar2.visibility = View.GONE
                        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                    }
                }
            }
            else {
                progressBar2.visibility = View.GONE
                errorMessage2.visibility = View.VISIBLE
            }
        }
    }
}