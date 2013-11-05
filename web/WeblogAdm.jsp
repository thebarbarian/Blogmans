<%-- 
    Document   : addPosting
    Created on : Sep 4, 2013, 9:13:10 PM
    Author     : peterk
--%>

<%@page import="model.Posting"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link href="css/blogcss.css" rel="stylesheet" type="text/css" > 
         <script type="text/javascript" src="ajax/ajax.js"></script>
         <script>
             function vulTitelEnContentVelden(Title,Content){
                 // invullen veldjes na op de edit knop gedrukt te hebben.                
                 document.forms["PostingWindow"]["postTitle"].value = Title;
                 document.forms["PostingWindow"]["postContent"].value = Content;        
             }
             
         </script>
         
        <title>WebLog Administration</title>
    </head>
    <body>
        <%@ include file="includes/header.jsp" %>
        <p align="right"> <a href="goWeblog" >View my Blog!</a> </p>
        
        <%
       // out.print("<form action=\"postMessage\" name =\"PostingWindow\" method=\"post\">");
        String GekozenId="n";        
        out.print("<form name =\"PostingWindow\" action=\"postMessage\" method=\"post\">");
        out.print("Title : <input type=\"text\" name=\"postTitle\" style=\"width:205px;\"><BR>");
        out.print("Post  : <input type=\"text\" name=\"postContent\" style=\"width:205px; height:80px;\">");        
        
        if(request.getParameter("postId")!=null){         
                GekozenId = request.getParameter("postId");
                out.print("<input type=\"hidden\" name=\"postId\" value=\""+GekozenId+"\">");        
                out.print("<input type=\"submit\" value=\"add Posting\" onClick=\"replacePost("+GekozenId+")\" ></form>");               
                } else if (request.getParameter("postId")==null) {
                    GekozenId="n";            
                    out.print("<input type=\"submit\" value=\"add Posting\" onClick=\"addPosting()\" ></form>");           
                    }        
        %>
        <BR>
        
         <BR>
        
         <%
         out.print("<form action=\"setAdminMode\" method=\"post\">");
         out.print("<button name =\"ModeKnop\" type =\"submit\" value=\"BasicMode\">Basic</button>");
         out.print("<button name =\"ModeKnop\" type =\"submit\" value=\"AdvancedMode\">Advanced</button>"
                 + "</form>");                    
        %>
         
         <%              
             //if (request.getAttribute("AdminMode")=="Advanced")
             if(session.getAttribute("AdminMode")=="Advanced")
             {
                 out.print("Advanced Mode. Destruction muhahahaha!<BR><BR><BR>");                 
                 
                if(session.getAttribute("allPostings")!=null){
                List styles = (List) session.getAttribute("allPostings");               
                Iterator it = styles.iterator();  
                out.print("<table border=\"1\">");
                while(it.hasNext()) {
                      Posting p =(Posting) it.next();
                      out.print("<tr><td><form name=\"posts\" border=\"1\" >");
                      out.print(p.getTitle()+"<BR>");
                      out.print(p.getContent()+"<BR>");
                      out.print(p.getDate()+"  ");
                      out.print("<input type=\"hidden\" name=\"EditPostId\" value=\""+p.getId()+"\" />");
                      // zet de waardes in titel en content veld, doet niets serverside !
                      out.print("<input type=\"button\" value=\"Edit\" onClick=\"selectEditPost("+p.getId()+",'"+p.getTitle()+"','"+p.getContent()+"');\" />");  
                      out.print("<input type=\"button\" value=\"Destroy\" onClick=\"removePost("+p.getId()+");\" />");
                      out.print("</form></td></tr>");
                      //out.print("<button type=\"submit\" value=\"Delete\"/></form></td></tr> ");                    
                }}
                out.print("</table>");                
             }             
         %>   
    </body>
</html>
