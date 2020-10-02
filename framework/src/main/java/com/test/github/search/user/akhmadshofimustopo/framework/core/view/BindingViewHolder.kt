package com.test.github.search.user.akhmadshofimustopo.framework.core.view


import android.view.View

interface BindingViewHolder<in T> : BindingView {

    fun onItemClick(view: View, item: T)

}