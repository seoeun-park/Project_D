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
import com.dteam.app.command.ADeleteMultiCommand;
import com.dteam.app.command.AProfileDeleteMultiCommand;
import com.dteam.app.command.AProfileInsertCommand;
import com.dteam.app.command.ASubUpdateMultiCommand;
import com.dteam.app.command.AUpdateMultiCommand;

@Controller
public class JKController {
		
		ACommand command;
		
		@RequestMapping(value="/anProfileInsertMulti", method = {RequestMethod.GET, RequestMethod.POST}  )
		public void anInsertMulti(HttpServletRequest req, Model model){
			System.out.println("anProfileInsertMulti()");	
			
			try {
				req.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 		
			String id = (String) req.getParameter("id");
			String dbImgPath = (String) req.getParameter("dbImgPath");
			
			System.out.println(id);
			System.out.println(dbImgPath);
			
			model.addAttribute("id", id);
			model.addAttribute("dbImgPath", dbImgPath);	
			
			MultipartRequest multi = (MultipartRequest)req;
			MultipartFile file = multi.getFile("image");
			
				
			if(file != null) {
				String fileName = file.getOriginalFilename();
				System.out.println(fileName);
				
				// �뜝�럥�꺏�뜝�럩�쟼�뜝�럥苑낉옙逾녑뜝占� �댖怨뺣샍占쎌궨�뜝�럥由�嶺뚯쉻�삕 �뜝�럥瑜ュ뜝�럩紐든춯濡녹삕 �뜝�럡臾멨뜝�럡�뎽
				makeDir(req);	
					
				if(file.getSize() > 0){			
					String realImgPath = req.getSession().getServletContext()
							.getRealPath("/resources/");
					
					System.out.println( fileName + " : " + realImgPath);
					System.out.println( "fileSize : " + file.getSize());					
													
				 	try {
				 		// �뜝�럩逾졿쾬�꼶梨룟뜝�룞�삕占쎌냱�뜝�럩逾� �뜝�룞�삕�뜝�럩�궋
						file.transferTo(new File(realImgPath, fileName));										
					} catch (Exception e) {
						e.printStackTrace();
					} 
										
				}else{
					// �뜝�럩逾졿쾬�꼶梨룟뜝�룞�삕占쎌냱�뜝�럩逾� �뜝�럥堉꾢뜝�럥�넮�뜝�럥六�
					fileName = "FileFail.jpg";
					String realImgPath = req.getSession().getServletContext()
							.getRealPath("/resources/" + fileName);
					System.out.println(fileName + " : " + realImgPath);
							
				}			
				
			}
			System.out.println(id);
			command = new AProfileInsertCommand();
			command.execute(model);
			
			/* return "anProfileInsert"; */
		}
		

		public void makeDir(HttpServletRequest req){
			File f = new File(req.getSession().getServletContext()
					.getRealPath("/resources"));
			if(!f.isDirectory()){
			f.mkdir();
			}	
		}
		public void makeDir(String req){
			File f = new File(req);
			if(!f.isDirectory()){
				f.mkdir();
			}	
		}
		
		@RequestMapping(value="/anUpdateMulti", method = {RequestMethod.GET, RequestMethod.POST})
		public void anUpdateMulti(HttpServletRequest req, Model model){
			System.out.println("anUpdateMulti()");	
			
			try {
				req.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			String id = (String) req.getParameter("id");
			String dbImgPath = (String) req.getParameter("dbImgPath");
			int pos = dbImgPath.lastIndexOf("/");
			String path = dbImgPath.substring(0, pos);
			String file_name = dbImgPath.substring(pos);
			dbImgPath = path+"/profile"+file_name;
			
			String pDbImgPath = (String) req.getParameter("pDbImgPath");
			
			System.out.println("Sub1Update:id " + id);
			System.out.println("Sub1Update:dbImgPath " + dbImgPath);
			System.out.println("Sub1Update:pDbImgPath " + pDbImgPath);
			
			model.addAttribute("id", id);
			model.addAttribute("dbImgPath", dbImgPath);

			// 占쎈쐻占쎈뼓獄�袁⑹굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈뼏�ⓦ끉�굲 占쎈쐻占쎈솏筌뤿슣�굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈뼓獄�袁⑹굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
			if(!dbImgPath.equals(pDbImgPath)){			
				
				String pFileName = req.getParameter("pDbImgPath").split("/")[req.getParameter("pDbImgPath").split("/").length -1];
				String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/profile/" + pFileName);
				
				File delfile = new File(delDbImgPath);
				System.out.println(delfile.getAbsolutePath());
				
		        if(delfile.exists()) {
		        	boolean deleteFile = false;
		            while(deleteFile != true){
		            	deleteFile = delfile.delete();
		            }	            
		            
		        }//if(delfile.exists())
			
			}//if(!dbImgPath.equals(pDbImgPath))  
			
			MultipartRequest multi = (MultipartRequest)req;
			MultipartFile file = null;
			
			file = multi.getFile("image");
				
			if(file != null) {
				String fileName = file.getOriginalFilename();
				System.out.println(fileName);
				
				// 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈쑓�뵳占� 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
				makeDir(req.getSession().getServletContext().getRealPath("/resources/profile"));	
					
				if(file.getSize() > 0){			
					String realImgPath = req.getSession().getServletContext()
							.getRealPath("/resources/profile");
					
					System.out.println( fileName + " : " + realImgPath);
					System.out.println( "fileSize : " + file.getSize());					
													
				 	try {
				 		// 占쎈쐻占쎈뼓獄�袁⑹굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
						file.transferTo(new File(realImgPath, fileName));						
					} catch (Exception e) {
						e.printStackTrace();
					} 
										
				}else{
					fileName = "FileFail.jpg";
					String realImgPath = req.getSession().getServletContext()
							.getRealPath("/resources/" + fileName);
					System.out.println(fileName + " : " + realImgPath);
							
				}			
				
			}
			
			command = new AUpdateMultiCommand();
			command.execute(model);		
			
		}
		
		@RequestMapping(value="/anSubUpdateMulti", method = {RequestMethod.GET, RequestMethod.POST})
		public void anSubUpdateMulti(HttpServletRequest req, Model model){
			System.out.println("anSubUpdateMulti()");	
			
			try {
				req.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			String id = (String) req.getParameter("id");
			String name = (String) req.getParameter("name");
			String nickname = (String) req.getParameter("nickname");
			String tel = (String) req.getParameter("tel");
			String addr = (String) req.getParameter("addr");
			String latitude = (String) req.getParameter("latitude");
			String longitude = (String) req.getParameter("longitude");
			
			System.out.println("Sub1Update:id " + id);
			System.out.println("Sub1Update:name " + name);
			System.out.println("Sub1Update:nickname " + nickname);
			System.out.println("Sub1Update:tel " + tel);
			System.out.println("Sub1Update:addr " + addr);
			System.out.println("Sub1Update:latitude " + latitude);
			System.out.println("Sub1Update:longitude " + longitude);
			
			model.addAttribute("id", id);
			model.addAttribute("name", name);
			model.addAttribute("nickname", nickname);
			model.addAttribute("tel", tel);
			model.addAttribute("addr", addr);
			model.addAttribute("latitude", latitude);
			model.addAttribute("longitude", longitude);
			
			command = new ASubUpdateMultiCommand();
			command.execute(model);		
			
		}
		
		@RequestMapping(value="/anDeleteMulti", method = {RequestMethod.GET, RequestMethod.POST})
		public void anDeleteMulti(HttpServletRequest req, Model model){
			System.out.println("anDeleteMulti()");		
			
			model.addAttribute("id", req.getParameter("id"));		
					
			System.out.println((String)req.getParameter("id"));
			System.out.println((String)req.getParameter("delDbImgPath"));
			
			String pFileName = req.getParameter("delDbImgPath").split("/")[req.getParameter("delDbImgPath").split("/").length -1];
			String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);		
			
			// �뜝�럩逾졿쾬�꼶梨룟뜝占� �뜝�럥�냱�뜝�럩逾х춯�쉻�삕�뜝�럩�뮡�뼨�먯삕
			File delfile = new File(delDbImgPath);
			System.out.println(delfile.getAbsolutePath());
			
	        if(delfile.exists()) {
	            System.out.println("Sub1Del:pDelImagePath " + delfile.exists());
	            boolean deleteFile = false;
	            while(deleteFile != true){
	            	deleteFile = delfile.delete();
	            }     
	        }	
			
			command = new ADeleteMultiCommand();
			command.execute(model);	
			
		}
		
		@RequestMapping(value="/anProfileDeleteMulti", method = {RequestMethod.GET, RequestMethod.POST})
		public void anProfileDeleteMulti(HttpServletRequest req, Model model){
			System.out.println("anProfileDeleteMulti()");		
			
			model.addAttribute("id", req.getParameter("id"));
			model.addAttribute("image", req.getParameter("image"));
					
			System.out.println((String)req.getParameter("id"));
			System.out.println((String)req.getParameter("image"));
			
			
			command = new AProfileDeleteMultiCommand();
			command.execute(model);	
			
		}

	}