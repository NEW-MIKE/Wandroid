package io.github.kaya.wandroid.ui.xxmh

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.github.kaya.wandroid.base.BaseViewModel
import io.github.kaya.wandroid.extension.changeExt
import io.github.kaya.wandroid.net.xxmh.XBookApi

class XBookVM : BaseViewModel() {
    val xApi = XBookApi.get()
    val keyword = MutableLiveData<String>()
    private val bookPage = Transformations.switchMap(page) {
        xApi.bookPage(it,keyword.value?:"")
    }
    val list = Transformations.map(bookPage) {
        moreLoading.value = false
        refreshing.value = false
        val isLast = it?.content?.isLastPage ?: true
        hasMore.value = !isLast
        it?.content?.list ?: ArrayList()
    }

    override fun refresh() {
        page.value = 1
        refreshing.value = true
    }


    fun isFirst(): Boolean {
        return page.value == 1
    }
}