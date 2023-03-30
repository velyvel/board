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
    <title>글 수정하기</title>
</head>
<body>
<div id="wrap">
    <!-- @ CONTAINER -->
    <section id="container" class="sub">
        <!-- @ CONTENTS -->
        <div id="contents">
            <div class="sub-title-area">
                <h2 class="tit">News & Info </h2>
            </div>
            <form id="update" action="update" method="post" enctype="multipart/form-data">
                <input type="hidden" id="pageNo" name="pageNo" value="${pageNo}">
                <input type="hidden" id="boardNo" name="boardNo" value="${board.boardNo}">
                <table class="basic_write">
                    <caption>News 입력</caption>
                    <colgroup>
                        <col style="width:150px">
                        <col style="width:*">
                    </colgroup>
                    <tr>
                        <th>제목<span class="star">*</span></th>
                        <td><input type="text" title="제목" name="boardTitle" id="boardTitle" class="add_txt"  placeholder="제목 *" value="${board.boardTitle}"></td>
                    </tr>
                    <tr>
                        <th>작성자<span class="star">*</span></th>
                        <td><input type="text" title="성명" name="writer" id="writer" class="name_txt" placeholder="성명 *" value="${board.writer}"></td>
                    </tr>

                    <tr>
                        <th>첨부파일</th>
                        <td>
                            <input type="file" title="첨부파일" name="attach" id="attach" placeholder="학과/부서" class="name_txt">
                        </td>
                    </tr>
                    <tr>
                        <th>내용 <span class="star">*</span></th>
                        <td>
                            <textarea name="boardContent" id="boardContent" cols="30" rows="10" style="width:100%">${board.boardContent}</textarea>
                        </td>
                    </tr>

                </table>
                <div class="btn_area">
                    <input id="submitBtn" type="submit" class="btn btn-outline-primary" value="작성완료" >
                    <input id="cancelBtn" type="button" class="btn btn-primary" value="목록보기">
                </div>
            </form>

        </div>
        <!-- CONTENTS @ -->
    </section>
    <!-- CONTAINER @ -->
</div>

<%--scripts --%>
<jsp:include page="/WEB-INF/views/modules/common-js.jsp" />

<script type="text/javascript">
    $(function (){
        $('#cancelBtn').on('click', function (event){
            location.href = 'list?pageNo=${ requestScope.pageNo }';
        });
        $('#submitBtn').on('click', function (event){
            event.preventDefault();
            const boardTitle = $('input[name = boardTitle]').val();
            const boardContent = $('textarea[name = boardContent]').val();
            const writer = $('input[name = writer]').val();
            if (boardTitle.length==0){
                alert("제목 빠짐")
                return;
            }else if(boardContent.length==0){
                alert("내용이 없다")
                return;
            }else if(writer.length == 0){
                alert("아이고오 작성자 빠졌슈")
                return;
            }else{
                $('#update')[0].submit();
            }
        });
    });



</script>
</body>
</html>