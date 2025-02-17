package me.leon.controller

import java.io.File
import me.leon.encode.base.base64
import me.leon.ext.*
import me.leon.ext.crypto.*
import tornadofx.*

class SymmetricCryptoController : Controller() {
    fun encrypt(
        key: ByteArray,
        data: String,
        iv: ByteArray,
        alg: String,
        charset: String = "UTF-8",
        isSingleLine: Boolean = false,
        inputEncode: String = "raw",
        outputEncode: String = "base64",
    ): String =
        catch({ "encrypt error: $it" }) {
            println("encrypt  $alg")
            if (isSingleLine)
                data.lineAction2String {
                    encrypt(it, inputEncode, charset, key, iv, alg, outputEncode)
                }
            else encrypt(data, inputEncode, charset, key, iv, alg, outputEncode)
        }

    private fun encrypt(
        data: String,
        inputEncode: String,
        charset: String,
        key: ByteArray,
        iv: ByteArray,
        alg: String,
        outputEncode: String
    ) =
        if (alg.startsWith("XXTEA"))
            XXTEA
                .encrypt(data.decodeToByteArray(inputEncode, charset), key)
                .encodeTo(outputEncode, charset)
        else
            data.decodeToByteArray(inputEncode, charset)
                .encrypt(key, iv, alg)
                .encodeTo(outputEncode, charset)

    fun encryptByFile(key: ByteArray, path: String, iv: ByteArray, alg: String) =
        catch({ "encrypt error: $it" }) {
            println("encrypt  $alg")
            val parentFile = path.toFile().parentFile.absolutePath
            val encryptDir =
                File(parentFile, "enc").also { if (!it.exists()) it.mkdirs() }.absolutePath
            val outFileName = path.replace(parentFile, encryptDir)
            if (alg.startsWith("XXTEA"))
                outFileName.toFile().outputStream().use { out ->
                    path.toFile().inputStream().use {
                        out.write(XXTEA.encrypt(it.readBytes(), key))
                    }
                }
            else path.encryptFile(key, iv, alg, outFileName)
            "加密文件路径(同选择文件目录): ${File(outFileName).absolutePath} \n" +
                "alg: $alg\n" +
                "key(base64): ${key.base64()}\n" +
                "iv(base64): ${iv.base64()}\n"
        }

    fun decryptByFile(key: ByteArray, path: String, iv: ByteArray, alg: String) =
        catch({ "decrypt error: $it" }) {
            println("decrypt  $alg")
            val parentFile = path.toFile().parentFile.absolutePath
            val decryptDir =
                File(parentFile, "dec").also { if (!it.exists()) it.mkdirs() }.absolutePath
            val outFileName = path.replace(parentFile, decryptDir)
            if (alg.startsWith("XXTEA"))
                outFileName.toFile().outputStream().use { out ->
                    path.toFile().inputStream().use {
                        out.write(XXTEA.decrypt(it.readBytes(), key))
                    }
                }
            else path.decryptFile(key, iv, alg, outFileName)
            "解密文件路径(同选择文件目录): $outFileName"
        }

    fun decrypt(
        key: ByteArray,
        data: String,
        iv: ByteArray,
        alg: String,
        charset: String = "UTF-8",
        isSingleLine: Boolean = false,
        inputEncode: String = "raw",
        outputEncode: String = "base64",
    ): String =
        catch({ "decrypt error: $it" }) {
            println("decrypt  $alg")
            if (isSingleLine)
                data.lineAction2String {
                    decrypt(it, inputEncode, charset, key, iv, alg, outputEncode)
                }
            else decrypt(data, inputEncode, charset, key, iv, alg, outputEncode)
        }

    private fun decrypt(
        data: String,
        inputEncode: String,
        charset: String,
        key: ByteArray,
        iv: ByteArray,
        alg: String,
        outputEncode: String
    ) =
        if (alg.startsWith("XXTEA"))
            XXTEA
                .decrypt(data.decodeToByteArray(inputEncode, charset), key)
                .encodeTo(outputEncode, charset)
        else
            data.decodeToByteArray(inputEncode, charset)
                .decrypt(key, iv, alg)
                .encodeTo(outputEncode, charset)
}
