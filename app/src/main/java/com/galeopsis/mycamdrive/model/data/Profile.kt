package com.galeopsis.mycamdrive.model.data

import com.google.gson.annotations.SerializedName

data class Profile(
    val email: String,
    @SerializedName("group_name")
    val groupName: String,
    val name: String,
    @SerializedName("pay_balance")
    val payBalance: String,
    @SerializedName("pay_balance_unit")
    val payBalanceUnit: String,
    val permissions: Permissions,
    val username: String
)