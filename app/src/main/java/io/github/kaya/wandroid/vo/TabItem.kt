package io.github.kaya.wandroid.vo

import androidx.fragment.app.Fragment


class TabItem(
    var icon: Int,
    var name: String,
    var fragmentCls: Class<out Fragment>
)