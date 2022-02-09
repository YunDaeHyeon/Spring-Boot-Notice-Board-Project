package com.noticeboard.mapper;

import com.noticeboard.dto.BoardDTO;
import com.noticeboard.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    // 게시글 불러오기
    List<BoardDTO> boardInquire() throws Exception;

    // 게시글 입력 (글 쓰기)
    void boardInsertAction(BoardDTO boardDTO) throws Exception;

    // 게시글 불러오기
    BoardDTO boardReadAction(int boardNo) throws Exception;

    // 게시글 조회수 증가
    void boardHitCount(int boardNo) throws Exception;
}
