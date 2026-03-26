package wiki.comnet.alerttrigger.data.local.prefs

import android.content.Context
import androidx.core.content.edit

class PrefsManager(
    context: Context,
) {
    private val prefs = context.getSharedPreferences("alert_trigger_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_ACCESS_TOKEN, token) }
    }

    fun getToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)

    fun clearToken() {
        prefs.edit { remove(KEY_ACCESS_TOKEN) }
    }

    fun saveUserMessage(message: String) {
        prefs.edit { putString(KEY_USER_MESSAGE, message) }
    }

    fun getUserMessage(): String? = prefs.getString(KEY_USER_MESSAGE, null)

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_USER_MESSAGE = "user_message"
    }
}