package com.skim.core.datastore.migration

import androidx.datastore.core.DataMigration
import com.skim.core.datastore.AccessToken
import com.skim.core.datastore.copy

// AccessToken : data class가 아닌 .proto 파일로 선언
// proto 사용 이유 DataMigration 같은 마이그레이션 API 제공
// 트랜잭션 단위로 안전하게 쓰기/읽기 가능


// 언제 유용한가?
// 버전·호환성을 고려해 앞으로도 스키마가 계속 진화할 가능성이 있을 때
// 서버·다른 플랫폼과 동일한 스키마를 공유해야 할 때

object AccessTokenAddCookieMigration: DataMigration<AccessToken> {

    override suspend fun cleanUp() = Unit

    override suspend fun shouldMigrate(currentData: AccessToken) = !currentData.hasDoneMigrationAddCookie

    override suspend fun migrate(currentData: AccessToken): AccessToken {

        return currentData.copy {
//            clearAccessToken()
//            clearIsExpired()
//            clearCookie()
//            hasDoneMigrationAddCookie = true
        }
    }
}