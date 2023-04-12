package com.anbtech.webffice.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private String boardWritter;
    private String boardTitle;
    private String boardContent;
    private int boardHits;
    private String createDate;
    private String updateDate;
}
