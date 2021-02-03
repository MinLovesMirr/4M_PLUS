package com.example.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("commonDAO")
@SuppressWarnings("unchecked")//unchecked : 검증되지 않은 연산자 관련 경고 억제
public class CommonDAO extends AbstractDAO {

	
	//----------------로그인처리 JIN---------------------
	//Logger log = Logger.getLogger(this.getClass());
	
	//unchecked : 검증되지 않은 연산자 관련 경고 억제
	public List<Map<String, Object>> bom(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("jin.bom", map);
	}
	public List<Map<String, Object>> bomInsertOne(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("jin.bomInsertOne", map);
	}
	public List<Map<String, Object>> bomInsertTwo(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("jin.bomInsertTwo", map);
	}
		
	public Map<String, Object> managerList(Map<String, Object> map) {
		return (Map<String, Object>)selectOne("jin.managerList", map);
	}	
	public int ging(Map<String, Object> map) {
		return (Integer)selectOne("jin.ging", map);
	}
	//----------------IMS주문리스트 MIN---------------------
	
	//IMS주문내역 페이징 -- MIN
	public int orderlistPaging(Map<String, Object> map) {
		return (Integer)selectOne("common.orderlistPaging", map);
	}

	//IMS주문내역 -- MIN
	public List<Map<String, Object>> orderlist(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("common.orderlist", map);
	}
	
	//IMS주문상세 -- MIN
	public List<Map<String, Object>> orderDetail(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("common.orderDetail", map);
	}
	
	//IMS주문정보 -MIN
	public List<Map<String, Object>> orderInsert(Map<String, Object> map) {
		update("common.orderInsert2", map);	// im_db 상태 대기로 변경
		return (List<Map<String, Object>>)selectList("common.orderInsert", map);
	}
	
	
	
	//----------------생산계획관리 MIN---------------------
	//생산계획 조회 페이징-전체글수조회 -- MIN
	public int p_paging(Map<String, Object> map) {
		return (Integer)selectOne("common.db_p_paging", map);
	}

	//생산계획 조회 페이징2 -- MIN
	public List<Map<String, Object>> db_p_view(Map<String, Object> map) {
		
		return (List<Map<String, Object>>)selectList("common.db_p_view_paging", map);
	}
	
	//과거 생산계획 조회 페이징-전체글수조회 -- MIN
	public int pp_paging(Map<String, Object> map) {
		return (Integer)selectOne("common.db_pp_paging", map);
	}
	
	//과거 생산계획 조회 페이징2 -- MIN
	public List<Map<String, Object>> db_pp_view(Map<String, Object> map) {
		
		return (List<Map<String, Object>>)selectList("common.db_pp_view_paging", map);
	}
	
	
	//생산계획 등록용 -- MIN 
	public int db_p_input(Map<String, Object> map) {
		
		int a= (Integer)insert("common.db_p_inputf", map);
		int c =(Integer)selectOne("common.db_p_intputt");
		if(c==-1) {
				update("common.db_p_delete", map);
				return c;
		}else {
			if(a>0) {
				return a;
			}else {
				update("common.db_p_delete", map);
				return 0;
			}
		}
	}
	//생산계획 LOT_NO중복체크용 -- MIN 
	public int db_p_LOTchecker(Map<String, Object> map) {
		int a = (Integer)selectOne("common.db_p_LOTchekcer", map);
		return a;
	}
	
	//생산계획 수정용 -- MIN 
	public int db_p_update(Map<String, Object> map) {
		Object todate = selectOne("common.db_p_s_d", map);
		Date S_DATE = java.sql.Date.valueOf((String)todate);
		Date today = new java.sql.Date(System.currentTimeMillis());
		
		//오늘자 날짜 넣기
		int compare=S_DATE.compareTo(today);
		if(compare > 0) {
			//비교후 시작날짜가 오늘보다 늦으면 업데이트시켜줌
			return (Integer)update("common.db_p_update", map);
		}else if(compare <0 || compare== 0) {
			//비교후 시작날짜가 오늘전이거나 오늘이면 업데이트X 알럿 
			return 0;
		}else {
			return 0;
		}
	}
	
