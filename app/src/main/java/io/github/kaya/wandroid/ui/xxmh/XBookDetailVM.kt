package io.github.kaya.wandroid.ui.xxmh

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.github.kaya.wandroid.base.BaseViewModel
import io.github.kaya.wandroid.net.xxmh.XBookApi
import io.github.kaya.wandroid.util.StringUtil
import io.github.kaya.wandroid.vo.xxmh.XBook

class XBookDetailVM : BaseViewModel() {
    private val xApi = XBookApi.get()
    val book = MutableLiveData<XBook>()
    private val _list = Transformations.switchMap(refreshTrigger) {
        xApi.chapterList(book.value?.id ?: 0)
    }
    val list = Transformations.map(_list) {
        loading.value = false
        it?.content ?: ArrayList()
    }
}