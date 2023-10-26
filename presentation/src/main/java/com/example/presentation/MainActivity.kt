package com.example.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.presentation.activity_contract.InternetConnection
import com.example.presentation.activity_contract.Navigator
import com.example.presentation.activity_contract.NetworkConnectivityChecker
import com.example.presentation.activity_contract.navigator
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.fragment.currencyChart.CurrencyChartFragment
import com.example.presentation.fragment.currencyChart.CurrencyChartFragmentArgs
import com.example.presentation.fragment.mainMenu.MainMenuFragment
import com.example.presentation.fragment.mainMenu.MainMenuFragmentDirections

class MainActivity : AppCompatActivity(), InternetConnection {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var networkConnectivityChecker: NetworkConnectivityChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        networkConnectivityChecker = NetworkConnectivityChecker(this)
        navController =
            (supportFragmentManager.findFragmentById(R.id.FragmentLayout) as NavHostFragment).navController
    }

    override fun isInternetConnected(): Boolean = networkConnectivityChecker.isInternetConnected()
}