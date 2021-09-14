package com.galeopsis.mycamdrive.model.data


import com.google.gson.annotations.SerializedName

data class Timezone(
    val id: String,
    @SerializedName("raw_offset")
    val rawOffset: Int
)