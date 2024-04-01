package com.korea.mall.common;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CommonUrlResource implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Cacheable(value = "urlList")
	public ArrayList<String> getUrilList(){
		ArrayList<String> output  = new ArrayList<String>();
//		String url= "http://localhost:9090/mall/";
//		String resourceSrc = request.getServletContext().getRealPath("");
//		//pageURL경로 추가
//		resourceSrc += "resources/pageURL/pageURL";
//		try (BufferedReader br = new BufferedReader(new FileReader(resourceSrc))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//            	output.add(url+line);
//            }
//       
//		}catch (IOException e) {
//            e.printStackTrace();
//        }
		return output;
	}
	
}
