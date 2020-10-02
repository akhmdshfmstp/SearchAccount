package com.test.github.search.user.akhmadshofimustopo.framework.core.base

import androidx.paging.DataSource

abstract class BaseDataSourceFactory<T> : DataSource.Factory<Int, T>() {

    override fun create(): DataSource<Int, T> {
        return createDataSource()
    }

    abstract fun createDataSource(): DataSource<Int, T>
}