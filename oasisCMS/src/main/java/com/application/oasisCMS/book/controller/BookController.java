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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/book")
public class BookController {
	@Value("${file.repo.path.book}")
    private String fileRepositoryPath;

	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/main")
	public String main() {
		return "book/main";
	}
	
	@GetMapping("/bdList")
	public String bdList(Model model) {
		
		// 단위테스트
		System.out.println("도서 게시판리스트 컨트롤러-겟 도착");
		List<BookDTO> bdList = bookService.getBdList();
		for(BookDTO dto : bdList) {
			System.out.println("bookDTO :" + dto);
		}
		model.addAttribute("bdList" ,bdList );
		return "book/bdList";
	}
	

	@GetMapping("/createBd")
	public String createBD(Model model, HttpServletRequest req) {
		System.out.println("기념관-게시글 등록 컨트롤러-겟 도착");
		HttpSession session = req.getSession();
		model.addAttribute("memberId", (String)session.getAttribute("memberId"));
		
		return "book/createBd";
	}
	@PostMapping("/createBd")
	public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
							@ModelAttribute BookDTO bookDTO) 
							throws IllegalStateException, IOException{
//		public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, @ModelAttribute BookDTO bookDTO) throws IllegalStateException, IOException{
		// html에서 이미지를 위해 enctype="multipart/form-data" 추가해야함.
		System.out.println("기념관 등록 포스트 컨트롤러임");
		System.out.println("bookDTO : " + bookDTO);
		bookService.createBd(uploadImg, bookDTO);
		return "redirect:/book/bdList";
	}
		
		@GetMapping("/bdDetail")
		public String bdDetail(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
	        
			HttpSession session = req.getSession();
			String memberId = (String)session.getAttribute("memberId");
	        model.addAttribute("memberId", memberId);

			// 단위테스트
			System.out.println("기념관보드세부내용 겟 - " + bookService.getBdDetail(bdId));
			
			BookDTO momorialDTO = bookService.getBdDetail(bdId);
			model.addAttribute("bookDTO" , momorialDTO);

			return "book/bdDetail";
		}
		
		// 썸네일기능 구현 예시 : 원래는 썸내일을 build.gradle에 구현을 했어야 했다. 지금은 그냥 하면 된다. 
		@GetMapping("/thumbnails")
		@ResponseBody
		public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException {
			// new UrlResource("file:" + 파일접근경로) 객체를 반환하여 이미지를 조회한다.
			return new UrlResource("file:" + fileRepositoryPath + fileName); // 이미지 경로 수정하여 사용
		}
		
		
		
		@GetMapping("/updateBd")
		public String updateBd(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
			System.out.println("get 업뎃보드도착 ");
			HttpSession session = req.getSession();
			model.addAttribute("memberId" , (String)session.getAttribute("memberId"));
			model.addAttribute("bookDTO", bookService.getBdDetail(bdId));
			return "book/updateBd";
		}
		
		@PostMapping("/updateBd")
		@ResponseBody
		public String updateBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
				@ModelAttribute BookDTO bookDTO) 
						throws IllegalStateException, IOException{
			bookService.updateBd(uploadImg, bookDTO);
			String jsScript = """
					<script>
						alert('수정 됐습니다.');
						location.href = '/book/bdList';
					</script>
					""";
			return jsScript;
		} 
		
		@GetMapping("/deleteBd")
		public String deleteBd(@RequestParam("bdId") long bdId) {
			System.out.println("get delete보드도착 ");
			System.out.println(bookService.getBdList());
			bookService.deleteBd(bdId);
			return "redirect:/book/bdList";
		}
	
		
}
