package com.test.github.search.user.akhmadshofimustopo.di.component

import com.test.github.search.user.akhmadshofimustopo.di.module.network.networkModule
import org.koin.core.module.Module

val networkComponent: List<Module> = listOf(
    networkModule
)