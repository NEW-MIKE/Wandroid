package io.github.kaya.wandroid.adapter

import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.databinding.ItemXmenuChapterBinding
import io.github.kaya.wandroid.databinding.ItemXpictureBinding
import io.github.kaya.wandroid.vo.xxmh.XChapter
import io.github.kaya.wandroid.vo.xxmh.XPicture

class XMenuChapterAdapter :
    DataBoundAdapter<XChapter, ItemXmenuChapterBinding>() {
    lateinit var itemClick: (Int) -> Unit
    var chapterIndex = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun initView(binding: ItemXmenuChapterBinding, item: XChapter) {
        binding.vo = item
        binding.chapterIndex = chapterIndex
        binding.root.setOnClickListener {
            itemClick(item.sequence)
        }
    }

    override val layoutId: Int
        get() = R.layout.item_xmenu_chapter


}