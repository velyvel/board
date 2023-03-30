<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%--   meta --%>
    <jsp:include page="/WEB-INF/views/modules/common-meta.jsp" />
<%--  Css Styles ,font --%>
    <jsp:include page="/WEB-INF/views/modules/common-css.jsp" />
    <meta charset="UTF-8">
    <title>리스트페이지</title>
</head>
<body>
<div id="wrap">

    <%--현재날짜--%>
    <jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
    <%--날짜--%>
    <fmt:parseDate value="${boardRegDate1}" pattern="yy-MM-dd" var="startDate"></fmt:parseDate>

    <!-- @ CONTAINER -->
    <section id="container" class="sub  new">
        <!-- @ CONTENTS -->
        <div id="contents">
            <!-- @ SUB TITLE AREA -->
            <div class="sub-title-area">
                <h2 class="tit">News & Info </h2>
            </div>
            <div class="btn_area">
                <a href="write" class="btn btn btn-primary" style="color: white">글쓰기</a>
            </div>
            <!-- 페이징 처리는 5개씩 해주세요-->
            <table class="news_list">
                <caption>News 리스트</caption>
                <colgroup>
                    <col style="width: 10%">
                    <col style="width:*">
                    <col style="width: 10%">
                    <col style="width: 10%">
                    <col style="width: 5%">
                    <col style="width: 8%">
                </colgroup>
                <thead>
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">작성자</th>
                    <th scope="col">등록일</th>
                    <th scope="col">조회</th>
                    <th scope="col">첨부</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="board" items="${ boards }">
                <tr>
                    <td>${ board.boardNo }</td>
                    <td><a href='detail?boardNo=${ board.boardNo }&pageNo=${ pageNo }'>${ board.boardTitle }</a></td>
                    <td>${ board.writer }</td>
                    <td><fmt:formatDate pattern="yy-MM-dd" value="${board.boardRegDate}"/></td>
                    <td>${ board.boardCount }</td>
                    <td class="board_file"><span class="file_icon">파일다운로드</span>
                </tr>
                </c:forEach>
            </table>

            <div class="pagination">
                ${ pager }
            </div>
<%--            <div class="find_wrap">--%>
<%--                <select name="" id="">--%>
<%--                    <option value="">제목</option>--%>
<%--                    <option value="">내용</option>--%>
<%--                </select>--%>
<%--                <input type="text" name="" id=""  title="검색어 입력" placeholder="검색어 입력">--%>
<%--                <a href="#" class="btn_gray">검색</a>--%>
<%--            </div>--%>
        </div>
        <!-- CONTENTS @ -->
    </section>
    <!-- CONTAINER @ -->
</div>


<%--scripts --%>
<jsp:include page="/WEB-INF/views/modules/common-js.jsp" />
</body>
</html>