package io.github.kaya.wandroid.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import io.github.iamyours.router.annotation.Route
import io.github.kaya.wandroid.R
import io.github.kaya.wandroid.base.BaseActivity
import io.github.kaya.wandroid.databinding.ActivityLoginBinding
import io.github.kaya.wandroid.extension.viewModel
import io.github.kaya.wandroid.util.Constants
import io.github.kaya.wandroid.util.LiveDataBus
import io.github.kaya.wandroid.util.SP

@Route(path = "/login")
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_login

    val vm by viewModel<LoginVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm
        vm.attachLoading(loadingState)
        vm.loginUser.observe(this, Observer {
            it?.run {
                SP.put(SP.KEY_IS_LOGIN, true)
                SP.put(SP.KEY_NICK_NAME, nickname)
                SP.put(SP.KEY_USER_NAME, username)
                setResult(Constants.RESULT_LOGIN)
                LiveDataBus.username.postValue(username)
                finish()
            }
        })
        vm.close.observe(this, Observer { finish() })
    }
}