<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="board.*" %>
<%@ page import="dto.*" %>
<jsp:useBean id="dao" class="board.BoardDao" />
<%
	int num = Integer.parseInt(request.getParameter("num"));
	BoardVo vo = dao.selectOne(num);
	pageContext.setAttribute("vo", vo);
%>
<!DOCTYPE html>
<html>
<head>
<title>글 수정</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<h3>수정하기</h3>

	<form action="edit.jsp" method="post" accept-charset="UTF-8">
		<input type="hidden" name="num" value="${vo.num}">
		<input type="text" name="title" value="${vo.title}" required><br>
		<input type="text" name="writer" value="${vo.writer}" placeholder="작성자" required disabled><br>
		<textarea rows="4" cols="20" name="content" >${vo.content}</textarea><br>
		<input type="submit" value="수정하기">
	</form>
</body>
</html>