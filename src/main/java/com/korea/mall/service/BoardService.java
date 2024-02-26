package com.korea.mall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.korea.mall.dao.BoardDAO;
import com.korea.mall.dto.BoardDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardDAO boardDao;
	
	public List<BoardDTO> selectList(BoardDTO param){
		
		return boardDao.selectList(param);
	}
	
	
	public BoardDTO selectOne(BoardDTO param){
		
//		�ʼ��� üũ
		if("".equals( param.getSeq()) || null == param.getSeq()) {
			//����
		}
		
		return boardDao.selectOne(param);
	}
	
	
}
