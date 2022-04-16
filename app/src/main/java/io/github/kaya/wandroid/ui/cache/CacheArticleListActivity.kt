package io.github.kaya.wandroid.ui.cache

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.iamyours.router.annotation.Route
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.adapter.CacheArticleAdapter
import io.github.kaya.wandroid.base.BaseActivity
import io.github.kaya.wandroid.databinding.ActivityCacheArticleListBinding
import io.github.kaya.wandroid.extension.viewModel

@Route(path = "/cache")
class CacheArticleListActivity:BaseActivity<ActivityCacheArticleListBinding>(){
    override val layoutId: Int
        get() = R.layout.activity_cache_article_list
    val vm by viewModel<CacheVM>()
    val adapter = CacheArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm
        binding.executePendingBindings()
        initRecyclerView()
        vm.autoRefresh()
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }
        vm.list.observe(this, Observer {
            adapter.addAll(it, vm.isFirst())
        })
    }
}