package com.test.github.search.user.akhmadshofimustopo.presentation.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.test.github.search.user.akhmadshofimustopo.datamodule.model.dto.user.UserDto
import com.test.github.search.user.akhmadshofimustopo.datamodule.repository.user.UserRepository
import com.test.github.search.user.akhmadshofimustopo.framework.core.base.BaseDataSourceFactory

class UserDataFactory(
    var keySearch: String,
    private val userRepository: UserRepository,
    private val setRetryAfter: (
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, UserDto.Items>,
        exception: Exception?,
        message: String?
    ) -> Unit,
    private val itemSize: Int,
    private val runLayoutAnimation: () -> Unit
) : BaseDataSourceFactory<UserDto.Items>() {

    val userLiveData = MutableLiveData<UserDataSource>()
    override fun createDataSource(): DataSource<Int, UserDto.Items> {
        val dataSource = UserDataSource(
            keySearch,
            userRepository,
            setRetryAfter,
            itemSize,
            runLayoutAnimation
        )
        userLiveData.postValue(dataSource)

        return dataSource
    }
}