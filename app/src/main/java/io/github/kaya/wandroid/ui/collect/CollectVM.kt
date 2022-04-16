package io.github.kaya.wandroid.ui.collect

import androidx.lifecycle.Transformations
import io.github.kaya.wandroid.base.BaseViewModel

class CollectVM : BaseViewModel() {
    private val _collectPage = Transformations.switchMap(page) {
        api.collectPage(it)
    }
    val collectPage = mapPage(_collectPage)
}