package com.xiaocai.xiguavideo

import com.su.mediabox.pluginapi.IPluginFactory
import com.su.mediabox.pluginapi.action.Action
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.action.CustomPageAction
import com.su.mediabox.pluginapi.components.*
import com.xiaocai.xiguavideo.page.*

class PluginFactory : IPluginFactory() {

    override fun <T : IBasePageDataComponent> createComponent(clazz: Class<T>) = when(clazz) {
        MyPageDataComponent::class.java -> MyPageDataComponent()
        IHomePageDataComponent::class.java -> HomePageDataComponent()
        IMediaDetailPageDataComponent::class.java -> MediaDetailPageDataComponent()
        IVideoPlayPageDataComponent::class.java -> VideoPlayPageDataComponent()
        IMediaClassifyPageDataComponent::class.java -> MediaClassifyPageDataComponent()
        IMediaSearchPageDataComponent::class.java -> MediaSearchPageDataComponent()
        else -> null
    } as? T

    override val host: String
        get() = Constant.host
}