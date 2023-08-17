<%@ page import="Usernames_DAO.models.profile" %>
<%@ page import="java.util.List" %>
<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="Usernames_DAO.models.Achievement" %>
<!DOCTYPE html>
<html>

<head>
  <link rel="icon" href="logo.png" />
  <title>QuizHub</title>
  <link rel="stylesheet" type="text/css" href="css/ProfileStyle.css" />
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
<form action="ChallengeFriend" method="POST">
<div style="width: 100%; margin-top:50px; display: flex; justify-content: center;">
  <div class="FriendsCard" style="background: #eee;">
    <div class="friendsTitle">
      <p>Choose Friend:</p>
    </div>
    <%
      profile profile = new profile((String)request.getSession().getAttribute("user"));
      List<String> friends = profile.getFriends();
      for(int i = 0; i < friends.size(); i++){
        User curr = User.getUser(friends.get(i));
        List<Achievement> achs = curr.getAchievement();
        int achtype = 0;
        if(achs.size() > 0) {
          Achievement last = achs.get(achs.size() - 1);
          achtype = last.getAchievment_id();
          if (achtype == 5) {
            achtype = 6;
          } else if (achtype == 6) {
            achtype = 5;
          }
        }
    %>
    <button class="friendsln" name = "button<%=curr.getUsername()%>" value="val" style="cursor: pointer; border: none;">
      <div class="friendName">
        <img src="assets/creatorWhite.svg" alt="Medal icon" width="35" ; />
        <p class="friendLink"><%=curr.getUsername()%></p>
      </div>
      <%
        if (achtype != 0) {
      %>
      <img src="assets/medal<%=achtype%>.svg" alt="Medal icon" width="35" ; />
      <%
        }
      %>
    </button>
    <%
      }
    %>
  </div>
</div>
</form>
</body>
<%
  }
%>
</html>
