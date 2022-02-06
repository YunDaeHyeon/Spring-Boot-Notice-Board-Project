package com.noticeboard.mapper;

import com.noticeboard.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    // 게시글 불러오기
    List<BoardDTO> boardInquire() throws Exception;
}
