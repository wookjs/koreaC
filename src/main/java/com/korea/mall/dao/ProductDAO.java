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
	public List<ProductDTO> selectList() {
		return sqlSession.selectList("p.product_list");
	}
	
	// ��ǰ �ϳ� ��ȸ
	public ProductDTO selectOne(int p_num) {
		return sqlSession.selectOne("p.p_selectOne", p_num);
	}
	
	// ��ǰ �˻�
	public List<ProductDTO> search(String p_name) {
		return sqlSession.selectList("p.p_search", p_name);
	}
	
//	// ��ǰ ī�װ��� ��ȸ
//	public List<ProductDTO> selectList(int p_id){
//		return sqlSession.selectList("p.product_list", p_id);
//	}
}
