<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	* @Class Name : myPage.jsp
	* @Description : myPage 화면
	* @Modification Information
	*
	*   수정일         수정자                   수정내용
	*  ------------    -----------    ---------------------------
	*  2017.06.20     최초 생성
	*
	* author 김주영 사원
	* since 2017.06.20
	*
	*/
%>
<script>
/* 패스워드변경 버튼 클릭시*/
function fn_pwdPopup() {
	window.open("/popup/pwdRegForm.do","pwdRegForm","location=no,menubar=no,resizable=yes,scrollbars=no,status=no,width=1000,height=450,top=0,left=0");
}
</script>

		<div id="contents">
			<div class="location">
				<ul>
					<li>My PAGE</li>
					<li class="on">개인정보 수정</li>
				</ul>
			</div>

			<div class="contents_wrap">
				<h4>개인정보 수정</h4>
				<div class="contents">
					<div class="cmm_grp">
						<div class="btn_type_01">
							<span class="btn"><button onclick="fn_update()">저장</button></span>
						</div>
						<table class="write2">
							<caption>개인정보 수정 저장 폼</caption>
							<colgroup>
								<col style="width: 122px;" />
								<col />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">사용자 아이디</th>
									<td>${usr_id}</td>
								</tr>
								<tr>
									<th scope="row">사용자명 (*)</th>
									<td><input type="text" class="txt" name="usr_nm" id="usr_nm" value="${usr_nm}" /></td>
								</tr>
								<tr>
									<th scope="row">패스워드 (*)</th>
									<td><a href="#n" onclick="fn_pwdPopup()"><img src="../images/ico_pwd_ud.png" alt="패스워드변경" /></a></td>
								</tr>
								<tr>
									<th scope="row">권한 구분</th>
									<td>${aut_id}</td>
								</tr>
								<tr>
									<th scope="row">소속 (*)</th>
									<td><input type="text" class="txt" name="bln_nm" id="bln_nm" value="${bln_nm}" /></td>
								</tr>
								<tr>
									<th scope="row">부서 (*)</th>
									<td><input type="text" class="txt" name="dept_nm" id="dept_nm" value="${dept_nm}" /></td>
								</tr>
								<tr>
									<th scope="row">직급 (*)</th>
									<td><input type="text" class="txt" name="pst_nm" id="pst_nm" value="${pst_nm}" /></td>
								</tr>
								<tr>
									<th scope="row">휴대폰 번호 (*)</th>
									<td><input type="text" class="txt" name="cpn" id="cpn" value="${cpn}" /></td>
								</tr>
								<tr>
									<th scope="row">담당 업무 (*)</th>
									<td><input type="text" class="txt" name="rsp_bsn_nm" id="rsp_bsn_nm" value="${rsp_bsn_nm}" /></td>
								</tr>
								<tr>
									<th scope="row">사용자 만료일</th>
									<td>${usr_expr_dt}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>