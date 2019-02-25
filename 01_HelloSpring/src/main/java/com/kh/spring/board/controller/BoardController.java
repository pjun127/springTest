package com.kh.spring.board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.common.PageFactory;
import com.kh.spring.common.exception.BoardException;

@Controller
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardService service;
	
	@RequestMapping("/board/boardList")
	public ModelAndView boardList(@RequestParam(value="cPage", required=false, defaultValue="0") int cPage) {
		
		int numPerPage = 5;
		int contentCount=service.selectBoardCount();
		
		ModelAndView mv = new ModelAndView();
		
		List<Map<String,String>> list = service.selectBoardList(cPage, numPerPage);
		
		mv.addObject("pageBar",PageFactory.getPageBar(contentCount, cPage, numPerPage, "/spring/board/boardList"));
		mv.addObject("list",list);
		mv.setViewName("/board/boardList");
		
		return mv;
	}
	
	@RequestMapping("/board/boardForm")
	public String boardForm() {
		
		return "/board/boardForm";
	}
	
	@RequestMapping("/board/boardFormEnd.do")
	public String boardFormEnd(String boardTitle, String boardWriter, String boardContent,
								MultipartFile[] upFile, HttpServletRequest request) throws BoardException{
		
		String saveDir = request.getSession().getServletContext().getRealPath("/resources/upload/board");
		
		//board에 대한 값 title, comment ....
		//파일 두개~
		Map<String,String> board = new HashMap();
		
		board.put("title", boardTitle);
		board.put("writer", boardWriter);
		board.put("content", boardContent);
		
		ArrayList<Attachment> files = new ArrayList();

		for(MultipartFile f : upFile) {
			if(!f.isEmpty()) {
				//파일명을 생성(rename)
				String oriFileName = f.getOriginalFilename();
				String ext = oriFileName.substring(oriFileName.lastIndexOf("."));
				
				//rename 규칙설정
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSSS");
				
				int rdv = (int)(Math.random()*1000);
				
				String reName = sdf.format(System.currentTimeMillis())+"_"+rdv+ext;
				
				//파이을 저장해보자
				try {
					//transferTo()를 이용하여 파일 입출력을 하는 매소드이다.
					f.transferTo(new File(saveDir+"/"+reName));
				}
				//IO exception인데 Dir 폴더 못 찾았을 경우 예외 처리
				catch(IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				Attachment att = new Attachment();
				
				att.setRenamedFileName(reName);
				att.setOriginalFileName(oriFileName);
				files.add(att);
			}
			
			/*logger.info("객체 : "+f);
			logger.info("파라미터이름 : "+f.getName());
			logger.info("파일이름 : "+f.getOriginalFilename());
			logger.info("파일크기 : "+f.getSize());*/
			
		}
		
		//매개변수를 2개를 넣어서 보내지만 사실상 service에서 두개로 나눠진다 테이블이 다름!!
		int result = service.insertBoard(board,files);
		
		return "redirect:/board/boardList";
		
	}
	
	@RequestMapping("/board/boardView.do")
	public ModelAndView boardView(int boardNo) {
		
		ModelAndView mv = new ModelAndView();
		
		//Board객체로 하는 것은 따로 만들어 보기
		Map<String, String> map = service.selectBoard(boardNo);
		List<Map<String, String>> attach = service.selectAttach(boardNo);
		
		mv.addObject("board",map);
		mv.addObject("attach",attach);
		
		mv.setViewName("/board/boardView");
		
		return mv;
	}
	
	@RequestMapping("/board/filedownLoad.do")
	public void fileDownLoad(String oName, String rName,
				HttpServletRequest request,
				HttpServletResponse response
			)
	{
		BufferedInputStream bis = null;
		ServletOutputStream sos = null;
		
		String dir = request.getSession().getServletContext().getRealPath("/resources/upload/board");
		File saveFile = new File(dir+"/"+rName);
		
		try {
			FileInputStream fis = new FileInputStream(saveFile);
			bis = new BufferedInputStream(fis);
			sos = response.getOutputStream();
			
			//파일 명 처리 (인코딩 처리 되어 있으므로)  
			String resFilename = "";
			
			boolean isMSIE = request.getHeader("user-agent").indexOf("MSIE")!=-1||request.getHeader("user-agent").indexOf("Trident")!=-1;
			
			if(isMSIE) {
				resFilename=URLEncoder.encode(oName, "UTF-8");
				resFilename=resFilename.replaceAll("\\+", "%20");
			}
			else {
				resFilename = new String(oName.getBytes("UTF-8"),"ISO-8859-1");
			}
			response.setContentType("application/octet-stream;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\""+resFilename+"\"");
			
			//파일길이 설정
			response.setContentLength((int)saveFile.length());
			
			int read = 0;
			while((read=bis.read())!=-1) {
				sos.write(read);
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				sos.close();
				bis.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
