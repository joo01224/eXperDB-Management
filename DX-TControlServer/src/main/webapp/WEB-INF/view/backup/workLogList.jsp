<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	/**
	* @Class Name : workLogList.jsp
	* @Description : Log List 화면
	* @Modification Information
	*
	*   수정일         수정자                   수정내용
	*  ------------    -----------    ---------------------------
	*  2017.06.07     최초 생성
	*
	* author YoonJH
	* since 2017.06.07
	*
	*/
%>
<script type="text/javascript">
var tableRman = null;
var tableDump = null;
var tab = "rman";

/* ********************************************************
 * Data initialization
 ******************************************************** */
$(window.document).ready(function() {
	fn_rman_init();
	fn_dump_init();
	
	var today = new Date();
	var day_end = today.toJSON().slice(0,10);
	
	today.setDate(today.getDate() - 7);
	var day_start = today.toJSON().slice(0,10); 

	$("#wrk_strt_dtm").val(day_start);
	$("#wrk_end_dtm").val(day_end);
	
	fn_get_rman_list();
	//fn_get_dump_list();
	
	$( ".calendar" ).datepicker({
		dateFormat: 'yy-mm-dd',
		changeMonth : true,
		changeYear : true
 	});
});

/* ********************************************************
 * Rman Data Table initialization
 ******************************************************** */
function fn_rman_init(){
   	tableRman = $('#logRmanList').DataTable({	
		scrollY: "250px",
		searching : false,
		scrollX: true,
	    columns : [
		         	{ data: "rownum", className: "dt-center", defaultContent: ""}, 
		         	{ data: "wrk_nm", className: "dt-center", defaultContent: ""}, 
		         	{ data: "wrk_exp", className: "dt-center", defaultContent: ""}, 		         	
 		         	{ data: "bck_opt_cd_nm", className: "dt-center", defaultContent: ""}, 
 		         	{ data: "file_sz", className: "dt-center", defaultContent: ""}, 
		         	{ data: "bck_file_pth", className: "dt-center", defaultContent: ""}, 
 		         	{ data: "wrk_strt_dtm", className: "dt-center", defaultContent: ""}, 
 		         	{ data: "wrk_end_dtm", className: "dt-center", defaultContent: ""}, 
 		         	{ data: "wrk_dtm", className: "dt-center", defaultContent: ""}, 
	 		   		{
	 					data : "exe_rslt_cd_nm",
	 					render : function(data, type, full, meta) {
	 						var html = '';
	 						if (data == 'Success') {
	 							html += ' <img src="../images/ico_w_20.png" alt="" />';
	 						} else {
	 							html += ' <img src="../images/ico_w_19.png" alt="" />';
	 						}
	 						return html;
	 					},
	 					className : "dt-center",
	 					defaultContent : ""
	 				}
 		        ] 
	});
   	
   	tableRman.tables().header().to$().find('th:eq(0)').css('min-width', '40px');
   	tableRman.tables().header().to$().find('th:eq(1)').css('min-width', '100px');
   	tableRman.tables().header().to$().find('th:eq(2)').css('min-width', '100px');
   	tableRman.tables().header().to$().find('th:eq(3)').css('min-width', '100px');
   	tableRman.tables().header().to$().find('th:eq(4)').css('min-width', '100px');
   	tableRman.tables().header().to$().find('th:eq(5)').css('min-width', '100px');
   	tableRman.tables().header().to$().find('th:eq(6)').css('min-width', '100px');
   	tableRman.tables().header().to$().find('th:eq(7)').css('min-width', '100px');
   	tableRman.tables().header().to$().find('th:eq(8)').css('min-width', '100px');
   	tableRman.tables().header().to$().find('th:eq(9)').css('min-width', '100px');
    $(window).trigger('resize'); 
}

/* ********************************************************
 * Dump Data Table initialization
 ******************************************************** */
