package firstportfolio.wordcharger.sevice.payment;

import firstportfolio.wordcharger.repository.ProductsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// @Transactional 을 쓰지 않는다. select 문.
@Service
@Slf4j
@RequiredArgsConstructor
public class CheckStockService {

    private final ProductsMapper productsMapper;

    public Integer checkStock(String productId){
        //productId 로 재고량을 파악한다.
        Integer stock = productsMapper.findStock(productId);

        if (stock == 0) {
            //재고량이 없는 경우
            return 0;
        } else{
            return 1;
        }

    }

}
