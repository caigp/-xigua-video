package com.xiaocai.xiguavideo.page

import android.util.Log
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.components.IMediaClassifyPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.ClassifyItemData
import com.su.mediabox.pluginapi.data.MediaInfo1Data
import com.xiaocai.xiguavideo.http.HttpUtils
import okhttp3.Headers
import org.json.JSONObject
import java.net.URLEncoder

class MediaClassifyPageDataComponent : IMediaClassifyPageDataComponent {


    private val TAG = MediaClassifyPageDataComponent::class.java.simpleName

    private var url: String? = null

    override suspend fun getClassifyItemData(): List<ClassifyItemData> {
        return listOf(ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=94349533351&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "VLOG")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=61887739376&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "游戏")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=6141508391&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "美食")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=94349531488&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "手工")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=61887739390&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "农人")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=61887739345&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "综艺")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=61887739369&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "搞笑")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=61887739368&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "音乐")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=6141508395&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "儿童")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=94349530916&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "NBA")
        }, ClassifyItemData().apply {
            action = ClassifyAction.obtain("https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=6141508406&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more", "", "宠物")
        })
    }

    override suspend fun getClassifyData(
        classifyAction: ClassifyAction,
        page: Int
    ): List<BaseData> {
        val data = mutableListOf<BaseData>()

        url = classifyAction.url
        Log.d(TAG, "url=$url")

        val headers = Headers.Builder()
            .add("cookie", "ttwid=7225789753688655397;ixigua-a-s=0;")
            .build()
        val json = HttpUtils.syncGet(TAG, url, headers)
        val jsonObject = JSONObject(json)
        val statusCode = jsonObject.optInt("status_code")
        if (statusCode == 0) {
            val dataJSONObject = jsonObject.getJSONObject("data")
            val feedJSONObject = dataJSONObject.getJSONObject("channel_feed")
            val dataJSONArray = feedJSONObject.getJSONArray("data")
            val length = dataJSONArray.length()
            for (i in 0 until length) {
                val itemJSONObject = dataJSONArray[i] as JSONObject
                val videoDataJSONObject = itemJSONObject.getJSONObject("data")
                val groupId = videoDataJSONObject.getString("group_id")
                val play_count = videoDataJSONObject.optLong("play_count")
                val duration = videoDataJSONObject.optInt("duration")
                val publishTime = videoDataJSONObject.optLong("publish_time", 0)
                val title = videoDataJSONObject.getString("title")
                val coverImageUrl = videoDataJSONObject.getString("cover_image_url")
                val userInfoJSONObject =  videoDataJSONObject.getJSONObject("user_info")
                val userName = userInfoJSONObject.getString("name")
                val desc = userInfoJSONObject.getString("author_desc")
                val url = "https://m.ixigua.com/video/$groupId?title= " +
                        URLEncoder.encode(title, "UTF-8") +
                        "&cover=" +
                        URLEncoder.encode(coverImageUrl, "UTF-8") +
                        "&publishTime=$publishTime" +
                        "&play_count=$play_count" +
                        "&duration=$duration" +
                        "&name=$userName" +
                        "&desc=" +
                        URLEncoder.encode(desc, "UTF-8")

                data.add(MediaInfo1Data(title, coverImageUrl, "", "", userName).apply {
                    spanSize = 4
                    action = DetailAction.obtain(url)
                })
            }
        }

        return data
    }
}