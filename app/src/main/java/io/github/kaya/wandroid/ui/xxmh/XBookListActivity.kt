package io.github.kaya.wandroid.ui.xxmh

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.iamyours.router.ARouter
import io.github.iamyours.router.annotation.Route
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.adapter.XBookAdapter
import io.github.kaya.wandroid.base.BaseActivity
import io.github.kaya.wandroid.databinding.ActivityXbookListBinding
import io.github.kaya.wandroid.extension.viewModel

@Route(path = "/xbook")
class XBookListActivity : BaseActivity<ActivityXbookListBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_xbook_list
    val vm by viewModel<XBookVM>()
    val adapter = XBookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm
        binding.executePendingBindings()
        initRecyclerView()
        vm.autoRefresh()
        binding.tvRight.setOnClickListener {
            ARouter.getInstance().build("/updateBook")
                .navigation(this)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(this, 3)
        }
        vm.list.observe(this, Observer {
            adapter.addAll(it, vm.isFirst())
        })
    }
}