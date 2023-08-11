<%@ page import="Usernames_DAO.models.Achievement" %>
<%@ page import="java.util.List" %>
<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ParameterMetaData" %>
<%@ page import="Usernames_DAO.manager.HomepageManager" %>
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
  %>
</head>

<body style ="overflow-y: hidden;">
<div class="top">
  <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
    <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
    <a href="Homepage.jsp">
      <p class="logo">QuizHub</p>
    </a>
  </div>
  <div style="display: flex; flex-direction: row; gap: 30px; padding-right: 30px;">
    <div style="position: relative;">
      <input type="search" placeholder="Search..." class="search" />
      <button type="submit" class="cButton"><img src="assets/search.svg" alt="Search button" width="34"
                                                 height="34" ; /></button>
    </div>
    <a href = "QuizCreate.jsp" class="addButton"><img src="assets/addQuiz.svg" alt="Add quiz button" width="50"
                                                      height="50" ; /></a>
    <div style="position: relative;">
      <a href="Inbox.html" style="margin-top: -2px"><img src="assets/inbox.svg" alt="Inbox button" height="52"
                                                         ; /></a>
      <a href="Inbox.html" style="position: absolute; bottom: -15px; right: -15px;"><img
              src="assets/notificationMessage.svg" alt="Notification Icon" height="30" ; /></a>
    </div>
    <a href="Profile.html"><img src="assets/profile.svg" alt="Profile button" width="50" height="50" ; /></a>
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
          User user = (User) request.getSession().getAttribute("user");
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
    <div class="recents">
      <a href="Profile.html" class="whiteQuizCardLink">My Activity History</a>
    </div>
  </div>
  <hr style="width: 1px; height: 840px;">
  </hr>
  <div class="announcements">
    <div class="announcement">
      <div class="announcementTitle">
        <p class="announcementName" style="max-width: 600px; overflow: hidden">Announcement_name</p>
        <div class="creatorTitle">
          <a href="profile.html" class="creatorLink" style="max-width: 300px; overflow: hidden">Creator_name</a>
          <img src="assets/creator.svg" alt="Creator icon" width="40"></img>
        </div>
      </div>
      <div class="announcementContent">
        <p class="text"> Lorem ipsum.
        <p>
      </div>
    </div>
  </div>
  <div class="rightPannel">
    <div class="friendsActivities">
      <div class="friendsActivitiesTitle">
        <p>Friends' Activities</p>
      </div>
      <div class="timeLine"></div>
      <div class="activity">
        <div class="friendName">
          <hr class="dot">
          </hr>
          <p>Friend_name</p>
          <img src="assets/medal4.svg" alt="Medal icon" width="25" height="25"></img>
          <img src="assets/medal6.svg" alt="Medal icon" width="25" height="25"></img>
        </div>
        <div class="friendQuiz">
          <p>Taken Quiz:&nbsp</p>
          <a href="QuizSummary.html" class="friendQuizName" style="max-width: 200px; overflow: hidden">quiz_name</a>
        </div>
      </div>
      <div class="activity">
        <div class="friendName">
          <hr class="dot">
          <p>Friend_name</p>
          <img src="assets/medal2.svg" alt="Medal icon" width="25" height="25"></img>
          <img src="assets/medal5.svg" alt="Medal icon" width="25" height="25"></img>
          <img src="assets/medal6.svg" alt="Medal icon" width="25" height="25"></img>
        </div>
        <div class="friendQuiz">
          <p>Taken Quiz:&nbsp</p>
          <a href="QuizSummary.html" class="friendQuizName">quiz_name</a>
        </div>
      </div>
      <div class="activity">
        <div class="friendName">
          <hr class="dot">
          </hr>
          <p>Friend_name</p>
          <img src="assets/medal1.svg" alt="Medal icon" width="25" height="25"></img>
        </div>
        <div class="friendQuiz">
          <p>Taken Quiz:&nbsp</p>
          <a href="QuizSummary.html" class="friendQuizName">quiz_name</a>
        </div>
      </div>
      <div class="activity">
        <div class="friendName">
          <hr class="dot">
          </hr>
          <p>Friend_name</p>
          <img src="assets/medal3.svg" alt="Medal icon" width="25" height="25"></img>
          <img src="assets/medal5.svg" alt="Medal icon" width="25" height="25"></img>
        </div>
        <div class="friendQuiz">
          <p>Taken Quiz:&nbsp</p>
          <a href="QuizSummary.html" class="friendQuizName">quiz_name</a>
        </div>
      </div>
      <div class="noMoreActivity">
        <hr class="emptyDot">
        </hr>
        <p>No More Activities.</p>
      </div>
    </div>
    <div class="blueQuizCard">
        <div class="blueQuizCardTitle">
          <p>Popular</p>
          <form action = "HomePage" method = "POST">
            <%
              if (request.getSession().getAttribute("recentSwitch") == null){
            %>
              <button name = "popularOrRecent" class = "switch">
                <hr style="width: 24px; height: 24px; border-radius: 50%; background: white; margin-left: 3px;">
              </button>
            <%
              }else{
            %>
              <button name = "popularOrRecent" class = "switch" style = "direction: rtl;">
                <hr style="width: 24px; height: 24px; border-radius: 50%; background: white; margin-right: 3px;">
              </button>
            <%
              }
            %>
          </form>
          <p>Recent</p>
        </div>
      <div class="blueQuizCardList" style="overflow:hidden;">
        <%
          List<Pair<Pair<Integer, Integer>, String>> ls;
          HomepageManager homepageManager = (HomepageManager) request.getSession().getAttribute("homepage");
          try {
            if (request.getSession().getAttribute("recentSwitch") == null) {
              ls = homepageManager.getPopularQuizes();
            } else {
              ls = homepageManager.getRecentQuizes();
            }
          } catch (SQLException e) {
            throw new RuntimeException(e);
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

</html>