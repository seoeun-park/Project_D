package com.dteam.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.dteam.app.dto.MdDto;

public class CHDao {
	DataSource dataSource;
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	
	public CHDao () {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/dteam");
		} catch (NamingException e) {
			e.getMessage();
		}
	}
	
	public ArrayList<MdDto> anCategorySelect(String category) {
		ArrayList<MdDto> adtos = new ArrayList<MdDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;	
		
		System.out.println("category" + category);
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select md_name, md_category, md_price, md_rental_term,"
					     + "md_deposit,md_detail_content,md_photo_url,member_id,"
					     + "md_fav_count,md_registration_date,md_serial_number,md_rent_status,md_hits "					
							+ " from tblmerchandise" 
							+ " where md_category = '" + category + "' " ;
							//+ " order by member_id desc";
			
		
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String md_name = resultSet.getString("md_name");
				String md_category = resultSet.getString("md_category");
				String md_price = resultSet.getString("md_price");
				String md_rental_term = resultSet.getString("md_rental_term");
				String md_deposit = resultSet.getString("md_deposit");
				String md_detail_content = resultSet.getString("md_detail_content");
				String md_photo_url = resultSet.getString("md_photo_url");
				String member_id = resultSet.getString("member_id");
				String md_fav_count = resultSet.getString("md_fav_count");
				String md_registration_date = resultSet.getString("md_registration_date");
				String md_serial_number = resultSet.getString("md_serial_number");
				String md_rent_status = resultSet.getString("md_rent_status");
				String md_hits = resultSet.getString("md_hits");
				

				MdDto adto = new MdDto(md_name, md_category, md_price, md_rental_term, md_deposit,
						              md_detail_content, md_photo_url, member_id , md_fav_count,
						              md_registration_date, md_serial_number, md_rent_status,md_hits );
				adtos.add(adto);			
			}	
			
			System.out.println("adtos크기" + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;
	}


	

    /*
	public ArrayList<ANDto> anSelectMulti() {		
		
		ArrayList<ANDto> adtos = new ArrayList<ANDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select id, name, hire_date, image_path "					
							+ " from android" 
							+ " order by id desc";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Date date = resultSet.getDate("hire_date"); 
				String imagePath = resultSet.getString("image_path"); 

				ANDto adto = new ANDto(id, name, date, imagePath);
				adtos.add(adto);			
			}	
			
			System.out.println("adtos크기" + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}
	*/
    
	/*
	public int anInsertMulti(int id, String name, String date, String dbImgPath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
				
		int state = -1;

		try {			
			// 
			connection = dataSource.getConnection();
			String query = "insert into android(id, name, hire_date, image_path) " + "values(" + id + ",'" 
							+ name + "'," + "to_date('" + date + "','rr/mm/dd') , '" + dbImgPath + "' )";

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 

		}

		return state;

	}
	*/
    /*
	public int anUpdateMulti(int id, String name, String date, String dbImgPath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// 아이디는 수정할수 없음			
			connection = dataSource.getConnection();
			String query = "update android set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "
		             + ", image_path = '" + dbImgPath + "' "
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정1성공");
				
			} else {
				System.out.println("수정1실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	
	}
	*/
    /*
	public int anUpdateMultiNo(int id, String name, String date) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// 아이디는 수정할수 없음			
			connection = dataSource.getConnection();
			String query = "update android set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "		             
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정2성공");
				
			} else {
				System.out.println("수정2실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	}
	*/
    /*
	public int anDeleteMulti(int id) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "delete from android where id=" + id;
			
			System.out.println(id);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("삭제성공");				
			} else {
				System.out.println("삭제실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state;

	}
	*/
		
	
	

}
