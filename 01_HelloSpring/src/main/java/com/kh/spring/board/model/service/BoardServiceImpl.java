package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.common.exception.BoardException;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao dao;
	
	@Override
	public List<Map<String, String>> selectBoardList(int cPage, int numPerPage) {
		
		return dao.selectBoardList(cPage, numPerPage);
	}

	@Override
	public int selectBoardCount() {
		
		return dao.selectBoardCount();
	}

	@Override
	public int insertBoard(Map<String, String> board, List<Attachment> files) {
		
		//dao에 세번 가야함 : 파일이 2개, board로 가는게 1번
		int result=0;
		int boardNo=0;
		
		try {
			result = dao.insertBoard(board);
			
			System.out.println("돌아온 값 : "+board.get("boardNo"));
			
			if(result==0) {
				throw new BoardException();
			}
			for(Attachment a : files) {
				a.setBoardNo(Integer.parseInt(board.get("boardNo")));
				result = dao.insertAttach(a);
				if(result == 0 ) {
					throw new BoardException();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	

	
}
