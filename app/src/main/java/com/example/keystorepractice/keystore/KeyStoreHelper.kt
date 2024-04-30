package com.example.keystorepractice.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.nio.charset.Charset
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.KeyStore.PrivateKeyEntry
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Base64
import javax.crypto.Cipher

private const val ANDROID_KEY_STORE_PROVIDER = "AndroidKeyStore"
private const val KEY_STORE_ALIAS = "keyStoreAlias"
private const val TRANSFORMATION =
    "${KeyProperties.KEY_ALGORITHM_RSA}/${KeyProperties.BLOCK_MODE_ECB}/${KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1}"

object KeyStoreHelper {
    private val keyStore by lazy {
        checkNotNull(KeyStore.getInstance(ANDROID_KEY_STORE_PROVIDER).apply { load(null) })
    }

    private fun genKeyPair() {
        if (keyStore.containsAlias(KEY_STORE_ALIAS)) return

        val keyPairGenerator =
            KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA,
                ANDROID_KEY_STORE_PROVIDER
            )

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_STORE_ALIAS,
            KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_ECB)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .build()

        keyPairGenerator.initialize(keyGenParameterSpec)
        keyPairGenerator.genKeyPair()
    }

    private val publicKey: PublicKey
        get() {
            genKeyPair()
            val privateKeyEntry = keyStore.getEntry(KEY_STORE_ALIAS, null) as? PrivateKeyEntry
            return checkNotNull(privateKeyEntry?.certificate?.publicKey)
        }

    private val privateKey: PrivateKey
        get() {
            genKeyPair()
            val privateKeyEntry = keyStore.getEntry(KEY_STORE_ALIAS, null) as? PrivateKeyEntry
            return checkNotNull(privateKeyEntry?.privateKey)
        }

    val encodedPublicKey: String get() = Base64.getEncoder().encodeToString(publicKey.encoded)

    fun encrypt(data: String): String {
        return Cipher.getInstance(TRANSFORMATION).run {
            init(Cipher.ENCRYPT_MODE, publicKey)
            Base64.getEncoder().encodeToString(doFinal(data.toByteArray(Charsets.UTF_8)))
        }
    }

    fun decrypt(encryptedData: String): String {
        return Cipher.getInstance(TRANSFORMATION).run {
            init(Cipher.DECRYPT_MODE, privateKey)
            doFinal(Base64.getDecoder().decode(encryptedData)).toString(Charsets.UTF_8)
        }
    }
}