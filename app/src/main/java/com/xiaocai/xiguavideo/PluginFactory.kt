package com.xiaocai.xiguavideo

import com.su.mediabox.pluginapi.IPluginFactory
import com.su.mediabox.pluginapi.components.IBasePageDataComponent
import com.su.mediabox.pluginapi.components.IHomePageDataComponent
import com.su.mediabox.pluginapi.components.IMediaDetailPageDataComponent
import com.xiaocai.xiguavideo.page.HomePageDataComponent
import com.xiaocai.xiguavideo.page.MediaDetailPageDataComponent

class PluginFactory : IPluginFactory() {

    override fun <T : IBasePageDataComponent> createComponent(clazz: Class<T>) = when(clazz) {
        IHomePageDataComponent::class.java -> HomePageDataComponent()
        IMediaDetailPageDataComponent::class.java -> MediaDetailPageDataComponent()
        else -> null
    } as? T

    override val host: String
        get() = Constant.host
}