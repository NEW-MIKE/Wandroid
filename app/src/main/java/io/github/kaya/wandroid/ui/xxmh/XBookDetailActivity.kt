package io.github.kaya.wandroid.ui.xxmh

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.iamyours.router.annotation.Route
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.adapter.XChapterAdapter
import io.github.kaya.wandroid.base.BaseActivity
import io.github.kaya.wandroid.databinding.ActivityXbookDetailBinding
import io.github.kaya.wandroid.db.AppDataBase
import io.github.kaya.wandroid.extension.viewModel
import io.github.kaya.wandroid.vo.xxmh.XBook

@Route(path = "/xbookDetail")
class XBookDetailActivity : BaseActivity<ActivityXbookDetailBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_xbook_detail
    val vm by viewModel<XBookDetailVM>()
    val adapter = XChapterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm
        binding.executePendingBindings()
        vm.book.value = intent.getParcelableExtra("book")
        adapter.book = vm.book.value
        initRecyclerView()
        vm.loadData()
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
        vm.list.observe(this, Observer {
            adapter.addAll(it, true)
        })
    }
}