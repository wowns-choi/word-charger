package firstportfolio.wordcharger.sevice.login;

import firstportfolio.wordcharger.repository.OrdersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowOrdersService {

    private final OrdersMapper ordersMapper;

    public void showOrders(Integer page){
        // 2. 현재 페이지
        Integer currentPage = page;

        // 3. 총 페이지 수
            Integer pageSize = 20; // 페이지당 보여질 주문 수
            // 총 orders 수 구해와야 함.
            Integer totalOrders = ordersMapper.selectAllCount(); //총 주문 행 개수

        Integer totalPages = (int)Math.ceil((double) totalOrders/pageSize); // 이게 총 페이지 수

        // 6. 몇개의 페이지를 하나의 그룹으로 묶을건지.
        Integer pageGroupSize = 9; // 9개의 페이지를 하나의 그룹으로 묶는다.

        // 4. 현재 페이지가 속한 페이지그룹의 첫번째 페이지.
        // 일단, 현재페이지가 속한 페이지그룹을 알아야 함.
        Integer currentGroup = (int) Math.ceil((double) currentPage / pageGroupSize);
        // ((페이지그룹 - 1) * pageGroupSize) + 1 이라고 하면 현재 페이지가 속한 페이지그룹의 첫번째 페이지가 되겠지.
        Integer currentGroupFirstPage = (currentGroup - 1) * pageGroupSize + 1;

        // 5. 현재 페이지 그룹의 마지막 페이지
        Integer currentGroupLastPage= Math.min(currentGroupFirstPage + pageGroupSize - 1, totalPages);

        // 1. 현재 페이지에 보여질 게시글(PostsDTO) 를 담은 List 자료구조





    }
}
