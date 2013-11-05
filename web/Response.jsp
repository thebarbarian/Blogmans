<%-- 
    Document   : Response
    Created on : Sep 7, 2013, 2:05:08 AM
    Author     : peterk
--%>

<%@page import="model.Comment"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="model.Posting"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/blogcss.css" rel="stylesheet" type="text/css" >
         <script type="text/javascript" src="ajax/ajax.js"></script>
         <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
         <script>
            // functie die aangeroepen moet worden met de data uit het XML bestand.
            function PutCommentInDiv(commentstring){  
                    var currentTime = new Date();
                    var month = currentTime.getMonth() + 1;
                    var day = currentTime.getDate();
                    var year = currentTime.getFullYear();
                    // Eerst moeten we alle bestaande comments ophalen
                    // Dan het nieuwe comment appenden
                    // En dan de <div id=comments> weer opnieuw invullen
                    // 1: Ophalen bestaande comments :
                    var existingComments = document.getElementById("commentsTable").innerHTML.toString();
                    var AllCommentsNoTable = existingComments.split("</tbody>"); // haal laatste table body element eraf zucht.
                    
                    // 2: Ophalen nieuw comment : (Dit is alleen het comment zelf! HTML nog maken!
                    var newComment = document.forms['newCommentsForm'].elements['newComment'].value;
                    var tijdstip = day+"-"+month+"-"+year;
                    var newCommentInHTML = "<tr><td>\n\
                    <form name=\"comments\" border=\"1\" action=\"Response\"\n\
                     method=\"get\">"+newComment+"<BR>"+tijdstip+"</form></td></tr></tbody>";
                    // nieuwe comment appenden aan existingComments :
                    var nieuweLijst = AllCommentsNoTable[0].concat(newCommentInHTML);
                    document.forms["newCommentsForm"]["newComment"].value = ""; // tekstvak opruimen
                    document.getElementById("commentsTable").innerHTML = nieuweLijst; // waarom dit niet gebeurt heeft met readystate te maken.                    
            }            
        </script>  
        
        <title>Reageer!</title>
    </head>
    <body>
       <div id="container">
        <%@ include file="includes/header.jsp" %>
          <p align="right"> <a href="goWeblog" >See all posts!</a> </p>
          <div id="leftbar">
          <form><input type="text" name="blogpost" style="width:300px; height:80px;" value="About" readonly></form><BR>
          <form><input type="text" name="blogpost" style="width:300px; height:80px;" value="Pictures" readonly></form><BR>
          <form><input type="text" name="blogpost" style="width:300px; height:140px;" value="Friends" readonly></form>
          </div>
            <div id="Postingsdiv">
                <%                             
                       Posting p = (Posting) request.getAttribute("PostForComment");
                       out.print(p.getTitle()+"<BR>");
                       out.print(p.getContent()+"<BR>");                       
                       out.print("Posted "+p.getDate());
                %>
                <table id="commentsTable" border="1">
                
                <%
                       if(request.getAttribute("allComments")!=null){
                           List comments = (List) request.getAttribute("allComments");               
                Iterator it = comments.iterator();  
                
                while(it.hasNext()) {
                      Comment c =(Comment) it.next();
                      out.print("<tr><td><form name=\"comments\" border=\"1\" action=\"Response\" method=\"get\">");
                      out.print(c.getContent()+"<BR>");
                      out.print(c.getDate()+"  ");  
                      out.print("</form></td></tr>");                                                       
                }}%>
                
                </table>
              <%               
             //   out.print("<form action=\"newComment\" id=\"newComment\" method=\"post\">");
                   out.print("<form name=\"newCommentsForm\" method=\"post\">");
                out.print("<input type=\"hidden\" name=\"postId\" value=\""+p.getId()+"\">");
                out.print("<input type=\"text\" id=\"probeersel\" name=\"newComment\" style=\"width:205px;height:80px;\"><BR>");                              
                  out.print("<input type=\"button\" value=\"Add Comment\" onClick=\"addComment("+p.getId()+");\" />");
                  out.print("</form>");
                  // via Ajax comment submitten
                %>   
            </div>
                                          
          </div>
    </body>
</html>
