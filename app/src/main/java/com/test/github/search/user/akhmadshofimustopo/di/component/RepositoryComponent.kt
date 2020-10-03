package com.test.github.search.user.akhmadshofimustopo.di.component

import org.koin.core.module.Module
import com.test.github.search.user.akhmadshofimustopo.datamodule.module.repository.repositoryModule

val repositoryComponent: List<Module> = listOf(
    repositoryModule
)