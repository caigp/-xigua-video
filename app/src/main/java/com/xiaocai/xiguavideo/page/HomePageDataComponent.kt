package com.xiaocai.xiguavideo.page

import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.action.PlayAction
import com.su.mediabox.pluginapi.components.IHomePageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.Cover1Data
import com.su.mediabox.pluginapi.data.MediaInfo1Data
import com.su.mediabox.pluginapi.data.SimpleTextData
import com.xiaocai.xiguavideo.http.HttpUtils
import org.json.JSONObject

class HomePageDataComponent : IHomePageDataComponent {

    private val TAG = HomePageDataComponent::class.java.simpleName

    private val url: String = "https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=94349555027&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more&Attrs[ImageFormat]=webp&image_control[cover_image_middle][format]=webp&image_control[cover_image_large][format]=webp&image_control[first_frame][format]=webp"

    override suspend fun getData(page: Int): List<BaseData> {
        val data = mutableListOf<BaseData>()

        val json = HttpUtils.syncGet(TAG, url)
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
                val title = videoDataJSONObject.getString("title")
                val coverImageUrl = videoDataJSONObject.getString("cover_image_url")
                val userInfoJSONObject =  videoDataJSONObject.getJSONObject("user_info")
                val userName =  userInfoJSONObject.getString("name")
                val url = "https://m.ixigua.com/video/$groupId?wid_try=1"

                data.add(MediaInfo1Data(title, coverImageUrl, "", "", userName).apply {
                    spanSize = 8
                    action = DetailAction.obtain(url)
                })
            }
        }

        return data
    }

}