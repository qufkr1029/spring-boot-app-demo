package ai.junbeom.demo.account.service;

import ai.junbeom.demo.account.domain.User;
import ai.junbeom.demo.account.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;
    void signup(UserDto userDto);
    boolean checkUserIdExists(String userId);
    boolean checkEmailExists(String email);
    User findUserByUserId(String userId);
    List<User> findAll();
}
