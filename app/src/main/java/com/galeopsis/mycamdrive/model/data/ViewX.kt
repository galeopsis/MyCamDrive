package com.galeopsis.mycamdrive.model.data


import com.google.gson.annotations.SerializedName

data class ViewX(
    val available: Int,
    @SerializedName("send_audio_to_device")
    val sendAudioToDevice: Int
)