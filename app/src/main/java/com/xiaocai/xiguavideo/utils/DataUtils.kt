package com.xiaocai.xiguavideo.utils

import android.util.Log
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.MediaInfo1Data
import com.xiaocai.xiguavideo.MyApp
import com.xiaocai.xiguavideo.http.HttpUtils
import okhttp3.Headers
import org.json.JSONObject
import java.net.URLEncoder

object DataUtils {

    fun getDataList(TAG: String, url: String): List<BaseData> {
        val data = mutableListOf<BaseData>()
        Log.d(TAG, MyApp.ttwid.toString())
        val headers = Headers.Builder()
            .add("cookie", "ttwid=${MyApp.ttwid};ixigua-a-s=0;")
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
                        "&name=" +
                        URLEncoder.encode(userName, "UTF-8") +
                        "&desc=" +
                        URLEncoder.encode(desc, "UTF-8") +
                        "&groupId=" +
                        URLEncoder.encode(groupId, "UTF-8")

                data.add(MediaInfo1Data(title, coverImageUrl, "", "", userName).apply {
                    spanSize = 4
                    action = DetailAction.obtain(url)
                })
            }
        }
        return data
    }
}