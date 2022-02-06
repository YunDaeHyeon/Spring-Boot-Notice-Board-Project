package com.noticeboard.service;

import com.noticeboard.dto.BoardDTO;
import com.noticeboard.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    // 게시글 조회
    public List<BoardDTO> boardInquire() throws Exception{
        return boardMapper.boardInquire();
    }
}