	//생산계획 삭제용 -- MIN 
	public int db_p_delete(Map<String, Object> map) {
		Object todate = selectOne("common.db_p_s_d", map);
		Date S_DATE = java.sql.Date.valueOf((String)todate);
		Date today = new java.sql.Date(System.currentTimeMillis());
		
		//오늘자 날짜 넣기
		int compare=S_DATE.compareTo(today);
		if(compare > 0) {
			//비교후 시작날짜가 오늘보다 늦으면 삭제시켜줌
			return (Integer)update("common.db_p_delete", map);
		}else if(compare <0 || compare== 0) {
			//비교후 시작날짜가 오늘전이거나 오늘이면 삭제X 알럿 
			return 0;
		}else {
			return 0;
		}
	}

	//창고 시작 ju
	//----------------제품창고 JU 0104---------------------
	//창고 시작 ju
	//입고 전 작업번호 전체 갯수 구하기
	public  int mwhGetLotCount(Map<String, Object> map){
		return (Integer)selectOne("wh.LotAllCount", map);
	}
	//입고된 전체 모델 갯수 구하기
	public  int mwhGetPartCount(Map<String, Object> map){
		return (Integer)selectOne("wh.InPartAllCount", map);
	}
	//입고된 작업번호 전체 갯수 구하기
	public  int mwhGetInLotCount(Map<String, Object> map){
		return (Integer)selectOne("wh.InLotAllCount", map);
	}
	//작업번호 list 검색 ju
	public  List<Map<String, Object>> mwhGetLotList(){//Map<String, Object> wh) {
		return (List<Map<String, Object>>)selectLotList("wh.LotAll");//, wh);
	}
	//작업번호로 검색 ju
	public  Map<String, Object> mwhGetSeaLot(Map<String, Object> wh) {
		return (Map<String, Object>)selectSeaLot("wh.SeaLot", wh);
	}
	//창고에 물품등록 ju
	public  List<Map<String, Object>> mwhInOut(Map<String, Object> wh) {
		return (List<Map<String, Object>>)selectList("wh.InOut", wh);
	}

	//창고에 물품검색
	public  List<Map<String, Object> > mwhSeaLot(Map<String, Object> wh) {
		return (List<Map<String, Object> > )selectSeaLot("wh.SeaLot", wh);
	}
	//창고에 물품 입고 ju
	public  void mwhIn(Map<String, Object> wh) {
		insertIn("wh.In", wh);
	}
	//창고에 물품 출고 ju
	public  void mwhOut(Map<String, Object> wh) {
		insertOut("wh.Out", wh);
		update("common.orderOut", wh);
	}
	//제품 이름 검색 ju
	public  List<Map<String, Object>> mwhGetPartName(Map<String, Object> wh) {
		return (List<Map<String, Object>>)selectGetPartName("wh.getPartName", wh);
	}
	//창고에 물품 partno 검색 ju
	public  List<Map<String, Object> > mwhSeaPart(Map<String, Object> wh) {
		return (List<Map<String, Object> > )selectSeaPart("wh.SeaPart", wh);
	}
	//창고에 물품 전체검색 ju
	public  List<Map<String, Object> > mwhSeaLotAll(Map<String, Object> map){
		return (List<Map<String, Object> > )selectSeaLotAll("wh.SeaLotAll", map);//, wh);
	}
	//창고에 있는 물품 수정 검색 ju
	public  Map<String, Object> mwhCor(Map<String, Object> wh) {
		return (Map<String, Object> )selectCor("wh.Cor", wh);
	}
	//창고에 있는 물품 수정사항 등록 ju
	public void mwhUpdateCor(Map<String, Object> wh){
		updateCor("wh.UpdateCor", wh);
	}
	//창고 끝

	
	//-------------------자재창고 MIN--------------------------
	//자재창고 조회 페이징-자재수목록 조회 -- MIN
	public int m_paging(Map<String, Object> map) {
		return (Integer)selectOne("common.db_m_paging", map);
	}
	
	//자재창고 조회용 -- MIN
	public List<Map<String, Object>> db_m_view(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("common.db_m_view", map);
	}
	
	//자재창고 자재수량추가용 -- MIN
	public int db_m_update(Map<String, Object> map) {
		return (Integer)update("common.db_m_update", map);
	}
	
	//자재창고 자재추가용 -- MIN
	public int db_m_input(Map<String, Object> map) {
		return (Integer)insert("common.db_m_input", map);
	}
	
	//자재창고 제품번호 중복체크용 -- MIN 
	public int db_m_NOchecker(Map<String, Object> map) {
		int a = (Integer)selectOne("common.db_m_NOchecker", map);
		System.out.println(a);
		return a;
	}
}
