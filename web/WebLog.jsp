<%-- 
    Document   : showPosting
    Created on : Sep 6, 2013, 7:48:25 PM
    Author     : peterk
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="model.Posting"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/blogcss.css" rel="stylesheet" type="text/css" >
        <title>JSP Page</title>
    </head>
    <body>        
        <div id="container">
        <%@ include file="includes/header.jsp" %>
          <p align="right"> <a href="goWeblogAdm" >Make new Post!</a> </p>
          <div id="leftbar">
          <form><input type="text" name="blogpost" style="width:300px; height:80px;" value="About" readonly></form><BR>
          <form><input type="text" name="blogpost" style="width:300px; height:80px;" value="Pictures" readonly></form><BR>
          <form><input type="text" name="blogpost" style="width:300px; height:140px;" value="Friends" readonly></form>
          
          </div>      
          <div id="Postings">           
            <%
                if(request.getAttribute("allPostings")!=null){
                List styles = (List) request.getAttribute("allPostings");               
                Iterator it = styles.iterator();  
                out.print("<table border=\"1\">");
                while(it.hasNext()) {
                      Posting p =(Posting) it.next();
                      out.print("<tr><td><form name=\"posts\" border=\"1\" action=\"Response\" method=\"get\">");
                      out.print(p.getTitle()+"<BR>");
                      out.print(p.getContent()+"<BR>");
                      out.print(p.getDate()+"  ");
                      out.print("<input type=\"hidden\" name=\"postId\" value=\""+p.getId()+"\"/>");
                      out.print("<input type=\"submit\" value=\"add Comment!\"/></form></td></tr> ");                    
                }}
                out.print("</table>");
            %>          
          </div>           
        </div>
    </body>
</html>
