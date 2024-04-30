package com.application.oasisCMS.book.controller;

import java.io.IOException;
import java.net.MalformedURLException;
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

import com.application.oasisCMS.book.dto.BookDTO;
import com.application.oasisCMS.book.service.BookService;
import com.application.oasisCMS.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller

@RequestMapping("/book")
public class BookController {
	
	@Value("${file.repo.path.book}")
    private String fileRepositoryPath;

	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BookService bookService;
	
	
	@GetMapping("/socialCareer")
	private String socialCareer() {
		return "book/socialCareer";
	}
	
	@GetMapping("/spiritualAspect")
	private String spiritualAspect() {
		return "book/spiritualAspect";
	}
	
	@GetMapping("/")
	public String book(Model model, HttpServletRequest req) {
			HttpSession session = req.getSession();
			model.addAttribute("memberId", (String)session.getAttribute("memberId"));
		return "redirect:/book/bdList";
	}
	@GetMapping("/main")
	public String main() {
		return "book/main";
	}
	
	@GetMapping("/createBd")
	public String createBD(Model model, HttpServletRequest req) {
		System.out.println("[컨트롤러] 도서 등록 - 겟 도착");
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		
		return "book/createBd";
	}
	@PostMapping("/createBd")
	public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
							@ModelAttribute BookDTO bookDTO) 
							throws IllegalStateException, IOException{
//		public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, @ModelAttribute BookDTO bookDTO) throws IllegalStateException, IOException{
		// html에서 이미지를 위해 enctype="multipart/form-data" 추가해야함.
		System.out.println("[컨트롤러] 도서 등록 포스트 : " + bookDTO);
		bookService.createBd(uploadImg, bookDTO);
		return "redirect:/book/bdList";
	}
	
	@GetMapping("/bdList")
	public String bdList(Model model) {
		
		// 단위테스트
		System.out.println("[컨트롤러] 도서 리스트 -겟");
		List<BookDTO> bdList = bookService.getBdList();
		for(BookDTO dto : bdList) {
			System.out.println("[컨트롤러] 도서 리스트 :" + dto);
		}
		model.addAttribute("bdList" ,bdList );
		return "book/bdList";
	}
	
	// 상세조회
	@GetMapping("/bdDetail")
	public String bdDetail(Model model, @RequestParam("bdId") long bdId, HttpServletRequest req){
		HttpSession session = req.getSession();
		String memberId = (String) session.getAttribute("memberId");
		model.addAttribute("memberId", memberId);
		
		//단위테스트
		System.out.println(memberId);
		System.out.println("[컨트롤러] 도서 상세 - 겟 : " + bookService.getBdDetail(bdId));
		
		BookDTO bookDTO = bookService.getBdDetail(bdId);
		model.addAttribute("bookDTO", bookDTO);
		
		return "book/bdDetail";
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
		System.out.println("[컨트롤러] 도서 업뎃 - 겟 ");
		HttpSession session = req.getSession();
		model.addAttribute("memberId" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
		model.addAttribute("bookDTO", bookService.getBdDetail(bdId));
		return "book/updateBd";
	}
	
	@PostMapping("/updateBd")
	@ResponseBody
	public String updateBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
			@ModelAttribute BookDTO bookDTO) 
			throws IllegalStateException, IOException{
		
				System.out.println("[컨트롤러] 도서 업뎃 포스트" + bookDTO);
				bookService.updateBd(uploadImg, bookDTO);
		String jsScript = """
				<script>
					location.href = '/book/bdList';
				</script>
				""";
		return jsScript;
	} 
	
	// 삭제
	@GetMapping("/deleteBd")
	public String deleteBd(@RequestParam("bdId") long bdId) {
		System.out.println("[컨트롤러] 도서 삭제 - 겟"); 
		System.out.println(bookService.getBdList());
		bookService.deleteBd(bdId);
		return "redirect:/book/bdList";
	}
}
