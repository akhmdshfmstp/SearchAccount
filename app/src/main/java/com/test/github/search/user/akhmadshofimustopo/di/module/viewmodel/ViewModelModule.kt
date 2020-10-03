package com.test.github.search.user.akhmadshofimustopo.di.module.viewmodel

import com.test.github.search.user.akhmadshofimustopo.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(
            get()
        )
    }

}