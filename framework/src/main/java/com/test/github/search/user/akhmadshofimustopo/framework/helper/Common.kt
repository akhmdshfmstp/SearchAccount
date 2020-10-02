package com.test.github.search.user.akhmadshofimustopo.framework.helper

import android.content.Context
import com.test.github.search.user.akhmadshofimustopo.framework.R

object Common {

    fun isScreenNotTab(context: Context): Boolean {
        return context.resources.getBoolean(R.bool.portrait_only)
    }

}