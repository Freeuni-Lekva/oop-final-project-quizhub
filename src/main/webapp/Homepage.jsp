<%@ page import="Usernames_DAO.models.Achievement" %>
<%@ page import="java.util.List" %>
<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ParameterMetaData" %>
<%@ page import="Usernames_DAO.manager.HomepageManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Usernames_DAO.manager.AdminManager" %>
<%@ page import="Usernames_DAO.models.Announcement" %>
<%@ page import="DATABASE_DAO.Database" %>
<%@ page import="Usernames_DAO.models.profile" %>
<!DOCTYPE html>
<html>

<head>
  <link rel="icon" href="logo.png" />
  <title>QuizHub</title>
  <link rel="stylesheet" type="text/css" href="css/HomePageStyle.css" />
  <%
    request.getSession().setAttribute("quiz", null);
    request.getSession().setAttribute("correction", null);
    request.getSession().setAttribute("new quiz", null);
    request.getSession().setAttribute("pageType", null);
    request.getSession().setAttribute("QuestionType", null);
    request.getSession().setAttribute("rowNum", null);
    request.getSession().setAttribute("result page", null);
    request.getSession().setAttribute("quizSummary", null);
    request.getSession().setAttribute("LastDaySwitch", null);
    request.getSession().setAttribute("order", null);
    request.getSession().setAttribute("TopFriendsSwitch", null);
    request.getSession().setAttribute("quizSummaryPage", null);
    request.getSession().setAttribute("profilePage", null);
    request.getSession().setAttribute("challenge", null);
    request.getSession().setAttribute("annCreated", null);
    request.getSession().setAttribute("challengeID", null);
  %>
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
<body style ="overflow-y: hidden;">
<div class="top">
  <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
    <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
    <a href="Homepage.jsp">
      <p class="logo">QuizHub</p>
    </a>
  </div>
  <div style="display: flex; flex-direction: row; gap: 30px; padding-right: 30px;">
    <form action = "Search" method = "POST">
    <div style="position: relative;">
      <input placeholder="Search..." class="search" name = "searchField" />
      <button class="cButton" name = "searchButton" value="search"><img src="assets/search.svg" alt="Search button" width="34"
                                                 height="34" ; /></button>
    </div>
    </form>
    <a href = "QuizCreate.jsp" class="addButton"><img src="assets/addQuiz.svg" alt="Add quiz button" width="50"
                                                      height="50" ; /></a>
    <div style="position: relative;">
      <a href="Inbox.jsp" style="margin-top: -2px"><img src="assets/inbox.svg" alt="Inbox button" height="52"
                                                         ; /></a>
      <%
        profile profile = new profile((String)request.getSession().getAttribute("user"));
        if(profile.getNotification("message")){
      %>
      <a href="Inbox.jsp" style="position: absolute; bottom: -15px; right: -15px;"><img
              src="assets/notificationMessage.svg" alt="Notification Icon" height="30" ; /></a>
    <%
      }else if(profile.getNotification("request")){
    %><a href="Inbox.jsp" style="position: absolute; bottom: -15px; right: -15px;"><img
            src="assets/notificationAddFriends.svg" alt="Notification Icon" height="30" ; /></a>
      <%
        }else if(profile.getNotification("challenge")){
      %>
      <a href="Inbox.jsp" style="position: absolute; bottom: -15px; right: -15px;"><img
              src="assets/notificationChallange.svg" alt="Notification Icon" height="30" ; /></a>
      <%
        }
      %>
    </div>
    <a href="Profile.jsp"><img src="assets/profile.svg" alt="Profile button" width="50" height="50" ; /></a>
  </div>
