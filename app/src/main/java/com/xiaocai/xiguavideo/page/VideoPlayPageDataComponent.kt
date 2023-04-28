package com.xiaocai.xiguavideo.page

import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract.Colors
import android.util.Log
import com.su.mediabox.pluginapi.components.IVideoPlayPageDataComponent
import com.su.mediabox.pluginapi.data.VideoPlayMedia
import com.kuaishou.akdanmaku.data.DanmakuItemData
import com.xiaocai.xiguavideo.http.HttpUtils
import okhttp3.Headers
import org.json.JSONObject

class VideoPlayPageDataComponent : IVideoPlayPageDataComponent {

    private val TAG = "VideoPlay"

    override suspend fun getVideoPlayMedia(episodeUrl: String): VideoPlayMedia {
        return VideoPlayMedia("", episodeUrl)
    }

    override suspend fun getDanmakuData(
        videoName: String,
        episodeName: String,
        episodeUrl: String
    ): List<DanmakuItemData>? {
        //https://www.ixigua.com/vpc_danmaku/list/v1/?format=json&group_id=7199258085419188788&start_time=0&end_time=12800000

        val list = mutableListOf<DanmakuItemData>()

        Log.d(TAG, "episodeName: $episodeName")
        Log.d(TAG, "episodeUrl: $episodeUrl")

        val uri = Uri.parse(episodeUrl)

        val url = "https://www.ixigua.com/vpc_danmaku/list/v1/?format=json&" + uri.getQueryParameter("danmaku")
        val ret = HttpUtils.syncGet(TAG, url, Headers.headersOf())

        try {
            val jsonObject = JSONObject(ret)
            val dataJSONArray = jsonObject.optJSONArray("data")
            val length = dataJSONArray?.length() ?: 0
            for (i in 0 until length) {
                val itemJSONObject = dataJSONArray?.getJSONObject(i)
                val content = itemJSONObject?.optString("text", "")
                val id = itemJSONObject?.optLong("danmaku_id")
                val position = itemJSONObject?.optLong("offset_time")
                val textColor = itemJSONObject?.optInt("text_color", 0xfdfdfd)
                val score = itemJSONObject?.optDouble("score")
                Log.d(TAG, "textColor -> $textColor")
                val danmakuItemData = DanmakuItemData(content = content!!,
                    danmakuId = id!!, mode = DanmakuItemData.DANMAKU_MODE_ROLLING,
                    position = position!!, textColor = Color.WHITE,
                    textSize = 25, score = score!!.toInt())

                list.add(danmakuItemData)
            }
        } catch (_: Exception) {
        }

        Log.d(TAG, "getDanmakuData: $ret")
        return list
    }
}