package com.xiaocai.xiguavideo.page

import android.util.Base64
import android.util.Log
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.action.PlayAction
import com.su.mediabox.pluginapi.components.IHomePageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.MediaInfo1Data
import com.su.mediabox.pluginapi.util.UIUtil.dp
import com.xiaocai.xiguavideo.http.HttpUtils
import com.xiaocai.xiguavideo.utils.DataUtils
import okhttp3.Headers
import org.json.JSONObject
import java.net.URLEncoder

class HomePageDataComponent : IHomePageDataComponent {

    private val TAG = HomePageDataComponent::class.java.simpleName

    private var url: String? = null

    override suspend fun getData(page: Int): List<BaseData> {
        val data = mutableListOf<BaseData>()

        url = "https://m.ixigua.com/api/feedv2/feedById?aid=3586&channelId=94349555027&request_from=710&queryCount=1&count=10&offset=0&refresh_type=load_more"
        Log.d(TAG, "url=$url")

        data.addAll(DataUtils.getDataList(TAG, url!!))

        return data
    }

}