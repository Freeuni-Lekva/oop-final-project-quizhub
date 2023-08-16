<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/AnnouncementCreateStyle.css" />
</head>
    <%
    if(request.getSession().getAttribute("user") == null){
%>
<body style ="height: 820px; overflow-y: hidden; display: flex; flex-direction: column; justify-content: center; align-items: center">
<p style="font-size: 68px; color: white">Did you get lost?</p>
<p style="font-size: 68px; color: white">You can't get in without log in! ;)</p>
</body>
<%
        }else{
%>
<body style="overflow-y: hidden;">
<div class="top">
    <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
        <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
        <a href="Homepage.jsp">
            <p class="logo">QuizHub</p>
        </a>
    </div>
    <div class = "endButtons">
        <a href="Homepage.jsp" class="eButton">Exit</a>
    </div>
</div>
<hr style="width: 100%; height: 1px; color: #FFF;">
</hr>
<form action = "AnnouncementCreate" method = "POST">
<div class="content">
    <div class="rightPannel">
        <div class="pannelRow">
            <p>Enter announcement name:</p>
            <div class="nameInput">
                <input type="text" class = "nameInp" name="name"  placeholder="Type name here...">
            </div>
        </div>
        <div class="pannelRow">
            <p>Write announcement description:</p>
        </div>
        <div class="descDiv">
                <textarea class="descTextArea"
                          placeholder="Type description here..." name = "desc"></textarea>
        </div>
        <div style = "width: 1100px; display: flex; justify-content: center;">
            <button type="submit" class="sButton" name = "saveButton" val= "val">Save details and move to<br>the questions section!</button>
        </div>
        <%
            request.getSession().setAttribute("annCreated", "true");
        %>
    </div>
</div>
</form>
</body>
<%
    }
%>
</html>