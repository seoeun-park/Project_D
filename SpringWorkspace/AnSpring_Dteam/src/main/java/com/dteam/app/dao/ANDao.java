package com.dteam.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.dteam.app.dto.MdDto;
import com.dteam.app.dto.MemberDto;

public class ANDao {

	DataSource dataSource;

	public ANDao() {
		try {
			Context context = new InitialContext();
			/* dataSource = (DataSource) context.lookup("java:/comp/env/team01"); */
			dataSource = (DataSource) context.lookup("java:/comp/env/dteam");
			/* dataSource = (DataSource) context.lookup("java:/comp/env/CSS"); */
		} catch (NamingException e) {
			e.getMessage();
		}

	}

	public MemberDto anLogin(String id, String pw) {

		MemberDto adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * " + " from tblmember" + " where member_id = '" + id + "' and member_pw = '" + pw
					+ "' ";
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				String member_id = resultSet.getString("member_id");
				String member_pw = resultSet.getString("member_pw");
				String member_nickname = resultSet.getString("member_nickname");
				String member_tel = resultSet.getString("member_tel");
				String member_addr = resultSet.getString("member_addr");
				String member_latitude = resultSet.getString("member_latitude");
				String member_longitude = resultSet.getString("member_longitude");
				String member_grade = resultSet.getString("member_grade");
				// int member_grade = Integer.parseInt(resultSet.getString("member_grade"));
				String member_name = resultSet.getString("member_name");
				String member_profile = resultSet.getString("member_profile");

				adto = new MemberDto(member_id, member_pw, member_nickname, 
						member_tel, member_addr, member_latitude, member_longitude, 
						member_grade, member_name, member_profile);
			}

			System.out.println("MemberDTO id : " + adto.getMember_id());

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

