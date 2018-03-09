package com.k4m.dx.tcontrol.encrypt.auditlog.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.k4m.dx.tcontrol.cmmn.serviceproxy.vo.AuditLog;
import com.k4m.dx.tcontrol.cmmn.serviceproxy.vo.AuditLogSite;
import com.k4m.dx.tcontrol.cmmn.serviceproxy.vo.BackupLog;
import com.k4m.dx.tcontrol.cmmn.serviceproxy.vo.SystemUsage;
import com.k4m.dx.tcontrol.common.service.HistoryVO;
import com.k4m.dx.tcontrol.encrypt.service.call.AuditLogServiceCall;


@Controller
public class AuditLogController {

//	String restIp = "127.0.0.1";
//	int restPort = 8443;
//	String strTocken = "hO/BggRGMbP8kfNKQADdUSasVYO1qhsZjL+p6ZQkTQI=";
	
	/**
	 * 암복호화 감사로그 화면을 보여준다
	 * 
	 * @param
	 * @return ModelAndView mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/encodeDecodeAuditLog.do")
	public ModelAndView encodeDecodeAuditLog(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			// 화면접근이력 이력 남기기
			/*CmmnUtils.saveHistory(request, historyVO);
			historyVO.setExe_dtl_cd("DX-T0055");
			accessHistoryService.insertHistory(historyVO);*/
			AuditLogServiceCall sic = new AuditLogServiceCall();
			JSONArray result = new JSONArray();
			
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");	
			
			result = sic.selectEntityAgentList(restIp, restPort, strTocken, loginId ,entityId);
			
