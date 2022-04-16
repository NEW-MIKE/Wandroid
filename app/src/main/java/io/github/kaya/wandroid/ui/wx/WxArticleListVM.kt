package io.github.kaya.wandroid.ui.wx

import androidx.lifecycle.Transformations
import io.github.kaya.wandroid.base.BaseViewModel

class WxArticleListVM : BaseViewModel() {
    var id = 0
    private var articleList = Transformations.switchMap(page) {
        api.wxArticlePage(id, it)
    }
    var articlePage = mapPage(articleList)

    override fun refresh() {
        page.value = 1
        refreshing.value = true
    }
}