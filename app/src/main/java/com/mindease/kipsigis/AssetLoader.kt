package com.mindease.kipsigis

import android.content.Context
import java.io.File

object AssetLoader {

    fun copyAssetToFile(context: Context, assetName: String): File {
        val outFile = File(context.filesDir, assetName)

        if (outFile.exists()) return outFile

        context.assets.open(assetName).use { input ->
            outFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return outFile
    }

    fun readAssetBytes(context: Context, assetName: String): ByteArray {
        return context.assets.open(assetName).use { it.readBytes() }
    }
}
