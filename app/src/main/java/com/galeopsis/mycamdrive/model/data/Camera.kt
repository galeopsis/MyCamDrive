package com.galeopsis.mycamdrive.model.data

import com.google.gson.annotations.SerializedName

data class Camera(
    val archive: Boolean,
    @SerializedName("camera_channel_id")
    val cameraChannelId: String,
    @SerializedName("camera_connected_server")
    val cameraConnectedServer: Boolean,
    @SerializedName("camera_model")
    val cameraModel: String,
    @SerializedName("camera_name")
    val cameraName: String,
    @SerializedName("camera_public")
    val cameraPublic: Boolean,
    @SerializedName("mobile_audio")
    val mobileAudio: Boolean,
    @SerializedName("preview_url")
    val previewUrl: Any?,
    @SerializedName("stream_url")
    val streamUrl: Any?,
    val timezone: Timezone,
    val status: CameraData,
    val profile: Profile
)