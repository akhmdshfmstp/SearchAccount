package com.test.github.search.user.akhmadshofimustopo.framework.core.owner

import com.test.github.search.user.akhmadshofimustopo.framework.core.base.BaseViewModel

interface ViewModelOwner<T : BaseViewModel> {
    val viewModel: T
}