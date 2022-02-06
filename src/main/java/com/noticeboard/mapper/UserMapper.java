package com.noticeboard.mapper;

import com.noticeboard.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 회원가입
    void registerUser(UserDTO userDTO);
    // 로그인
    UserDTO loginAction(String userId);
}
