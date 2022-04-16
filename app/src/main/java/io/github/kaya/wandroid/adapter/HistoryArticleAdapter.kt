package io.github.kaya.wandroid.adapter

import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.databinding.ItemHistoryArticleBinding
import io.github.kaya.wandroid.databinding.ItemQaBinding
import io.github.kaya.wandroid.ui.web.WebActivity
import io.github.kaya.wandroid.util.RouterUtil
import io.github.kaya.wandroid.vo.ArticleVO

class HistoryArticleAdapter :
    DataBoundAdapter<ArticleVO, ItemHistoryArticleBinding>() {
    override fun initView(binding: ItemHistoryArticleBinding, item: ArticleVO) {
        binding.vo = item
        binding.root.setOnClickListener {
            RouterUtil.navWeb(item, it.context)
        }
    }

    override val layoutId: Int
        get() = R.layout.item_history_article
}