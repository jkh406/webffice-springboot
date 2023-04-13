package com.anbtech.webffice.domain.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    void createBoard(BoardDTO boardDTO);
    List<BoardDTO> readBoardTitles(BoardRequestDTO boardRequestDTO);
    int getBoardListLimit(BoardRequestDTO boardRequestDTO);
    BoardDTO getBoard(int boardId);
}
