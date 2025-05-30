package com.skim.core.model

object BaseConfig {
    var FLAVOR_SERVER = ""

    var APP_NAME = ""

    var PROV_ID = ""

    var PROV_ID_SECURITIES = ""

    var PROV_ID_BANK = ""

    var VERSION_NAME: String = ""

    var SKIP_CHECK_VERSION = true

    var SKIP_LOGIN = false

    var SKIP_AUTH_CELL_PHONE = false

    var TEST_BUTTON = false

    var CUSTOM_TEST_BUTTON = false

    var TEST_POPUP = false

    var DEBUG = false

    var DEBUG_LOG = false

    var ENCRYPT_API = false

    var WEB_PROTOCOL = ""

    var WEB_URL = ""

    var SERVER_PROTOCOL = ""

    var API_SERVER_URL = ""

    var TIMEOUT = 160L

    var ENCRYPT_KEYPAD: Boolean = true

    var MDM_URL: String = ""

    var USE_MDM: Boolean = false

    var USE_MDM_ID_CHECK: Boolean = false

    var BUSINESS_DOMAIN = BusinessDomain.ODS

    var OPTION_MASKING_RRN = 7

    var RECORD_VERSION = "1"

    var PAPERLESS_VERSION: String = "1.0"

    object OCR {
        var AUTH_CODE_PREFIX = "IDV0000"
        var AUTH_CODE_ID = "1"
        var AUTH_CODE_OVERSEAS = "1"
        var AUTH_CODE_DRIVER = "2"
        var AUTH_CODE_FOREIGN = "3"
        var AUTH_CODE_PASSPORT = "4"
        var AUTH_CODE_DOMESTIC = "5"
    }

    object Seal {
        var RECT_WIDTH_INCH = 1.574803f    // 도장 등록 원장의 도장이 찍히는 사각형의 실제 가로 사이즈를 인치로 환산. 기본 4cm
        var RECT_HEIGHT_INCH = 1.574803f   // 도장 등록 원장의 도장이 찍히는 사각형의 실제 세로 사이즈를 인치로 환산. 기본 4cm
        var FIRST_CROP_OFFSET = 20         // 인식용 사각형 제거에 사용

        var MAX_RED_RANGE1 = floatArrayOf(360f, 1f, 1f)
        var MIN_RED_RANGE1 = floatArrayOf(300f, 0.3f, 0f)

        var MAX_RED_RANGE2 = floatArrayOf(15f, 1f, 1f)
        var MIN_RED_RANGE2 = floatArrayOf(0f, 0.3f, 0f)
    }

}