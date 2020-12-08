package com.dteam.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.dteam.app.dto.MdDto;
import com.dteam.app.dto.MemberDto;

public class JKDao {

	DataSource dataSource;
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	public JKDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/dteam");
		} catch (NamingException e) {
			e.getMessage();
		}
	}

	public int anUpdateMulti(String id, String dbImgPath) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			// �븘�씠�뵒�뒗 �닔�젙�븷�닔 �뾾�쓬
			connection = dataSource.getConnection();
			String query = "update tblmember set MEMBER_PROFILE = '" + dbImgPath
					+ "' where upper(member_id) LIKE upper('" + id + "')";

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("�닔�젙1�꽦怨�");

			} else {
				System.out.println("�닔�젙1�떎�뙣");
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

	public int anSubUpdateMulti(String id, String name, String nickname, String tel, String addr, String latitude, String longitude) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			// �븘�씠�뵒�뒗 �닔�젙�븷�닔 �뾾�쓬
			connection = dataSource.getConnection();
			String query = "update tblmember set MEMBER_NAME = '" + name + "', MEMBER_NICKNAME = '" + nickname
					+ "', MEMBER_TEL = '" + tel + "', MEMBER_ADDR = '" + addr
					+ "', MEMBER_LATITUDE = '" + latitude + "', MEMBER_LONGITUDE = '" + longitude 
					+ "' where upper(member_id) LIKE upper('" + id + "')";
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("수정성공");

			} else {
				System.out.println("수정실패");
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

	public int anProfileInsert(String id, String dbImgPath) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			//
			connection = dataSource.getConnection();
			String query = "update tblmember set MEMBER_PROFILE = '" + dbImgPath + "' where upper(member_id) LIKE upper('" + id + "')";
			System.out.println(id);
			prepareStatement = connection.prepareStatement(query);
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
			}

		}

		return state;

	}

	public int anDeleteMulti(String id) {

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		int state = -1;

		try {
			connection = dataSource.getConnection();
			// String query = "delete from tblmerchandise where upper(member_id) LIKE
			// upper('" + id + "')";
			String query = "delete from tblmember where upper(member_id) LIKE upper('" + id + "')";

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

		public int anProfileDeleteMulti(String id, String image) {

			Connection connection = null;
			PreparedStatement prepareStatement = null;
			ResultSet resultSet = null;

			int state = -1;

			try {
				connection = dataSource.getConnection();
				String query =  "update tblmember set member_profile = '"+ image +"' where upper(member_id) LIKE upper('" + id + "')";

				System.out.println(id);
				System.out.println(image);

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
}
