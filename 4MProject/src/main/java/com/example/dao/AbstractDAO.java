package com.example.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//원본
//SQL문 호출
public class AbstractDAO {
	protected Log log = LogFactory.getLog(AbstractDAO.class);
	@Autowired
	private SqlSessionTemplate sqlSession;

	protected void printQueryId(String queryId) {
		if (log.isDebugEnabled()) {
			log.debug("\t QueryId \t: " + queryId);
		}
	}

//창고 시작 ju 0104
	//작업번호 list 검색 ju
	public List selectLotList(String queryId) {//, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);//, params);
	}
	//작업번호로 검색 ju
	public Object selectSeaLot(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	//창고 물품 입고 ju
	public void insertIn(String queryId, Object params) {
		printQueryId(queryId);
		sqlSession.update(queryId, params);
	}
	//창고 물품 출고 ju
	public void insertOut(String queryId, Object params) {
		printQueryId(queryId);
		sqlSession.update(queryId, params);
	}
	//제품 이름 검색 ju
	public List selectGetPartName(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
	//창고 물품 partno 검색 ju
	public List selectSeaPart(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
	//창고 물품 전체검색 ju
	public List selectSeaLotAll(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);//, params);
	}
	//창고 물품 수정 검색 ju
	public Object selectCor(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	//창고 풀물 수정 입력 ju
	public void updateCor(String queryId, Object params) {
		printQueryId(queryId);
		sqlSession.update(queryId, params);
	}

	public Object insert(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}

	public Object update(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}

	public Object delete(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}

	public Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}

	public Object selectOne(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}

	
}
