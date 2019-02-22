package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Attachment;

public interface BoardDao {

	List<Map<String,String>> selectBoardList(int cPage, int numPerPage);
	int selectBoardCount();
	int insertBoard(Map<String,String> board);
	//하나하나 분할해서 들어가니까 Attachment 씀
	int insertAttach(Attachment a);
}
