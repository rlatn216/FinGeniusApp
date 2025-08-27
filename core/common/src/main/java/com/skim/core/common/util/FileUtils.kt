package com.skim.core.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.skim.core.model.BaseLog
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

object FileUtils {

    /**
     * 외장 메모리 사용 가능 공간 가져오기(단위 : bytes)
     */
    @SuppressLint("NewApi")
    @Throws(IOException::class)
    private fun getRemainStorageSize(context: Context): Long {
        if (!Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED, ignoreCase = true)
        ) {
            return 0L
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val storageManager = context.getSystemService(
                StorageManager::class.java
            )
            val appSpecificInternalDirUuid =
                storageManager.getUuidForPath(context.filesDir)
            storageManager.getAllocatableBytes(appSpecificInternalDirUuid)
        } else {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            availableBlocks * blockSize
        }
    }

    fun availableStorage(context: Context, downloadFileSize: Long): Boolean {
        return try {
            val remainStorageSize = getRemainStorageSize(context)
            val bufferSize = (50 * 1024 * 1024).toLong() // 버퍼 50MB
            val availableSize = remainStorageSize - downloadFileSize - bufferSize
            (remainStorageSize > 0 && availableSize > 0)
        } catch (e: IOException) {
            BaseLog.e(e)
            false
        }
    }

    fun saveFile(path: String, byteArray: ByteArray) {
        with(File(path)) {
            if (isDirectory) {
                if (exists()) {
                    BaseLog.i("directory is already exist : $path")
                    return
                }

                mkdirs()
            } else {
                if (exists()) delete()

                parentFile?.mkdirs()
                writeBytes(byteArray)
            }
        }
    }

    fun saveBitmap(
        path: String,
        image: Bitmap,
        format: CompressFormat = CompressFormat.JPEG,
        quality: Int = 100
    ): Boolean {
        var ret: Boolean
        File(path).parentFile?.apply {
            mkdirs()
        }
        FileOutputStream(path).use { stream ->
            ret = image.compress(format, quality, stream)
        }

        return ret
    }

    /**
     * Bitmap을 ByteArray 로 변환
     */
    private fun bitmapToByteArray(
        bitmap: Bitmap,
        format: CompressFormat = CompressFormat.JPEG
    ): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(format, 80, stream)
        return stream.toByteArray()
    }

    fun bitmapToBase64(bitmap: Bitmap, format: CompressFormat): String {
        return Base64.encodeToString(bitmapToByteArray(bitmap, format), Base64.NO_WRAP)
    }

    /**
     * Base64 문자열을 Bitmap으로 변환
     */
    fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.NO_WRAP)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    /**
     * Base64 문자열을 Bitmap으로 변환하고 흰색 배경을 투명으로 변경
     */
    fun base64ToBitmapWithTransparentBackground(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.NO_WRAP)
        val originalBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

        val width = originalBitmap.width
        val height = originalBitmap.height
        val transparentBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(transparentBitmap)
        val paint = Paint()

        canvas.drawBitmap(originalBitmap, 0f, 0f, paint)

        val pixels = IntArray(width * height)
        transparentBitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        for (i in pixels.indices) {
            if (pixels[i] == Color.WHITE) {
                pixels[i] = Color.TRANSPARENT
            }
        }

        transparentBitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return transparentBitmap
    }

    @Throws(IOException::class)
    fun readFile(path: String): ByteArray {
        return readFile(File(path))
    }

    @Throws(IOException::class)
    fun readFile(file: File): ByteArray {
        val size = file.length().toInt()
        val bytes = ByteArray(size)

        BufferedInputStream(FileInputStream(file)).use {
            it.read(bytes, 0, bytes.size)
        }

        return bytes
    }

    @Throws(IOException::class)
    fun mkdir(dstString: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            var dst = File(dstString)
            if (dst.isDirectory) {
                dst.mkdirs()
            } else {
                dst.parentFile?.mkdirs()
            }
        } else {
            var dst = Path(dstString)
            if (dst.isDirectory()) {
                dst.toFile().mkdirs()
            } else {
                dst.parent.toFile().mkdirs()
            }
        }
    }

    @Throws(IOException::class)
    fun copy(src: String, dst: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            copy(File(src), File(dst))
        } else {
            copy(Path(src), Path(dst))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(IOException::class)
    fun copy(src: Path, dst: Path) {
        mkdir(dst.toString())

        if (src.exists()) {
            Files.walk(src).forEach {
                Files.copy(it, dst.resolve(src.relativize(it)), StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }

    fun copy(src: File, dst: File) {
        mkdir(dst.toString())

        if (src.exists()) {
            src.copyRecursively(dst, true)
        }
    }

    fun delete(path: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            delete(File(path))
        } else {
            delete(Path(path))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun delete(path: Path) {
        if (path.exists()) {
            Files.walk(path).sorted(Comparator.reverseOrder()).forEach { it.deleteIfExists() }
        }
    }

    fun delete(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach { childFile ->
                delete(childFile)
            }

        }

        file.delete()
    }

    fun rename(src: String, dst: String) {
        File(dst).apply {
            if (exists()) {
                delete()
            }
        }
        File(src).renameTo(File(dst))
    }

    fun getNameWithoutExtension(fileName: String): String {
        val dotIndex = fileName.lastIndexOf(".")
        return if (dotIndex != -1) fileName.substring(0, dotIndex) else fileName
    }

    private const val DEFAULT_BUFFER_SIZE = 1024 * 4

    @Throws(IOException::class)
    fun zipFile(fileToZip: String, zipFile: String?) {
        mkdir(zipFile.toString())
        ZipOutputStream(FileOutputStream(zipFile)).use { outputStream ->
            val srcFile = File(fileToZip)
            if (srcFile.isDirectory) {
                srcFile.list { dir, fileName ->
                    addToZip("", "$fileToZip/$fileName", outputStream)
                    true
                }
            } else {
                addToZip("", fileToZip, outputStream)
            }

            true
        }
    }

    @Throws(IOException::class)
    private fun addToZip(path: String, srcFile: String, zipOutputStream: ZipOutputStream) {
        val file = File(srcFile)
        val filePath = if ("" == path) file.name else path + "/" + file.name
        if (file.isDirectory) {
            val childList = file.list()

            childList?.let {
                val folderPath = "$filePath/"
                val entry = ZipEntry(folderPath)
                zipOutputStream.putNextEntry(entry)


                for (fileName in childList) {
                    addToZip(filePath, "$srcFile/$fileName", zipOutputStream)
                }
            }
        } else {
            val entry = ZipEntry(filePath)
            zipOutputStream.putNextEntry(entry)
            val `in` = FileInputStream(srcFile)
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var len: Int
            while (`in`.read(buffer).also { len = it } != -1) {
                zipOutputStream.write(buffer, 0, len)
            }
        }
    }

    @Throws(IOException::class)
    fun unZip(zipFilePath: String, targetPath: String) {
        File(targetPath).mkdirs()

        ZipFile(zipFilePath).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                when (entry.isDirectory) {
                    true -> File(targetPath, entry.name).mkdirs()
                    false -> zip.getInputStream(entry).use { input ->
                        File(targetPath, entry.name).outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
        }
    }

    // 정상적인 MP3 파일인지 체크
    fun isMp3FileValid(filePath: String): Boolean {
        return try {
            MediaMetadataRetriever().use { retriever ->
                retriever.setDataSource(filePath)
                val durationLength =
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                Log.d("FileCheck", "MP3 파일 길이: $durationLength")
                durationLength ?: throw Exception("MP3 파일 길이 추출 실패")
                Log.d("FileCheck", "정상적인 MP3 파일입니다.: $filePath")
                true
            }
        } catch (e: Exception) {
            Log.e("FileCheck", "MP3 파일 정상 여부 확인 중 오류 발생: $filePath", e)
            false
        }
    }
}