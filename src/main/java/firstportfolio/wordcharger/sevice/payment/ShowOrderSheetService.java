package firstportfolio.wordcharger.sevice.payment;

import firstportfolio.wordcharger.DTO.MemberAllDataFindDTO;
import firstportfolio.wordcharger.repository.MemberMapper;
import firstportfolio.wordcharger.repository.ProductsMapper;
import firstportfolio.wordcharger.util.FindLoginedMemberIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional <- select 문 만 존재하는 서비스계층이기 때문에 쓰지 않는다.
public class ShowOrderSheetService {
    private final ProductsMapper productsMapper;
    private final MemberMapper memberMapper;

    public String showOrderSheet(String productId, HttpServletRequest request, Model model){
        Integer stock = productsMapper.findStock(productId);
        if (stock == 0) {
            model.addAttribute("outOfStockMessage", "죄송합니다. 상품이 품절되었습니다.");
            return "outOfStock";
        }

        //회원 정보 model에 담아주기
        Integer id = FindLoginedMemberIdUtil.findLoginedMember(request);
        MemberAllDataFindDTO memberTotalData = memberMapper.findMemberTotalData(String.valueOf(id));
        model.addAttribute("memberTotalData", memberTotalData);

        //사려는 상품의 <id + 이름 + 가격> 을 model에 담아주기
        String productName = productsMapper.findProductNameByProductId(productId);
        Integer amount = productsMapper.findAmount(productId);

        model.addAttribute("productId", productId);
        model.addAttribute("productName", productName);
        model.addAttribute("amount", amount);

        return "success";
    }
}
