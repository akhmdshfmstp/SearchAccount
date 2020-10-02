package com.test.github.search.user.akhmadshofimustopo.framework.core.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.test.github.search.user.akhmadshofimustopo.framework.core.common.NetworkState
import java.util.concurrent.Executors

abstract class BasePageKeyedDataSource<T> : PageKeyedDataSource<Int, T>() {
    open val network = MutableLiveData<NetworkState>()
    open val initial = MutableLiveData<NetworkState>()
    private var retry: (() -> Any)? = null
    private val executorService = Executors.newFixedThreadPool(5)

    protected fun loadInitialError(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>,
        exception: Exception? = null,
        message: String? = null
    ) {
        retry = { loadInitial(params, callback) }
        postInitialState(
            when {
                message != null -> {
                    NetworkState.error(
                        message
                    )
                }
                exception != null -> {
                    NetworkState.error(
                        exception
                    )
                }
                else -> {
                    NetworkState.unknownError()
                }
            }
        )
    }

    protected fun loadAfterError(params: LoadParams<Int>, callback: LoadCallback<Int, T>, e: Exception) {
        retry = { loadAfter(params, callback) }
        postAfterState(
            NetworkState.error(
                e
            )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        //ignore
    }

    protected fun prepareLoadInitial() {
        postInitialState(NetworkState.LOADING)
    }

    protected fun prepareLoadAfter() {
        postAfterState(NetworkState.LOADING)
    }

    protected fun loadInitialEmpty() {
        postInitialState(NetworkState.EMPTY)
        retry = null
    }

    protected fun loadInitialSuccess() {
        postInitialState(NetworkState.LOADED)
        retry = null
    }

    protected fun loadAfterSuccess() {
        postAfterState(NetworkState.LOADED)
        retry = null
    }

    protected fun postInitialState(state: NetworkState) {
        initial.postValue(state)
    }

    protected fun postAfterState(state: NetworkState) {
        network.postValue(state)
    }

    open fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let { retry ->
            executorService.execute { retry() }
        }
    }
}