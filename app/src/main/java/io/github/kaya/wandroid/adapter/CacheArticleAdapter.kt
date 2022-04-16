package io.github.kaya.wandroid.adapter

import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.databinding.ItemCacheArticleBinding
import io.github.kaya.wandroid.databinding.ItemHistoryArticleBinding
import io.github.kaya.wandroid.util.RouterUtil
import io.github.kaya.wandroid.vo.CacheArticleVO

class CacheArticleAdapter :
    DataBoundAdapter<CacheArticleVO, ItemCacheArticleBinding>() {
    override fun initView(binding: ItemCacheArticleBinding, item: CacheArticleVO) {
        binding.vo = item
        binding.root.setOnClickListener {
            RouterUtil.navWeb(item, it.context)
        }
    }

    override val layoutId: Int
        get() = R.layout.item_cache_article
}