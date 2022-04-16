package io.github.kaya.wandroid.ui.qa

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.adapter.QaAdapter
import io.github.kaya.wandroid.base.BaseFragment
import io.github.kaya.wandroid.databinding.FragmentQaBinding
import io.github.kaya.wandroid.extension.viewModel

/**
 * 问答
 */
class QaFragment : BaseFragment<FragmentQaBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_qa
    private val adapter = QaAdapter()
    val vm by viewModel<QaVM>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        binding.vm = vm
        binding.executePendingBindings()
        binding.refreshLayout.autoRefresh()
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity)
        }
        vm.articlePage.observe(this, Observer {
            adapter.addAll(it.datas, it.curPage == 1)
        })
    }
}