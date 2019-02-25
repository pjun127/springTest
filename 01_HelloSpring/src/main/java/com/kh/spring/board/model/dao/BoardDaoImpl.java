package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Attachment;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	@Override
	public List<Map<String, String>> selectBoardList(int cPage, int numPerPage) {
		RowBounds rb = new RowBounds((cPage-1)*numPerPage, numPerPage);
		return sqlSession.selectList("board.boardList", null, rb);
	}

	@Override
	public int selectBoardCount() {
		
		return sqlSession.selectOne("board.selectBoardCount");
	}

	@Override
	public int insertBoard(Map<String, String> board) {
		
		return sqlSession.insert("board.insertBoard",board);
	}

	@Override
	public int insertAttach(Attachment a) {
		
		return sqlSession.insert("board.insertAttach",a);
	}

	@Override
	public Map<String, String> selectBoard(int boardNo) {
		
		return sqlSession.selectOne("board.selectBoard",boardNo);
	}

	@Override
	public List<Map<String, String>> selectAttach(int boardNo) {
		
		return sqlSession.selectList("board.selectAttach",boardNo);
	}
	
	

	
}
