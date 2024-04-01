<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Form</title>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Open Sans', sans-serif;
        }
    </style>
  <script src="resources/js/HttpRequest.js"></script>
</head>
<body class="bg-gray-50 p-8">
<jsp:include page="/WEB-INF/views/main/header.jsp"></jsp:include>
    <div class="max-w-4xl mx-auto">
        <h1 class="text-3xl font-semibold mb-6">Order</h1>
        <div class="bg-white shadow rounded-lg p-6 mb-6">
            <h2 class="text-xl font-semibold mb-4">배송지</h2>
            <div class="mb-4">
                <label for="recipient" class="block text-sm font-medium text-gray-700">받는사람</label>
                <input type="text" id="recipient" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm" value="${user.u_username }">
                <input type="hidden" id="u_id" value="${user.u_id }">
            </div>
            <div class="mb-4">
                <label for="address" class="block text-sm font-medium text-gray-700">주소</label>
                <input type="text" id="address" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm" value="${user.u_ip }">
                <button type="button" class="mt-2 text-indigo-600 hover:text-indigo-900 text-sm" onclick="ipsearch(this.form)">주소 찾기</button>
            </div>
             <div class="mb-4">
                    <label for="phone" class="block text-sm font-medium text-gray-700">상세주소</label>
                    <input type="text" name="u_ipExtraAddr" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500" placeholder="상세주소">
                </div>
            <div class="mb-4">
                <label for="phone" class="block text-sm font-medium text-gray-700">핸드폰 번호</label>
                <input type="tel" id="phone" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm" value="${user.u_tel }">
            </div>
            <div class="mb-4">
                <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
                <input type="email" id="email" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm" value="${user.u_email }">
            </div>
            <div class="mb-4">
                <label for="message" class="block text-sm font-medium text-gray-700">Message</label>
                <select id="message" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm">
                    <option>--메시지 선택 (선택사항)--</option>
                    <option>배송전에 미리 연락 부탁드립니다.</option>
                    <option>부재 시 경비실에 맡겨주세요.</option>
                    <option>부재 시 문 앞에 놓아주세요.</option>
                    <option>빠른 배송 부탁드립니다.</option>
                    <option>택배함에 보관해주세요.</option>
                </select>
            </div>
        </div>

        <div class="bg-white shadow rounded-lg p-6 mb-6">
            <h2 class="text-xl font-semibold mb-4">주문 세부정보</h2>
            <c:forEach var="order" items="${order}">
            <div class="flex items-center mb-4">
                <img src="resources/product_img/${order.p_picture}" style="width: 280px; height: 230px;">
                <div>상품 번호 
                    <p class="font-semibold">${order.p_name }</p>
                    <p>${order.p_size }, ${order.p_color }</p>
                    <p>${order.b_quantity } 개</p>
                    <p class="font-semibold">${order.p_price * order.b_quantity }원</p>
                    <input type="hidden" class="total" value="${order.p_price * order.b_quantity }">
                    <input type="hidden" class="p_num" value="${order.p_num }">
                    <input type="hidden" class="b_idx" value="${order.b_idx }">
                    <input type="hidden" class="quantity" value="${order.b_quantity }">
                </div>
                
            </div>
            <hr>
            <br>
            </c:forEach>
        </div>

        <div class="bg-white shadow rounded-lg p-6 mb-6">
            <h2 class="text-xl font-semibold mb-4">결제 정보</h2>
            <div class="flex justify-between items-center mb-4">
                <p>주문 가격</p>
                <p id="totalhtml"></p>
            </div>
            <div class="flex justify-between items-center mb-4">
                <p>배송비</p>
                <p>3000원</p>
            </div>
            <div class="flex justify-between items-center font-semibold mb-4">
                <p>총 가격</p>
                <p id = "alltotalhtml"></p>
            </div>
            <div class="mb-4">
                <label for="payment-method" class="block text-sm font-medium text-gray-700">결제 수단 선택</label>
                <select id="payment-method" class="mt-1 block w-full border-gray-300 rounded-md shadow-sm">
                <option>.</option>
                    <option>계좌이체</option>
                    <option>토스</option>
                </select>
            </div>
            <button class="w-full bg-black text-white rounded-md py-3 font-semibold hover:bg-gray-700" id = "alltotalhtml2" onclick="pay()"></button>
        </div>
    </div>
      <script type="text/javascript">
    let total = 0;
   	let bsb = 0;
    let val = document.getElementsByClassName('total');
    for(let i = 0; i < val.length; i++){
    total += Number(val[i].value);
 	   bsb += 3000;
    };
 
    let totalhtml = document.getElementById("totalhtml");
    
    totalhtml.innerHTML = total.toString() + "원";
    
    let alltotal = bsb + total;
    
    let alltotalhtml =  document.getElementById("alltotalhtml");
    
    alltotalhtml.innerHTML = alltotal.toString() + "원";
    
    let alltotalhtml2 =  document.getElementById("alltotalhtml2");
    alltotalhtml2.innerHTML = alltotal.toString() + "원 결제하기";
	 
    let u_addres = document.getElementById("address").value.trim();
    function ipsearch(f){
    	
		 new daum.Postcode({
		        oncomplete: function(data) {
		        	
		        	console.log(data);
		        	let u_ipNumber='';
		        	let u_ipAddr='';
		        	let u_ipExtraAddr='';
		        	u_ipNumber = data.zonecode;
		        	
		        	if(data.userSelectedType === 'R'){
		        		u_ipAddr = data.roadAddress;
		        	}else{
		        		u_ipAddr = data.jibunAddress;
		        	}
		        	
		        	if(data.userSelectedType =='R'){
		        		if(data.bname !== ''){
		        			u_ipExtraAddr += data.bname;
		        		}
		        		if(data.buildingName !==''){
		        			u_ipExtraAddr += (u_ipExtraAddr !== '' ?',' + data.buildingName : data.buildingName);	
		        		}
		        		u_ipAddr += (u_ipExtraAddr !=='' ?'(' + u_ipExtraAddr + ')' : '');	
		        	}        	
		   	
		        	u_addres = '('+u_ipNumber+')' + u_ipAddr;
		        	
		            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
		            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
		        }
		    }).open();
	}
    
    
    function pay(){
    	let u_ipExtraAddr = document.getElementsByName('u_ipExtraAddr')[0].value.trim();
    	let u_username = document.getElementById("recipient").value;
    	let u_id = document.getElementById("u_id").value;
    	let u_addplus = u_addres + u_ipExtraAddr
    	let u_tel = document.getElementById("phone").value;
    	let u_email = document.getElementById("email").value;
    	let message = document.getElementById("message").value;
    	let p_num = document.getElementsByClassName("p_num");
    	let b_idx = document.getElementsByClassName("b_idx");
    	let quantity = document.getElementsByClassName("quantity");
    	let p_numTotal = [];
    	let b_idxTotal = [];
    	let quantityTotal = [];
    	
    	for(i =0; i < p_num.length; i++){
//     		p_numTotal = p_numTotal + p_num[i].value + ",";
    		p_numTotal.push(p_num[i].value);

    	}
    	
    	for(i =0; i < b_idx.length; i++){
//     		b_idxTotal = b_idxTotal + b_idx[i].value + ","; 
			b_idxTotal.push(b_idx[i].value);	
    	}
    	
    	for(i = 0; i < quantity.length; i++){
    		quantityTotal.push(quantity[i].value);
    	}
    	
    	let jsonString = {
    			u_usernamejs : u_username,
    			u_idjs : u_id,
    			u_addresjs : u_addres,
    			u_teljs : u_tel,
    			u_emailjs : u_email,
    			messagejs : message,
    			p_numjs : p_numTotal,
    			b_idxjs : b_idxTotal,
    			quantityjs : quantityTotal,
    			totaljs : total
    	}
    	
    	
    	
    	
    	let url = "order_pay";
    	param = JSON.stringify(jsonString);
    	
//     	sendRequest(url, param, paycallBack, "POST");
    	sendRequestContent(url, param, paycallBack, "POST" , false , "application/json");
    	
    }
    
    function paycallBack(){
    	if(xhr.readyState == 4 && xhr.status == 200){
    		let data = xhr.responseText;
			let json = (new Function('return' + data))();
			
			if(json[0].param == 'yes'){
				alert("결제 성공");
				location.href = 'main';
			}
			if(json[0].param == 'no'){
				alert("결제 실패");
			}
    	}
    }
    
	</script>
	
</body>
<jsp:include page="/WEB-INF/views/main/footer.jsp"></jsp:include>
</html>