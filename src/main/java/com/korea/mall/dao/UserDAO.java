package com.korea.mall.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.korea.mall.dto.UserDTO;

import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class UserDAO {
	private final SqlSession sqlSession;
	
	public List<UserDTO> selectList(){
		return sqlSession.selectList("user.user_list");
	}
	
	//�α��� üũ
		public UserDTO selectOne(String u_id) {
			return sqlSession.selectOne("user.loginCheck",u_id);
		}
		
		public int insert(UserDTO dto) {
			return sqlSession.insert("user.insert",dto);
		}
	
	
}
