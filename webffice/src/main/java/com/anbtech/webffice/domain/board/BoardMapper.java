package com.anbtech.webffice.domain.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anbtech.webffice.domain.schedule.ScheduleVO;

@Mapper
public interface BoardMapper {
    void createBoard(BoardDTO boardDTO);
    List<BoardDTO> readBoardTitles(BoardRequestDTO boardRequestDTO);
    int getBoardListLimit(BoardRequestDTO boardRequestDTO);
    BoardDTO getBoard(int boardId);
    List<BoardVO> getColumns(String id);
    void updateBoard(BoardDTO boardDTO);
    void deleteBoard(String id);
}
