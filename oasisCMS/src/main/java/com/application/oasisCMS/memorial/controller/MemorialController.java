package com.application.oasisCMS.memorial.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

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

import com.application.oasisCMS.member.service.MemberService;
import com.application.oasisCMS.memorial.dto.MemorialDTO;
import com.application.oasisCMS.memorial.service.MemorialService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/memorial")
public class MemorialController {

	@Value("${file.repo.path.memorial}")
    private String fileRepositoryPath;
	
	@Autowired
	private MemorialService memorialService;
	
	@Autowired
	private MemberService memberService;

	@GetMapping("/main")
	public String main() {
		return "memorial/purpose";
	}
	
	@GetMapping("/")
	public String m() {
		return "memorial/purpose";
	}

	@GetMapping("/visit")
	public String visit() {
		return "memorial/visit";
	}
	
	@GetMapping("/visit/1")
	public String visit1() {
		return "memorial/visit/1";
	}
	@GetMapping("/visit/2")
	public String visit2() {
		return "memorial/visit/2";
	}
	@GetMapping("/visit/3")
	public String visit3() {
		return "memorial/visit/3";
	}
	@GetMapping("/visit/4")
	public String visit4() {
		return "memorial/visit/4";
	}
	@GetMapping("/visit/5")
	public String visit5() {
		return "memorial/visit/5";
	}
	@GetMapping("/visit/6")
	public String visit6() {
		return "memorial/visit/6";
	}
	@GetMapping("/visit/7")
	public String visit7() {
		return "memorial/visit/7";
	}
	
	
	@GetMapping("/purpose")
	public String purpose() {
		return "memorial/purpose";
	}
	@GetMapping("/bdList")
	public String bdList(Model model) {
		
		// 단위테스트
		System.out.println("기념관-게시판리스트 컨트롤러-겟 도착");
		List<MemorialDTO> bdList = memorialService.getBdList();
		for(MemorialDTO dto : bdList) {
			System.out.println("memorialDTO :" + dto);
		}
		model.addAttribute("bdList1" ,bdList );
		return "memorial/bdList";
	}
	
	@GetMapping("/createBd")
	public String createBD(Model model, HttpServletRequest req) {
		System.out.println("기념관-게시글 등록 컨트롤러-겟 도착");
		HttpSession session = req.getSession();
		model.addAttribute("memberDTO", memberService.getMemberDetail((String)session.getAttribute("memberId")));
		
		return "memorial/createBd";
	}
	@PostMapping("/createBd")
	public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, 
							@ModelAttribute MemorialDTO memorialDTO) 
							throws IllegalStateException, IOException{
//		public String createBd(@RequestParam("uploadImg") MultipartFile uploadImg, @ModelAttribute MemorialDTO memorialDTO) throws IllegalStateException, IOException{
		// html에서 이미지를 위해 enctype="multipart/form-data" 추가해야함.
		System.out.println("기념관 등록 포스트 컨트롤러임");
		System.out.println("memorialDTO : " + memorialDTO);
		memorialService.createBd(uploadImg, memorialDTO);
		return "redirect:/memorial/bdList";
	}
		
		@GetMapping("/bdDetail")
		public String bdDetail(Model model , @RequestParam("bdId") long bdId, HttpServletRequest req) {
	        
			HttpSession session = req.getSession();
			String memberId = (String)session.getAttribute("memberId");
	        model.addAttribute("memberId", memberId);

			// 단위테스트
			System.out.println("기념관보드세부내용 겟 - " + memorialService.getBdDetail(bdId));
			
			MemorialDTO momorialDTO = memorialService.getBdDetail(bdId);
			model.addAttribute("memorialDTO" , momorialDTO);

			return "memorial/bdDetail";
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
			System.out.println("[컨트롤러] 기념관 수정 - 겟");
			HttpSession session = req.getSession();
			model.addAttribute("memberId" , memberService.getMemberDetail((String)session.getAttribute("memberId")));
			model.addAttribute("memorialDTO", memorialService.getBdDetail(bdId));
			return "memorial/updateBd";
		}
		
		@PostMapping("/updateBd")
		@ResponseBody
		public String updateBd(@RequestParam("uploadImg") MultipartFile uploadImg, @ModelAttribute MemorialDTO memorialDTO)throws IllegalStateException, IOException {
			System.out.println("[서비스] 기념관 수정 - 포스트 ");
			
			if(uploadImg.isEmpty()) {
				memorialDTO.setBdImg(fileRepositoryPath + memorialDTO.getBdUUID());
				
			} else if (!uploadImg.isEmpty()) {
				String originalFilename = uploadImg.getOriginalFilename();
				System.out.println("originalFilename : " + originalFilename);
				
				memorialDTO.setBdImg(originalFilename);
				
				String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
				
				String uploadFile = UUID.randomUUID() + extension;
				memorialDTO.setBdUUID(uploadFile);
				
				uploadImg.transferTo(new File(fileRepositoryPath + uploadFile));
//	 			uploadImg.transferTo(new File(fileRepositoryPath +"author/"+ uploadFile));
				
			}
			//단위테스트
			System.out.println("[서비스] 저자 수정 후 : " + memorialDTO);
			memorialService.updateBd(uploadImg, memorialDTO);
			String jsScript = """
					<script>
						alert('수정 됐습니다.');
						location.href = '/memorial/bdList';
					</script>
					""";
			return jsScript;
		} 
		
		@GetMapping("/deleteBd")
		public String deleteBd(@RequestParam("bdId") long bdId) {
			System.out.println("[컨트롤러] 기념관 삭제 겟 ");
			System.out.println(memorialService.getBdList());
			memorialService.deleteBd(bdId);
			return "redirect:/memorial/bdList";
		}
	
		
}
