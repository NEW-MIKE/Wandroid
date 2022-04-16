package io.github.kaya.wandroid.net.juejin

data class JuejinEntryList<T>(
    val total: Int,
    val entrylist: List<T>
) {
    var first = false
}