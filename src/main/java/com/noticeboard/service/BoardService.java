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

    // 게시글 입력 (글 쓰기
    public void boardInsertAction(BoardDTO boardDTO) throws Exception{
        boardMapper.boardInsertAction(boardDTO);
    }

    // 게시글 읽어오기
    public BoardDTO boardReadAction(int boardNo) throws Exception{
        boardMapper.boardHitCount(boardNo);
        BoardDTO boardDTO = boardMapper.boardReadAction(boardNo);
        return boardDTO;
    }
}
