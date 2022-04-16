package io.github.kaya.wandroid.binds

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.text.Html
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.extension.*
import io.github.kaya.wandroid.listener.SimpleRecyclerOnScrollerListener
import io.github.kaya.wandroid.ui.web.ImageShowActivity
import io.github.kaya.wandroid.util.Constants
import io.github.kaya.wandroid.util.EmptyCornerDrawable
import io.github.kaya.wandroid.util.ImageUtil
import io.github.kaya.wandroid.web.PositionImage

@BindingAdapter(
    value = ["refreshing", "moreLoading", "hasMore"],
    requireAll = false
)
fun bindSmartRefreshLayout(
    smartLayout: SmartRefreshLayout,
    refreshing: Boolean,
    moreLoading: Boolean,
    hasMore: Boolean?

) {
    if (!refreshing) {
        smartLayout.finishRefresh()
    }
    if (!moreLoading) {
        smartLayout.finishLoadMore()
    }

    if (hasMore != null)
        smartLayout.setNoMoreData(!hasMore)//调用次方法会停止刷新动作
}

@BindingAdapter(value = ["refreshEnable"])
fun bindSmartRefreshLayoutRefresh(
    smartLayout: SmartRefreshLayout,
    refreshEnable: Boolean
) {
//    smartLayout.setEnableRefresh(refreshEnable)
}

@BindingAdapter(
    value = ["autoRefresh"]
)
fun bindSmartRefreshLayout(
    smartLayout: SmartRefreshLayout,
    autoRefresh: Boolean
) {
    if (autoRefresh) smartLayout.autoRefresh()
}

@BindingAdapter(
    value = ["onRefreshListener", "onLoadMoreListener"],
    requireAll = false
)
fun bindListener(
    smartLayout: SmartRefreshLayout,
    refreshListener: OnRefreshListener?,
    loadMoreListener: OnLoadMoreListener?
) {
    smartLayout.setOnRefreshListener(refreshListener)
    smartLayout.setOnLoadMoreListener(loadMoreListener)
}

@BindingAdapter(value = ["searchAction"])
fun bindSearch(et: EditText, callback: () -> Unit) {
    et.setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callback()
            et.hideKeyboard()
        }
        true
    }
}

@BindingAdapter(value = ["onFirstObject"])
fun bindRecyclerView(
    rv: RecyclerView,
    callback: (Any) -> Unit
) {
    rv.setOnScrollListener(object : SimpleRecyclerOnScrollerListener() {
        override fun onFirstObject(obj: Any) {
            callback(obj)
        }
    })
}

@BindingAdapter(value = ["gone"])
fun bindGone(v: View, gone: Boolean) {
    v.visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["invisible"])
fun bindInvisible(v: View, invisible: Boolean) {
    v.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter(value = ["data"])
fun bindBanner(banner: BGABanner, data: List<Any>?) {
    data?.run {
        banner.setData(this, null)
    }
}

@BindingAdapter(value = ["url", "radius"])
fun bindImage(iv: ImageView, url: String?, radius: Int?) {
    if (url != null) {
        iv.displayWithUrl(url, (radius ?: 1).toFloat())
    }
}

@BindingAdapter(value = ["level"])
fun bindLevel(iv: AppCompatImageView, level: Int) {
    val rid = when (level) {
        1 -> R.drawable.lv1
        2 -> R.drawable.lv2
        3 -> R.drawable.lv3
        4 -> R.drawable.lv4
        5 -> R.drawable.lv5
        6 -> R.drawable.lv6
        else -> 0
    }
    if (rid != 0) {
        iv.setImageResource(rid)
        iv.visibility = View.VISIBLE
    } else {
        iv.visibility = View.GONE
    }
}

@BindingAdapter(value = ["url", "radius", "h"])
fun bindImage(
    iv: ImageView,
    url: String?,
    radius: Int,
    height: Int?
) {
    if (url != null) {
        val radiusPx = radius.dp2IntPx(iv.context)
        val empty = EmptyCornerDrawable(0xff969696.toInt(), radiusPx.toFloat())
        Glide.with(iv).asBitmap()
            .load(url)
            .apply(
                RequestOptions().transforms(
                    CenterCrop(),
                    RoundedCorners(radiusPx)
                )
                    .placeholder(empty).error(
                        empty
                    )
            )
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val w = resource.width
                    val h = resource.height
                    val lp = iv.layoutParams

                    if (height != null) {
                        val nW = height.dp2IntPx(iv.context) * w / h
                        lp.width = nW.toInt()
                    }
                    iv.layoutParams = lp
                    iv.setImageBitmap(resource)
                }
            })
    }
}


@BindingAdapter(value = ["picUrl"])
fun bindImage2(iv: ImageView, url: String?) {
    if (url != null) {
        iv.displayBase64(url)
    }
}

@BindingAdapter(value = ["imageId"])
fun bindImage(iv: ImageView, id: Int?) {
    if (id != null)
        iv.setImageResource(id)
}


@BindingAdapter(value = ["select"])
fun bindSelect(v: View, select: Boolean) {
    v.isSelected = select
}

@BindingAdapter(value = ["back"])
fun bindBackAction(v: View, select: Boolean) {
    if (select) {
        v.setOnClickListener {
            (v.context as Activity).onBackPressed()
        }
    }
}

@BindingAdapter(value = ["open"])
fun bindDrawer(v: DrawerLayout, open: Boolean) {
    if (open) {
        v.openDrawer(Gravity.LEFT)
    } else {
        v.closeDrawer(Gravity.LEFT)
    }
}


@BindingAdapter(value = ["html"])
fun bindHtml(tv: TextView, text: String) {
    tv.text = Html.fromHtml(text)
}

/**
 * 显示图片
 */
@BindingAdapter(value = ["showImage"])
fun bindImage(iv: ImageView, showImage: PositionImage?) {
    showImage?.run {
        val lp = iv.layoutParams as ViewGroup.MarginLayoutParams
        val parentWidth = iv.context.resources.displayMetrics.widthPixels
        val scale = parentWidth / clientWidth
        val rect = Rect()
        rect.left = (x * scale).toInt()
        rect.right = rect.left + (width * scale).toInt()
        rect.top = (y * scale).toInt()
        rect.bottom = rect.top + (height * scale).toInt()
        lp.leftMargin = rect.left
        lp.width = rect.width()
        lp.height = rect.height()
        lp.topMargin = rect.top
        iv.layoutParams = lp
        if (TextUtils.isEmpty(url)) return@run
        iv.displayOverride(url){
            ImageUtil.checkImage(iv, rect) {
                "finished:".logE()
                val activity = iv.getActivity()
                activity?.let {
                    val pair: androidx.core.util.Pair<View, String> =
                        androidx.core.util.Pair(iv, "image")
                    val option =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            it,
                            pair
                        )
                    val intent = Intent(it, ImageShowActivity::class.java)
                    Constants.sharedUrl = url
                    it.startActivityForResult(intent, 1, option.toBundle())
                }
            }
        }
    }
}




