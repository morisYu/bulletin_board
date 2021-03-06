<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="board.*" %>
<%@ page import="dto.*" %>

<jsp:useBean id="vo" class="dto.BoardVo" />
<jsp:useBean id="dao" class="board.BoardDao" />
<!-- post 방식일 경우 인코딩 설정 -->
<%
	request.setCharacterEncoding("UTF-8");
%>
<!-- form 에서 온 데이터를 request로 일일이 변수에 저장하지 않고 한 번에 가지고 와서 setter에 값 넣어줌 -->
<!-- 데이터를 가지고 오기 전에 인코딩 설정을 미리 해줘야 함 -->
<jsp:setProperty property="*" name="vo"/>

<%
	dao.insert(vo);

	// 아래와 실행결과 같음
	// response.sendRedirect(request.getContextPath() + "/list.jsp");
%>
<c:redirect url="list.jsp" />