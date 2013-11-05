<%! 
 int pageCount = 0;
 void addCount() {
   pageCount++;
 }
%>
<% addCount(); %>
<html>
<head>
<title>My Blog Adm</title>
</head>
<body>
    <div id="title">
<center>
<p>This site has been visited <%= pageCount %> times.</p>
My Blog!
</center>
    </div>

<br/><br/>