function fn_dump_init(){
   	tableDump = $('#logDumpList').DataTable({	
		scrollY: "250px",	
		searching : false,
		scrollX: true,
	    columns : [
		         	{ data: "rownum", className: "dt-center", defaultContent: ""}, 
		         	{ data: "wrk_nm", className: "dt-center", defaultContent: ""}, 
		         	{ data: "wrk_exp", className: "dt-center", defaultContent: ""}, 
 		         	{ data: "db_nm", className: "dt-center", defaultContent: ""}, 
 		         	{ data: "file_sz", className: "dt-center", defaultContent: ""},
 		         	{ data: "bck_file_pth", className: "dt-center", defaultContent: ""},
 		         	{ data: "wrk_strt_dtm", className: "dt-center", defaultContent: ""}, 
 		         	{ data: "wrk_end_dtm", className: "dt-center", defaultContent: ""},  		         			         	
 		         	{ data: "wrk_dtm", className: "dt-center", defaultContent: ""},
	 		   		{
	 					data : "exe_rslt_cd_nm",
	 					render : function(data, type, full, meta) {
	 						var html = '';
	 						if (data == 'Success') {
	 							html += ' <img src="../images/ico_w_20.png" alt="" />';
	 						} else {
	 							html += ' <img src="../images/ico_w_19.png" alt="" />';
	 						}
	 						return html;
	 					},
	 					className : "dt-center",
	 					defaultContent : ""
	 				}
 		        ] 
	});
   	
   	tableDump.tables().header().to$().find('th:eq(0)').css('min-width', '40px');
   	tableDump.tables().header().to$().find('th:eq(1)').css('min-width', '100px');
   	tableDump.tables().header().to$().find('th:eq(2)').css('min-width', '100px');
   	tableDump.tables().header().to$().find('th:eq(3)').css('min-width', '100px');
   	tableDump.tables().header().to$().find('th:eq(4)').css('min-width', '100px');
   	tableDump.tables().header().to$().find('th:eq(5)').css('min-width', '100px');
   	tableDump.tables().header().to$().find('th:eq(6)').css('min-width', '100px');
   	tableDump.tables().header().to$().find('th:eq(7)').css('min-width', '100px');
   	tableDump.tables().header().to$().find('th:eq(8)').css('min-width', '100px');
   	tableDump.tables().header().to$().find('th:eq(9)').css('min-width', '100px');
    $(window).trigger('resize');
}


/* ********************************************************
 * Get Rman Log List
 ******************************************************** */
function fn_get_rman_list(){

	$.ajax({
		url : "/backup/selectWorkLogList.do",
	  	data : {
	  		bck_bsn_dscd : "TC000201",
	  		bck_opt_cd : $("#bck_opt_cd").val(),
	  		wrk_strt_dtm : $("#wrk_strt_dtm").val(),
	  		wrk_end_dtm : $("#wrk_end_dtm").val(),
	  		exe_rslt_cd : $("#exe_rslt_cd").val()
	  	},
		dataType : "json",
		type : "post",
		error : function(request, status, error) {
			alert("ERROR CODE : "+ request.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ request.responseText.replace(/(<([^>]+)>)/gi, ""));
		},
		success : function(result) {
			tableRman.clear().draw();
			tableRman.rows.add(result).draw();
		}
	});
}

/* ********************************************************
 * Get Dump Log List
 ******************************************************** */
function fn_get_dump_list(){
	var db_id = $("#db_id").val();
	if(db_id == "") db_id = 0;

	$.ajax({
		url : "/backup/selectWorkLogList.do",
	  	data : {
	  		bck_bsn_dscd : "TC000202",
	  		db_id : db_id,
	  		wrk_strt_dtm : $("#wrk_strt_dtm").val(),
	  		wrk_end_dtm : $("#wrk_end_dtm").val(),
	  		exe_rslt_cd : $("#exe_rslt_cd").val()
	  	},
		dataType : "json",
		type : "post",
		error : function(request, status, error) {
			alert("ERROR CODE : "+ request.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ request.responseText.replace(/(<([^>]+)>)/gi, ""));
		},
		success : function(result) {
			tableDump.clear().draw();
			tableDump.rows.add(result).draw();
		}
	});
}

/* ********************************************************
 * Click Search Button
 ******************************************************** */
$(function() {
	$("#btnSelect").click(function() {
		var wrk_strt_dtm = $("#wrk_strt_dtm").val();
		var wrk_end_dtm = $("#wrk_end_dtm").val();

		if (wrk_strt_dtm != "" && wrk_end_dtm == "") {
			alert("종료일자를 선택해 주세요");
			return false;
		}

		if (wrk_end_dtm != "" && wrk_strt_dtm == "") {
			alert("시작일자를 선택해 주세요");
			return false;
		}

		if(tab == "rman"){
			fn_get_rman_list();
		}else{
			fn_get_dump_list();
		}
	});
});

/* ********************************************************
 * Tab Click
 ******************************************************** */
var clickDump = false;
function selectTab(intab){
	tab = intab;
	if(intab == "dump"){
		$("#tab_rman").hide();
		$("#tab_dump").show();
		$(".search_rman").hide();
		$(".search_dump").show();
		$("#logRmanListDiv").hide();
		$("#logDumpListDiv").show();
		if(clickDump == false){
			fn_get_dump_list();
			clickDump = true;
		}
	}else{
		$("#tab_rman").show();
		$("#tab_dump").hide();
		$(".search_rman").show();
		$(".search_dump").hide();
		$("#logDumpListDiv").hide();
		$("#logRmanListDiv").show();
	}
}

