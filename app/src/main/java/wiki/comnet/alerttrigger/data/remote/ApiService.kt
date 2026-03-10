package wiki.comnet.alerttrigger.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import wiki.comnet.alerttrigger.data.remote.dto.BaseResponse
import wiki.comnet.alerttrigger.data.remote.dto.ShellyData
import wiki.comnet.alerttrigger.data.remote.dto.VerifyOtpData
import wiki.comnet.alerttrigger.data.remote.dto.VerifyOtpRequest

class ApiService(private val client: HttpClient) {
    private val BASE_URL = "http://192.168.100.108:4000"

    suspend fun verifyOtp(data: VerifyOtpRequest): BaseResponse<VerifyOtpData?> {
        return client.post("$BASE_URL/api/v1/auth/verify_otp") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }.body<BaseResponse<VerifyOtpData?>>()
    }

    suspend fun getShellies(): BaseResponse<List<ShellyData>> {
        return client.get("$BASE_URL/api/v1/shellies").body<BaseResponse<List<ShellyData>>>()
    }

    suspend fun toggleShellyAlert(shellyId: String) {
        client.post("$BASE_URL/api/v1/alert/$shellyId/toggle") {
            contentType(ContentType.Application.Json)
            setBody(null)
        }
    }

    suspend fun toggleAllAlerts() {
        client.post("$BASE_URL/api/v1/alert/toggle") {
            contentType(ContentType.Application.Json)
            setBody(null)
        }
    }
}