			mv.setViewName("encrypt/auditLog/encodeDecodeAuditLog");
			mv.addObject("result",result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 암복호화 감사로그 조회한다.
	 * 
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectEncodeDecodeAuditLog.do")
	public @ResponseBody JSONObject selectEncodeDecodeAuditLog(HttpServletRequest request) {
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONObject result = new JSONObject();
		try {
			String DateTimeFrom = request.getParameter("from")+" 00:00:00.000000";
			String DateTimeTo = request.getParameter("to")+" 23:59:59.999999";
			
			String agentUid = request.getParameter("agentUid");
			
			String searchFieldName= request.getParameter("searchFieldName");
			String searchOperator = request.getParameter("searchOperator");
			String searchFieldValueString = request.getParameter("searchFieldValueString");
			String successTrueFalse = request.getParameter("successTrueFalse");
			
			AuditLogSite param = new AuditLogSite();
			param.setSearchAgentLogDateTimeFrom(DateTimeFrom); 
			param.setSearchAgentLogDateTimeTo(DateTimeTo);
			param.setAgentUid(agentUid);
			param.setSearchFieldName(searchFieldName);
			param.setSearchOperator(searchOperator);
			param.setSearchFieldValueString(searchFieldValueString);
			
			System.out.println(successTrueFalse);
			
			if(successTrueFalse.equals("true")){
				param.setSuccessTrueFalse(true);
			}else if(successTrueFalse.equals("fasle")){
				param.setSuccessTrueFalse(false);
			}
					
			param.setPageOffset(1);
			param.setPageSize(10001);
			param.setTotalCountLimit(10001);
			
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");	
			
			result = sic.selectAuditLogSiteList(restIp, restPort, strTocken, param, loginId ,entityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 관리서버 감사로그 화면을 보여준다
	 * 
	 * @param
	 * @return ModelAndView mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/managementServerAuditLog.do")
	public ModelAndView managementServerAuditLog(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONArray entityuid = new JSONArray();
		try {
			// 화면접근이력 이력 남기기
			/*CmmnUtils.saveHistory(request, historyVO);
			historyVO.setExe_dtl_cd("DX-T0055");
			accessHistoryService.insertHistory(historyVO);*/
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");	
			
			
			entityuid =sic.selectEntityList(restIp, restPort, strTocken, loginId ,entityId);
			mv.addObject("entityuid", entityuid);
			
			mv.setViewName("encrypt/auditLog/managementServerAuditLog");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 관리서버 감사로그 조회한다.
	 * 
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectManagementServerAuditLog.do")
	public @ResponseBody JSONObject selectManagementServerAuditLog(HttpServletRequest request) {
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONObject result = new JSONObject();
		try {
			String DateTimeFrom = request.getParameter("from")+" 00:00:00.000000";
			String DateTimeTo = request.getParameter("to")+" 23:59:59.999999";
			String resultcode = request.getParameter("resultcode");
			String entityuid = request.getParameter("entityuid");
			
			AuditLog param = new AuditLog();
			param.setSearchLogDateTimeFrom(DateTimeFrom); 
			param.setSearchLogDateTimeTo(DateTimeTo);
			param.setEntityUid(entityuid);
			param.setResultCode(resultcode);

			param.setPageOffset(1);
			param.setPageSize(10001);
			param.setTotalCountLimit(10001);

			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");	
			
			result = sic.selectAuditLogList(restIp, restPort, strTocken, loginId ,entityId, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 암복호화키 감사로그 화면을 보여준다
	 * 
	 * @param
	 * @return ModelAndView mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/encodeDecodeKeyAuditLog.do")
	public ModelAndView encodeDecodeKeyAuditLog(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONArray entityuid = new JSONArray();
		try {
			// 화면접근이력 이력 남기기
			/*CmmnUtils.saveHistory(request, historyVO);
			historyVO.setExe_dtl_cd("DX-T0055");
			accessHistoryService.insertHistory(historyVO);*/
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");	
			
			entityuid =sic.selectEntityList(restIp, restPort, strTocken, loginId ,entityId);
			mv.addObject("entityuid", entityuid);
			
			mv.setViewName("encrypt/auditLog/encodeDecodeKeyAuditLog");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 암복호화키 감사로그 조회한다.
	 * 
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectEncodeDecodeKeyAuditLog.do")
	public @ResponseBody JSONObject selectEncodeDecodeKeyAuditLog(HttpServletRequest request) {
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONObject result = new JSONObject();
		try {
			String DateTimeFrom = request.getParameter("from")+" 00:00:00.000000";
			String DateTimeTo = request.getParameter("to")+" 23:59:59.999999";
			String resultcode = request.getParameter("resultcode");
			String entityuid = request.getParameter("entityuid");
			
			AuditLog param = new AuditLog();
			param.setKeyAccessOnly(true);
			param.setSearchLogDateTimeFrom(DateTimeFrom); 
			param.setSearchLogDateTimeTo(DateTimeTo);
			param.setEntityUid(entityuid);
			param.setResultCode(resultcode);

			param.setPageOffset(1);
			param.setPageSize(10001);
			param.setTotalCountLimit(10001);
			
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");	
			
			result = sic.selectAuditLogListKey(restIp, restPort, strTocken, loginId, entityId, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 백업및복원 감사로그 화면을 보여준다
	 * 
	 * @param
	 * @return ModelAndView mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/backupRestoreAuditLog.do")
	public ModelAndView backupRestoreAuditLog(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONArray entityuid = new JSONArray();
		JSONArray result = new JSONArray();
		try {
			// 화면접근이력 이력 남기기
			/*CmmnUtils.saveHistory(request, historyVO);
			historyVO.setExe_dtl_cd("DX-T0055");
			accessHistoryService.insertHistory(historyVO);*/
			
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");	
			
			entityuid =sic.selectEntityList(restIp, restPort, strTocken, loginId ,entityId);

			mv.addObject("entityuid", entityuid);
			
			mv.setViewName("encrypt/auditLog/backupRestoreAuditLog");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 백업및복원 감사로그 조회한다.
	 * 
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectBackupRestoreAuditLog.do")
	public @ResponseBody JSONObject selectBackupRestoreAuditLog(HttpServletRequest request) {
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONObject result = new JSONObject();
		try {
			String DateTimeFrom = request.getParameter("from")+" 00:00:00.000000";
			String DateTimeTo = request.getParameter("to")+" 23:59:59.999999";
			String worktype = request.getParameter("worktype");
			String entityuid = request.getParameter("entityuid");
			
			BackupLog param = new BackupLog();

			param.setBackupWorkType(worktype);
			param.setSearchLogDateTimeFrom(DateTimeFrom); 
			param.setSearchLogDateTimeTo(DateTimeTo);
			param.setEntityUid(entityuid);
			
			param.setPageOffset(1);
			param.setPageSize(10001);
			param.setTotalCountLimit(10001);
			
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");	
			
			result = sic.selectBackupLogList(restIp, restPort, strTocken, loginId ,entityId, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 자원사용 감사로그 화면을 보여준다
	 * 
	 * @param
	 * @return ModelAndView mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/resourcesUseAuditLog.do")
	public ModelAndView resourcesUseAuditLog(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONArray monitoreduid = new JSONArray();
		try {
			// 화면접근이력 이력 남기기
			/*CmmnUtils.saveHistory(request, historyVO);
			historyVO.setExe_dtl_cd("DX-T0055");
			accessHistoryService.insertHistory(historyVO);*/
			
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");
			
			monitoreduid =sic.selectEntityList(restIp, restPort, strTocken, loginId ,entityId);
			mv.addObject("monitoreduid", monitoreduid);

			mv.setViewName("encrypt/auditLog/resourcesUseAuditLog");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 자원사용 감사로그 조회한다.
	 * 
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectResourcesUseAuditLog.do")
	public @ResponseBody JSONObject selectResourcesUseAuditLog(HttpServletRequest request) {
		AuditLogServiceCall sic = new AuditLogServiceCall();
		JSONObject result = new JSONObject();
		try {
			String DateTimeFrom = request.getParameter("from")+" 00:00:00.000000";
			String DateTimeTo = request.getParameter("to")+" 23:59:59.999999";
			String monitoreduid = request.getParameter("monitoreduid");
			
			SystemUsage param = new SystemUsage();

			param.setSearchDateTimeFrom(DateTimeFrom); 
			param.setSearchDateTimeTo(DateTimeTo);
			param.setMonitoredUid(monitoreduid);
			
			param.setPageOffset(1);
			param.setPageSize(10001);
			
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");
			
			result = sic.selectSystemUsageLogList(restIp, restPort, strTocken, loginId ,entityId, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 암호화 agent리스트 조회
	 * 
	 * @return resultSet
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectEncryptAgentList.do")
	public @ResponseBody JSONArray selectEncryptAgentList(HttpServletRequest request) {
			
		AuditLogServiceCall alsc= new AuditLogServiceCall();
		JSONArray result = new JSONArray();
		try {
			HttpSession session = request.getSession();
			String restIp = (String)session.getAttribute("restIp");
			int restPort = (int)session.getAttribute("restPort");
			String strTocken = (String)session.getAttribute("tockenValue");
			String loginId = (String)session.getAttribute("usr_id");
			String entityId = (String)session.getAttribute("ectityUid");
			
			result = alsc.selectEntityAgentList(restIp, restPort, strTocken, loginId ,entityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}	
}