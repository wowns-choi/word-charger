package firstportfolio.wordcharger.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TermsOfAgreementMapper {

    void insertTermsOfAgreement(Integer memberId, String checkboxOne, String checkboxTwo, String checkboxThree);
}
