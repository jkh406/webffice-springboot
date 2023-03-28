package com.anbtech.webffice.domain.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
	
    @Autowired
    private ScheduleMapper scheduleMapper;

    public List<ScheduleVO> selectScheduleList(String id){ 
    	return scheduleMapper.selectScheduleList(id); 
	}
    
    public void insertSchedule(ScheduleVO vo){
    	scheduleMapper.insertSchedule(vo);
	}
    
    public void deleteSchedule(String id){
    	scheduleMapper.deleteSchedule(id);
	}
}