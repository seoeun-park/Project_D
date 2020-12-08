package com.dteam.app.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.dteam.app.command.ACommand;
import com.dteam.app.command.ADetailCommand;
import com.dteam.app.command.AIdCheckCommand;
import com.dteam.app.command.AJoinCommand;
import com.dteam.app.command.ALoginCommand;
import com.dteam.app.command.AMdInsertCommand;
import com.dteam.app.command.AMdUpdateCommand;
import com.dteam.app.command.AMainSelectCommand;
import com.dteam.app.command.AMdDeleteCommand;
import com.dteam.app.command.ANickNameCheckCommand;
import com.dteam.app.command.ARentStatusCommand;
import com.dteam.app.command.AReviewDeleteCommand;
import com.dteam.app.command.AReviewInsertCommand;
import com.dteam.app.command.ASearchSelectCommand;
import com.dteam.app.command.anFavUpdateCommand;

@Controller
public class AController {
	
	ACommand command;
	

	//검색페이지 
	@RequestMapping(value="/SearchSelect", method = {RequestMethod.GET, RequestMethod.POST} )
	public String SearchSelect(HttpServletRequest request, Model model, @RequestParam(required = false) String searchKeyword) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 	
		
		command = new ASearchSelectCommand(searchKeyword);
		
		command.execute(model);
		
