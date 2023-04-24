package com.xiaocai.xiguavideo.page

import android.util.Log
import com.su.mediabox.pluginapi.Constant
import com.su.mediabox.pluginapi.components.IMediaDetailPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.SimpleTextData
import com.su.mediabox.pluginapi.util.WebUtil
import com.su.mediabox.pluginapi.util.WebUtilIns
import org.jsoup.Jsoup

class MediaDetailPageDataComponent : IMediaDetailPageDataComponent {
    private val TAG = "MediaDetailPageDataComp"

    override suspend fun getMediaDetailData(partUrl: String): Triple<String, String, List<BaseData>> {
        val html = WebUtilIns.getRenderedHtmlCode(partUrl, "(.*)xgplayer?(.*)")
        val document = Jsoup.parse(html)

        val feedTitle = document.title()

        return Triple(
            "123",
            feedTitle,
            mutableListOf<BaseData>().apply {
                add(SimpleTextData(document.text()))
            }
        )
    }
}