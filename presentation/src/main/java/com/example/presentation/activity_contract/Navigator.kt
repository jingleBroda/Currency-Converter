package com.example.presentation.activity_contract

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.navigator() = requireActivity() as Navigator

interface Navigator{
    fun next(firstDestination: String, lastDestination: String, args: Bundle?)
    fun back()
}