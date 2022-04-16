package io.github.kaya.wandroid.ui.qa

import androidx.lifecycle.Transformations
import io.github.kaya.wandroid.base.BaseViewModel
import io.github.kaya.wandroid.extension.logE

class QaVM : BaseViewModel() {
    //问答列表
    private val result = Transformations.switchMap(page) {
        api.qAList(it)
    }

    override fun refresh() {
        page.value = 1
        refreshing.value = true
    }

    val articlePage = mapPage(result)
}