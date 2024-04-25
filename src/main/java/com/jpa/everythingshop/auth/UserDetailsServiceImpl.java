package com.jpa.everythingshop.auth;

import com.jpa.everythingshop.entity.User;
import com.jpa.everythingshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * fileName : MemberDetailsServiceImpl
 * author   : pilming
 * date     : 2023-03-08
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * WebSecurityConfig의 /loginProc 주소로 들어오면
     * UserDetailService를 구현한 클래스(@Service가 붙은 클래스) 내에 loadUserByUsername 메소드가 자동으로 실행
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + userId);
        }

        return new UserDetailsImpl(user);
    }
}
