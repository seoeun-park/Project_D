package com.dteam.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.dteam.app.dto.FavDto;
import com.dteam.app.dto.MdDto;
import com.dteam.app.dto.MemberDto;
import com.dteam.app.dto.ReviewDto;

public class JEDao {

	DataSource dataSource;
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	

	public JEDao() {
		try {
			Context context = new InitialContext();
			/* dataSource = (DataSource) context.lookup("java:/comp/env/team01"); */
			dataSource = (DataSource) context.lookup("java:/comp/env/dteam");
			/* dataSource = (DataSource) context.lookup("java:/comp/env/CSS"); */
		} catch (NamingException e) {
			e.getMessage();
		}
	}	
	
	
	//상세페이지에 회원정보 가져오기
	public MemberDto anDetail(String member_idin) {

		MemberDto adto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * from tblmember where member_id = '" + member_idin + "' ";
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
			    String member_name = resultSet.getString("member_name");
			    String member_profile = resultSet.getString("member_profile");

			    adto = new MemberDto(member_id, member_pw, member_nickname, member_tel, member_addr, member_latitude,
						member_longitude, member_grade, member_name, member_profile);
			
			}
			System.out.println("member_id : " + adto.getMember_id());

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
	}//anDetail()
	
	
	// 전체 상품정보 가져오기
	public ArrayList<MdDto> anMainSelect(String member_addr_in) {	
		ArrayList<MdDto> mdDtos = new ArrayList<MdDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select md.* from tblmerchandise md where md.member_id in (select m.member_id from tblmember m where m.member_addr like '%" + member_addr_in  + "%')";
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
	}// anMainSelect()

	// 전체 회원정보 가져오기
	public ArrayList<MemberDto> anMember() {

		ArrayList<MemberDto> memberDtos = new ArrayList<MemberDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * " + " from tblmember ";
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
				String member_grade = String.valueOf(resultSet.getInt("member_grade"));
				String member_name = resultSet.getString("member_name");
			}
			System.out.println("memberDtos size : " + memberDtos.size());
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
		return memberDtos;
	}// anMember()

		
	//상세페이지에서 해당회원의 다른상품 보기
	public ArrayList<MdDto> anDarunSelect(String member_id_in) {
		
		ArrayList<MdDto> mdDtos = new ArrayList<MdDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * from tblmerchandise where member_id = '" + member_id_in + "' ";
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
	}//anDarunSelect()
	

	//상품상세사진(md_photo_url) 가져오기
	 public MdDto anDetailPhoto(String md_serial_number) {

         MdDto mdDto = null;
         Connection connection = null;
         PreparedStatement prepareStatement = null;
         ResultSet resultSet = null;

         try {
            connection = dataSource.getConnection();
            String sql = "select * from tblmerchandise where md_serial_number = '" + md_serial_number + "' ";
            prepareStatement = connection.prepareStatement(sql);
            resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
               //member_id = resultSet.getString("member_id");
               //md_photo_url = resultSet.getString("md_photo_url");
               
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
               md_serial_number = resultSet.getString("md_serial_number");
               String md_rent_status = resultSet.getString("md_rent_status");
               String md_hits = resultSet.getString("md_hits");

               
               mdDto = new MdDto(md_name, md_category, md_price, md_rental_term,
                     md_deposit, md_detail_content, md_photo_url, member_id,  md_fav_count, 
                     md_registration_date, md_serial_number, md_rent_status, md_hits);
            }
            
            System.out.println("md_serial_number : " + mdDto.getMd_serial_number());

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
         return mdDto;
      }//anDetailPhoto()


		 
	//찜하기 버튼눌렀을 때 MD테이블의 md_fav_count가 1상승하게 하기 
	public MdDto anFavUpdate(String md_serial_number) {
         Connection connection = null;
         PreparedStatement prepareStatement = null;
         ResultSet resultSet = null;

         try {
            connection = dataSource.getConnection();
            String sql = "update tblmerchandise set md_fav_count = md_fav_count+1 where md_serial_number = '" + md_serial_number + "' ";
            prepareStatement = connection.prepareStatement(sql);
            resultSet = prepareStatement.executeQuery();
            
            System.out.println("dao md_serial_number : " + md_serial_number);

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
         return null;	
	
	}//anFavUpdate()


	//찜하기 버튼 다시 눌렀을 때 MD테이블의 md_fav_count가 1내려감
	public MdDto anFavUpdateMinus(String md_serial_number) {
         Connection connection = null;
         PreparedStatement prepareStatement = null;
         ResultSet resultSet = null;

         try {
            connection = dataSource.getConnection();
            String sql = "update tblmerchandise set md_fav_count = md_fav_count-1 where md_serial_number = '" + md_serial_number + "' ";
            prepareStatement = connection.prepareStatement(sql);
            resultSet = prepareStatement.executeQuery();
            
            System.out.println("dao md_serial_number : " + md_serial_number);

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
         return null;
	}//anFavUpdateMinus()


	//찜하기 버튼 눌렀을 때 찜테이블에 로그인아이디&찜한상품의 시리얼넘버 등록
	public Object anFavInsert(String member_id, String md_serial_number) {
		 Connection connection = null;
         PreparedStatement prepareStatement = null;
         ResultSet resultSet = null;

         try {
            connection = dataSource.getConnection();
            String sql = "insert into tblfavorite values('" + member_id + "', '" + md_serial_number + "', '1')";
            prepareStatement = connection.prepareStatement(sql);
            resultSet = prepareStatement.executeQuery();
            
            System.out.println("dao : " + member_id + "&" + md_serial_number);

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
         return null;
	}//anFavInsert()


	//찜취소 했을 때 찜테이블에 담긴 데이터 삭제
	public Object anFavDelete(String member_id, String md_serial_number) {
		 Connection connection = null;
         PreparedStatement prepareStatement = null;
         ResultSet resultSet = null;

         try {
            connection = dataSource.getConnection();
            String sql = "delete from tblfavorite where member_id = '"+ member_id +"' and md_serial_number = '"+ md_serial_number +"'";
            prepareStatement = connection.prepareStatement(sql);
            resultSet = prepareStatement.executeQuery();
            
            System.out.println("dao : " + member_id + "&" + md_serial_number);

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
         return null;
	}//anFavDelete()
		

	/* ▼▼▼▼▼insert, update, delete을 사용하는데 커밋을 안해도되는지 궁금해서 찾아봄 ▼▼▼▼▼
	 기본적으로 Connection 객체에 setAutoCommit(boolean autoCommit) 이란 메소드가 있는데 기본값이 true 로 설정이 되어 있다. 
	 기본적으로 JSP 는 오토커밋(Autocommit)이다. commit 이 자동으로 수행된다.
	그러나 트랜잭션(Transaction)을 처리할 때는 오토커밋이 일어나서 자동으로 commit을 사용하면 안 된다. 
	여러 개의 쿼리 문장이 하나의 작업으로 수행되어야 하기 때문에 JSP의 오토커밋이 자동으로 작동되지 못하게 해야한다.
	오토커밋이 자동으로 작동되지 못하게 하려면 setAutoCommit(false); 로 지정해야 한다.
	 */
		
	
	//찜테이블 정보 가져오기 
	public FavDto anFavSelect(String member_id, String md_serial_number) {
		 FavDto favDto = null;	
		 Connection connection = null;
         PreparedStatement prepareStatement = null;
         ResultSet resultSet = null;

         try {
            connection = dataSource.getConnection();
            String sql = "select * from tblfavorite where member_id = '" 
            			+ member_id +"' and md_serial_number = '" + md_serial_number + "'";
            prepareStatement = connection.prepareStatement(sql);
            resultSet = prepareStatement.executeQuery();
            
            
            while (resultSet.next()) {
				member_id = resultSet.getString("member_id");
				md_serial_number = resultSet.getString("md_serial_number");
				String md_fav_status = resultSet.getString("md_fav_status");
			    
			    favDto = new FavDto(member_id, md_serial_number, md_fav_status);
			}
            
            System.out.println("dao md_status: " + favDto.getMd_fav_status());

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
         return favDto;
	}//anFavSelect()


	//찜목록에 로그인한 회원의 찜리스트 가져오기
	public ArrayList<MdDto> anFavSelectList(String member_login_id) {
		ArrayList<MdDto> mdDtos = new ArrayList<MdDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * from tblmerchandise where md_serial_number in (select md_serial_number" + 
						 " from tblfavorite" + 
						 " where member_id = '" + member_login_id + "')";
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
	}//anFavSelectList()


	//상세페이지에서 해당상품의 리뷰보기
	public ArrayList<ReviewDto> anReviewSelect(String md_serial_number_in) {
		ArrayList<ReviewDto> reviews = new ArrayList<ReviewDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * from tblreview where md_serial_number = '" + md_serial_number_in + "' ";
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				String member_id = resultSet.getString("member_id");
				String review_scope = resultSet.getString("review_scope");
				String review_content = resultSet.getString("review_content");
				String review_num = resultSet.getString("review_num");
				String member_nickname = resultSet.getString("member_nickname");
				String md_member_id = resultSet.getString("md_member_id");
				String md_serial_number = resultSet.getString("md_serial_number");
				String member_profile = resultSet.getString("member_profile");
				 
				reviews.add(new ReviewDto(member_id,  review_scope,  review_content, review_num,
		                member_nickname,  md_member_id, md_serial_number, member_profile));
				 
			}
			System.out.println("reviews size : " + reviews.size());

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
		return reviews;
	}//anReviewSelect()
		
	
	
}//class
