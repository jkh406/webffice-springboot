package com.anbtech.webffice.domain.board;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbtech.webffice.global.DTO.response.MapDataResponse;
import com.anbtech.webffice.global.DTO.response.SingleDataResponse;
import com.anbtech.webffice.global.service.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    
    @Autowired
    ResponseService responseService;
    @Autowired
    BoardService boardService;
    
    @GetMapping("/{id}")
    public List<BoardVO> getColumns(@PathVariable String id){ 
    	return boardService.getColumns(id);
	}

    @PostMapping("/createBoard")
    public ResponseEntity<SingleDataResponse<Boolean>> createBoard(
            @RequestBody BoardDTO boardDTO,
            @RequestHeader("Authorization") String accessToken) {

        boolean isEnd = boardService.createBoardService(boardDTO, accessToken);
        SingleDataResponse<Boolean> response = responseService.getSingleDataResponse(true, "게시판 생성 성공", isEnd);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/readBoardLists")
    public ResponseEntity<?> readBoardLists(@RequestBody BoardRequestDTO boardRequestDTO) {
    	System.out.print("startPage ="+ boardRequestDTO);

        List<BoardDTO> boards = boardService.readBoardLists(boardRequestDTO);
        int limit = boardService.getBoardListLimit(boardRequestDTO);
        
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("Boards", boards);
        data.put("Limit", limit);
        
        MapDataResponse<Object> response = responseService.getMapDataResponse(true, "Success", data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBoard(@PathVariable String id) {
    	boardService.deleteBoard(id);
    }
    
    @PostMapping("/updateBoard")
    public ResponseEntity<SingleDataResponse<Boolean>> updateBoard(
            @RequestBody BoardDTO boardDTO,
            @RequestHeader("Authorization") String accessToken) {

        boolean isEnd = boardService.updateBoard(boardDTO, accessToken);
        SingleDataResponse<Boolean> response = responseService.getSingleDataResponse(true, "게시판 수정 성공", isEnd);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @PostMapping("/getBoardListLimit")
//    public ResponseEntity<SingleDataResponse<Integer>> getBoardListLimit() {
//        int limit = boardService.getBoardListLimit();
//        SingleDataResponse<Integer> response = responseService.getSingleDataResponse(
//                true,
//                "게시판 최대값 불러오기 성공",
//                limit);
//
//        return ResponseEntity.ok(response);
//    }

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