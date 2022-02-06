package com.noticeboard.dto;

import lombok.Data;

@Data
public class BoardDTO {
    private int boardNo;
    private String boardTitle;
    private String boardContents;
    private String boardCreator;
    private String boardCreatedDate;
    private String boardUpdator;
    private String boardUpdatedDate;
    private String boardDeletedCheck;
    private int boardHit;
}
