package com.test.github.search.user.akhmadshofimustopo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.test.github.search.user.akhmadshofimustopo.datamodule.model.dto.user.UserDto
import com.test.github.search.user.akhmadshofimustopo.datamodule.repository.user.UserRepository
import com.test.github.search.user.akhmadshofimustopo.framework.core.base.BasePagedViewModel
import com.test.github.search.user.akhmadshofimustopo.framework.helper.ValidationHelper
import com.test.github.search.user.akhmadshofimustopo.presentation.paging.UserDataFactory
import com.test.github.search.user.akhmadshofimustopo.presentation.paging.UserDataSource

class MainViewModel(private val userRepository: UserRepository) : BasePagedViewModel() {

    private var userList: LiveData<PagedList<UserDto.Items>>? = null
    private var sourceFactory: UserDataFactory? = null
    private val page = 1
    private val item = 100
    var params: MutableLiveData<PageKeyedDataSource.LoadParams<Int>>? = null
    var callback: MutableLiveData<PageKeyedDataSource.LoadCallback<Int, UserDto.Items>>? =
        null
    val keySearch = MutableLiveData<String>("")
    val showLoadingView = MutableLiveData(false)

    override fun refresh() {
        sourceFactory?.userLiveData?.value?.invalidate()
    }

    override fun retry() {
        sourceFactory?.userLiveData?.value?.retryAllFailed()
    }

    fun retryAfter() {
        params?.let { params ->
            this.callback?.let { callback ->
                sourceFactory?.userLiveData?.value?.loadAfter(params.value!!, callback.value!!)
            }
        }
    }

    fun clearKeySearch(): Boolean {
        var ret = false
        if (!ValidationHelper.isEmpty(getKeySearch())) {
            setSearch("")
            ret = true
        }
        return ret
    }

    fun setSearch(value: String) {
        keySearch.value = value
    }

    fun getKeySearch() = keySearch.value

    fun searchUserByKeyword(
        setRetryAfter: (
            params: PageKeyedDataSource.LoadParams<Int>,
            callback: PageKeyedDataSource.LoadCallback<Int, UserDto.Items>,
            exception: Exception?,
            message: String?
        ) -> Unit,
        runLayoutAnimation: () -> Unit
    ) {
        if (userList == null) {
            getSearchListUser(setRetryAfter, runLayoutAnimation)
        } else {
            sourceFactory?.keySearch = getKeySearch()!!
            sourceFactory?.create()
            sourceFactory?.userLiveData?.value?.invalidate()
        }
    }

    fun getSearchListUser(
        setRetryAfter: (
            params: PageKeyedDataSource.LoadParams<Int>,
            callback: PageKeyedDataSource.LoadCallback<Int, UserDto.Items>,
            exception: Exception?,
            message: String?
        ) -> Unit,
        runLayoutAnimation: () -> Unit
    ): LiveData<PagedList<UserDto.Items>> {
        if (userList == null) {
            getSearchListUserFromApi(setRetryAfter, runLayoutAnimation)
        }
        return userList as LiveData<PagedList<UserDto.Items>>
    }

    private fun getSearchListUserFromApi(
        setRetryAfter: (
            params: PageKeyedDataSource.LoadParams<Int>,
            callback: PageKeyedDataSource.LoadCallback<Int, UserDto.Items>,
            exception: Exception?,
            message: String?
        ) -> Unit,
        runLayoutAnimation: () -> Unit
    ) {
        val pagedListConfig = pagedListConfig(page)
        sourceFactory = UserDataFactory(
            getKeySearch()!!,
            userRepository,
            setRetryAfter,
            item,
            runLayoutAnimation
        )

        userList = LivePagedListBuilder(sourceFactory!!, pagedListConfig).build()
        mNetworkState = Transformations.switchMap(
            sourceFactory?.userLiveData!!,
            UserDataSource::network
        )
        mInitialState = Transformations.switchMap(
            sourceFactory?.userLiveData!!,
            UserDataSource::initial
        )
        observeState()
    }

    fun setParams(params: PageKeyedDataSource.LoadParams<Int>) {
        if (this.params == null)
            this.params = MutableLiveData()
        this.params?.postValue(params)
    }

    fun setCallback(callback: PageKeyedDataSource.LoadCallback<Int, UserDto.Items>) {
        if (this.callback == null)
            this.callback = MutableLiveData()
        this.callback?.postValue(callback)
    }

    fun isValid(): Boolean {
        var ret = true
        if (ValidationHelper.isEmpty(getKeySearch())) {
            ret = false
        }
        return ret
    }

    fun showLoading() {
        showLoadingView.value = true
    }

    fun hideLoading() {
        showLoadingView.value = false
    }

}