package io.github.kaya.wandroid.ui.search

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.iamyours.router.annotation.Route
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.adapter.ArticleAdapter
import io.github.kaya.wandroid.base.BaseActivity
import io.github.kaya.wandroid.databinding.ActivitySearchBinding
import io.github.kaya.wandroid.extension.viewModel

@Route(path = "/search")
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_search
    val vm by viewModel<SearchVM>()
    val adapter = ArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }
        vm.articlePage.observe(this, Observer {
            adapter.addAll(it.datas, it.curPage == 1)
        })
    }
}