		return "anSearchSelect";
	}

	//상품등록
	@RequestMapping(value="/anInsert", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anInsert(HttpServletRequest request, Model model){
		System.out.println("anInsert()");
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 		
		
		String md_name = (String) request.getParameter("md_name");
		String md_photo_url = (String) request.getParameter("md_photo_url");
		int pos = md_photo_url.lastIndexOf("/");
		String path = md_photo_url.substring(0, pos);
		String file_name = md_photo_url.substring(pos);
		md_photo_url = path+"/dteam"+file_name;
		String md_category = (String) request.getParameter("md_category");
		String md_price = (String) request.getParameter("md_price");
		String md_rental_term = (String) request.getParameter("md_rental_term");
		String md_deposit = (String) request.getParameter("md_deposit");
		String md_detail_content = (String) request.getParameter("md_detail_content");
		String member_id = (String) request.getParameter("member_id");
		String md_serial_number = (String) request.getParameter("md_serial_number");
		
		
		System.out.println(md_name);
		System.out.println(md_photo_url);
		System.out.println(md_category);
		System.out.println(md_price);
		System.out.println(md_rental_term);
		System.out.println(md_deposit);
		System.out.println(md_detail_content);
		System.out.println(member_id);
		System.out.println(md_serial_number);
		
		model.addAttribute("md_name", md_name);
		model.addAttribute("md_photo_url", md_photo_url);
		model.addAttribute("md_category", md_category);
		model.addAttribute("md_price", md_price);
		model.addAttribute("md_rental_term", md_rental_term);
		model.addAttribute("md_deposit", md_deposit);
		model.addAttribute("md_detail_content", md_detail_content);
		model.addAttribute("member_id", member_id);
		model.addAttribute("md_serial_number", md_serial_number);
		
		MultipartRequest multi = (MultipartRequest) request;
		MultipartFile file = multi.getFile("image");
		
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			
			makeDir(request);	
				
			if(file.getSize() > 0){			
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/dteam");
				
				System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
					file.transferTo(new File(realImgPath, fileName));										
				} catch (Exception e) {
					e.printStackTrace();
				} 
									
			}else{
				fileName = "FileFail.jpg";
				String realImgPath = request.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
			
		}
				
		command = new AMdInsertCommand();
		command.execute(model);
		
		return "anInsert";
	}

	private void makeDir(HttpServletRequest request) {
		File f = new File(request.getSession().getServletContext()
				.getRealPath("/resources/dteam"));
		if(!f.isDirectory()){
		f.mkdir();
		}	
		
	}
	
	//상품 대여상태
	@RequestMapping(value="/anMdRentStatus", method = {RequestMethod.GET, RequestMethod.POST} )
			public String anFavUpdate(HttpServletRequest request, Model model) {
				System.out.println("anMdRentStatus()");
				
				try {
					request.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} 		
				
				String md_rent_status = (String)request.getParameter("md_rent_status");
				String md_serial_number = (String) request.getParameter("md_serial_number");
				
				System.out.println("md_rent_status : " + md_rent_status);
				System.out.println("md_serial_number : " + md_serial_number);
				
				model.addAttribute("md_rent_status", md_rent_status);
				model.addAttribute("md_serial_number", md_serial_number);
				
				command = new ARentStatusCommand();
				command.execute(model);
				
				return "anMdRentStatus";
			}
			
	//리뷰 등록
			@RequestMapping(value="/anReviewInsert", method = {RequestMethod.GET, RequestMethod.POST} )
			public String anReviewInsert(HttpServletRequest request, Model model) {
				System.out.println("anReviewInsert()");
				
				try {
					request.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} 		
				
				String member_id = (String) request.getParameter("member_id");
				String review_scope = (String) request.getParameter("review_scope");
				String review_content = (String) request.getParameter("review_content");
				String member_nickname = (String) request.getParameter("member_nickname");
				String md_member_id = (String) request.getParameter("md_member_id");
				String md_serial_number = (String) request.getParameter("md_serial_number");
				String member_profile = (String) request.getParameter("member_profile");
				String review_num = (String) request.getParameter("review_num");
				
				System.out.println("member_id : " + member_id);
				System.out.println("review_scope : " + review_scope);
				System.out.println("review_content : " + review_content);
				System.out.println("member_nickname : " + member_nickname);
				System.out.println("md_member_id : " + md_member_id);
				System.out.println("md_serial_number : " + md_serial_number);
				System.out.println("member_profile : " + member_profile);
				System.out.println("review_num : " + review_num);
				
				model.addAttribute("member_id", member_id);
				model.addAttribute("review_scope", review_scope);
				model.addAttribute("review_content", review_content);
				model.addAttribute("member_nickname", member_nickname);
				model.addAttribute("md_member_id", md_member_id);
				model.addAttribute("md_serial_number", md_serial_number);
				model.addAttribute("member_profile", member_profile);
				model.addAttribute("review_num", review_num);
				
				command = new AReviewInsertCommand();
				command.execute(model);
				
				return "anReviewInsert";
			}
			
			
			
			//상품등록
			@RequestMapping(value="/anMdUpdate", method = {RequestMethod.GET, RequestMethod.POST}  )
			public String anMdUpdate(HttpServletRequest request, Model model){
				System.out.println("anMdUpdate()");
				
				try {
					request.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} 		
				
				String md_name = (String) request.getParameter("md_name");
				String md_category = (String) request.getParameter("md_category");
				String md_price = (String) request.getParameter("md_price");
				String md_rental_term = (String) request.getParameter("md_rental_term");
				String md_deposit = (String) request.getParameter("md_deposit");
				String md_detail_content = (String) request.getParameter("md_detail_content");
				String member_id = (String) request.getParameter("member_id");
				String md_serial_number = (String) request.getParameter("md_serial_number");
				
				System.out.println(md_name);
				System.out.println(md_category);
				System.out.println(md_price);
				System.out.println(md_rental_term);
				System.out.println(md_deposit);
				System.out.println(md_detail_content);
				System.out.println(member_id);
				System.out.println(md_serial_number);
				
				model.addAttribute("md_name", md_name);
				model.addAttribute("md_category", md_category);
				model.addAttribute("md_price", md_price);
				model.addAttribute("md_rental_term", md_rental_term);
				model.addAttribute("md_deposit", md_deposit);
				model.addAttribute("md_detail_content", md_detail_content);
				model.addAttribute("member_id", member_id);
				model.addAttribute("md_serial_number", md_serial_number);
				

						
				command = new AMdUpdateCommand();
				command.execute(model);
				
				return "anMdUpdate";
			}

			
			
			//md 삭제
			@RequestMapping(value="/anMdDelete", method = {RequestMethod.GET, RequestMethod.POST}  )
			public String anMdDelete(HttpServletRequest request, Model model){
				System.out.println("anMdDelete()");
				
				try {
					request.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} 		
				
				String md_serial_number = (String) request.getParameter("md_serial_number");
				System.out.println(md_serial_number);
				model.addAttribute("md_serial_number", md_serial_number);
						
				command = new AMdDeleteCommand();
				command.execute(model);
				
				return "anMdDelete";
			}
	
			//리뷰 삭제
			@RequestMapping(value="/anReviewDelete", method = {RequestMethod.GET, RequestMethod.POST}  )
			public String anReviewDelete(HttpServletRequest request, Model model){
				System.out.println("anReviewDelete()");
				
				try {
					request.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} 		
				
				String review_num = (String) request.getParameter("review_num");
				System.out.println(review_num);
				model.addAttribute("review_num", review_num);
						
				command = new AReviewDeleteCommand();
				command.execute(model);
				
				return "anReviewDelete";
			}
			
			
			

}
