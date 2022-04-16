package io.github.kaya.wandroid.ui.project

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.adapter.ProjectAdapter
import io.github.kaya.wandroid.adapter.ProjectCategoryAdapter
import io.github.kaya.wandroid.base.BaseFragment
import io.github.kaya.wandroid.databinding.FragmentProjectBinding
import io.github.kaya.wandroid.extension.viewModel

class ProjectFragment : BaseFragment<FragmentProjectBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_project
    val vm by viewModel<ProjectVM>()
    lateinit var categoryAdapter: ProjectCategoryAdapter
    private val projectAdapter = ProjectAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = vm
        binding.executePendingBindings()
        initCategory()
        initProjects()
        vm.loadData()
    }

    private fun initProjects() {
        binding.recyclerView.run {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        vm.projectPage.observe(this, Observer {
            projectAdapter.addAll(it.datas, it.curPage == 1)
        })
    }

    private fun initCategory() {
        binding.rvCategory.run {
            categoryAdapter = ProjectCategoryAdapter(vm.selectCategory)
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(activity)
            vm.categorys.observe(this@ProjectFragment, Observer {
                categoryAdapter.addAll(it, true)
            })
        }

    }
}