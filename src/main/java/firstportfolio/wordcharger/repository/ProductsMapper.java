package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductsMapper {

    String findProductNameByProductId(String productId);

    Integer findAmount(String productId);

    Integer findStock(String productId);

    void stockCountPlusByProductId(String productId, Integer count);

}
