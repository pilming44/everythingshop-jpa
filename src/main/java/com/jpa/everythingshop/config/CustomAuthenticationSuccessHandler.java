package com.jpa.everythingshop.config;

import com.jpa.everythingshop.auth.CustomUserDetails;
import com.jpa.everythingshop.entity.User;
import com.jpa.everythingshop.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Value("${default.login.point.amount}")
    private int loginPointAmount;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException{
        //사용자 정보
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        User user = customUserDetails.getUser();

//        //로그인 이력 insert
//        userRepository.insertLoginHistory(user);
//        //오늘 최초 로그인 확인
//        int loginCount = userRepository.selectTodayLoginCount(user);
//
//        //포인트 지급
//        if(loginCount == 1) {
//            //포인트 추가
//            userRepository.setHoldingPoint(user.getHoldingPoint()+loginPointAmount);
//            userRepository.updateHoldingPoint(user);
//
//            //포인트 이력 추가
//            PointHistory pointHistory = PointHistory.builder()
//                    .userNum(user.getUserNum())
//                    .pointChangeCd("05")
//                    .addPoint(loginPointAmount)
//                    .build();
//            userRepository.insertPointHistory(pointHistory);
//        }

        // 로그인 전의 요청 정보
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        String targetUrl = "/";  // 기본 URL
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();  // 로그인 전의 요청 URL
        }

        response.sendRedirect(targetUrl);  // 리다이렉트
    }
}
