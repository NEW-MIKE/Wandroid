package io.github.kaya.wandroid.adapter

import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.databinding.ItemCollectArticleBinding
import io.github.kaya.wandroid.util.RouterUtil
import io.github.kaya.wandroid.vo.ArticleVO

class CollectArticleAdapter :
    DataBoundAdapter<ArticleVO, ItemCollectArticleBinding>() {
    override fun initView(binding: ItemCollectArticleBinding, item: ArticleVO) {
        binding.vo = item
        binding.root.setOnClickListener {
            RouterUtil.navWeb(item, it.context) { collect ->
                if (!collect) {
                    mData.remove(item)
                    notifyDataSetChanged()
                }

            }
        }
    }

    override val layoutId: Int
        get() = R.layout.item_collect_article
}