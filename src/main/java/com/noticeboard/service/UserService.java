package com.noticeboard.service;

import com.noticeboard.dto.UserDTO;
import com.noticeboard.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        userDTO.setUserAuth("USER");
        userDTO.setCreatedDate(format.format(time).toString());
        userMapper.registerUser(userDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDTO userDTO = userMapper.loginAction(userId);
        if(userDTO==null){
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }
        return userDTO;
    }
}
