package com.anbtech.webffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbtech.webffice.vo.ScheduleVO;
import com.anbtech.webffice.mapper.ScheduleMapper;

import java.util.List;

@Service
public class ScheduleService {
	
    @Autowired
    private ScheduleMapper scheduleMapper;

    public List<ScheduleVO> selectBoardList(String id){ 
    	return scheduleMapper.selectBoardList(id); 
	}
    
    public void insert(ScheduleVO vo){
    	scheduleMapper.insert(vo);
	}
}