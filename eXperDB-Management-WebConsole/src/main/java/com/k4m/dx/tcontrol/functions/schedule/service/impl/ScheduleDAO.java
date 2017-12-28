package com.k4m.dx.tcontrol.functions.schedule.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.k4m.dx.tcontrol.backup.service.WorkVO;
import com.k4m.dx.tcontrol.functions.schedule.service.ScheduleDtlVO;
import com.k4m.dx.tcontrol.functions.schedule.service.ScheduleVO;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;


@Repository("ScheduleDAO")
public class ScheduleDAO extends EgovAbstractMapper{

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<WorkVO> selectWorkList(WorkVO workVO) {
		List<WorkVO> sl = null;
		sl = (List<WorkVO>) list("scheduleSql.selectWorkList", workVO);
		return sl;
	}

	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>> selectScheduleWorkList(HashMap paramvalue) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectScheduleWorkList", paramvalue);		
		return sl;
	}

	
	public int selectScd_id() {
		int scd_id  = getSqlSession().selectOne("scheduleSql.selectScd_id");
		return scd_id;	
	}
	
	
	public void insertSchedule(ScheduleVO scheduleVO) {
		insert("scheduleSql.insertSchedule",scheduleVO);
	}


	public void insertScheduleDtl(ScheduleDtlVO scheduleDtlVO) {
		insert("scheduleSql.insertScheduleDtl",scheduleDtlVO);	
	}

	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>> selectScheduleList(ScheduleVO scheduleVO) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectScheduleList", scheduleVO);		
		return sl;
	}

	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<ScheduleVO> selectInitScheduleList() {
		List<ScheduleVO> sl = null;
		sl = (List<ScheduleVO>) list("scheduleSql.selectInitScheduleList", null);		
		return sl;
	}


	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>> selectExeScheduleList(String scd_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectExeScheduleList", scd_id);		
		return sl;
	}


	public void updatePrevJobTime(HashMap<String , Object> hp) {
		update("scheduleSql.updatePrevJobTime",hp);
	}


	public void updateNxtJobTime(HashMap<String, Object> hp) {
		update("scheduleSql.updateNxtJobTime",hp);		
	}


	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>> selectDbconn(int scd_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectDbconn", scd_id);		
		return sl;
	}


	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Map<String, Object>> selectAddOption(int wrk_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectAddOption", wrk_id);		
		return sl;
	}


	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>> selectAddObject(int wrk_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectAddObject", wrk_id);		
		return sl;
	}


	public void deleteScheduleList(int scd_id) {
		delete("scheduleSql.deleteDscheduleList",scd_id);
		delete("scheduleSql.deleteMscheduleList",scd_id);
	}


	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>> selectModifyScheduleList(int scd_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectModifyScheduleList", scd_id);		
		return sl;
	}


	public void updateSchedule(ScheduleVO scheduleVO) {
		update("scheduleSql.updateSchedule",scheduleVO);	
	}


	public void deleteScheduleDtl(ScheduleDtlVO scheduleDtlVO) {
		delete("scheduleSql.deleteScheduleDtl",scheduleDtlVO);	
	}


	public void updateScheduleStatus(ScheduleVO scheduleVO) {
		update("scheduleSql.updateScheduleStatus",scheduleVO);			
	}


	public int scd_nmCheck(String scd_nm) {
		int resultSet = 0;
		resultSet = (int) getSqlSession().selectOne("scheduleSql.scd_nmCheck", scd_nm);
		return resultSet;
	}


	public List<Map<String, Object>> selectWrkScheduleList(int scd_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectWrkScheduleList", scd_id);		
		return sl;
	}


	public List<Map<String, Object>> selectWorkDivList() {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectWorkDivList", null);		
		return sl;
	}


	public List<Map<String, Object>> selectScdInfo(int scd_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectScdInfo", scd_id);		
		return sl;
	}


	public List<Map<String, Object>> selectWrkInfo(int wrk_id) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("scheduleSql.selectWrkInfo", wrk_id);		
		return sl;
	}

}