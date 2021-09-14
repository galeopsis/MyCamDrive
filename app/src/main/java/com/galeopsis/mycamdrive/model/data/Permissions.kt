package com.galeopsis.mycamdrive.model.data


data class Permissions(
    val archive: Archive,
    val online: Online,
    val payment: Payment,
    val settings: Settings
)