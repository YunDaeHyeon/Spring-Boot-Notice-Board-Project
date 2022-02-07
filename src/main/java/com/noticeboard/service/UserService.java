package com.noticeboard.service;

import com.noticeboard.dto.UserDTO;
import com.noticeboard.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    Date time = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    @Autowired
    UserMapper userMapper;

    @Transactional
    public void saveUser(UserDTO userDTO){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setUserPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setUserAuth("ROLE_USER");
        userDTO.setCreatedDate(format.format(time).toString());
        userMapper.registerUser(userDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDTO userDTO = userMapper.loginAction(userId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userDTO.getUserAuth()));
        if("admin".equals(userId))
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        System.out.println("userId : "+userId+" getUserAuth : "+userDTO.getUserAuth());
        return new User(userDTO.getUserId(),userDTO.getPassword(),authorities);
    }
}
