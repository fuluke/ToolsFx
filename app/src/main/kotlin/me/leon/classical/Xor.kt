package me.leon.classical

import me.leon.encode.base.base64
import me.leon.encode.base.base64Decode
import me.leon.ext.hex2ByteArray
import me.leon.ext.toHex

fun String.xorBase64(key: String) =
    toByteArray()
        .mapIndexed { index, c -> (c.toInt() xor key[index % key.length].code).toByte() }
        .toByteArray()
        .base64()

fun String.xorBase64Decode(key: String) =
    base64Decode()
        .mapIndexed { index, c -> (c.toInt() xor key[index % key.length].code).toByte() }
        .toByteArray()
        .base64()

fun String.xorHex(key: String) =
    toByteArray()
        .mapIndexed { index, c -> (c.toInt() xor key[index % key.length].code).toByte() }
        .toByteArray()
        .toHex()

fun String.xorHexDecode(key: String) =
    hex2ByteArray()
        .mapIndexed { index, c -> (c.toInt() xor key[index % key.length].code).toByte() }
        .toByteArray()
        .base64()

fun ByteArray.xor(key: String) =
    mapIndexed { index, c -> (c.toInt() xor key[index % key.length].code).toByte() }.toByteArray()
