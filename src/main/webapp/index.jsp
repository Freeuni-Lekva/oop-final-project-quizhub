<%@ page import="Usernames_DAO.models.User" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/LogInStyle.css" />
    <%
        request.getSession().setAttribute("user", null);
    %>
</head>

<body>
<div style="
        display: flex;
        justify-content: center;
        align-items: center;
        position: relative;
      ">
    <div class="logo">
        <img src="logo.png" alt="QuizHub Logo" width="127" height="127" ; />
    </div>
    <div style="display: flex; flex-direction: column; gap: 40px">
        <div class="title">
            <div class="bigTitle">Welcome to QuizHub!</div>
            <div class="smallTitle">Please log in</div>
        </div>
        <div style="display: flex; flex-direction: column; gap: 25px; width: 445px">
            <form action = "LogIn" method = "POST">
                <div class="inputs">
                    <input name = "username" type="username" placeholder="Your Username" class="input" />
                    <input name = "password" type="password" placeholder="Your Password" class="input" />
                    <button type="submit" class="cButton">Log In</button>
                </div>
            </form>
            <a href="Register.jsp" class="cLink">Create New Account</a>
        </div>
    </div>
</div>
</body>

</html>