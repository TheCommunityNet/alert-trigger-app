package wiki.comnet.alerttrigger.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import wiki.comnet.alerttrigger.domain.repository.DeviceIdRepository

class DeviceIdRepositoryImpl(
    private val context: Context,
) : DeviceIdRepository {
    override fun getDeviceId(): String = getSettingDeviceId(context)

    @SuppressLint("HardwareIds")
    private fun getSettingDeviceId(context: Context): String {
        val androidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        if (androidId != null) {
            return androidId
        }
        return "unknown"
    }
}
