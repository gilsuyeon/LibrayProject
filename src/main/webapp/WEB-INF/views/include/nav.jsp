<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@page import="com.goodee.library.member.MemberVo" %>
<link href="<c:url value='/resources/css/include/nav.css'/>" rel="stylesheet" type="text/css">
<nav>
   <div id="nav_wrap">
   	  <%
   	  		MemberVo loginedMember = (MemberVo)session.getAttribute("loginMember");
   	  		if(loginedMember == null) {
   	  %>			
   	  <div class="menu">
         <ul>
            <li>
               <a href="<c:url value='/member/login'/>">로그인</a>
            </li>
            <li>
               <a href="<c:url value='/member/create'/>">회원가입</a>
            </li>
         </ul>
      </div>
      <div class="search">
         <form>
            <input type="text">
            <input type="button" value="검색">
         </form>
      </div>			
   	  <%} else{%>
   	 <div class="menu">
         <ul>
            <li>
               <a href="<c:url value='/member/logout'/>">로그아웃</a>
            </li>
            <li>
               <a href="<c:url value='/member/${loginMember.m_no }' />"> 계정수정</a>
               
            </li>
            <li>
               <a href="<c:url value='/member'/>">회원목록</a>
            </li>
            <li>
            	<a href="<c:url value='/member/${loginMember.m_no}' />"> 계정수정</a>

            </li>
            <li>
            	<a href="<c:url value='/book/create'/>">도서등록</a>
            </li>
            <li>
               <a href="<c:url value='/book/modify/1'/>">도서 수정</a>
            </li>
              <li>
               <a onclick="deleteBook('1');">도서 삭제</a>
            </li>
         </ul>
      </div>
      <div class="search">
         <form>
            <input type="text">
            <input type="button" value="검색">
         </form>
      </div>
      <%} %>
   </div>
</nav>    
<script type="text/javascript">
   function deleteBook(bookNo) { 
	   let result = confirm('해당 도서를 정말 삭제하시겠습니까?');
	   
	   if(result) {  
		   fetch('/book/'+bookNo,{
			   method:'DELETE',
			   headers:{
				   'Content-type':'application/jason;charset=utf-8'
			   }
		   })
		   .then(response => response.text())
		   .then(data => { 
			   if(data === '400') {
				   alert('삭제성공');
				   location.replace('/book');
			   }else{
				   alert('삭제실패');
			   }
		   })
		   .catch(error => {
			   console.log(error);
		   });
	   }
   }
   </script>