package com.korea.mall.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.korea.mall.dto.ProductDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDAO {
	
	final SqlSession sqlSession;
	
	// ��ǰ �߰�
	public int p_add(ProductDTO dto) {
		return sqlSession.insert("p.p_insert",dto);
	}
	
	// ��ǰ ��ü ��ȸ
	public List<ProductDTO> selectList(String p_name) {
		return sqlSession.selectList("p.product_list",p_name);
	}
	
//	// ��ǰ ī�װ��� ��ȸ
//	public List<ProductDTO> selectList(int p_id){
//		return sqlSession.selectList("p.product_list", p_id);
//	}
}
