<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%--   meta --%>
    <jsp:include page="/WEB-INF/views/modules/common-meta.jsp" />
<%--  Css Styles ,font --%>
    <jsp:include page="/WEB-INF/views/modules/common-css.jsp" />
    <meta charset="UTF-8">
    <title>글쓰기화면</title>
</head>
<body>
<div id="wrap">
    <!-- @ CONTAINER -->
    <section id="container" class="sub">
        <!-- @ CONTENTS -->
        <div id="contents">
            <form method="post" id="detail" action="detail" name="detail">
                <div class="sub-title-area">
                    <h2 class="tit">News & Info</h2>
                </div>
                <div class="write_title">
                    ${board.boardTitle}
                </div>
                <div class="write_date">
                    <span class="write_line"><strong>작성자  :</strong> ${board.writer} </span>
                    <span class="write_line"><strong>조회수  :</strong> ${board.boardCount} </span>
                    <span class="write_line">${board.boardRegDate}</span>
                    <span><em class="file_icon"></em>
                        <c:forEach var="boardAttachment" items="${ board.boardAttachments }">
                        <a href="download?attachNo=${ boardAttachment.attachNo }" style="text-decoration: none">
                                ${ boardAttachment.attachName }
                        </a>
                        </c:forEach>
                    </span>

                </div>
                <div class="con_box">
                    ${board.boardContent}
                </div>
                <input type="hidden" id="boardRegDate" name="boardRegDate" value="${board.boardRegDate}">
                <input type="hidden" id="pageNo" name="pageNo" value="${pageNo}">
                <input type="hidden" id="boardNo" name="boardNo" value="${board.boardNo}">
                <input type="hidden" id="boardTitle" name="boardTitle" value="${board.boardTitle}">
                <input type="hidden" id="boardContent" name="boardContent" value="${board.boardContent}">
                <input type="hidden" id="writer" name="writer" value="${board.writer}">
                <input type="hidden" id="boardDeleted" name="boardDeleted" value="${board.boardDeleted}">

                <div class="btn_area">
                    <%--         있던 페이지로 돌아가는 로직 추가       --%>
                    <input type="button" id="showListBtn" value="목록보기" class="btn btn-primary" >
                    <input type="button" id="editBtn" value="글 수정" class="btn btn-outline-primary" >
                    <input type="button" id="deleteBtn" value="글 삭제" class="btn btn-outline-primary" >
                </div>
            </form>

<%--     댓글 추가       --%>
            <form id="commentForm" action="commentForm" method="post">
                <input type="hidden" name="boardNo" value="${ board.boardNo }" />
                <input type="hidden" name="pageNo" value="${ pageNo }" />
                <div class="my-3 p-3 bg-white rounded shadow-sm">
                    <div class="row">
                        <div class="col-sm-10" >
                            <textarea id="commentContent" name="commentContent" value="" class="form-control" rows="3" placeholder="댓글을 입력해 주세요"></textarea>
                        </div>
                        <div class="col-sm-2" style="float: right;">
                            <input class="form-control" value="" name="commentWriter"id="commentWriter" placeholder="별명 입력하기">
                            <a id="writeComment" href="javascript:" class="btn btn-success" style="width: 100%; margin-top: 10px"> 댓글 등록 </a>
                        </div>
                    </div>
                </div>
            </form>

            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered" id="commentList" width="100%" cellspacing="0">

                    </table>
                </div>
            </div>

                <!-- @ SUB TITLE AREA -->
        </div>
        <!-- CONTENTS @ -->
    </section>
    <!-- CONTAINER @ -->
</div>

<%--scripts --%>
<jsp:include page="/WEB-INF/views/modules/common-js.jsp" />
<script type="text/javascript">


    $(function (){
        $('#showListBtn').on('click', function (event){
            location.href = 'list?pageNo=${ requestScope.pageNo }';
        });

        $('#editBtn').on('click', function (event){
            location.href='update?boardNo=${board.boardNo}&pageNo=${pageNo}';
        });

        $('#deleteBtn').on('click', function(event) {
            const ok = confirm("${ board.boardNo }번 글을 삭제할까요?");
            if (!ok) return;

            location.href = '${board.boardNo}/delete?pageNo=${pageNo}';
        });

        $('#commentList').load("commentList?boardNo=${board.boardNo}");
        $('#writeComment').on('click',function (event){
            const commentContent =  $('textarea[name = commentContent]').val();
            const writer = $('input[name = commentWriter]').val();

            if(commentContent.length==0){
                alert("댓글 내용을 입력해 주세요")
                return;
            }else if(writer.length==0){
                alert("닉네임을 입력해 주세요")
                return;
            }

            const formData = $('#commentForm').serialize();
            $.ajax({
                "url":"/board/commentForm",
                "method":"post",
                "data":formData,
                "success":function(data, status, xhr){
                    if(data == "success"){
                        alert("댓글등록 성공");
                        $('#commentList').load("commentList?boardNo=${board.boardNo}");
                        $('#commentForm textarea').val("");
                    }
                },
                "error": function (xhr, status, err){

                }
            });


    });

    });




</script>


</body>
</html>