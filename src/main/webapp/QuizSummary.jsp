<%@ page import="Usernames_DAO.UserQuiz.SummaryQuiz" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="Usernames_DAO.models.User" %>
<!DOCTYPE html>
<html>

<head>
  <link rel="icon" href="logo.png" />
  <title>QuizHub</title>
  <link rel="stylesheet" type="text/css" href="css/QuizSummaryStyle.css" />
</head>

<body style ="overflow-y: hidden;">
<form action = "QuizSummary" method = "POST">
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
    <div class="QuizCard">
      <div class="QuizDescCard">
        <%
          SummaryQuiz quiz = null;
          User user = (User) request.getSession().getAttribute("user");
          request.getSession().setAttribute("recentSwitch", null);
          request.getSession().setAttribute("quizSummaryPage", 1);
          if (request.getSession().getAttribute("quizSummary") != null) {
            quiz = (SummaryQuiz) request.getSession().getAttribute("quizSummary");
          } else {
            try {
              quiz = new SummaryQuiz(Integer.parseInt(request.getParameter("quizID")));
              request.getSession().setAttribute("quizSummary", quiz);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        %>
        <div class="QuizCardTitle" style="overflow: hidden;">
          <p class="QuizName" style = "max-width: 850px; overflow: hidden;"><%=quiz.getQuizName()%></p>
          <div class="creator">
            <a href="profile?userID=<%=quiz.getCreatorName()%>" class="creatorLink"><%=quiz.getCreatorName()%></a>
            <img src="assets/creator.svg" alt="Creator Icon" height="40" ; />
          </div>
        </div>
        <div class="QuizCardTitle">
          <p class="Category">Category: <%=quiz.getCategory()%></p>
          <div class="QuizTags" style="max-width: 700px; overflow: hidden;">
            <%ArrayList<String> tags = quiz.getTags();
              for(int i = 0; i < tags.size(); i++){%>
            <p class="QuizTag">#<%=tags.get(i)%></p>
            <%}%>
          </div>
        </div>
        <div class="quizContent">
          <p class="text"><%=quiz.getDescription()%></p>
        </div>
      </div>
      <div class="QuizStatisticCard">
        <div class="statisticsTitle">
          <p class="statisticsName">Statistics</p>
          <p>Number of Performers: <%=quiz.getNumberOfPerformers()%></p>
        </div>
        <div style="width: 870px; display: flex; flex-direction: row; font-size: 28px; color: #fff;">
          <div
                  style="width: 435px; display: flex; flex-direction: column; align-items: end; justify-content: flex-end; gap: 10px; padding-right: 20px;">
            <p>Average Score: <%=quiz.getAverageScore()%>%</p>
            <p>The Best Score: <%=quiz.getBestScore()%>%</p>
          </div>
          <div
                  style="width: 435px; display: flex; flex-direction: column; justify-content: flex-start; gap: 10px; padding-left: 20px;">
            <p>Average Time: <%=quiz.getAverageTime()%></p>
            <p>The Best Time: <%=quiz.getBestTime()%></p>
          </div>
        </div>
        <hr style="width: 1px; height: 70px; margin-top: -80px;">
        </hr>
      </div>
      <div class="QuizCardButtons">
        <%
            if (quiz.isPracticeMode()) {
        %>
        <div class="PracticeModeButton">
          <a href="QuizPage.jsp?quizID=<%=quiz.getQuiz_id()%>1" class="QuizCardLinks">Practice Mode</a>
        </div>
        <%
            }
        %>
        <div class="StartButton">
          <a href="QuizPage.jsp?quizID=<%=quiz.getQuiz_id()%>0" class="QuizCardLinks">Start!</a>
        </div>
      </div>
    </div>
    <div class="HistoryCard">
      <div class="HistoryCardTitle">
        <p>My History</p>
        <div class="orderBy">
          <p>Order By:</p>
          <div class="dropdown">
            <button class="dropbtn" disabled>
              <%
                String ordering;
                if(request.getSession().getAttribute("order") == null || request.getSession().getAttribute("order").equals("score")){
                ordering = "score";
              %>
              Score
              <%
                }else if (request.getSession().getAttribute("order").equals("date")){
                ordering = "date";
              %>
              Date
              <%
                }else{
                ordering = "time";
              %>
              Time
              <%
                }
              %>
            </button>
            <div class="dropdown-content">
              <button name = "ordering" value = "date">Date</button>
              <button name = "ordering" value = "score">Score</button>
              <button name = "ordering" value = "time">Time</button>
            </div>
          </div>
        </div>
      </div>
      <div class="HistoryTableContainer">
        <div class="HistoryTableCard">
          <table class="HistoryTable">
            <tr>
              <th>Date</th>
              <th>Score</th>
              <th>Time</th>
            </tr>
            <%
              ArrayList<Pair<String, Pair<Integer, String>>> ls;
              ls = quiz.getMyHistory(user, ordering);
              for(int i = 0; i < ls.size(); i++){
            %>
            <tr>
              <td><%=ls.get(i).getKey()%></td>
              <td><%=ls.get(i).getValue().getKey()%>%</td>
              <td><%=ls.get(i).getValue().getValue()%></td>
            </tr>
            <%
              }
            %>
          </table>
        </div>
      </div>
    </div>
  </div>
  <hr style="width: 1px; height: 840px;">
  </hr>
  <div class="rightPannel">
    <div class = "outlineContainer">
      <div class = "upperContainer">
        <div class = "topPerformersTitle">
          <p class = "topPerformersName">Top Performers</p>
          <div class="topPerfSwitcher">
            <p>All Time</p>
            <%
              if (request.getSession().getAttribute("LastDaySwitch") == null || request.getSession().getAttribute("LastDaySwitch").equals("AllTime")){
            %>
            <button name = "AllTimeOrLastDay" value = "AllTime" class = "switch">
              <hr style="width: 24px; height: 24px; border-radius: 50%; background: white; margin-left: 3px;">
            </button>
            <%
            }else{
            %>
            <button name = "AllTimeOrLastDay" value = "LastDay" class = "switch" style = "direction: rtl;">
              <hr style="width: 24px; height: 24px; border-radius: 50%; background: white; margin-right: 3px;">
            </button>
            <%
              }
            %>
            <p>In Last Day</p>
          </div>
        </div>
        <div class="topPerfTableContainer">
          <div class="topPerfTableCard">
            <table class="topPerfTable">
              <tr>
                <th>User Name</th>
                <th>Score</th>
                <th>Time</th>
              </tr>
              <%
                try {
                  if (request.getSession().getAttribute("LastDaySwitch") == null || request.getSession().getAttribute("LastDaySwitch").equals("AllTime")) {
                    ls = quiz.getTopPerfomers("All Time");
                  } else {
                    ls = quiz.getTopPerfomers("Last Day");
                  }
                } catch (SQLException e) {
                  throw new RuntimeException(e);
                }

                for (int i = 0; i < ls.size(); i++) {
              %>
              <tr>
                <td><%=ls.get(i).getKey()%></td>
                <td><%=ls.get(i).getValue().getKey()%>%</td>
                <td><%=ls.get(i).getValue().getValue()%></td>
              </tr>
              <%
                }
              %>
            </table>
          </div>
        </div>
      </div>
      <div class = "botContainer">
        <div class = "RecentPerformersTitle">
          <p>Recent Performers</p>
        </div>
        <div class="recentPerfTableContainer">
          <div class="recentPerfTableCard">
            <table class="recentPerfTable">
              <tr>
                <th>User Name</th>
                <th>Score</th>
                <th>Time</th>
              </tr>
              <%
                ArrayList<Pair<String, Pair<Integer, String>>> ls1;
                try {
                  ls1 = quiz.getRecentPerfomers();
                } catch (SQLException e) {
                  throw new RuntimeException(e);
                }

                for (int i = 0; i < ls1.size(); i++) {
              %>
              <tr>
                <td><%=ls1.get(i).getKey()%></td>
                <td><%=ls1.get(i).getValue().getKey()%>%</td>
                <td><%=ls1.get(i).getValue().getValue()%></td>
              </tr>
              <%
                }
              %>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</form>
</body>

</html>