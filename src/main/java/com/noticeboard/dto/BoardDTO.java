package com.noticeboard.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class BoardDTO extends UserDTO{
    @Getter @Setter
    private int boardNo;
    @Getter @Setter
    private String boardTitle;
    @Getter @Setter
    private String boardContents;
    @Getter @Setter
    private String boardCreator;
    @Getter @Setter
    private String boardCreatedDate;
    @Getter @Setter
    private String boardUpdator;
    @Getter @Setter
    private String boardUpdatedDate;
    @Getter @Setter
    private String boardDeletedCheck;
    @Getter @Setter
    private int boardHit;

    @Getter @Setter
    public String userName = getUserName();

}
