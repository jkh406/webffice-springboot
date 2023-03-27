package com.anbtech.webffice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.anbtech.webffice.vo.ScheduleVO;

@Mapper
public interface ScheduleMapper {
	
    List<ScheduleVO> selectScheduleList(String id);
    void insertSchedule(ScheduleVO vo);
    void deleteSchedule(String id);
    
}