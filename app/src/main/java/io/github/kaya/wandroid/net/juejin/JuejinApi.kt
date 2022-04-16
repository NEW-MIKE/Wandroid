package io.github.kaya.wandroid.net.juejin

import androidx.lifecycle.LiveData
import io.github.kaya.wandroid.net.ApiFactory
import io.github.kaya.wandroid.vo.juejin.JuejinArticleVO
import retrofit2.http.GET
import retrofit2.http.Query

interface JuejinApi {
    /**
     * Android文章列表
     */
    @GET("get_entry_by_timeline?limit=20&category=5562b410e4b00c57d9b94a92&src=android")
    fun aritcleList(
        @Query("before") before: String
    ): LiveData<JuejinResponse<JuejinEntryList<JuejinArticleVO>>>

    companion object {
        fun get(): JuejinApi {
            return get("https://timeline-merger-ms.juejin.im/v1/")
        }

        fun get(url: String): JuejinApi {
            return ApiFactory.create(
                url,
                false
            ) { code, msg, obj ->
                JuejinResponse(code, msg, obj)
            }
        }
    }
}