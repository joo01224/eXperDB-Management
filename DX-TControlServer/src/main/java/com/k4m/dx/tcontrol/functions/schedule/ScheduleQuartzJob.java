package com.k4m.dx.tcontrol.functions.schedule;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.k4m.dx.tcontrol.admin.dbserverManager.service.DbServerVO;
import com.k4m.dx.tcontrol.cmmn.client.ClientInfoCmmn;
import com.k4m.dx.tcontrol.common.service.AgentInfoVO;
import com.k4m.dx.tcontrol.common.service.CmmnServerInfoService;
import com.k4m.dx.tcontrol.functions.schedule.service.ScheduleService;

public class ScheduleQuartzJob implements Job{

	@Autowired
	private CmmnServerInfoService cmmnServerInfoService;
	
	private ConfigurableApplicationContext context;
	
	ArrayList<String> CMD = new ArrayList<String>();
	ArrayList<String> BCK_NM = new ArrayList<String>();
	
	/**
	 * 1. 스케줄ID를 가져옴
	 * 2. 해당 스케줄ID에 해당하는 스케줄 상세정보 조회(work 정보)
	 * 3. 디비서버_ID 추출하여 접속정보 조회
	 * 4. 부가옵션 조회
	 * 5. 오브젝트 옵션
	 */
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {

		 System.out.println(">>>>>>>>>>>>>>>> Schedule Start >>>>>>>>>>>>>");

			if(context != null && context.isActive())
			{	
				context.close();
			}
			
		try{
			
			List<Map<String, Object>> resultWork = null;
			List<Map<String, Object>> resultDbconn = null;
			List<Map<String, Object>> addOption = null;
			List<Map<String, Object>> addObject = null;
			
			JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();
			
			// 1. 스케줄ID를 가져옴
			String scd_id= dataMap.getString("scd_id");
			
			System.out.println("스케줄작업명 : " +jobContext.getJobDetail().getKey().getName());
			System.out.println("스케줄ID : " +scd_id);
			
			String xml[] = {
					"egovframework/spring/context-aspect.xml",
					"egovframework/spring/context-common.xml",
					"egovframework/spring/context-datasource.xml",
					"egovframework/spring/context-mapper.xml",
					"egovframework/spring/context-properties.xml",
					"egovframework/spring/context-transaction.xml"};
			
			context = new ClassPathXmlApplicationContext(xml);
			context.getAutowireCapableBeanFactory().autowireBeanProperties(this,
					AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
			
			ScheduleService scheduleService = (ScheduleService) context.getBean("scheduleService");			
		
			
			// 2. 해당 스케줄ID에 해당하는 스케줄 상세정보 조회(work 정보)
			resultWork= scheduleService.selectExeScheduleList(scd_id);
		
			Calendar calendar = Calendar.getInstance();				
	        java.util.Date date = calendar.getTime();
	        String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
	        
	        String bck_fileNm ="";
			
	        
	        
	        //WORK 갯수만큼 루프
			for(int i =0; i<resultWork.size(); i++){
		        
		        bck_fileNm = "eXperDB_"+resultWork.get(i).get("wrk_id")+"_"+today+".dump";
				
				int db_svr_id = Integer.parseInt(resultWork.get(i).get("db_svr_id").toString());
				int wrk_id = Integer.parseInt(resultWork.get(i).get("wrk_id").toString());
				
					
				resultDbconn= scheduleService.selectDbconn(db_svr_id);
				
				// 4. 부가옵션 조회
				addOption= scheduleService.selectAddOption(wrk_id);
				
				// 5. 오브젝트옵션 조회
				addObject= scheduleService.selectAddObject(wrk_id);
				
				
				
				// 백업 내용이 DUMP 백업일경우 
				if(resultWork.get(i).get("bck_bsn_dscd").equals("TC000202")){
					String strCmd = dumpBackupMakeCmd(resultDbconn, resultWork, addOption, addObject, i, bck_fileNm);	
					BCK_NM.add(bck_fileNm);
					CMD.add(strCmd);
				// 백업 내용이 RMAN 백업일경우	
				}else{
					String rmanCmd = rmanBackupMakeCmd(resultWork, i);		
					CMD.add(rmanCmd);
				}
			}			
			System.out.println("명령어="+CMD);
			agentCall(resultWork, CMD, BCK_NM);
		}catch(Exception e){
			e.printStackTrace();
		}

	}


	/**
	 * 1. Connection Option 명령어 생성
	 *   1.1 데이터베이스명
	 *   1.2 호스트명
	 *   1.3 포트
	 *   1.4 사용자명
	 * 2. 기본옵션 명령어 생성
	 *   2.1 저장경로
	 *   2.2 파일포멧
	 *   2.3 압축률
	 *   2.4 인코딩방식
	 *   2.5 Rolename
	 * 3. 부가옵션 명령어 생성  
	 * @param addObject 
	 * @param i 
	 * @param resultDbconn2 
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unused")
	private String dumpBackupMakeCmd(List<Map<String, Object>> resultDbconn, List<Map<String, Object>> resultWork, List<Map<String, Object>> addOption, List<Map<String, Object>> addObject, int i, String bck_fileNm) {

		String strCmd = "pg_dump";
		String strLast = "";
		
		try {		

			//1. Connection Option 명령어 생성
			for(int k =0; k<resultDbconn.size(); k++){
				//1.1 연결할 데이터베이스의 이름 지정
				//strCmd += "--dbname="+resultDbconn.get(k).get("dft_db_nm");
				//1.2 호스트 이름 지정
				//strCmd += " --host="+resultDbconn.get(k).get("ipadr");
				//1.3 서버가 연결을 청취하는 TCP포트 설정
				strCmd += " --port="+resultDbconn.get(k).get("portno");
				//1.4 연결할 사용자이름
				strCmd += " --username="+resultDbconn.get(k).get("svr_spr_usr_id");	
				strCmd += " --no-password";	
			}
			
			//2. 기본옵션 명령어 생성
			
				String basicOpt = fn_basicOptCmd(resultWork, i, bck_fileNm);
				strLast +=basicOpt;
								
				String addObjCmd = fn_addOption(addOption);	
				strCmd += addObjCmd;
			
			
			if(addObject.size() != 0){
				String addObjectCmd = fn_addObject(addObject);	
				strCmd += addObjectCmd;
			}			
			
			
			strCmd += strLast;
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		return strCmd;
	}

	
	private String fn_addObject(List<Map<String, Object>> addObject) {
		
		String basicObj = null;
		
		//4. 오브젝트옵션 명령어 생성
		for(int n=0; n<addObject.size(); n++){			
			if(addObject.get(n).get("obj_nm").equals(null) || addObject.get(n).get("obj_nm").equals("")){
				basicObj+=" -n "+addObject.get(n).get("scm_nm").toString().toLowerCase();
			}else{
				basicObj+=" -t "+addObject.get(n).get("obj_nm").toString().toLowerCase();
			}
		}
		return basicObj;
	}


	//3. 부가옵션 명령어 생성
	private String fn_addOption(List<Map<String, Object>> addOption) {
		
		String basicOpt = null;
		
		for(int j =0; j<addOption.size(); j++){
			// Sections
			if(addOption.get(j).get("grp_cd").toString() != null && addOption.get(j).get("grp_cd").toString().equals("TC0006")){
				basicOpt +=" --section="+addOption.get(j).get("opt_cd_nm").toString().toLowerCase();
			}
			// Object형태
			if(addOption.get(j).get("opt_cd").toString() != null && addOption.get(j).get("opt_cd").toString().equals("TC0007")){
				// Object형태(Only data)
				if(addOption.get(j).get("opt_cd").toString().equals("TC000701")){
					basicOpt +=" --data-only";
				// Object형태(Only Schema)
				}else if (addOption.get(j).get("opt_cd").toString().equals("TC000702")){
					basicOpt +=" --schema-only";
				// Object형태(Blobs)
				}else{
					basicOpt +=" --blobs";
				}
			}
			// 저장제외항목
			if(addOption.get(j).get("grp_cd").toString() != null && addOption.get(j).get("grp_cd").toString().equals("TC0008")){
				basicOpt +=" --no-"+addOption.get(j).get("opt_cd_nm").toString().toLowerCase();
			}
			// 쿼리
			if(addOption.get(j).get("grp_cd").toString() != null && addOption.get(j).get("grp_cd").toString().equals("TC0009")){
				// 쿼리(Use Column Inserts)
				if(addOption.get(j).get("opt_cd").toString().equals("TC000901")){
					basicOpt +=" --column-insert";
				// 쿼리(Use Insert Commands)
				}else if (addOption.get(j).get("opt_cd").toString().equals("TC000902")){
					basicOpt +=" --attribute-inserts";
				// 쿼리(Include CREATE DATABASE statement)
				}else if(addOption.get(j).get("opt_cd").toString().equals("TC000903")){
					basicOpt +=" --create";
				// 쿼리(Include DROP DATABASE statement)
				}else{
					basicOpt +=" --clean";
				}
			}
			// Miscellaneous
			if(addOption.get(j).get("grp_cd").toString() != null && addOption.get(j).get("grp_cd").toString().equals("TC0010")){
				// Miscellaneous(With OID(s))
				if(addOption.get(j).get("opt_cd").toString().equals("TC001001")){
					basicOpt +=" --oids";
				// Miscellaneous(Verbose messages)
				}else if (addOption.get(j).get("opt_cd").toString().equals("TC001002")){
					basicOpt +=" --verbose";
				// Miscellaneous(Force double quote on identifiers)
				}else if(addOption.get(j).get("opt_cd").toString().equals("TC001003")){
					basicOpt +=" --quote-all-identifiers";
				// Miscellaneous(Use SET SESSION AUTHORIZATION)
				}else{
					basicOpt +=" --use-set-session-authorization";
				}
			}
		}
		return basicOpt;
	}


	private String fn_basicOptCmd(List<Map<String, Object>> resultWork, int i, String bck_fileNm) {
		String basicOpt = null;
	
		basicOpt +=" "+resultWork.get(i).get("db_nm")+"  > "+resultWork.get(i).get("save_pth")+"/"+bck_fileNm;
		
		if(resultWork.get(i).get("file_fmt_cd_nm") != null && resultWork.get(i).get("file_fmt_cd_nm") != ""){
			//2.2 파일포멧에 따른 명령어 생성
			basicOpt += " --format="+resultWork.get(i).get("file_fmt_cd_nm").toString().toLowerCase();
			//2.3 파일포멧이 tar일경우 압축률 명령어 생성
			if(resultWork.get(i).get("file_fmt_cd_nm") == "tar"){
				basicOpt += " --compress="+resultWork.get(i).get("cprt").toString().toLowerCase();
			}
		}
		
		//2.4 인코딩 방식 명령어 생성
		if(resultWork.get(i).get("incd") != null && resultWork.get(i).get("incd") != ""){
			basicOpt +=" --encoding="+resultWork.get(i).get("incd").toString().toLowerCase();
		}
		
		//2.5 rolename 명령어 생성		
		if(resultWork.get(i).get("incd") != null && !resultWork.get(i).get("usr_role_nm").equals("")){
			basicOpt +=" --role="+resultWork.get(i).get("usr_role_nm").toString().toLowerCase();
		}
		
		return basicOpt;
	}


	private String rmanBackupMakeCmd(List<Map<String, Object>> resultWork, int i) {
		String rmanCmd = "pg_rman backup";

		//데이터베이스 클러스터의 절대경로
		rmanCmd += " --pgdata="+resultWork.get(i).get("data_pth").toString();
		
		//백업 카탈로그의 절대경로
		rmanCmd += " --backup-path="+resultWork.get(i).get("bck_pth").toString();
		
		//백업모드
		if(resultWork.get(i).get("bck_opt_cd").toString().equals("TC000301")){
			rmanCmd += " --backup-mode=full";
		}else if(resultWork.get(i).get("bck_opt_cd").toString().equals("TC000302")){
			rmanCmd += " --backup-mode=incremental";
		}else{
			rmanCmd += " --backup-mode=archive";
		}
		
		rmanCmd += " -A $PGDATA/pg_xlog/archive_status/";
		
		if(resultWork.get(i).get("cps_yn").toString().equals("Y")){
			rmanCmd += " --with-serverlog";
		}
		
		if(resultWork.get(i).get("log_file_bck_yn").toString().equals("Y")){
			rmanCmd += " --compress-data";
		}
				
		rmanCmd += " --keep-data-generations="+resultWork.get(i).get("bck_mtn_ecnt");
		rmanCmd += " --keep-data-days="+resultWork.get(i).get("file_stg_dcnt");
		rmanCmd += " --keep-arclog-files="+resultWork.get(i).get("acv_file_mtncnt");
		rmanCmd += " --keep-arclog-days="+resultWork.get(i).get("acv_file_stgdt");
		rmanCmd += " --keep-srvlog-files="+resultWork.get(i).get("log_file_mtn_ecnt");
		rmanCmd += " --keep-srvlog-days="+resultWork.get(i).get("log_file_stg_dcnt");

		rmanCmd += " >> "+resultWork.get(i).get("log_file_pth")+"/"+resultWork.get(i).get("wrk_nm")+".log 2>&1";
		return rmanCmd;	
	}
	
	
	public void agentCall(List<Map<String, Object>> resultWork, ArrayList<String> CMD, ArrayList<String> BCKNM) {
		List<DbServerVO> ipResult = null;	
		int db_svr_id = Integer.parseInt(resultWork.get(0).get("db_svr_id").toString());

		try {
			
			ipResult = cmmnServerInfoService.selectAllIpadrList(db_svr_id);
			
			for(int i=0; i<ipResult.size(); i++){
				AgentInfoVO vo = new AgentInfoVO();
				vo.setIPADR(ipResult.get(i).getIpadr());
				
				AgentInfoVO agentInfo =  (AgentInfoVO) cmmnServerInfoService.selectAgentInfo(vo);
				
				String IP = ipResult.get(i).getIpadr();
				int PORT = agentInfo.getSOCKET_PORT();
				
				ClientInfoCmmn clc = new ClientInfoCmmn();
				clc.db_backup(resultWork, CMD, IP ,PORT, BCKNM);
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
