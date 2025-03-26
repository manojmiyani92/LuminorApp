package com.luminor.infrastructure.utils

import androidx.core.util.PatternsCompat
import java.security.MessageDigest




fun CharSequence.isValidEmail() = PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

fun String.passwordEncryption(): String {
    val md = MessageDigest.getInstance("MD5")
    val hashBytes = md.digest(this.toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) }

}
