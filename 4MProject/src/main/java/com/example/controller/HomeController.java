package com.example.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.common.CommandMap;
import com.example.service.CommonService;

/**
 * Handles requests for the application home page.
 */
@Controller 
public class HomeController {
	// 저거 다 Class다 와우 org 폴더 안에 apache 폴더 안에 log4j 폴더 안에 Logger라는 class
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "commonService")
	CommonService commonService = new CommonService();

	//----------------로그인처리 JIN---------------------
	@Autowired
//	BOM2Bean vo = new BOM2Bean();
	/**
	 * Simply selects the home view to render by returning its name.
		*/
	@RequestMapping(value = { "/", "/index.do" }, method = RequestMethod.GET)
	public ModelAndView home(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		log.debug("Request Parameter : " + map);
			
		ModelAndView mv = new ModelAndView("/home");

		mv.addObject("msg", map.get("msg"));

		return mv;
		/*
		 * 1. ContextPath 후에 붙는 단어들은 다 mapping 해야한다. 2. Mapping 되는건 페이지가 아니고 함수이다. ★★★★
	     * 3. 함수는 ModelAndView 라는 객체를 통해서 페이지를 보여준다. [requestDispatcha 랑같다] 4. .jsp 페이지
		 * 안에서 데이터를 사용할 수 있게 ModelAndView에 addObject를 이옹해 데이터를 넣어준다. ★★★★ 5. addObject의
		 * key값으로 .jsp에서 접근이 가능하다. 6. Service는 service -> dao -> sql -> dao -> service
		 * 이순서로 db에서 값을 가져온다. ★★★★d
		 */
	}

	@RequestMapping(value = { "/t_include.do", "/T_INCLUDE.do" }, method = RequestMethod.POST)
	public ModelAndView tinclude(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		log.debug("Request Parameter : " + map); 
		String id = (String)map.get("id");
		HttpSession session = request.getSession();
		session.setAttribute("sessionId", id); //tester --삭제해도됩니당
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/t_include");
		Map<String, Object> rs = commonService.managerList(map);
		mv.addObject("rs", rs);
			
		if(rs == null) {
			mv.setViewName("redirect:/index.do");
			mv.addObject("msg", "로그인 실패");
		}
		else mv.setViewName("redirect:/orderListPage.do");
		return mv;
	}
		//로그아웃d
	@RequestMapping(value = { "/logout.do", "/LOGOUT.do"}, method = RequestMethod.GET)
	public ModelAndView logout(@RequestParam Map<String, Object> map) {
		log.debug("Request Parameter : " + map);
		
		ModelAndView mv = new ModelAndView("/logout");
			
		return mv;
	}
		//
	@RequestMapping(value = { "/l_include.do", "/L_INCLUDE.do"}, method = RequestMethod.GET)
	public ModelAndView l_include(@RequestParam Map<String, Object> map) {
		log.debug("Request Parameter : " + map);
			
		ModelAndView mv = new ModelAndView("/l_include");
			
		return mv;
	}

	
	//----------------IMS 주문조회 MIN---------------------
	//IMS주문조회
	@RequestMapping(value = { "/orderListPage.do" }, method = RequestMethod.GET)
	public ModelAndView orderListPage(@RequestParam(value="num", required=false, defaultValue="1") 
	int num, @RequestParam Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) throws IOException{
		log.debug("Request Parameter : " + map);
		ModelAndView mv = new ModelAndView("/orderListPage");
		
		if(IDCheck(req, res));
		//작업 총 갯수구하기
		int count = commonService.orderlistPaging(map);
		//한페이지 출력수
		int postNum=10;
		map.put("postNum", postNum);
		//하단 페이지 번호	
		int pageNum=(int)Math.ceil((double)count/postNum);
	
		//출력 게시물
		int displayPost = (num-1) * postNum;
		map.put("displayPost", displayPost);
		List<Map<String, Object>> list = commonService.orderlist(map);
		mv.addObject("list", list);
		mv.addObject("pageNum", pageNum);
	
		return mv;
	}
	
	//IMS상세주문조회
	@RequestMapping(value = { "/orderDetail.do" }, method = RequestMethod.GET)
	public ModelAndView orderDetail(@RequestParam Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) throws IOException{
		log.debug("Request Parameter : " + map);
		ModelAndView mv = new ModelAndView("/orderDetail");
		
		System.out.println(map.get("request_code"));
			List<Map<String, Object>> list = commonService.orderDetail(map);
			mv.addObject("list", list);
			
		return mv;
	}
	
	
	//----------------생산계획관리 MIN---------------------
	// 생산계획 조회, 검색용 - MIN

	@RequestMapping(value = { "/p_view.do" }, method = RequestMethod.GET)
	public ModelAndView p_paging(@RequestParam(value="num", required=false, defaultValue="1") int num,@RequestParam(value="num2", required=false, defaultValue="1") int num2, @RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse res) throws IOException{
		log.debug("Request Parameter : " + map);
		ModelAndView mv = new ModelAndView("/p_view");
		
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("sessionId"); 
		res.setContentType("text/html; charset=UTF-8");
		if(sessionId == null) {
			PrintWriter out;
			out = res.getWriter();
			out.print("<script>alert('세션정보가 만료되었나봐요! 로그인 다시해주세요!');");
			out.print("location.href='/index.do';");
			out.print("</script>");
			out.flush();			
		}else {
			//--------------------신규작업------------- 
			//작업 총 갯수구하기
			int count = commonService.p_paging(map);
			//한페이지 출력수
			int postNum=10;
			map.put("postNum", postNum);
			//하단 페이지 번호
			int pageNum=(int)Math.ceil((double)count/postNum);
		
			//출력 게시물
			int displayPost = (num-1) * postNum;
			map.put("displayPost", displayPost);
			List<Map<String, Object>> list = commonService.db_p_view(map);
			mv.addObject("list", list);
			mv.addObject("pageNum", pageNum);
			
			//--------------------완료작업------------- 
			//작업 총 갯수구하기
			int count2 = commonService.pp_paging(map);
			//한페이지 출력수
			int postNum2=10;
			map.put("postNum2", postNum2);
			//하단 페이지 번호
			int pageNum2=(int)Math.ceil((double)count2/postNum2);
			
		
			//출력 게시물
			int displayPost2 = (num2-1) * postNum2;
			map.put("displayPost2", displayPost2);
			List<Map<String, Object>> list2 = commonService.db_pp_view(map);
			mv.addObject("list2", list2);
			mv.addObject("pageNum2", pageNum2);			
			}
			return mv;
	}

	// p_insert호출용- MIN
	@RequestMapping(value = "/transferToP_view.do")
	public ModelAndView transferToP_view() {
		ModelAndView mv = new ModelAndView("/p_Insert");
		mv.addObject("list", null);
		return mv;
	}
	
	// 주문조회 페이지에서 생산계획 등록시 -> 생산계획등록 페이지로
	@RequestMapping(value = { "/orderToPInsert.do" }, method = RequestMethod.GET)
	public ModelAndView orderToInsert(@RequestParam Map<String, Object> map, 
			HttpServletRequest req,HttpServletResponse res) throws IOException{
		log.debug("Request Parameter : " + map);
		ModelAndView mv = new ModelAndView("/p_Insert");
		
		if(IDCheck(req, res));		
		List<Map<String, Object>> list = commonService.orderInsert(map);
		mv.addObject("list", list);
			
		return mv;
	}

	// 생산계획 등록용 - MIN
	@RequestMapping(value = "/p_insert.do", method = RequestMethod.GET)
	public void p_insert(@RequestParam Map<String, Object> map, HttpServletResponse res, HttpServletRequest req) throws IOException {
		log.debug("Request Parameter : " + map);
		PrintWriter out;
		res.setContentType("text/html; charset=UTF-8");
		out = res.getWriter();
		
		if(IDCheck(req, res));
		int LOT_checker = commonService.db_p_LOTchecker(map, res); // LOT_NO 중복체크용
		if(LOT_checker>0) {
			System.out.println(LOT_checker+"2");
			out.print("<script>alert('작업번호가 중복입니다!');");
			out.print("location.href='transferToP_view.do';");
		}else {
			int rs = commonService.db_p_input(map, res);
			if (rs > 0) {
				out.print("<script>alert('데이터가 삽입되었습니다.');");
				out.print("location.href='p_view.do';");
			} else if(rs==-1){
				out.print("<script>alert('자재의 양이 충분하지 않은것 같네요!');");
					out.print("location.href='transferToP_view.do';");
			} else {
				out.print("<script>alert('데이터가 제대로 삽입되지 않았어요!');");
				out.print("location.href='transferToP_view.do';");
			}
		}
		out.print("</script>");
		out.flush();
	}
		
	@RequestMapping(value =  "/transferToPU.do", method = RequestMethod.GET)
	public ModelAndView transferToPU(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		log.debug("Request Parameter : " + map);
		
		ModelAndView mv = new ModelAndView("/p_update");//파일명 대소문자 구분
		List<Map<String, Object>> list = commonService.db_p_view(map);//LOT_NO기준으로 자료찾기
		
		mv.addObject("list", list);
		return mv;
	}

	// 생산계획 수정용 - MIN
	@RequestMapping(value = "/p_update.do", method = RequestMethod.GET)
	public void p_update(@RequestParam Map<String, Object> map, HttpServletResponse res, HttpServletRequest req) throws IOException {
		log.debug("Request Parameter : " + map);
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		out = res.getWriter();
		
		if(IDCheck(req, res));
		int rs = commonService.db_p_update(map, res);
		if (rs > 0) {
				out.print("<script>alert('수정하였습니다.');");
			// 성공
		}else if(rs==-1){
			out.print("<script>alert('자재의 양이 충분하지 않은것 같네요!');");
		}else {
			out.print("<script>alert('선택하신 계획은 수정 불가능합니다!');");
		}
		out.print("location.href='p_view.do';");
		out.print("</script>");
		out.flush();
	}
	
	// 생산계획 삭제용 - MIN
	@RequestMapping(value = "/p_delete.do", method = RequestMethod.GET)
	public void p_delete(@RequestParam Map<String, Object> map, HttpServletResponse res, HttpServletRequest req) throws IOException {
		log.debug("Request Parameter : " + map);
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		out = res.getWriter();
		
		if(IDCheck(req, res));
		int rs = commonService.db_p_delete(map, res);
		if (rs > 0) {
				out.print("<script>alert('삭제하였습니다.');");
			// 성공
		} else {
				out.print("<script>alert('선택하신 계획은 삭제 불가능합니다!');");
		}
		out.print("location.href='p_view.do';");
		out.print("</script>");
		out.flush();
	}
	
	//----------------제품창고 JU---------------------
	//상품입출고창고등록	ju
	@RequestMapping(value = { "/MCHWH.do", "/mchwh.do" }, method = RequestMethod.GET)
	public ModelAndView mchwh(@RequestParam Map<String, Object> map,
			@RequestParam(value="num", required=false, defaultValue="1") int num,
			HttpServletRequest req, HttpServletResponse res )throws IOException {
		
		log.debug("Request Parameter : " + map);
		//알림 팝업을 위한 선언부분
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		out = res.getWriter();
		
		ModelAndView mv = new ModelAndView("/MCHWH");//파일명 대소문자 구분
		Map<String, Object> mwhIn = new HashMap(); //해당 작업 내역
		Map<String, Object> mwhGetNo = new HashMap(); //해당 작업 내역
		List<Map<String, Object>> nameList = commonService.mwhGetPartName(map); //네임리스트
		List<Map<String, Object>> lotList = commonService.mwhGetLotList(); //작업번호 리스트
		String lotno = (String)map.get("LOTNO"); // 작업번호 창고에서 입고시
		String lot_no = (String)map.get("LOT_NO"); // 작업번호 p_view에서 입고시
		String lno ="";
		String io = (String)map.get("IO"); // 입고
		int R_QUAN = 0;
		int iFail = 0;
		int chCount= 0; 
		
		//작업번호 총 갯수구하기
		int lotCount = commonService.mwhGetLotCount(map);
		//한페이지 출력수
		int postNum = 10;
		map.put("postNum", postNum);
		//하단 페이지 번호
		int pageNum=(int)Math.ceil((double)lotCount/postNum);
	
		//출력 게시물
		int displayPost = (num-1) * postNum;
		map.put("displayPost", displayPost);
		mv.addObject("pageNum", pageNum);
		
		if(IDCheck(req, res));
			
		String tmp = (String)map.get("FAIL"); // 불량 수량
		String tmp2 = (String)map.get("QUAN"); // to
		int quan = 0;
		
		if(tmp != null) {
			iFail = Integer.parseInt( tmp );
			chCount++;
		}
		if(tmp2 != null)
			quan = Integer.parseInt(tmp2);//생산 수량
		
		if(lotno != null && lotno.length() > 0) { // 입고버튼으로 입력시 작업번호가 있으면
			chCount++;
			for(int i = 0; i < lotList.size(); i++) {
				if(lotno.equalsIgnoreCase((String)lotList.get(i).get("LOT_NO"))) {
					lno = lotno; 
					mwhGetNo.put("LOT_NO", lotno);
					mwhIn = commonService.mwhGetSeaLot(mwhGetNo);
					mv.addObject("mwhIn", mwhIn);
				}
			}
		}
		
		if(lot_no != null && lot_no.length() > 0) { //p_view에서 입고시
			chCount++;
			for(int i = 0; i < lotList.size(); i++) {
				if(lot_no.equalsIgnoreCase((String)lotList.get(i).get("LOT_NO") ) ) {
					lno = lot_no;
					mwhGetNo.put("LOT_NO", lot_no);
					mwhIn = commonService.mwhGetSeaLot(mwhGetNo);
					mv.addObject("mwhIn", mwhIn);
				}
			}
		}

		if(chCount == 2) {
			if(quan >= iFail){//io != null && io.length() > 0) {
				//(Integer)map.get("QUAN");//
				R_QUAN = quan - iFail;
				map.put("R_QUAN", R_QUAN); //실 생산 수량
				map.put("IO", "O" ); //입고처리
				commonService.mwhIn(map);
				out.print("<script>location.href='MCHWH.do'</script>");
				out.flush();
			}else {
				out.print("<script>alert('불량 수량이 입력수량 보다 큽니다.')");
				out.print("location.href='MCHWH.do?LOT_NO=" + lno + "}';"); 
				out.print("</script>"); 
				out.flush();
			}
		}
		

		mv.addObject("partName", nameList); //제품이름 리스트
		mv.addObject("lotList", lotList); //작업전체 리스트
		
		return mv;
	}//상품 입고 창고 등록	
	
	//상품 입고 수정 ju
	@RequestMapping(value = { "/MCHWHCOR.do", "/mchwhcor.do" }, method = RequestMethod.GET)
	public ModelAndView mchwhcor(@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.debug("Request Parameter : " + map);
		
		//알림 팝업을 위한 선언부분
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		out = res.getWriter();
				
		ModelAndView mv = null;
		Map<String, Object> cor = new HashMap<String, Object>();
		List<Map<String, Object>> nameList = commonService.mwhGetPartName(map);
		String LOT_NO = (String)map.get("LOT_NO");
		String quan = (String)map.get("QUAN"); //수정사항 있는지 체크
		String fail = (String)map.get("FAIL");
		
		if(IDCheck(req, res) );
		
		mv = new ModelAndView("/MCHWHCOR");//수정화면
		
		if(quan != null && quan.length() > 0) {//수정사항이 있으면
			int rqual = Integer.parseInt(quan) - Integer.parseInt(fail);//실 생산 수량
			if(rqual < 0) {
				out.print("<script>alert('불량 수량이 입력수량 보다 큽니다.')");
				out.print("location.href='MCHWHCOR.do?LOT_NO=" + LOT_NO + "}';"); 
				out.print("</script>"); 
				out.flush();
			}else {
				map.replace("R_QUAN", rqual);//map data 수정
				commonService.mwhUpdateCor(map);
			}
				
				cor = commonService.mwhCor(map);
				mv.addObject("list", cor);
		}
		
		if(LOT_NO != null && LOT_NO.length() > 0) {
			cor = commonService.mwhCor(map);
			mv.addObject("list", cor);
		}
		
		mv.addObject("partName", nameList); //제품이름 리스트
		
		return mv;
	}//상품 입고 수정
	
	//상품 입출고 검색 ju
	@RequestMapping(value = { "/MCHWHSEA.do", "/mchwhsea.do" }, method = RequestMethod.GET)
	public ModelAndView mchwhsea(@RequestParam Map<String, Object> map,
			@RequestParam(value="num", required=false, defaultValue="1") int num,
			HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		log.debug("Request Parameter : " + map);
				
		List<Map<String, Object>> list = new ArrayList();
		List<Map<String, Object>> nameCountList = new ArrayList();//제품별 수량
		List<Map<String, Object>> nameList = commonService.mwhGetPartName(map);
		
		ModelAndView mv = new ModelAndView("/MCHWHSEA");//검색창
		
		if(IDCheck(req, res) );
		
		list = commonService.mwhSeaLotAll(map);

		//작업번호 총 갯수구하기
		int inPartCount = commonService.mwhGetInPartAllCount(map);
		//한페이지 출력수
		int postInNum = 10;
		map.put("postInNum", postInNum);
		//하단 페이지 번호
		int pageInNum=(int)Math.ceil((double)inPartCount/postInNum);
	
		//출력 게시물
		int displayInPost = (num-1) * postInNum;
		map.put("displayInPost", displayInPost);
		mv.addObject("pageInNum", pageInNum);
		
		for(int i = 0; i < nameList.size(); i++) {
			int iTemp = 0;
			Map<String, Object> partCount = new HashMap<String, Object>(); //추가용
			Map<String, Object> mTemp = nameList.get(i);
			String sTemp = "";
			sTemp = (String)mTemp.get("PART_NO");
			
			for(int j = 0; j < list.size(); j++) {
				Map<String, Object> mTemp2 = list.get(j);
				if(sTemp.equals((String)mTemp2.get("PART_NO")) ) {// 제품코드가 같으면
					int iTemp2 = (Integer)mTemp2.get("R_QUAN");
					iTemp += iTemp2;//Integer.parseInt(sTemp2);//실 수량 더하기 
				}
			}//for list
			
			partCount.put("PART_NO", sTemp);
			partCount.put("count", iTemp);
			nameCountList.add(partCount);
		} // for namelist

		//작업번호 총 갯수구하기
		int lotCount = commonService.mwhGetLotCount(map);
		//한페이지 출력수
		int postNum = 10;
		map.put("postNum", postNum);
		//하단 페이지 번호
		int pageNum=(int)Math.ceil((double)lotCount/postNum);
	
		//출력 게시물
		int displayPost = (num-1) * postNum;
		map.put("displayPost", displayPost);
		mv.addObject("pageNum", pageNum);
		
		mv.addObject("partName", nameList); //제품이름 리스트
		mv.addObject("nameCount", nameCountList);
		mv.addObject("list", list);
		
		return mv;
	}//상품 입출고 검색
	
	//창고 물품 검색 결과 화면 ju
	@RequestMapping(value = { "/MCHWHSCH.do", "/mchwhsch.do" }, method = RequestMethod.GET)
	public ModelAndView mchwhsch(@RequestParam Map<String, Object> map,
			@RequestParam(value="num", required=false, defaultValue="1") int num,
			HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.debug("Request Parameter : " + map);

		int count = 0;
		String io = "";
		List<Map<String, Object> > list = new ArrayList(); //전체 검색
		List<Map<String, Object>> nameList = commonService.mwhGetPartName(map);
		List<Map<String, Object>> nameCountList = new ArrayList();//제품별 수량
		Map<String, Object> part_no = new HashMap<String, Object>();//제품번호 저장용
		ModelAndView mv = null;
		mv = new ModelAndView("/MCHWHSCH");//검색결과창
		String partno = (String)map.get("PART_NO");

		//작업번호 총 갯수구하기
		int lotCount = commonService.mwhGetLotCount(map);
		//한페이지 출력수
		int postNum = 10;
		map.put("postNum", postNum);
		//하단 페이지 번호
		int pageNum=(int)Math.ceil((double)lotCount/postNum);
	
		//출력 게시물
		int displayPost = (num-1) * postNum;
		map.put("displayPost", displayPost);
		mv.addObject("pageNum", pageNum);

		if(IDCheck(req, res) );
		if(partno != null && partno.length() > 0) {
			count++;
		}
		
		if(count == 1) {
			if(partno.equalsIgnoreCase("all")) {
				list = commonService.mwhSeaLotAll(map);//sql검색결과 저장
			}else { //all로 검사시
				list = commonService.mwhSeaLot(map);//검색결과 저장
			}//개별 검사시
			//제품 전체 수량
			
			int iTemp = 0;
			String sTemp = "";
			sTemp = (String)map.get("PART_NO");
			
			for(int j = 0; j < list.size(); j++) {
				Map<String, Object> mTemp2 = list.get(j);
				if(sTemp.equalsIgnoreCase((String)mTemp2.get("PART_NO")) ) {// 제품코드가 같으면
					int iTemp2 = (Integer)mTemp2.get("R_QUAN");
					iTemp += iTemp2;//Integer.parseInt(sTemp2);//실 수량 더하기 
				}
			}//for list
			Map<String, Object> mCount = new HashMap<String, Object>();
			mCount.put("PART_NO", sTemp);
			part_no.put("PART_NO", sTemp);
			mCount.put("count", iTemp);
			nameCountList.add(mCount);
			
		}//if PART_NO가 있으면
		
		//창고 입출력 있을시
		mv.addObject("schPartNo",part_no);
		mv.addObject("list", list);
		mv.addObject("partName", nameList); //제품이름 리스트
		mv.addObject("nameCount", nameCountList); //총 제품 수량
		
		return mv;
	}//창고 물품 검색 결과 화면
	
	//출고 처리 ju
	@RequestMapping(value = { "/MCHWHOUT.do", "/mchwhout.do" }, method = RequestMethod.GET)
	public ModelAndView mchwhout(@RequestParam Map<String, Object> map,
			HttpServletRequest req, HttpServletResponse res )throws IOException {
		log.debug("Request Parameter : " + map);
		
		//알림 팝업을 위한 선언부분
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		out = res.getWriter();
		
		ModelAndView mv = null;
		mv = new ModelAndView("/MCHWHOUT");
		Map<String, Object> list = new HashMap<String, Object>();
		List<Map<String, Object>> nameList = commonService.mwhGetPartName(map);
		list = commonService.mwhCor(map);//검색결과 저장
		String outIO = (String)map.get("IO"); //입출고 상태
		
		if(IDCheck(req, res));
		
		if(outIO != null) {
			if(outIO.equalsIgnoreCase("X")) {
				commonService.mwhOut(map);
				out.print("<script>location.href='mchwhsea.do'</script>");
				out.flush();
			}
		}
		
		//mv.setViewName("redirect:/MCHWHSEA");
		mv.addObject("list", list);
		mv.addObject("partName", nameList); //제품이름 리스트
		
		return mv;
	}

	//-------------------BOM JIN--------------------------

	
	//bom화면
	@RequestMapping(value = { "/bom.do", "/BOM.do"}, method = RequestMethod.GET)
	public ModelAndView bom(@RequestParam(value="num2" , required=false, defaultValue="1")int num2, @RequestParam Map<String, Object> map, HttpServletRequest req, HttpServletResponse res)throws IOException {
		
		if(IDCheck(req, res));
		ModelAndView mv = new ModelAndView("/bom");
		
		int count2 = commonService.ging(map);
		//한페이지 출력수
		int postNum2=10;
		map.put("postNum2", postNum2);
		//하단 페이지 번호
		int pageNum2=(int)Math.ceil((double)count2/postNum2);
		mv.addObject("pageNum2", pageNum2);
		int displayPost2 = (num2-1) * postNum2;
		map.put("displayPost2", displayPost2);
		
		List<Map<String, Object>> list = commonService.bom(map);
		mv.addObject("list", list);
		return mv;
	}
	//bom등록
	@RequestMapping(value = { "/bom_insert.do"}, method = RequestMethod.GET)
	public ModelAndView bom_insert(@RequestParam Map<String, Object> map, HttpServletRequest req, HttpServletResponse res)throws IOException {
		log.debug("Request Parameter : " + map);
		if(IDCheck(req, res));
		ModelAndView mv = new ModelAndView("/bom_insert");
		List<Map<String, Object>> list = commonService.bomInsertOne(map);
		List<Map<String, Object>> lsst = commonService.bomInsertTwo(map);
		
		return mv;
	}

	
	//-------------------자재창고 MIN--------------------------

	// 자재창고 조회, 검색용 - MIN
	@RequestMapping(value = { "/m_view.do" }, method = RequestMethod.GET)
	public ModelAndView m_view(@RequestParam(value="num", required=false, defaultValue="1") 
	int num, @RequestParam Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) 
			throws IOException {
		log.debug("Request Parameter : " + map);

		ModelAndView mv = new ModelAndView("/m_view");
		if(IDCheck(req, res));
		//게시물 총 갯수구하기
		int count = commonService.m_paging(map);
		//한페이지 출력수
		int postNum=10;
		map.put("postNum", postNum);

		//하단 페이지 번호
		int pageNum=(int)Math.ceil((double)count/postNum);

		//출력 게시물
		int displayPost = (num-1) * postNum;
		map.put("displayPost", displayPost);

		List<Map<String, Object>> list = commonService.db_m_view(map);
		mv.addObject("list", list);
		mv.addObject("pageNum", pageNum);
		return mv;
	}
	
	// 자재창고 자재수량추가용 - MIN
	@RequestMapping(value = "/m_update.do", method = RequestMethod.GET)
	public void m_update(@RequestParam Map<String, Object> map, HttpServletRequest req, 
			HttpServletResponse res) throws IOException {
		log.debug("Request Parameter : " + map);
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		out = res.getWriter();
		int rs = commonService.db_m_update(map);
		
		if(IDCheck(req, res));		
		if (rs > 0) {
			out.print("<script>alert('자재를 추가하였습니다');");
			// 성공
		} else {
			out.print("<script>alert('다시 시도해주세요!');");
		}
		out.print("location.href='m_view.do';");
		out.print("</script>");
		out.flush();
	}
	
	// m_input호출용- MIN
	@RequestMapping(value = "/transferToM_input.do")
	public ModelAndView transferToM_view() {
		ModelAndView mv = new ModelAndView("/m_input");
		return mv;
	}
	
	// 자재창고 자재추가용 - MIN
	@RequestMapping(value = "/m_input.do", method = RequestMethod.GET)
	public void m_input(@RequestParam Map<String, Object> map, HttpServletRequest req, 
			HttpServletResponse res) throws IOException {
		log.debug("Request Parameter : " + map);
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		out = res.getWriter();
		
		if(IDCheck(req, res));
		int NO_checker = commonService.db_m_NOchecker(map, res); // M_NO 중복체크용
		System.out.println(NO_checker);
		if(NO_checker>0) {
			out.print("<script>alert('자재번호가 중복입니다!');");
			out.print("location.href='transferToM_input.do';");
		}else {
			int rs = commonService.db_m_input(map);
		
			if (rs > 0) {
				// 성공
				out.print("<script>alert('새로운 자재가 추가되었습니다.');");
				out.print("location.href='m_view.do';");
			} else {
				out.print("<script>alert('다시 시도해주세요!');");
				out.print("location.href='transferToM_input.do';");
			}
		}
		out.print("</script>");
		out.flush();
	}

	//ID 체크
	   public boolean IDCheck(HttpServletRequest req, HttpServletResponse res )throws IOException {
	      boolean check = false;
	      HttpSession session = req.getSession();
	      String sessionId = (String) session.getAttribute("sessionId"); 
	      res.setContentType("text/html; charset=UTF-8");
	      if(sessionId == null) {
	         check = true;
	         PrintWriter out;
	         out = res.getWriter();
	         out.print("<script>alert('세션정보가 만료되었나봐요! ㅠ 로그인다시해주세용');");
	         out.print("location.href='/index.do';");
	         out.print("</script>");
	         out.flush();         
	      }
	      
	      return check;
	   }
	
	@RequestMapping(value = "/sample/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("");
		if (!commandMap.isEmpty()) {
			Iterator<Entry<String, Object>> iterator = commandMap.getMap().entrySet().iterator();
			Entry<String, Object> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				log.debug("key : " + entry.getKey() + ", value : " + entry.getValue());
			}
		}
		return mv;

	}
	

} //class