		return adto;

	}

	public int anJoin(String member_id, String member_pw, String member_nickname, String member_tel, String member_addr,
			String member_latitude, String member_longitude, String member_name) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int state = -100;

		try {
			connection = dataSource.getConnection();
			String sql = "insert into tblmember(member_id, member_pw, member_nickname, member_tel, "
					+ "member_addr, member_latitude, member_longitude, member_name) " + "values('" + member_id + "', '"
					+ member_pw + "', '" + member_nickname + "', '" + member_tel + "', '" + member_addr + "', '"
					+ member_latitude + "', '" + member_longitude + "', '" + member_name + "' )";
			prepareStatement = connection.prepareStatement(sql);
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

	public MemberDto anIdCheck(String member_id) {
		MemberDto adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * " + " from tblmember" + " where member_id = '" + member_id + "' ";
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				member_id = resultSet.getString("member_id");
				String member_nickname = resultSet.getString("member_nickname");
				String member_tel = resultSet.getString("member_tel");
				String member_addr = resultSet.getString("member_addr");
				String member_latitude = resultSet.getString("member_latitude");
				String member_longitude = resultSet.getString("member_longitude");
				String member_grade = resultSet.getString("member_grade");
				String member_name = resultSet.getString("member_name");

				adto = new MemberDto(member_id, member_nickname, member_tel, member_addr, member_latitude,
						member_longitude, member_grade, member_name);
			}

			System.out.println("MemberDTO id : " + adto.getMember_id());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
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

		return adto;
	} // anIdCheck()

	public MemberDto anNickNameCheck(String member_nickname) {
		MemberDto adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * " + " from tblmember" + " where member_nickname = '" + member_nickname + "' ";
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				member_nickname = resultSet.getString("member_nickname");

				adto = new MemberDto(member_nickname);
			}

			System.out.println("MemberDTO nickname : " + adto.getMember_nickname());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
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

		return adto;
	} // anNickNameCheck()

	
	public int anInsert(String md_name, String md_photo_url, String md_category, String md_price,
			String md_rental_term, String md_deposit, String md_detail_content, String member_id, String md_serial_number ) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			connection = dataSource.getConnection(); //md_title, 빠짐 DTO에 컬럼 빠짐
			String sql = "insert into tblmerchandise(md_name, md_photo_url,  md_category, md_price, md_rental_term, "
					+ "md_deposit, md_detail_content, member_id, md_serial_number) " //md_title + "', '"
					+ "values('" + md_name + "', '" + md_photo_url + "', '"   
								+ md_category + "', '" + md_price + "', '" + md_rental_term
								+ "', '" + md_deposit +"', '" + md_detail_content + "','" + member_id + "', seq_md_serial.nextval)";
			
			prepareStatement = connection.prepareStatement(sql);
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
			} finally {

			}
		}
		return state;
	}

	
	
	public int anMdRentStatus(String md_rent_status, String md_serial_number) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
		
			connection = dataSource.getConnection();
			String query = "update tblmerchandise set md_rent_status ='"+ md_rent_status +"' where md_serial_number = '"+ md_serial_number+  "' ";
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

	
	
	//검색페이지 상품 가져오기
		public ArrayList<MdDto> anSearchSelect(String searchKeyword) {

			ArrayList<MdDto> mdDtos = new ArrayList<MdDto>();
			Connection connection = null;
			PreparedStatement prepareStatement = null;
			ResultSet resultSet = null;

			try {
				connection = dataSource.getConnection();
				String sql = "select * from tblmerchandise";
				if(searchKeyword != null) {
					sql += " WHERE md_name LIKE '%"+searchKeyword+"%'";
				}
				
				prepareStatement = connection.prepareStatement(sql);
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

					 
					mdDtos.add(new MdDto(md_name, md_category, md_price, md_rental_term,
							md_deposit, md_detail_content, md_photo_url, member_id,  md_fav_count, 
							md_registration_date, md_serial_number, md_rent_status, md_hits));
					 
				}
				System.out.println("mdDtos size : " + mdDtos.size());

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
			return mdDtos;
		}// anSearchSelect()

		
		//리뷰등록
		public int anReviewInsert(String member_id, String review_scope, String review_content
				, String member_nickname, String md_member_id, String md_serial_number
				, String member_profile, String review_num) {
			
			Connection connection = null;
			PreparedStatement prepareStatement = null;
			ResultSet resultSet = null;

			int state = -1;

			try {
				connection = dataSource.getConnection(); 
				String sql = "insert into tblreview (member_id, review_scope, review_content, member_nickname, md_member_id, md_serial_number, member_profile, review_num) "
						+ "values('" + member_id + "', '" + review_scope + "', '" + review_content + "', '" + member_nickname + "', '" + md_member_id + "', '" + md_serial_number + "', '" + member_profile + "', seq_review.nextval)";
				prepareStatement = connection.prepareStatement(sql);
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
				} finally {

				}
			}
			return state;
			
		}

		
		//md 수정
		public int anMdUpdate(String md_name, String md_category, String md_price,
				String md_rental_term, String md_deposit, String md_detail_content, String member_id,
				String md_serial_number) {
		
			Connection connection = null;
			PreparedStatement prepareStatement = null;
			ResultSet resultSet = null;

			int state = -1;

			try {
				connection = dataSource.getConnection(); 
				String sql = "update tblmerchandise "
						+ "set md_name = '" + md_name + "', "
						+ "md_category = '" + md_category + "', "
						+ "md_price = '" + md_price + "'"
						+ ", md_rental_term = '" + md_rental_term + "'"
						+ " , md_deposit = '" + md_deposit + "'"
						+ ", md_detail_content = '" + md_detail_content + "', "
						+ "member_id = '" + member_id + "' "
						+ "where md_serial_number = '" + md_serial_number + "' ";
				prepareStatement = connection.prepareStatement(sql);
				state = prepareStatement.executeUpdate();

				if (state > 0) {
					System.out.println(state + "수정성공");
				} else {
					System.out.println(state + "수정실패");
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
				} finally {

				}
			}
			return state;
		}

		public int anMdDelete(String md_serial_number) {
			Connection connection = null;
			PreparedStatement prepareStatement = null;
			ResultSet resultSet = null;
		
			int state = -1;

			try {
				connection = dataSource.getConnection(); 
				// delete from tblmerchandise where md_serial_number ='8';
				String sql = "delete from tblmerchandise where md_serial_number = '" + md_serial_number + "' ";
				prepareStatement = connection.prepareStatement(sql);
				state = prepareStatement.executeUpdate();

				if (state > 0) {
					System.out.println(state + "삭제성공");
				} else {
					System.out.println(state + "삭제실패");
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
				} finally {

				}
			}
			return state;
		}

		
		
		public int anReviewDelete(String review_num) {
			
			Connection connection = null;
			PreparedStatement prepareStatement = null;
			ResultSet resultSet = null;
		
			int state = -1;

			try {
				connection = dataSource.getConnection(); 
				String sql = "delete from tblreview where review_num = '" + review_num + "' ";
				prepareStatement = connection.prepareStatement(sql);
				state = prepareStatement.executeUpdate();

				if (state > 0) {
					System.out.println(state + "삭제성공");
				} else {
					System.out.println(state + "삭제실패");
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
				} finally {

				}
			}
			return state;
		}
		
}