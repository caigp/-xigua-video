package com.xiaocai.xiguavideo.page

import android.net.Uri
import android.util.Base64
import android.util.Log
import com.su.mediabox.pluginapi.components.IVideoPlayPageDataComponent
import com.su.mediabox.pluginapi.data.VideoPlayMedia
import com.su.mediabox.pluginapi.util.WebUtilIns
import com.xiaocai.xiguavideo.http.HttpUtils
import okhttp3.Headers
import okhttp3.internal.wait
import org.json.JSONObject
import java.net.URI
import kotlin.math.max

class VideoPlayPageDataComponent : IVideoPlayPageDataComponent {

    private val TAG = "VideoPlay"

    override suspend fun getVideoPlayMedia(episodeUrl: String): VideoPlayMedia {
        return VideoPlayMedia("", episodeUrl)
    }
}