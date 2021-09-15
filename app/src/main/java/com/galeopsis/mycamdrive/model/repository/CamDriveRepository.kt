package com.galeopsis.mycamdrive.model.repository

import androidx.lifecycle.MutableLiveData
import com.galeopsis.mycamdrive.model.api.CamDriveApi
import com.galeopsis.mycamdrive.model.data.Camera
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CamDriveRepository(
    private val camDriveApi: CamDriveApi
) {

    private val cameraList = MutableLiveData<List<Camera>?>()
    val data = cameraList

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val cameras = camDriveApi.getCameras().await()
            val cameraResponse = cameras.body()
            val listCameras = cameraResponse?.data?.cameras
            if (listCameras != null) {
                cameraList.postValue(listCameras)
            }
        }
    }

    suspend fun userLogin(login: String, password: String) {
        withContext(Dispatchers.IO) {
            val userLogin = camDriveApi.loginAsync(login, password).await()
            val loginResponse = userLogin.body()
            val listCameras = loginResponse?.data?.cameras
            if (listCameras != null) {
                cameraList.postValue(listCameras)
            }
        }
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            val userLogin = camDriveApi.logout()

        }
    }
}