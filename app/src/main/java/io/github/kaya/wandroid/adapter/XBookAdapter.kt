package io.github.kaya.wandroid.adapter

import io.github.iamyours.router.ARouter
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.databinding.ItemXbookBinding
import io.github.kaya.wandroid.vo.xxmh.XBook

class XBookAdapter :
    DataBoundAdapter<XBook, ItemXbookBinding>() {
    override fun initView(binding: ItemXbookBinding, item: XBook) {
        binding.vo = item
        binding.root.setOnClickListener {
            ARouter.getInstance().build("/xbookDetail")
                .withParcelable("book", item).navigation(it.context)
        }
    }

    override val layoutId: Int
        get() = R.layout.item_xbook
}