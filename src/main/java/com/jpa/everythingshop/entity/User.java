package com.jpa.everythingshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_USER")
@Getter
@Builder //상속받는 클래스가 없으므로 SuperBuilder가 아닌 builder사용
@AllArgsConstructor
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NUM", nullable = false)
    private Integer userNum;        //사용자번호

    @Column(name = "USER_ID", nullable = false)
    private String userId;          //사용자ID

    @Column(name = "USER_PW", nullable = false)
    private String userPw;          //비밀번호

    @Column(name = "USER_NM", nullable = false)
    private String userNm;          //이름

    @Column(name = "ROLE_CD", nullable = false)
    private String roleCd;          //역할[COM1001]

    @Column(name = "HOLDING_POINT")
    private Integer holdingPoint;   //보유포인트

    @Column(name = "ACCOUNT_STATUS_CD", nullable = false)
    private String accountStatusCd; //계정상태코드[COM1002]

    @Column(name = "GRADE_CD", nullable = false)
    private String gradeCd;         //등급코드[COM1003]

    @Column(name = "REGISTER_DT", nullable = false)
    private LocalDateTime registerDt;      //등록일자

    @Column(name = "CHANGE_DT", nullable = false)
    private LocalDateTime changeDt;        //수정일자

    protected User() {}

    public User(Integer holdingPoint) {
        this.holdingPoint = holdingPoint;
    }

    public User(String gradeCd) {
        this.gradeCd = gradeCd;
    }
    public User(String userId, String userPw, String userNm) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNm = userNm;
    }

    //포인트 사용 (차감)
    public void usePoints(Integer usedPoint){
        this.holdingPoint = holdingPoint - usedPoint;
    }
    public boolean isUpdateGrade(String newGradeCd) {
        if (!this.gradeCd.equals(newGradeCd)) {
            this.gradeCd = newGradeCd;
            return true;
        }
        return false;
    }

    public void refund(Integer refundPrice) {
        this.holdingPoint += refundPrice;
    }
}
