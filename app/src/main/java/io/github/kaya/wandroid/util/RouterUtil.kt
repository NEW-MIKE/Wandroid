package io.github.kaya.wandroid.util

import android.content.Context
import android.os.Bundle
import io.github.iamyours.router.ARouter
import io.github.kaya.wandroid.vo.ArticleVO
import io.github.kaya.wandroid.vo.CacheArticleVO

object RouterUtil {
    @JvmOverloads
    fun navWeb(
        item: ArticleVO,
        context: Context,
        callback: ((Boolean) -> Unit)? = null //回调
    ) {
        val bundle = Bundle()
        bundle.putInt("articleId", item.id)
        bundle.putBoolean("collect", item.collect)
        bundle.putString("link", item.link)
        ARouter.getInstance().build("/web2")
            .with(bundle)
            .navigation(context) { _, resultCode, data ->
                if (resultCode == Constants.RESULT_COLLECT_CHANGED) {
                    item.collect = data.getBooleanExtra("collect", false)
                    callback?.invoke(item.collect)
                }
            }
    }

    @JvmOverloads
    fun navWeb2(
        item: ArticleVO,
        context: Context,
        callback: ((Boolean) -> Unit)? = null //回调
    ) {
        val bundle = Bundle()
        bundle.putInt("articleId", item.id)
        bundle.putBoolean("collect", item.collect)
        bundle.putString("link", item.link)
        ARouter.getInstance().build("/web2")
            .with(bundle)
            .navigation(context) { _, resultCode, data ->
                if (resultCode == Constants.RESULT_COLLECT_CHANGED) {
                    item.collect = data.getBooleanExtra("collect", false)
                    callback?.invoke(item.collect)
                }
            }
    }

    @JvmOverloads
    fun navWeb2(
        link: String,
        context: Context
    ) {
        val bundle = Bundle()
        bundle.putString("link", link)
        ARouter.getInstance().build("/web2")
            .with(bundle)
            .navigation(context)
    }

    @JvmOverloads
    fun navWeb(
        item: CacheArticleVO,
        context: Context,
        callback: ((Boolean) -> Unit)? = null //回调
    ) {
        val bundle = Bundle()
        bundle.putString("link", item.link)
        ARouter.getInstance().build("/web2")
            .with(bundle)
            .navigation(context)
    }
}