<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>KoreaMall</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
<style>
    body {
        font-family: 'Roboto', sans-serif;
    }
</style>
</head>
<jsp:include page="/WEB-INF/views/main/header.jsp"></jsp:include>
<body class="bg-gray-50">
	<div class="relative h-full flex">
        <aside class="float-left w-64 bg-white p-6 shadow-md">
            <div class="flex flex-col space-y-4">
                <a href="new"><button class="text-gray-700 font-semibold">New</button></a>
                <a href="top"><button class="text-gray-700 font-semibold">Top50</button></a>
                <a href="category?p_id=1"><button class="text-gray-700 font-semibold">Outer</button></a>
                <a href="category?p_id=2"><button class="text-gray-700 font-semibold">Top</button></a>
                <a href="category?p_id=3"><button class="text-gray-700 font-semibold">Bottom</button></a>
                <a href="category?p_id=4"><button class="text-gray-700 font-semibold">Suit</button></a>  
            </div>
            <br><br><br>
            <div>
            	<a href="basket_page"><button class="text-gray-700 font-semibold">Cart</button></a>
            </div>
        </aside>
        <main class="flex-1 overscroll-auto static">
            <div class="py-6 px-4 sm:px-6 lg:px-8">
                <h2 class="text-2xl font-bold text-gray-900 mb-6">Top Seller</h2>
                <button>
	                <div class="grid grid-cols-6 gap-4 mb-6">
	                    <div class="w-24 h-24 bg-gray-200" style="width: 280px; height: 230px;">
	                        <img src="resources/product_img/${dto.p_name}/${dto.p_picture}">
	                    </div>
	                </div>
                </button>

                <h2 class="text-2xl font-bold text-gray-900 mb-6">New</h2>
                <c:forEach var="dto" items="${list}">
					<form action="detail" name="f" method="post">
						<input type="hidden" name="p_num" value="${dto.p_num}">
						<button>
			                <div class="grid grid-cols-6 gap-4">
			                    <div class="w-24 h-24 bg-gray-200" style="width: 280px; height: 230px;">
			                        <img src="resources/product_img/${dto.p_name}/${dto.p_picture}">
			                        <h2 class="text-sm mt-2" align="left">${dto.p_name}</h2>
		                    		<p class="text-sm text-gray-600" align="left">${dto.p_price}원</p>
			                    </div>
			                </div>
	                	</button>
		    		</form>
				</c:forEach>
            </div>
        </main>
    </div>
    <jsp:include page="/WEB-INF/views/main/footer.jsp"></jsp:include>
</body>
</html>

