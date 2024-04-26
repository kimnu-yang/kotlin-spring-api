package io.directional.recruitment.service

import io.directional.recruitment.common.error.ErrorCode
import io.directional.recruitment.common.exeption.ApiException
import io.directional.recruitment.db.index.IndexEntity
import io.directional.recruitment.db.index.IndexRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class IndexService {

    @Autowired
    lateinit var indexRepository: IndexRepository

    // 코드를 이용해 해당하는 지수 정보를 DB에서 조회
    fun getIndexByCode(code: String): IndexEntity {
        val response: Optional<IndexEntity> = indexRepository.findByCode(code)
        return response.orElseThrow { ApiException(ErrorCode.EMPTY_INDEX) }
    }

    // 모든 지수 정보를 DB에서 조회(name, sort 값으로 필터링)
    fun getAllIndex(name: String, sort: String): List<IndexEntity> {
        // 기본정렬은 name으로 정렬됨
        var order = Sort.by("name")
        if(sort != ""){
            // ,이후로 정렬방향이 있는지 확인
            val sortData =
                if(sort.contains(",")) sort.split(",")
                else listOf(sort)

            // 정렬에 사용할 전달받은값이 리스트에 포함되지 않으면 Exception 발생
            order = if(sortData[0] in listOf("code", "name", "changeRate","updatedAt")) {
                if(sortData.size > 1 && sortData[1].uppercase(Locale.getDefault()) == "DESC") {
                    Sort.by(Sort.Direction.DESC, sortData[0])
                } else {
                    Sort.by(sortData[0])
                }
            } else {
                throw ApiException(ErrorCode.INCORRECT_SORT_VALUE)
            }
        }

        // 전달받은 name 값이 있을경우에는 name 조건이 포함된 함수를 실행
        return if(name != ""){
            indexRepository.findByNameContaining(name, order).apply { if (isEmpty()) throw ApiException(ErrorCode.EMPTY_INDEX_LIST) }
        } else {
            indexRepository.findAll(order).apply { if (isEmpty()) throw ApiException(ErrorCode.EMPTY_INDEX_LIST) }
        }
    }
}