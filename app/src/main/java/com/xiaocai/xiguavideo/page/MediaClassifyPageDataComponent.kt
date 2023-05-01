package com.xiaocai.xiguavideo.page

import android.util.Log
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.components.IMediaClassifyPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.ClassifyItemData
import com.su.mediabox.pluginapi.data.MediaInfo1Data
import com.xiaocai.xiguavideo.http.HttpUtils
import com.xiaocai.xiguavideo.utils.DataUtils
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

        data.addAll(DataUtils.getDataList(TAG, url!!))

        return data
    }
}