</div>
<hr style="width: 100%; height: 1px; color: #FFF;">
</hr>
<div class="content">
  <div class="leftPannel">
    <div class="recents">
      <p>My Recent Activities</p>
    </div>
    <div class="quizCards">
      <div class="whiteQuizCard" style="overflow: hidden">
        <div style="position: relative;">
          <p class="whiteQuizCardTitleStroke">Taken Quizzes</p>
          <p class="whiteQuizCardTitle">Taken Quizzes</p>
        </div>
        <%
          User user = User.getUser((String) request.getSession().getAttribute("user"));
          List<Pair<Integer, String>> takenQuizes = null;
          try {
            takenQuizes = user.getRecentTakenQuizes();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
          for(int i = 0; i < takenQuizes.size(); i++){
            String script = "";
            script += "<div class=\"whiteQuiz\">\n" +
                    "            <img src=\"assets/done.svg\" alt=\"Quiz taken\" width=\"20\" height=\"20\" ; />\n" +
                    "            <a href=\"QuizSummary.jsp?quizID=";
            script += Integer.toString(takenQuizes.get(i).getKey());
            script += "\" class=\"whiteQuizCardLink\">";
            script += takenQuizes.get(i).getValue();
            script += "</a>";
            script += "</div>";
            out.println(script);
          }
          if(takenQuizes.size() < 10){
            out.println(
                    "<p class=\"whiteQuizNoMore\">No More Quiz Taken.</p>"
            );
          }
        %>
      </div>
      <div class="whiteQuizCard" style = "overflow: hidden">
        <div style="position: relative;">
          <p class="whiteQuizCardTitleStroke">Created Quizzes</p>
          <p class="whiteQuizCardTitle">Created Quizzes</p>
        </div>
        <%
          List<Pair<Integer, String>> createdQuizes = null;
          try {
            createdQuizes = user.getRecentCreatedQuizes();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
          for(int i = 0; i < createdQuizes.size(); i++){
            String script = "";
            script += "<div class=\"whiteQuiz\">\n" +
                    "            <img src=\"assets/create.svg\" alt=\"Quiz created\" width=\"20\" height=\"20\" ; />\n" +
                    "            <a href=\"QuizSummary.jsp?quizID=";
            script += Integer.toString(createdQuizes.get(i).getKey());
            script += "\" class=\"whiteQuizCardLink\">";
            script += createdQuizes.get(i).getValue();
            script += "</a>";
            script += "</div>";
            out.println(script);
          }
          if(createdQuizes.size() < 10){
            out.println(
                    "<p class=\"whiteQuizNoMore\">No More Quiz Created.</p>"
            );
          }
        %>
      </div>
    </div>
    <hr style="width: 100%; height: 1px;">
    </hr>
    <div class="achievementsContainer">
      <div class="achievements">
        <img src="assets/medal.svg" alt="Achievements" width="28" ; />
        <p style="padding-bottom: 3px">My Achievements</p>
      </div>
      <div class="awardList">
        <%
          List<Achievement> achievements = user.getAchievement();
          String script = "";
          for(int i = 0; i < achievements.size(); i++){
            script += "<div class=\"award\"> <img src=\"assets/medal";
            int achievementID = achievements.get(i).getAchievment_id();
            if(achievementID == 1){
              script += "1.svg\" alt=\"Medal icon\" width=\"28\" ; />\n" +
                      "          <p>Amateur Author - You created a quiz.</p>\n" +
                      "        </div>";
            }else if(achievementID == 2){
              script += "2.svg\" alt=\"Medal icon\" width=\"28\" ; />\n" +
                      "          <p>Prolific Author - You created five quizzes.</p>\n" +
                      "        </div>";
            }else if(achievementID == 3){
              script += "3.svg\" alt=\"Medal icon\" width=\"28\" ; />\n" +
                      "          <p>Prodigious Author - created ten quizzes.</p>\n" +
                      "        </div>";
            }else if(achievementID == 4){
              script += "4.svg\" alt=\"Medal icon\" width=\"28\" ; />\n" +
                      "          <p>Quiz Machine - You took ten quizzes.</p>\n" +
                      "        </div>";
            }else if(achievementID == 6){
              script += "5.svg\" alt=\"Medal icon\" width=\"28\" ; />\n" +
                      "          <p>Practice Makes Perfect - You used practice mode.</p>\n" +
                      "        </div>";
            }else if(achievementID == 5){
              script += "6.svg\" alt=\"Medal icon\" width=\"28\" ; />\n" +
                      "          <p>I am the Greatest - You had the highest score.</p>\n" +
                      "        </div>";
            }
          }
          script += "</div>";
          if(achievements.size() < 6){
            script += "<p class=\"achievementsNoMore\">No More Awards Received.</p>";
          }
          out.println(script);
        %>
      </div>
      <hr style="width: 100%; height: 1px;">
      </hr>
        <%
          user = User.getUser((String) request.getSession().getAttribute("user"));
          if(user.isAdmin()){
            AdminManager admin = new AdminManager((String) request.getSession().getAttribute("user"));
        %>
        <div style="color: white; margin-top: -15px; display: flex; flex-direction: column; gap: 5px; align-items: center;">
          <p style="font-size: 30px;">Website Statistics</p>
          <p style="font-size: 20px;">Total number of users: <%=admin.numOfUsers()%></p>
          <p style="font-size: 20px;">Total number of quizzes taken: <%=admin.numOfQuizes()%></p>
        </div>
        <%
          }else{
        %>
      <div class="recents">
        <a href="Profile.jsp" class="whiteQuizCardLink">My Activity History</a>
      </div>
        <%
          }
        %>
  </div>
    <hr style="width: 1px; height: 840px;">
    </hr>
    <div class="announcements">
      <%
        user = User.getUser((String) request.getSession().getAttribute("user"));
        if(user.isAdmin()){
      %>
      <a href = "AnnouncementCreate.jsp" class = "addAnnouncement">
        <p>Add Announcement</p>
      </a>
      <%
        }
        HomepageManager homepageManager = (HomepageManager) request.getSession().getAttribute("homepage");
        ArrayList<Announcement> anns = homepageManager.getAnnouncements();
        for(int i = anns.size() - 1; i >= 0; i--){
          Announcement curr = anns.get(i);
      %>
      <div class="announcement">
        <div class="announcementTitle">
          <p class="announcementName" style="max-width: 600px; overflow: hidden"><%=curr.getSubject()%></p>
          <div class="creatorTitle">
            <a href="Profile.jsp?username=<%=curr.getName()%>" class="creatorLink" style="max-width: 300px; overflow: hidden"><%=curr.getName()%></a>
            <img src="assets/creator.svg" alt="Creator icon" width="40"></img>
          </div>
        </div>
        <div class="announcementContent">
          <p class="text"> <%=curr.getText()%>
          <p>
        </div>
      </div>
      <%
        }
      %>
    </div>
    <div class="rightPannel">
      <div class="friendsActivities">
        <div class="friendsActivitiesTitle">
          <p>Friends' Activities</p>
        </div>
        <div class="timeLine"></div>
        <%
          ArrayList<Pair<String,Pair<Boolean,Pair<String,Integer>>>> lss =  User.getUser((String)request.getSession().getAttribute("user")).FriendsActivity();
          int num = lss.size();
          if(num != 0){
          for(int i = Math.max(0, num - 1); i >= Math.max(0, num - 5) ; i--){
            Pair<String,Pair<Boolean,Pair<String,Integer>>> curr = lss.get(i);
        %>

        <div class="activity">
          <div class="friendName">
            <hr class="dot">
            <a href="Profile.jsp?username=<%=curr.getKey()%>" class = "frName" ><%=curr.getKey()%></a>
            <%
              List<Achievement> achs = User.getUser(curr.getKey()).getAchievement();
              if(achs.size() != 0){
                for(int j = achs.size() - 1; j >= Math.max(0, achs.size() - 3); j--){
                Achievement currAch = achs.get(j);
                int type = currAch.getAchievment_id();
                if(type == 5){
                  type = 6;
                }else if(type == 6){
                  type = 5;
                }
            %>
            <img src="assets/medal<%=type%>.svg" alt="Medal icon" width="25" height="25">
            <%
                }
              }
            %>
          </div>
          <div class="friendQuiz" >
            <%
              if(curr.getValue().getKey()){
            %>
            <p>Created Quiz:&nbsp</p>
            <%
              }else{
            %>
            <p>Taken Quiz:&nbsp</p>
            <%
              }
            %>
            <a href="QuizSummary.jsp?quizID=<%=curr.getValue().getValue().getValue()%>" class="friendQuizName" style="max-width: 200px; overflow: hidden;"><%=curr.getValue().getValue().getKey()%></a>
          </div>
        </div>
        <%
            }
          }
          if(num < 5){
        %>
        <div class="noMoreActivity">
          <hr class="emptyDot">
          <p>No More Activities.</p>
        </div>
        <%
          }
        %>
      </div>
      <div class="blueQuizCard">
        <div class="blueQuizCardTitle">
          <p>Popular</p>
          <form action = "HomePage" method = "POST">
            <%
              boolean recent = false;
              if (request.getSession().getAttribute("recentSwitch") == null || request.getSession().getAttribute("recentSwitch").equals("popular")){
            %>
            <button name = "popularOrRecent" value = "popular" class = "switch">
              <hr style="width: 24px; height: 24px; border-radius: 50%; background: white; margin-left: 3px;">
            </button>
            <%
            }else{
            %>
            <button name = "popularOrRecent" value = "recent" class = "switch" style = "direction: rtl;">
              <hr style="width: 24px; height: 24px; border-radius: 50%; background: white; margin-right: 3px;">
            </button>
            <%
                recent = true;
              }
            %>
          </form>
          <p>Recent</p>
        </div>
        <div class="blueQuizCardList" style="overflow:hidden;">
          <%
            List<Pair<Pair<Integer, Integer>, String>> ls;
            if(recent){
              ls = homepageManager.getRecentQuizes();
            }else{
              ls = homepageManager.getPopularQuizes();
            }

            for (int i = 0; i < ls.size(); i++) {
          %>
          <div class="blueQuizCardLine">
            <div class="blueQuizCardQuiz">
              <img src="assets/doneBlue.svg" alt="Quiz" width="22" height="22" ; />
              <a href="<%="QuizSummary.jsp?quizID=" + ls.get(i).getKey().getKey()%>" class="blueQuizCardLink"><%=ls.get(i).getValue()%></a>
            </div>
            <div class="blueQuizCardParticipant">
              <%=ls.get(i).getKey().getValue()%>
              <img src="assets/group.svg" alt="Participants Icon" width="22" height="22" ; />
            </div>
          </div>
          <%
            }
          %>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<%
  }
%>
</html>