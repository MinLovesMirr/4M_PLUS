package com.example.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.dao.CommonDAO;

@Service("commonService")
public class CommonService {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonDAO")
	private CommonDAO commonDAO;
	
	//----------------로그인처리 JIN---------------------	
	public List<Map<String, Object >> bom(Map<String, Object> map){ 
		return commonDAO.bom(map);
	}
	public List<Map<String, Object >> bomInsertOne(Map<String, Object> map){ 
		return commonDAO.bomInsertOne(map);
	}
	public List<Map<String, Object >> bomInsertTwo(Map<String, Object> map){ 
		return commonDAO.bomInsertTwo(map);
	}

	public Map<String, Object > managerList(Map<String, Object> map){ 
		return commonDAO.managerList(map);
	}
	public int ging(Map<String, Object> map) {
		return commonDAO.ging(map);
	}
	
	
	//----------------IMS주문리스트 MIN---------------------
	
	//IMS주문내역 페이징 -MIN
	public int orderlistPaging(Map<String, Object> map){ 
		return commonDAO.orderlistPaging(map);
	}
	
	//IMS주문내역 -MIN
	public List<Map<String, Object >> orderlist(Map<String, Object> map){ 
		return commonDAO.orderlist(map);
	}
	
	//IMS주문상세 -MIN
	public List<Map<String, Object >> orderDetail(Map<String, Object> map){ 
		return commonDAO.orderDetail(map);
	}
	
	//IMS주문정보 -MIN
	public List<Map<String, Object >> orderInsert(Map<String, Object> map){ 
		System.out.println(map.get("request_code"));
		return commonDAO.orderInsert(map);
	}
	
	
	//----------------생산계획관리 MIN---------------------
	//생산계획 조회 페이징-전체계획수 조회 -MIN
	public int p_paging(Map<String, Object> map){ 
		return commonDAO.p_paging(map);
	}
	
	//생산계획 조회 페이징2 -MIN
	public List<Map<String, Object >> db_p_view(Map<String, Object> map){ 
		return commonDAO.db_p_view(map);
	}
	
	//과거 생산계획 조회 페이징-전체계획수 조회 -MIN
	public int pp_paging(Map<String, Object> map) {
		return commonDAO.pp_paging(map);
	}
	
	//과거 생산계획 조회 페이징2 -MIN
	public List<Map<String, Object >> db_pp_view(Map<String, Object> map){ 
		return commonDAO.db_pp_view(map);
	}
	
	//생산계획 LOT_NO중복체크용 -- MIN 
	public int db_p_LOTchecker(Map<String, Object> map,  HttpServletResponse res){
		int rs = commonDAO.db_p_LOTchecker(map);
		return rs;
	}

	//생산계획 등록용 -MIN
	public int db_p_input(Map<String, Object> map,  HttpServletResponse res){
		int rs = commonDAO.db_p_input(map);
		return rs;
	}
	
	//생산계획 수정용 -MIN
	public int db_p_update(Map<String, Object> map,  HttpServletResponse res){
		int rs = commonDAO.db_p_update(map);
		return rs;
	}
	
	//생산계획 삭제용 -MIN
	public int db_p_delete(Map<String, Object> map,  HttpServletResponse res){
		int rs = commonDAO.db_p_delete(map);
		return rs;
	}

	//----------------제품창고 JU 0104---------------------
	//창고 시작 ju
	//페이지 처리
	//작업번호 전체 갯수 구하기
	public int mwhGetLotCount(Map<String, Object> map){
		return commonDAO.mwhGetLotCount(map);
	}
	//제품번호 전체 갯수 구하기
	public int mwhGetInPartAllCount(Map<String, Object> map){
		return commonDAO.mwhGetPartCount(map);
	}
	//작업번호 전체 검색 ju
	public List<Map<String, Object>> mwhGetLotList(){//Map<String, Object> wh){
		return commonDAO.mwhGetLotList();//wh);
	}
	//작업번호로 검색 ju
	public Map<String, Object> mwhGetSeaLot(Map<String, Object> wh){
		return commonDAO.mwhGetSeaLot(wh);
	}
	//창고에 물품 입고 ju
	public void mwhIn(Map<String, Object> wh){ 
		commonDAO.mwhIn(wh);
	}
	//창고에 물품 출고 ju
	public void mwhOut(Map<String, Object> wh){ 
		commonDAO.mwhOut(wh);
	}
	//제품 이름 검색 ju
	public List<Map<String, Object> > mwhGetPartName(Map<String, Object> wh){ 
		return commonDAO.mwhGetPartName(wh);
	}
	//창고에 물품등록 ju
	public void mwhInOut(Map<String, Object> wh){ 
		commonDAO.mwhInOut(wh);
	}
	//창고에 물품검색 ju
	public List<Map<String, Object> > mwhSeaLot(Map<String, Object> wh){ 
		return commonDAO.mwhSeaPart(wh);
	}
	//창고에 있는 물품 전체 검색 ju
	public List<Map<String, Object> > mwhSeaLotAll(Map<String, Object> map){ 
		return commonDAO.mwhSeaLotAll(map);
	}
	//창고에 있는 물품 수정 검색 ju
	public Map<String, Object> mwhCor(Map<String, Object> wh){ 
		return commonDAO.mwhCor(wh);
	}
	//창고에 있는 물품 수정사항 등록 ju
	public void mwhUpdateCor(Map<String, Object> wh){ 
		commonDAO.mwhUpdateCor(wh);
	}
	//------------창고끝----------
	
	
	//-------------------자재창고 MIN--------------------------

	//자재창고 조회 페이징-자재수목록 조회 -MIN
	public int m_paging(Map<String, Object> map){ 
		return commonDAO.m_paging(map);
	}

	//자재창고 조회용 -- MIN
	public List<Map<String, Object >> db_m_view(Map<String, Object> map){ 
		return commonDAO.db_m_view(map);
	}

	//자재창고 자재수량추가용 -- MIN
	public int db_m_update(Map<String, Object> map){ 
		return (Integer)commonDAO.db_m_update(map);
	}

	//자재창고 제품번호 중복체크용 -- MIN 
	public int db_m_NOchecker(Map<String, Object> map,  HttpServletResponse res){
		int rs = commonDAO.db_m_NOchecker(map);
		System.out.println(rs);
		return rs;
	}

	//자재창고 자재추가용 -- MIN
	public int db_m_input(Map<String, Object> map){ 
		return (Integer)commonDAO.db_m_input(map);
	}
	
	
	//페이지 처리부분
}