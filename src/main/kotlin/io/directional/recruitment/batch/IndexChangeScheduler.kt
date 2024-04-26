package io.directional.recruitment.batch

import com.google.gson.JsonParser
import io.directional.recruitment.common.Function
import io.directional.recruitment.db.index.IndexEntity
import io.directional.recruitment.db.index.IndexRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime

@Configuration
@EnableScheduling
class IndexChangeScheduler() {
    @Autowired
    lateinit var indexRepository: IndexRepository

    /**
     * 15분 단위로 Index 변동률을 DB에 업데이트한다
     */
    @Scheduled(cron = "* */15 * * * *")
    fun updateIndex(){
        // 모든 지수 정보를 조회해 리스트화
        var indexPage = 0
        var reqIndexCnt = 0
        var resIndexCnt = 0
        val indexList = mutableListOf<IndexEntity>()

        // 요청하는 Size와 Result의 수가 같은 경우 다음 페이지가 있으므로 반복문 처리
        while(reqIndexCnt == resIndexCnt){
            val indexes = JsonParser.parseString(Function.getAllIndexes(indexPage)).asJsonObject
            reqIndexCnt = indexes.get("size").asInt
            resIndexCnt = indexes.get("numberOfElements").asInt
            val indexArray = indexes.getAsJsonArray("content")
            for(index in indexArray){
                indexList.add(
                    IndexEntity(
                        code = index.asJsonObject["indexCode"].asString,
                        name = index.asJsonObject["name"].asString,
                        updatedAt = LocalDateTime.now()
                    )
                )
            }
            indexPage++
        }

        // 모든 주가 정보를 조회해 리스트화
        var stockPage = 0
        var reqStockCnt = 0
        var resStockCnt = 0
        val stockList = mutableListOf<Stock>()

        // 요청하는 Size와 Result의 수가 같은 경우 다음 페이지가 있으므로 반복문 처리
        while(reqStockCnt == resStockCnt){
            val stocks = JsonParser.parseString(Function.getAllStockPrice(stockPage)).asJsonObject
            reqStockCnt = stocks.get("size").asInt
            resStockCnt = stocks.get("numberOfElements").asInt
            val stockArray = stocks.getAsJsonArray("content")
            for(stock in stockArray){
                stockList.add(
                    Stock(
                        code = stock.asJsonObject["issueCode"].asString,
                        priceCurrent = stock.asJsonObject["close"].asLong,
                        pricePrevious = stock.asJsonObject["previousClose"].asLong,
                        listedShares = stock.asJsonObject["listedShares"].asLong,
                    )
                )
            }
            stockPage++
        }

        // 불러온 주가 정보를 기반으로 지수 변동률을 구해 지수 리스트에 등록
        for(index in indexList){
            index.code?.let {
                var totalAmountNow: Long = 0
                var totalAmountPrevious: Long = 0
                val indexStocks = JsonParser.parseString(Function.getAllStocksByIndexCode(it)).asJsonObject
                val indexStockList = indexStocks.getAsJsonArray("issueCodes")

                // 지수에 포함되는 모든 주식의 현재 주가총액과 전일 주가총액을 계산
                for(indexStock in indexStockList){
                    val stockData = stockList.filter { stock -> stock.code == indexStock.asString }
                    totalAmountNow += stockData[0].priceCurrent * stockData[0].listedShares
                    totalAmountPrevious += stockData[0].pricePrevious * stockData[0].listedShares
                }

                // 기본 변동률은 0.0으로 설정. 지수에 포함된 주식이 없을때는 0.0 으로 표시됨
                index.changeRate = 0.0
                if(totalAmountNow != 0L && totalAmountPrevious != 0L) index.changeRate = (totalAmountNow - totalAmountPrevious) / totalAmountPrevious.toDouble() * 100
            }
        }

        // 모든 지수 정보를 데이터베이스에 저장
        indexRepository.saveAll(indexList)
    }
}

data class Stock(
    var code: String,
    var priceCurrent: Long,
    var pricePrevious: Long,
    var listedShares: Long
)