</script>
<!-- contents -->
<div id="contents">
	<div class="contents_wrap">
		<div class="contents_tit">
			<h4>백업이력<a href="#n"><img src="/images/ico_tit.png" class="btn_info"/></a></h4>
			<div class="infobox"> 
				<ul>
					<li>지정한 기간 동안 수행된 백업 이력을 조회합니다.</li>
				</ul>
			</div>
			<div class="location">
				<ul>
					<li class="bold">${db_svr_nm}</li>
					<li>백업관리</li>
					<li class="on">백업이력</li>
				</ul>
			</div>
		</div>
	
		<div class="contents">
			<div class="cmm_tab">
				<ul id="tab_rman">
					<li class="atv"><a href="javascript:selectTab('rman');">Rman 백업 이력</a></li>
					<li><a href="javascript:selectTab('dump');">Dump 백업 이력</a></li>
				</ul>
				<ul id="tab_dump" style="display:none">
					<li><a href="javascript:selectTab('rman');">Rman 백업 이력</a></li>
					<li class="atv"><a href="javascript:selectTab('dump');">Dump 백업 이력</a></li>
				</ul>
			</div>
			<div class="cmm_grp">
				<div class="btn_type_01">
					<span class="btn"><button id="btnSelect">조회</button></span>
				</div>
				<div class="sch_form">
				<form name="findList" id="findList" method="post">
				<input type="hidden" name="db_svr_id" id="db_svr_id" value="${db_svr_id}"/> 
					<table class="write">
						<caption>검색 조회</caption>
						<colgroup>
							<col style="width:80px;" />
							<col style="width:230px;" />
							<col style="width:100px;" />
							<col />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="t10">작업기간</th>
								<td colspan="3">
									<div class="calendar_area">
										<a href="#n" class="calendar_btn">달력열기</a>
										<input type="text" name="wrk_strt_dtm" id="wrk_strt_dtm" class="calendar" readonly/>
										<span class="wave">~</span>
										<a href="#n" class="calendar_btn">달력열기</a>
										<input type="text" name="wrk_end_dtm" id="wrk_end_dtm" class="calendar" readonly/>
									</div>
								</td>
							</tr>
							<tr style="height:35px;">
								<th scope="row" class="t9">상태</th>
								<td>
									<select name="exe_rslt_cd" id="exe_rslt_cd" class="select t5">
										<option value="">선택</option>
										<option value="TC001701">Success</option>
										<option value="TC001702">Fail</option>
									</select>
								</td>
								<th scope="row" class="t9 search_rman">백업옵션</th>
								<td class="search_rman">
									<select name="bck_opt_cd" id="bck_opt_cd" class="select t5">
										<option value="">선택</option>
										<option value="TC000301">전체백업</option>
										<option value="TC000302">증분백업</option>
										<option value="TC000303">변경로그백업</option>
									</select>
								</td>
								<th scope="row" class="t9 search_dump" style="display:none;">Database</th>
								<td class="search_dump" style="display:none;">
									<select name="db_id" id="db_id" class="select t5">
										<option value="">선택</option>
										<c:forEach var="result" items="${dbList}" varStatus="status">
										<option value="<c:out value="${result.db_id}"/>"><c:out value="${result.db_nm}"/></option>
										</c:forEach>
									</select>	
								</td>							
							</tr>
						</tbody>
					</table>
					</form>
				</div>
				<div class="overflow_area" id="logRmanListDiv">
					<table class="display" id="logRmanList" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th width="40">NO</th>
								<th width="100">WORK명</th>
								<th width="100">WORK설명</th>
								<th width="100">백업옵션</th>
								<th width="100">사이즈</th>
								<th width="100">백업파일경로</th>
								<th width="100">작업시작 시간</th>
								<th width="100">작업종료 시간</th>
								<th width="100">경과시간</th>
								<th width="100">상태</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="overflow_area" style="display:none;" id="logDumpListDiv">
					<table class="display" id="logDumpList" cellspacing="0" width="100%">
						<caption>Dump 백업관리 이력화면 리스트</caption>
						<thead>
							<tr>
								<th width="40">NO</th>
								<th width="100">WORK명</th>
								<th width="100">WORK설명</th>
								<th width="100">Database</th>
								<th width="100">사이즈</th>
								<th width="100">백업파일경로</th>								
								<th width="100">작업시작 시간</th>
								<th width="100">작업종료 시간</th>
								<th width="100">경과시간</th>
								<th width="100">상태</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div><!-- // contents -->

		
		</div><!-- // container -->
	</div>
</body>
</html>