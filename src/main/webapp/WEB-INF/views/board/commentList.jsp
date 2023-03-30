<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



            <thead>
            <tr>
                <th>번호</th>
                <th>날짜</th>
                <th>작성자</th>
                <th>내용</th>
                <th>삭제버튼</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="comment" items="${comments}">
                <tr>
                    <td>${comment.commentNo}</td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${comment.commentRegDate}"/></td>
                    <td>${comment.commentWriter}</td>
                    <td>${comment.commentContent}</td>
                    <td><button id="commentDeleteBtn" class="btn btn-secondary">삭제하기</button></td>
                </tr>
            </c:forEach>
            <input type="hidden" id="commentNo" name="commentNo" value="${comment.commentNo}">
            <input type="hidden" id="pageNo" name="pageNo" value="">


<script type="text/javascript">

    $('#commentDeleteBtn').on('click', function(event) {
        const ok = confirm("${ comment.commentNo }번 댓글을 삭제할까요?");
        if (!ok) return;
        location.href='detail?boardNo=${boardNo}&pageNo=${pageNo}';
    });

</script>
