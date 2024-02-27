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
	
	// ��ǰ ī�װ��� ��ȸ
	public List<ProductDTO> selectList(int p_id){
		return sqlSession.selectList("p.product_list", p_id);
	}
}
