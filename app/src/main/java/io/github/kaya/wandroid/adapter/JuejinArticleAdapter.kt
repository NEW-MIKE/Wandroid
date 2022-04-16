package io.github.kaya.wandroid.adapter

import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.databinding.JuejinItemArticleBinding
import io.github.kaya.wandroid.util.RouterUtil
import io.github.kaya.wandroid.vo.juejin.JuejinArticleVO

class JuejinArticleAdapter :
    DataBoundAdapter<JuejinArticleVO, JuejinItemArticleBinding>() {
    override val layoutId: Int
        get() = R.layout.juejin_item_article

    override fun initView(
        binding: JuejinItemArticleBinding,
        item: JuejinArticleVO
    ) {
        binding.vo = item
        binding.root.setOnClickListener {
            RouterUtil.navWeb2(item.originalUrl, it.context)
        }
    }
}