package io.github.kaya.wandroid.web

import android.content.Intent
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.github.kaya.wandroid.App
import io.github.kaya.wandroid.extension.logE
import io.github.kaya.wandroid.extension.logV
import io.github.kaya.wandroid.ui.web.WebActivity
import io.github.kaya.wandroid.util.FileUtil
import io.github.kaya.wandroid.util.Wget
import io.github.kaya.wandroid.util.glide.GlideUtil
import io.github.kaya.wandroid.vo.WebViewVO
import java.io.ByteArrayInputStream
import java.net.URISyntaxException

@Deprecated("已废弃，请使用Web2Activity")
open class BaseWebViewClient(
    protected var originUrl: String, private var vo:
    MutableLiveData<Boolean>
) :
    WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String?
    ): Boolean {

        if (url == null) {
            return false
        }
        if (url.startsWith("intent")) run {
            try {
                val context = view.context
                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    Toast.makeText(
                        context.applicationContext, "无法打开", Toast
                            .LENGTH_SHORT
                    ).show()
                }
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }

            return true
        }
        val isHttp = url.startsWith("http://") || url.startsWith("https://")
        "shouldOverrideUrlLoading：$url,isHttp:$isHttp,thread:${Thread.currentThread()}".logE()
        if (!isHttp) {//fix issue #5,阻止一些非http请求（如简书：jianshu://notes/xxx)
            "invalid url:$url".logE()
            return true
        }
        val fileName = url.substring(url.lastIndexOf("/"))
        val isResource = fileName.contains(".")
        val originName = originUrl.substring(originUrl.lastIndexOf("/"))
        //有些实际url相同，只不过http/https格式不一样
        if (fileName == originName) return super.shouldOverrideUrlLoading(
            view,
            url
        )

        return if (isHttp && !isResource) {
            WebActivity.nav(url, view.context)
            true
        } else
            false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        vo.value = true
//        loadBaseScript(view)
    }


    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: SslError?
    ) {
        super.onReceivedSslError(view, handler, error)
    }

    override fun shouldInterceptRequest(view: WebView?, url: String?)
            : WebResourceResponse? {
        val urlStr = "$url"
//        "shouldInterceptRequest:$url".logE()
        if (originUrl == url) {
            val cache = FileUtil.getHtml(originUrl)
            if (cache != null) {
                val input = ByteArrayInputStream(cache.toByteArray())
                return WebResourceResponse("text/html", "utf-8", input)
            }
        } else if (urlStr.endsWith(".css") || urlStr.endsWith(".js")) {

        } else if (urlStr.startsWith("http")) {
            val head = Wget.head(url)
            "head:$head".logV()
            if (head.startsWith("image")) {
                val bytes = GlideUtil.syncLoad(url, head)
                if (bytes != null) {
                    return WebResourceResponse(
                        head,
                        "utf-8",
                        ByteArrayInputStream(bytes)
                    )
                }
            }
        }
        return super.shouldInterceptRequest(view, url)
    }
}