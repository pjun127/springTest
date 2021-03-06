package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.common.exception.BoardException;

public interface BoardService {
	
	List<Map<String,String>> selectBoardList(int cPage, int numPerPage);
	int selectBoardCount();
	int insertBoard(Map<String,String> board, List<Attachment> files) throws BoardException;
	Map<String, String> selectBoard(int boardNo);
	List<Map<String,String>> selectAttach(int boardNo);
}
