package io.github.kaya.wandroid.base

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.kaya.wandroid.net.wan.WanResponse
import io.github.kaya.wandroid.net.wan.WanApi
import io.github.kaya.wandroid.util.LiveDataBus
import io.github.kaya.wandroid.util.SP
import io.github.kaya.wandroid.vo.PageVO

open class BaseViewModel : ViewModel() {
    val refreshTrigger = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    protected val api = WanApi.get()

    protected val page = MutableLiveData<Int>()
    val refreshing = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()
    val hasMore = MutableLiveData<Boolean>()
    val autoRefresh = MutableLiveData<Boolean>()//SmartRefreshLayout自动刷新标记

    /*
    * 登录相关
    * */
    val isLogin = MutableLiveData<Boolean>()
    //跳转登录
    val toLogin = MutableLiveData<Boolean>()
    val name = Transformations.map(isLogin) {
        val username = SP.getString(SP.KEY_USER_NAME)
        val nickname = SP.getString(SP.KEY_NICK_NAME)
        if (TextUtils.isEmpty(nickname)) username else nickname
    }

    init {
        isLogin.value = SP.getBoolean(SP.KEY_IS_LOGIN)
        LiveDataBus.username.observeForever {
            //监听登录变化
            isLogin.value = !TextUtils.isEmpty(it)
        }
    }

    fun isNotLogin(): Boolean {
        return if (isLogin.value == true) false
        else {
            toLogin.value = true
            true
        }
    }

    open fun loadMore() {
        page.value = (page.value ?: 0) + 1
        moreLoading.value = true
    }

    /**
     * 自动刷新
     */
    fun autoRefresh() {
        autoRefresh.value = true
    }

    open fun refresh() {
        page.value = 0
        refreshing.value = true
    }

    /**
     * 处理分页数据
     */
    fun <T> mapPage(source: LiveData<WanResponse<PageVO<T>>>): LiveData<PageVO<T>> {
        return Transformations.map(source) {
            refreshing.value = false
            moreLoading.value = false
            hasMore.value = !(it?.data?.over ?: false)
            it.data ?: PageVO(1, ArrayList(), 0, true, 1, 20, 0)
        }
    }

    open fun loadData() {
        refreshTrigger.value = true
        loading.value = true
    }

    /**
     * 绑定登录状态
     */
    fun attachLoading(otherState: MutableLiveData<Boolean>) {
        loading.observeForever {
            otherState.value = it
        }
    }
}