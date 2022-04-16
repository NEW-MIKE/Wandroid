package io.github.kaya.wandroid.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import io.github.iamyours.router.ARouter
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.base.BaseFragment
import io.github.kaya.wandroid.databinding.FragmentHomeBinding
import io.github.kaya.wandroid.ui.article.ArticleFragment
import io.github.kaya.wandroid.ui.juejin.JuejinArticleFragment
import io.github.kaya.wandroid.ui.qa.QaFragment

class HomeFragment :
    BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home
    private val titles = arrayOf("热门", "每日一问")
    val fragments =
        arrayOf(ArticleFragment(), QaFragment())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewPager()
        binding.ivSearch.setOnClickListener {
            ARouter.getInstance().build("/search").navigation(activity)
        }
    }

    private fun initViewPager() {
        binding.run {
            viewPager.adapter = VpAdapter()
            viewPager.offscreenPageLimit = fragments.size
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