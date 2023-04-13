package com.anbtech.webffice.domain.board;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.anbtech.webffice.global.jwt.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    
    @Autowired
    BoardMapper boardMapper;
    @Autowired
    JwtTokenProvider tokenProvider;
    
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    public boolean createBoardService(BoardDTO boardDTO, String accessToken) {
        String token = tokenProvider.resolveToken(accessToken);
        String userId = tokenProvider.getUserId(token);

        BoardDTO board = BoardDTO.builder()
                        .boardWritter(userId)
                        .boardContent(boardDTO.getBoardContent())
                        .boardTitle(boardDTO.getBoardTitle()) 
                        .boardHits(1)
                        .createDate(localTime)
                        .updateDate(localTime).build();

        boardMapper.createBoard(board);
        return true;
    }

    public List<BoardDTO> readBoardLists(BoardRequestDTO boardRequestDTO) {
        return boardMapper.readBoardTitles(boardRequestDTO);
    }

    public int getBoardListLimit(BoardRequestDTO boardRequestDTO) {
        return boardMapper.getBoardListLimit(boardRequestDTO);
    }

    public BoardDTO getBoard(int boardId) {
        return boardMapper.getBoard(boardId);
    }

    public String getUserIdFromToken(String accessToken) {
        String token = tokenProvider.resolveToken(accessToken);
        String userId = tokenProvider.getUserId(token);
        return userId;
    }
}
