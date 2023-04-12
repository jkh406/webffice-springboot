package com.anbtech.webffice.domain.board;

import java.util.List;

import com.anbtech.webffice.global.DTO.response.SingleDataResponse;
import com.anbtech.webffice.global.service.ResponseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    
    @Autowired
    ResponseService responseService;
    @Autowired
    BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<SingleDataResponse<Boolean>> createBoard(
            @RequestBody BoardDTO boardDTO,
            @RequestHeader("Authorization") String accessToken) {

        boolean isEnd = boardService.createBoardService(boardDTO, accessToken);
        SingleDataResponse<Boolean> response = responseService.getSingleDataResponse(true, "게시판 생성 성공", isEnd);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/readBoardLists")
    public ResponseEntity<SingleDataResponse<List<BoardDTO>>> readBoardLists(
            @RequestParam int startPage,
            @RequestParam int pageNum) {
    	System.out.print("startPage ="+ startPage);
    	System.out.print("pageNum ="+ pageNum);
        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
                .startPage(startPage)
                .pageNum(pageNum)
                .build();

        List<BoardDTO> boards = boardService.readBoardLists(boardRequestDTO);

        SingleDataResponse<List<BoardDTO>> response = responseService.getSingleDataResponse(
                true,
                "게시판 조회 성공",
                boards);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/getBoardListLimit")
    public ResponseEntity<SingleDataResponse<Integer>> getBoardListLimit() {
        int limit = boardService.getBoardListLimit();
        SingleDataResponse<Integer> response = responseService.getSingleDataResponse(
                true,
                "게시판 최대값 불러오기 성공",
                limit);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/getBoard")
    public ResponseEntity<SingleDataResponse<BoardDTO>> getBoard(@RequestParam int boardId) {
        BoardDTO boardDTO = boardService.getBoard(boardId);
        SingleDataResponse<BoardDTO> response = responseService.getSingleDataResponse(
                true,
                "게시판 불러오기 성공",
                boardDTO);

        return ResponseEntity.ok(response);
    }

}