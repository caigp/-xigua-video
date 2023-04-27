package com.xiaocai.xiguavideo.page

import android.graphics.Color
import android.net.Uri
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.su.mediabox.pluginapi.Constant
import com.su.mediabox.pluginapi.action.PlayAction
import com.su.mediabox.pluginapi.components.IMediaDetailPageDataComponent
import com.su.mediabox.pluginapi.data.*
import com.su.mediabox.pluginapi.util.TextUtil
import com.su.mediabox.pluginapi.util.WebUtil
import com.su.mediabox.pluginapi.util.WebUtilIns
import com.xiaocai.xiguavideo.http.HttpUtils
import com.xiaocai.xiguavideo.http.TimeUtils
import okhttp3.Headers
import org.json.JSONObject
import org.jsoup.Jsoup
import java.net.URLDecoder
import java.text.SimpleDateFormat

class MediaDetailPageDataComponent : IMediaDetailPageDataComponent {
    private val TAG = "MediaDetailPageDataComp"

    private var title: String = ""

    private var coverImage: String = ""

    private var _publishTime: String = ""

    private var play_count: Long = 0

    private var name: String = ""

    private var desc: String = ""

    private var duration: Int = 0

    override suspend fun getMediaDetailData(partUrl: String): Triple<String, String, List<BaseData>> {
        Log.d(TAG, "partUrl: $partUrl")

        val uri0 = Uri.parse(partUrl)
        title = uri0.getQueryParameter("title").toString()
        coverImage = uri0.getQueryParameter("cover").toString()
        val publishTime = uri0.getQueryParameter("publishTime")!!.toLong()
        _publishTime = TimeUtils.format(TimeUtils.PATTERN, publishTime * 1000)
        play_count = uri0.getQueryParameter("play_count")!!.toLong()
        name = uri0.getQueryParameter("name")!!
        desc = uri0.getQueryParameter("desc")!!
        if (desc.isEmpty()) {
            desc = "?"
        }
        duration = uri0.getQueryParameter("duration")!!.toInt()

        Log.d(TAG, "host: " + uri0.host)
        Log.d(TAG, "path: " + uri0.path)


        val videoUrls = WebUtilIns.interceptResource("https://" + uri0.host + uri0.path, "[(.*)video/urls(.*)][(.*)mime_type=video_mp4(.*)]", loadPolicy = object : WebUtil.LoadPolicy by WebUtil.DefaultLoadPolicy {
            override val timeOut: Long
                get() = 4000
        })
        Log.d(TAG, "videoUrls: $videoUrls")

        val videoInfoList = mutableListOf<EpisodeData>()
        val episodeListData = EpisodeListData(videoInfoList).apply {

        }

        val uri = Uri.parse(videoUrls)
        val callback = uri.getQueryParameter("callback")
        Log.d(TAG, "callback: $callback")
        val mime_type = uri.getQueryParameter("mime_type")
        if (mime_type == "video_mp4") {
            videoInfoList.add(
                EpisodeData(
                    "未知",
                    videoUrls
                ).apply {
                    action =
                        PlayAction.obtain(url, coverUrl = coverImage, videoName = title)
                })
        } else {
            val ret = HttpUtils.syncGet(TAG, videoUrls, Headers.headersOf())
//        Log.d(TAG, "ret: $ret")
            val json = ret.substring(callback!!.length + 1, ret.length - 1)
//        Log.d(TAG, "json: $json")


            val jsonObject = JSONObject(json)
            if (jsonObject.optInt("code") == 0) {
                val dataJSONObject = jsonObject.optJSONObject("data")
                val videoList = dataJSONObject?.optJSONObject("video_list")
                val keys = videoList?.keys()

                while (keys!!.hasNext()) {
                    val video = videoList.getJSONObject(keys.next())
                    val mainUrl = video.getString("main_url")
                    val vwidth = video.getInt("vwidth")
                    val vheight = video.getInt("vheight")
                    val definition = video.getString("definition")

                    videoInfoList.add(
                        EpisodeData(
                            definition,
                            String(Base64.decode(mainUrl, Base64.DEFAULT))
                        ).apply {
                            action =
                                PlayAction.obtain(url, coverUrl = coverImage, videoName = title)
                        })
                }
            }
        }

        return Triple(
            coverImage,
            title,
            mutableListOf<BaseData>().apply {
                add(Cover1Data(coverImage))
                add(SimpleTextData("作者:$name").apply {
                    fontColor = Color.WHITE
                })
                add(SimpleTextData("作者简介:$desc").apply {
                    fontColor = Color.WHITE
                })
                add(SimpleTextData("时长:" + duration / 60 + "分" + duration % 60 + "秒"))
                add(SimpleTextData("发布时间:$_publishTime"))
                add(SimpleTextData("${play_count}次播放"))
                add(SimpleTextData("清晰度"))
                add(episodeListData)
            }
        )
    }
}