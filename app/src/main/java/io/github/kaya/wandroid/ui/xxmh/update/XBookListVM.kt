package io.github.kaya.wandroid.ui.xxmh.update

import androidx.lifecycle.Transformations
import io.github.kaya.wandroid.base.BaseViewModel
import io.github.kaya.wandroid.net.xxmh.XBookApi
import io.github.kaya.wandroid.util.StringUtil

class XBookListVM : BaseViewModel() {
    val xApi = XBookApi.get("http://xxmh106.com/")
    var day = 0
    private val _bookPage = Transformations.switchMap(page) {
        xApi.updateBookPage(it, day)
    }

    val bookPage = Transformations.map(_bookPage) {
        it.content?.run {
            if (pageNum == 1) {
                refreshing.value = false
            } else {
                moreLoading.value = false
            }
            hasMore.value = hasNextPage
        }
        it.content
    }

    override fun refresh() {
        page.value = 1
        refreshing.value = true
    }

}