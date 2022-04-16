package io.github.kaya.wandroid.ui.article

import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.adapter.ArticleAdapter
import io.github.kaya.wandroid.base.BaseActivity
import io.github.kaya.wandroid.base.BaseFragment
import io.github.kaya.wandroid.databinding.FragmentArticleBinding
import io.github.kaya.wandroid.db.AppDataBase
import io.github.kaya.wandroid.extension.displayWithUrl
import io.github.kaya.wandroid.extension.viewModel
import io.github.kaya.wandroid.ui.web.WebActivity
import io.github.kaya.wandroid.util.LiveDataBus
import io.github.kaya.wandroid.vo.BannerVO

/**
 * 文章列表
 */
class ArticleFragment : BaseFragment<FragmentArticleBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_article
    val vm by viewModel<ArticleVM>()
    private val adapter = ArticleAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initBanner()
        binding.vm = vm
        binding.executePendingBindings()
        binding.refreshLayout.autoRefresh()

    }

    private fun initBanner() {
        binding.banner.run {
            val adapter: BGABanner.Adapter<ImageView, BannerVO> =
                BGABanner.Adapter { _, image,
                                    model,
                                    _ ->
                    image.displayWithUrl(model?.imagePath)
                }
            setAdapter(adapter)
            setDelegate { _, _, model, _ ->
                if (model is BannerVO) {
                    WebActivity.nav(model.url, activity!!)
                }
            }
        }
        //登录状态变化
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(activity)
        }
        vm.articlePage.observe(this, Observer {
            it?.run {
                adapter.addAll(datas, curPage == 1)
            }
        })
    }

}