package hr.fer.carpulse.util

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager

class PhoneUtils(private val context: Context) {
    fun getPhoneOperator(): String {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return manager.networkOperatorName
    }

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL

        return if (model.startsWith(manufacturer)) {
            model
        } else {
            "$manufacturer $model"
        }
    }

    fun getFingerprint(): String {
        return Build.FINGERPRINT
    }

    fun getAndroidId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}