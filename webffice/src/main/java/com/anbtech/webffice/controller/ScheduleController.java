package com.anbtech.webffice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbtech.webffice.service.ScheduleService;
import com.anbtech.webffice.vo.ScheduleVO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	
    @Autowired
    private ScheduleService scheduleService;
    
    @GetMapping("/{id}")
    public List<ScheduleVO> getSelectList(@PathVariable String id){ 
    	System.out.println(scheduleService.selectBoardList(id));
    	System.out.println("Scheduel Board List 출력 성공 " + id); 
    	return scheduleService.selectBoardList(id);
	}
    
    @GetMapping("insert")
    public void insertDemoVo(){
    	ScheduleVO vo = new ScheduleVO();
    	
        scheduleService.insert(vo);
    }
}