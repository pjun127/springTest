package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	//@Transactional/*(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,rollbackFor=Exception.class)*/
	public int insertBoard(Map<String, String> board, List<Attachment> files) throws BoardException {
		
		//dao에 세번 가야함 : 파일이 2개, board로 가는게 1번
		int result=0;
		int boardNo=0;
		
		//게시판과 attachment insert하는 구문 전체를 하나의 트랜잭션으로 만들어야 된다.
		//트랜잭션 매니저가 처리를 해야 되기 때문에 현 소스코드에서 exception 처리를 다 지운다!!! 있으면 트랜잭션 매니저 처리가 아님
		try {
			result = dao.insertBoard(board);
			
			System.out.println("돌아온 값 : "+board.get("boardNo"));
			
			if(result==0) {
				throw new BoardException("게시판 등록 실패");
			}
			for(Attachment a : files) {
				a.setBoardNo(Integer.parseInt(board.get("boardNo")));
				result = dao.insertAttach(a);
				if(result == 0 ) {
					throw new BoardException("파일 업로드 실패!");
				}
			}
		}catch(Exception e) {
			/*e.printStackTrace();*/
			throw e;
		}
		
		return result;
	}

	@Override
	public Map<String, String> selectBoard(int boardNo) {
		
		return dao.selectBoard(boardNo);
	}

	@Override
	public List<Map<String, String>> selectAttach(int boardNo) {
		
		return dao.selectAttach(boardNo);
	}
	
	

	
}
