<%@ page import="Usernames_DAO.UserQuiz.SummaryQuiz" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>

<head>
  <link rel="icon" href="logo.png" />
  <title>QuizHub</title>
  <link rel="stylesheet" type="text/css" href="css/QuizSummaryStyle.css" />
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
    <button type="addQuiz" class="addButton"><img src="assets/addQuiz.svg" alt="Add quiz button" width="50"
                                                  height="50" ; /></button>
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
          request.getSession().setAttribute("recentSwitch", null);
          try {
            quiz = new SummaryQuiz(Integer.parseInt(request.getParameter("quizID")));
          } catch (SQLException e) {
            throw new RuntimeException(e);
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
          <a href="QuizPage.jsp?quizID=<%=request.getParameter("quizID")%>1" class="QuizCardLinks">Practice Mode</a>
        </div>
        <%
            }
        %>
        <div class="StartButton">
          <a href="QuizPage.jsp?quizID=<%=request.getParameter("quizID")%>0" class="QuizCardLinks">Start!</a>
        </div>
      </div>
    </div>
    <div class="HistoryCard">
      <div class="HistoryCardTitle">
        <p>My History</p>
        <div class="orderBy">
          <p>Order By:</p>
          <div class="dropdown">
            <button class="dropbtn">Score</button>
            <div class="dropdown-content">
              <a href="QuizSummary.html">Date</a>
              <a href="QuizSummary.html">Score</a>
              <a href="QuizSummary.html">Time</a>
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
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
            <tr>
              <td>15:45:07 12/07/2023</td>
              <td>64%</td>
              <td>1h 54m</td>
            </tr>
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
            <label class="switch">
              <input type="checkbox">
              <span class="slider round"></span>
            </label>
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
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
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
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
              <tr>
                <td>User_name</td>
                <td>64%</td>
                <td>1h 54m</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>

</html>