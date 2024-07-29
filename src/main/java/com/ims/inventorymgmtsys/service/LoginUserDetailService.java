package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.config.CustomUserDetails;
import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public LoginUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        User user = userRepository.selectByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not Found with userName:" + userName);
        }
//        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getEmailAddress());
//        builder.password(user.getPassword());
//        builder.roles("ROLE_USER");

        return new CustomUserDetails(user);
    }
}
