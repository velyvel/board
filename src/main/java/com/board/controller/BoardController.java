package com.board.controller;

import com.board.common.BoardPager;
import com.board.common.DownloadView;
import com.board.common.Util;
import com.board.dto.BoardAttachDto;
import com.board.dto.BoardCommentDto;
import com.board.dto.BoardDto;
import com.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.View;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(path = { "/board" })
public class BoardController {

    private final int PAGE_SIZE = 5; 	// 한 페이지에 표시되는 데이터 개수
    private final int PAGER_SIZE = 3;	// 한 번에 표시할 페이지 번호 개수
    private final String LINK_URL = "list"; // 페이지 번호를 클릭했을 때 이동할 페이지 경로

    @Autowired
    @Qualifier("boardService")
    private BoardService boardService;

    //리스트 보여주는 페이지
    @GetMapping(path={"/list","/"})
    public String showBoardList(@RequestParam(defaultValue = "1") int pageNo, Model model){
        //페이징 없이 구현
//        List<BoardDto> boards = boardService.findAllBoard();
//		model.addAttribute("boards", boards);
//		model.addAttribute("pageNo", pageNo);

        //페이징처리1
        HashMap<String, Object> pagingData = boardService.findBoardByPage(pageNo - 1, PAGE_SIZE);

        BoardPager pager = new BoardPager((int)pagingData.get("dataCount"), pageNo, PAGE_SIZE, PAGER_SIZE, LINK_URL);

        model.addAttribute("boards", pagingData.get("boards"));
        model.addAttribute("pager", pager);
        model.addAttribute("pageNo", pageNo);

        return "board/list";
    }

    //글쓰기 화면 보여주는 페이지
    @GetMapping(path = {"/write"})
    public String showBoardWrite(Model model){

        return "board/write";
    }
    //글쓰기(기능)
    @PostMapping(path = {"/write"})
    public String writeNotice(BoardDto board, MultipartHttpServletRequest req,@RequestParam(defaultValue = "1")int pageNo, Model model) {

        model.addAttribute("pageNo",pageNo);
        //첨부파일과 함께 글쓰기
        //attach : write.jsp파일 전달인자로 사용함.
        MultipartFile attach = req.getFile("attach");
        if (attach != null) { //내용이 있는 경우
            // 2. 데이터 처리
            ServletContext application = req.getServletContext();
            String path = application.getRealPath("/board-attachments");
            String fileName = attach.getOriginalFilename(); //파일 이름 가져오기
            if (fileName != null && fileName.length() > 0) {
                String uniqueFileName = Util.makeUniqueFileName(fileName);

                try {
                    attach.transferTo(new File(path, uniqueFileName));//파일 저장

                    // 첨부파일 정보를 객체에 저장
                    ArrayList<BoardAttachDto> attachments = new ArrayList<>(); // 첨부파일 정보를 저장하는 DTO 객체
                    BoardAttachDto attachment = new BoardAttachDto();
                    attachment.setAttachName(fileName);
                    attachment.setSavedAttachName(uniqueFileName);
                    attachments.add(attachment);
                    board.setBoardAttachments(attachments);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                boardService.writeBoard2(board);
                return "redirect:list";
            }
        }
        boardService.writeBoard(board);

        return "redirect:list";
    }

    //상세보기 보여주는 페이지
    @GetMapping(path={"/detail"})
    public String showBoardDetail(@RequestParam(defaultValue = "-1") int boardNo,@RequestParam(defaultValue = "1") int pageNo, Model model){
        BoardDto board = boardService.findBoardByBoardNo(boardNo);

        // 글 조회수 증가
        boardService.increaseBoardReadCount(boardNo);
        model.addAttribute("board", board);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("enter", "\n");
        return "board/detail";
    }
    //업로드 한 파일 다운로드 할 수 있게(상세보기에서)
    @GetMapping(path = { "/download" })
    public View download(@RequestParam(defaultValue = "-1") int attachNo, Model model) {

        if (attachNo == -1) {
            model.addAttribute("error_type", "download");
            model.addAttribute("message", "첨부파일 번호가 없습니다.");
        }

        BoardAttachDto attachment = boardService.findBoardAttachByAttachNo(attachNo);

        // View에게 전달할 데이터 저장
        model.addAttribute("attachment", attachment);

        DownloadView view = new DownloadView();

        return view;
    }

    //글 수정화면으로 이동
    @GetMapping(path = {"/update"})
    public String showBoardUpdate(@RequestParam(defaultValue = "-1") int boardNo,@RequestParam(defaultValue = "1") int pageNo, Model model){
        BoardDto board = boardService.findBoardByBoardNo(boardNo);
        model.addAttribute("board", board);
        model.addAttribute("pageNo", pageNo);
        return "board/update";
    }

    //수정한 데이터 전송하기
    @PostMapping(path = { "/update" })
    public String updateBoard(BoardDto board,@RequestParam(defaultValue = "-1") int pageNo, Model model) {

//        if (board.getBoardNo() == 0 || pageNo == -1) {
//            model.addAttribute("error_type", "edit");
//            model.addAttribute("message", "글 번호 또는 페이지 번호가 없습니다.");
//            return "board/error";
//        }
        model.addAttribute("board", board);
        model.addAttribute("pageNo", pageNo);


        boardService.modifyBoard(board);

        return "redirect:detail?boardNo=" + board.getBoardNo() + "&pageNo=" + pageNo;

    }

    //글 삭제하기 : 원본 디비 삭제하지 말고 저장, 리스트에만 사라지도록 구현(나중에 필요한 데이터 복구를 위해)
//    @PostMapping(path = {"/detail"})
//    public String deleteBoard(@RequestParam(defaultValue = "-1") int pageNo, BoardDto board, Model model){
//
//        model.addAttribute("board", board);
//        model.addAttribute("pageNo", pageNo);
//        boardService.deleteBoard(board);
//
//        return "redirect:list?pageNo="+pageNo;
//    }

    //지금은 이 메서드 사용함
    @GetMapping(path = { "/{boardNo}/delete" })
    public String deleteBoard2(@PathVariable("boardNo") int boardNo, @RequestParam(defaultValue = "-1")int pageNo, Model model) {

        boardService.deleteBoard(boardNo);

        return "redirect:/board/list?pageNo=" + pageNo;
    }

    //댓글 쓰기
    @PostMapping(path = {"/commentForm"})
    @ResponseBody
    public String writeComment(BoardCommentDto comment){
        boardService.writeComment(comment);

        return "success";
    }
    //댓글조회하기
    @GetMapping(path={"/commentList"})
    public String showComments(int boardNo, Model model){
        List<BoardCommentDto> comments = boardService.findComments(boardNo);
        model.addAttribute("comments", comments);
        return "board/commentList";
    }

//    //댓글만삭
//    @GetMapping(path = { "/{commentNo}/delete" })
//    public String deleteBoardComment(@PathVariable("commentNo") int commentNo, @RequestParam(defaultValue = "1")int boardNo,@RequestParam(defaultValue = "1")int pageNo,Model model) {
//
//        boardService.deleteComment(commentNo);
//        model.addAttribute("commentNo", commentNo);
//        model.addAttribute("boardNo", boardNo);
//        model.addAttribute("pageNo", pageNo);
//
//        return "redirect:detail?boardNo=" + boardNo;
//    }


}
