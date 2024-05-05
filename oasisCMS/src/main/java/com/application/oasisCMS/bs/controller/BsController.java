package com.application.oasisCMS.bs.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.oasisCMS.bs.dto.BsDTO;
import com.application.oasisCMS.bs.service.BsService;
import com.application.oasisCMS.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bs")
public class BsController {
	
	@Value("${file.repo.path.bs}")
    private String fileRepositoryPath;
	
	@Autowired
	private BsService bsService;
	
	@Autowired
	private MemberService memberService;
	
	// 일반 주소 네비게이션
//	@GetMapping("/main")
//	public String main() {
//		System.out.println("[컨트롤러]");
//		bsService.getBdList();
//		return "bs/main";
//	}
	
	@GetMapping("/main")
	public String showMainPage(Model model) {
	    List<BsDTO> bdList = bsService.getBdList();
	    
	    System.out.println("[컨트롤러] 방송 메인- 전체리스트" + bdList +"\n");
	    
	    List<String> dayOfWeek = Arrays.asList("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일");
	    model.addAttribute("dayOfWeek", dayOfWeek);
	    model.addAttribute("bdList", bdList); // 가져온 게시글을 모델에 추가
	    return "bs/main"; // main.html 페이지로 이동
	}

	
	@GetMapping("/getBdListByCategory")
	@ResponseBody
	public List<BsDTO> getBdListByCategory(@RequestParam("category") int category) {
	    List<BsDTO> bdListByCategory = bsService.getBdListByCategory(category);
	    return bdListByCategory;
	}
	
	
	@GetMapping("/getAllBdList")
	@ResponseBody
	public List<BsDTO> getAllBdList() {
	    List<BsDTO> allBdList = bsService.getAllBdList();
	    return allBdList;
	}
	
	@GetMapping("/1")
	public String day1 () {
		return "bs/day/1";
	}
	@GetMapping("/2")
	public String day2 () {
		return "bs/day/2";
	}
	@GetMapping("/3")
	public String day3 () {
		return "bs/day/3";
	}
	@GetMapping("/4")
	public String day4 () {
		return "bs/day/4";
	}
	@GetMapping("/5")
	public String day5 () {
		return "bs/day/5";
	}
	@GetMapping("/6")
	public String day6 () {
		return "bs/day/6";
	}
	@GetMapping("/7")
	public String day7 () {
		return "bs/day/7";
	}
	
	
	// 기능별 네비게이션
	@GetMapping("/createBd")
	public String createBD(Model model, HttpServletRequest req) {
		System.out.println("[컨트롤러] 방송 등록 겟");
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		
		return "bs/createBd";
	}
	@PostMapping("/createBd")
	public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
							@ModelAttribute BsDTO bsDTO) 
							throws IllegalStateException, IOException{
		// public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, @ModelAttribute BsDTO bsDTO) throws IllegalStateException, IOException{
		// html에서 이미지를 위해 enctype="multipart/form-data" 추가해야함.
		System.out.println("[컨트롤러] 방송 등록 포스트");
		System.out.println("bsDTO : " + bsDTO);
		bsService.createBd(uploadImg, bsDTO);
		return "redirect:/bs/main";
	}
	
	@GetMapping("/bdList")
	public String bdList(Model model) {
		
		// 단위테스트
		System.out.println("[컨트롤러] 방송 리스트 - 겟 도착");
		List<BsDTO> bdList = bsService.getBdList();
		for(BsDTO dto : bdList) {
			System.out.println("[컨트롤러] 방송리스트의 DTO :" + dto);
		}
		model.addAttribute("bdList" ,bdList );
		return "bs/bdList";
	}
	
	// 상세조회
	@GetMapping("/bdDetail")
	public String bdDetail(Model model, @RequestParam("bdId") long bdId, HttpServletRequest req){
		HttpSession session = req.getSession();
		String memberId = (String) session.getAttribute("memberId");
		model.addAttribute("memberId", memberId);
		
		//단위테스트
		System.out.println(memberId);
		System.out.println("[컨트롤러] 방송 상세조회 겟 -   " + bsService.getBdDetail(bdId));
		
		BsDTO bsDTO = bsService.getBdDetail(bdId);
		model.addAttribute("bsDTO", bsDTO);
		
		return "bs/bdDetail";
	}	
	
//	 썸네일기능 구현
	@GetMapping("/thumbnails")
	@ResponseBody
	public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException {
		// new UrlResource("file:" + 파일접근경로) 객체를 반환하여 이미지를 조회한다.
		return new UrlResource("file:" + fileRepositoryPath + fileName); // 이미지 경로 수정하여 사용
	}


	@GetMapping("/updateBd")
	public String updateBd(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
		System.out.println("[컨트롤러] 방송 수정 - 겟 도착 ");
		HttpSession session = req.getSession();
		model.addAttribute("memberId" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		model.addAttribute("bsDTO", bsService.getBdDetail(bdId));
		return "bs/updateBd";
	}
	
	@PostMapping("/updateBd")
	@ResponseBody
	public String updateBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
			@ModelAttribute BsDTO bsDTO) 
			throws IllegalStateException, IOException{
		System.out.println("[컨트롤러] 방송 수정 - 포스트 ");
		bsService.updateBd(uploadImg, bsDTO);
		String jsScript = """
				<script>
					location.href = '/bs/bdList';
				</script>
				""";
		return jsScript;
	} 
	
	// 삭제
	@GetMapping("/deleteBd")
	public String deleteBd(@RequestParam("bdId") long bdId) {
		System.out.println("[컨트롤러] 방송 삭제 - 겟 도착 ");
		System.out.println(bsService.getBdList());
		bsService.deleteBd(bdId);
		return "redirect:/bs/bdList";
	}

}
