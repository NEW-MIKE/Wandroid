package io.github.kaya.wandroid.adapter

import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.databinding.ItemQaBinding
import io.github.kaya.wandroid.util.RouterUtil
import io.github.kaya.wandroid.vo.ArticleVO

class QaAdapter : DataBoundAdapter<ArticleVO, ItemQaBinding>() {
    override fun initView(binding: ItemQaBinding, item: ArticleVO) {
        binding.vo = item
        binding.root.setOnClickListener {
            RouterUtil.navWeb(item,it.context)
        }
    }

    override val layoutId: Int
        get() = R.layout.item_qa
}