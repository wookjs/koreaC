package com.korea.mall.dto;

import lombok.Data;

@Data
public class SearchDTO {
	 private long startdate; 
	 private long enddate;
	 private String orderstatus; 
	 private String userid ;
	 private int count;
	 private int startnum;
	 private int endnum;

}