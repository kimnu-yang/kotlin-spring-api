package io.directional.recruitment.common

import org.springframework.boot.web.client.RestTemplateBuilder

object Function {
    private val restTemplateBuilder = RestTemplateBuilder().build()

    // 모든 지수 정보를 조회
    fun getAllIndexes(page: Int = 0): String? {
        val url = "https://api-recruit.directional.io/v1/indexes?size=100&page=$page"
        return restTemplateBuilder.getForObject(url, String::class.java)
    }

    // 지수코드를 이용해 해당하는 지수 정보를 조회
    fun getAllStocksByIndexCode(indexCode: String): String? {
        return restTemplateBuilder.getForObject(
            "https://api-recruit.directional.io/v1/indexes/$indexCode",
            String::class.java
        )
    }

    // 모든 주식 가격 정보를 조회
    fun getAllStockPrice(page: Int = 0): String? {
        val url = "https://api-recruit.directional.io/v1/prices?size=500&page=$page"
        return restTemplateBuilder.getForObject(url, String::class.java)
    }
}