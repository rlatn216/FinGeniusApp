package com.skim.core.common.util

import java.io.File
import java.io.FilenameFilter

/**
 * Path manager
 *
 * @property filesPath
 * @property cachePath
 * @property externalFilesPath
 * @property externalDownLoadsPath
 * @property externalDocumentsPath
 * @property externalCachePath
 * @constructor Create empty Path manager
 */
class PathManager(
    private val filesPath: String,
    private val cachePath: String,
    private val externalFilesPath: String?,
    private val externalDownLoadsPath: String?,
    private val externalDocumentsPath: String?,
    private val externalCachePath: String?
) {
    companion object {
        const val EXT_PDF = ".pdf"
        const val EXT_XML = ".xml"
        const val EXT_JPG = ".jpg"
        const val EXT_TIF = ".tif"
        const val EXT_PNG = ".png"
        const val EXT_MP3 = ".mp3"
        const val EXT_CFG = ".cfg"
        const val EXT_ZIP = ".zip"
        const val EXT_JSON = ".json"
    }

    object GlobalDocument {
        const val DIRS = "global_document"
    }

    private object Log {
        const val DIRS = "log"
    }

    private object Temp {
        const val DIRS = "temp"
    }

    private object TempIdCard {
        const val DIRS = "temp_idcard"
    }

    private object Docs {
        const val DIRS = "docs"
    }

    private object Result {
        const val DIRS = "result"
    }

    fun getCacheDir() = "$externalCachePath"
    fun getRootDir() = "${getCacheDir()}/fingenius"
    fun getGlobalDocumentDir() = "${getCacheDir()}/${GlobalDocument.DIRS}"
    fun getGlobalDocumentDir(dirName: String) = "${getCacheDir()}/${GlobalDocument.DIRS}/$dirName"
    fun getPaperlessMasterDir() = "${getCacheDir()}/paperless_master"
    fun getEdocIdDir() = "${getRootDir()}/edocId"
    fun getEdocIdDir(edocId: String) = "${getEdocIdDir()}/$edocId"

    /**
     * 로그
     */
    fun getLogDir() = "${getEdocIdDir()}/${Log.DIRS}"



//    fun getTempDir() = "${getCacheDir()}/${BizSave.DIRS}"

//    fun getTempDir(edocId: String) = "${getTempDir()}/$edocId"

//    fun getTempWebDataPath(edocId: String) =
//        "${getTempDir(edocId)}/${Constants.TEMP_DATA_WEB_JSON_FILE_NAME}"
//
//    fun getTempPenDir(edocId: String) = "${getTempDir(edocId)}/${Pen.DIRS}"
//
//    fun getTempDocsDir(edocId: String) = "${getTempDir(edocId)}/attach"
//
//    fun getTempZipPath(edocId: String) = "${getCacheDir()}/${BizSave.DIRS}/$edocId$EXT_ZIP"

    fun getIdCardTempDir() = "${getRootDir()}/${TempIdCard.DIRS}"

    /**
     * 결과
     */
    fun getTempRoot(edocId: String) = "${getEdocIdDir()}/$edocId/${Temp.DIRS}"
    fun getTempRoot(edocId: String, dirName: String) = "${getEdocIdDir()}/$edocId/${Temp.DIRS}/$dirName"

    fun getJSONTempFile(edocId: String, fileName: String) = "${getTempRoot(edocId)}/$fileName$EXT_JSON"

    fun getResultRoot(edocId: String) = "${getEdocIdDir()}/$edocId/${Result.DIRS}"

    fun getResultXmlDir(edocId: String, order: Int) = "${getResultRoot(edocId)}/$order"

    fun getResultImageDir(edocId: String) = "${getResultRoot(edocId)}/IMG"

    fun getResultImageDir(edocId: String, code: String) = "${getResultImageDir(edocId)}/$code"

    fun getResultDummyImageDir(edocId: String, type: String, code: String, order: Int) = "${getResultImageDir(edocId)}/${type}_${code}_$order"

    fun getResultConfig(edocId: String) =
        "${getResultRoot(edocId)}/Data$EXT_CFG"

    fun getMetaPath(edocId: String) = "${getResultRoot(edocId)}/Meta$EXT_JSON"

    fun getResultZip(edocId: String) =
        "${getEdocIdDir()}/$edocId/$edocId$EXT_ZIP"

    fun getTempZipRoot() = "${getCacheDir()}/temp_zip"
    fun getTempZipRoot(edocId: String) =
        "${getTempZipRoot()}/$edocId$EXT_ZIP"
    fun getTempRecordRoot(edocId: String) =
        "${getTempZipRoot()}/records$edocId$EXT_ZIP"

    fun getSendImageFilePath(edocId: String, code: String, index: String) = "${getResultImageDir(edocId, code)}/${code}_${index}$EXT_PNG"

    fun getInvDataPath(edocId: String) = "${getEdocIdDir()}/$edocId/InvData$EXT_CFG"

    fun getInvDataResultPath(edocId: String) = "${getEdocIdDir()}/$edocId/${Result.DIRS}/InvData$EXT_CFG"
}