package io.github.kaya.wandroid.adapter

import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.databinding.ItemWxArticleBinding
import io.github.kaya.wandroid.util.RouterUtil
import io.github.kaya.wandroid.vo.ArticleVO

class WxArticleAdapter :
    DataBoundAdapter<ArticleVO, ItemWxArticleBinding>() {
    override fun initView(binding: ItemWxArticleBinding, item: ArticleVO) {
        binding.vo = item
        binding.root.setOnClickListener {
            RouterUtil.navWeb(item,it.context)
        }
    }

    override val layoutId: Int
        get() = R.layout.item_wx_article
}