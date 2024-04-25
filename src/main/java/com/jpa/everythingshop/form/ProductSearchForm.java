package com.jpa.everythingshop.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter @Setter
public class ProductSearchForm {
    //maria DB용 필드 시작
    @Range(min = 0, max = 999999999)
    private Integer fromPrice;         //시작가격
    @Range(min = 0, max = 999999999)
    private Integer toPrice;           //끝가격
    private Integer userNum; //등록자번호
    private String productNm;     //상품명
}
