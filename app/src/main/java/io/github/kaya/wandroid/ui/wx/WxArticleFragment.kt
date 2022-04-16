package io.github.kaya.wandroid.ui.wx

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.base.BaseFragment
import io.github.kaya.wandroid.databinding.FragmentWxArticleBinding
import io.github.kaya.wandroid.extension.viewModel
import io.github.kaya.wandroid.vo.WXChapterVO

class WxArticleFragment : BaseFragment<FragmentWxArticleBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_wx_article

    val vm by viewModel<WxArticleVM>()
    val fragments = ArrayList<Fragment>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vm = vm
        binding.executePendingBindings()
        vm.chapters.observe(this, Observer {
            Log.e("test", "$it")
            initViewPager(it)
        })
        vm.loadData()
    }

    private fun initViewPager(chapters: List<WXChapterVO>) {
        if (chapters.isEmpty()) return
        val titles = arrayOfNulls<String>(chapters.size)
        chapters.forEachIndexed { index, vo ->
            titles[index] = vo.name
            fragments.add(WXArticleListFragment.create(vo.id))
        }
        binding.run {
            viewPager.adapter = VpAdapter()
            tabLayout.setViewPager(viewPager, titles)
        }
    }

    inner class VpAdapter : FragmentStatePagerAdapter(childFragmentManager) {
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

    }
}