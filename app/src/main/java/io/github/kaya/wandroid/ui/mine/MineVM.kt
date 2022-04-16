package io.github.kaya.wandroid.ui.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.github.kaya.wandroid.base.BaseViewModel
import io.github.kaya.wandroid.extension.toast
import io.github.kaya.wandroid.util.AbsentLiveData
import io.github.kaya.wandroid.util.LiveDataBus
import io.github.kaya.wandroid.util.SP

class MineVM : BaseViewModel() {

    val route = MutableLiveData<String>()

    private val _userInfo = Transformations.switchMap(isLogin) {
        if (it) {
            api.userInfo()
        } else {
            AbsentLiveData.create()
        }
    }

    val userInfo = Transformations.map(_userInfo) {
        loading.value = false
        it.data
    }


    init {
        isLogin.value = SP.getBoolean(SP.KEY_IS_LOGIN)
        isLogin.observeForever {
            //登录成功，加载进度获取个人信息
            if (it) loading.value = true
        }

    }


    fun login() {
        isNotLogin()
    }

    fun logout() {
        SP.logout()
        isLogin.value = false
        LiveDataBus.username.postValue("")
    }

    fun toCollectAction() {
        if (isNotLogin()) return
        route.value = "/collect"
    }

    fun toHistoryAction() {
        if (isNotLogin()) return
        route.value = "/history"
    }

    fun toCacheList() {
        route.value = "/cache"
    }

    fun toXBook() {
//        "未开放".toast()
        route.value = "/xbook"  //老司机自行开放
    }
}