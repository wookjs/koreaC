<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.tailwindcss.com"></script>
<script type="text/javascript">
	window.onload = function() {
		
		document.getElementById("basketBtn").addEventListener( 'click' , function(e) {
		    console.log(e)
		    console.log("바스켓이동")
		    location.href = "basket_insert?pronum="+document.getElementsByName("p_num")[0].value
		    
		});
		
		document.getElementById("orderBtn").addEventListener( 'click' , function(e) {
		    console.log(e)
		    console.log("오더이동")
		    location.href = "order_page?pronum="+document.getElementsByName("p_num")[0].value+"&num=1";
		    
		});
		document.getElementById("backBtn").addEventListener( 'click' , function(e) {
			history.back()
		});
	}

</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/main/header.jsp"></jsp:include>
		<aside class="float-left w-64 bg-white p-6 shadow-md">
            <div class="flex flex-col space-y-4">
                <a href="new"><button class="text-gray-700 font-semibold">New</button></a>
                <a href="category?p_id=1"><button class="text-gray-700 font-semibold">Outer</button></a>
                <a href="category?p_id=2"><button class="text-gray-700 font-semibold">Top</button></a>
                <a href="category?p_id=3"><button class="text-gray-700 font-semibold">Bottom</button></a>
                <a href="category?p_id=4"><button class="text-gray-700 font-semibold">Suit</button></a>  
            </div>
            <br><br><br><br><br><br>
            <div>
	          	<a href="basket_page">
		           	<button class="text-gray-700 font-semibold">
		           		<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-10 h-10">
						  <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 0 0-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 0 0-16.536-1.84M7.5 14.25 5.106 5.272M6 20.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Zm12.75 0a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z" />
						</svg>
		           	</button>
	          	</a>
            </div>
        </aside>
		<input type="hidden" name="p_num" value="${dto.p_num}">
	<div class="grid grid-cols-2 grid-rows-2">
		<img class="w-4/5 h-800" src="resources/product_img/${dto.p_picture}">
		<div>
			<strong>${dto.p_name}</strong><br>
			<strong>${dto.p_price}원</strong>
			<div>
				상품설명 : 
				${dto.p_explain}
			</div>
			<div>
				원단 : 
				${dto.p_pabric}
			</div>
			<div>
				색상 : 
	            <select name="p_color">
					<option value="black">black</option>
					<option value="black">white</option>
					<option value="black">red</option>
					<option value="black">green</option>
				</select>
			</div>
			<div>
				사이즈 : 
				 <select name="p_size">
					<option value="M">M</option>
					<option value="L">L</option>
					<option value="XL">XL</option>
				</select>
			</div>
			<div class="flex mt-4 space-x-2">
				<button id="basketBtn" class="px-4 py-2 text-black text-white-500 hover:text-white-800">장바구니</button>
		   		<button id="orderBtn" class="px-4 py-2 text-black text-white-500 hover:text-white-800">구매하기</button>
		   		<button id="backBtn" class="px-4 py-2 text-black text-white-500 hover:text-white-800">뒤로가기</button>
	   		</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/main/footer.jsp"></jsp:include>
</body>
</html>