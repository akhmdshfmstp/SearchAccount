package com.test.github.search.user.akhmadshofimustopo.datamodule.module.repository

import com.test.github.search.user.akhmadshofimustopo.datamodule.repository.user.UserRepository
import com.test.github.search.user.akhmadshofimustopo.datamodule.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<UserRepository> { UserRepositoryImpl(userService = get()) }

}