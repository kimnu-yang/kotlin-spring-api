package io.directional.recruitment.controller

import io.directional.recruitment.common.api.Api
import io.directional.recruitment.db.index.IndexEntity
import io.directional.recruitment.service.IndexService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class IndexApiController {

    @Autowired
    lateinit var indexService: IndexService

    // code 값을 통해 해당하는 지수 정보를 조회
    @GetMapping("/index/{code}")
    fun getIndexByCode(
        @PathVariable(name = "code")
        code: String
    ): Api<IndexEntity> {
        val response = indexService.getIndexByCode(code)
        return Api.OK(response)
    }

    // 모든 지수 정보 조회
    // (name 값으로 지수 이름 검색, sort 값으로 정렬 ,을 기준으로 ASC|DESC 구분가능)
    @GetMapping("/index")
    fun getAllIndex(
        @RequestParam(name = "name", defaultValue = "")
        name: String,
        @RequestParam(name = "sort", defaultValue = "")
        sort: String
    ): Api<List<IndexEntity>> {
        val response = indexService.getAllIndex(name, sort)
        return Api.OK(response)
    }
}