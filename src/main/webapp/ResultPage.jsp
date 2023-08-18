<%@ page import="Usernames_DAO.UserQuiz.UserTakesQuiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Questions_DAO.Question" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Usernames_DAO.UserQuiz.SummaryQuiz" %>
<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="Usernames_DAO.models.profile" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/QuizResultStyle.css" />
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
    User user = User.getUser((String) request.getSession().getAttribute("user"));
    UserTakesQuiz quiz = (UserTakesQuiz) request.getSession().getAttribute("quiz");
    SummaryQuiz quizSummary = (SummaryQuiz) request.getSession().getAttribute("quizSummary");
    request.getSession().setAttribute("result page", 1);
%>
<body style="overflow-y: hidden;">
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
    <form action = "QuizResult" method = "POST">
<div class="content">
    <div class="leftPannel">
        <div class="QuizCard">
            <div class="QuizDescCard">
                <div class="QuizCardTitle" style="overflow: hidden; min-height: 40px;">
                    <p class="QuizName" style = "max-width: 850px; overflow: hidden;"><%=quiz.getQuiz().getQuizName()%></p>
                    <div class="creator">
                        <a href="Profile.jsp?username=<%=quiz.getQuiz().getCreatorName()%>" class="creatorLink"><%=quiz.getQuiz().getCreatorName()%></a>
                        <img src="assets/creator.svg" alt="Creator Icon" height="40" ; />
                    </div>
                </div>
                <div class="QuizCardTitle">
                    <p class="Category">Category: <%=quiz.getQuiz().getCategory()%></p>
                    <div class="QuizTags">
                        <%
                            ArrayList<String> tags = quiz.getQuiz().getTags();
                            for(int i = 0; i < tags.size(); i++){%>
                        <a href="Search.jsp?search=<%=tags.get(i)%>" class="QuizTag">#<%=tags.get(i)%></a>
                        <%}%>
                    </div>
                </div>
                <div class="ansDiv">
                    <div class="ansContainer">
                        <div class="ansCard">
                            <table class="ansTable">
                                <tr>
                                    <th>N</th>
                                    <th>Score</th>
                                    <th>Your Answer</th>
                                    <th>Correct Answer</th>
                                </tr>
                                <%
                                    ArrayList<ArrayList<String>> userAnswers= quiz.getQuiz().getUserAnswers();
                                    ArrayList<ArrayList<String>> correctAnswers = quiz.getQuiz().getCorrectAnswers();
                                    ArrayList<Integer> userScores = quiz.getQuiz().getUserScores();
                                    ArrayList<Integer> scores = quiz.getQuiz().getQuestionScores();
                                    ArrayList<Question> questions = quiz.getQuiz().getQuestionList();
                                    for(int j = 0; j < quiz.getQuiz().getTotalNumberOfQuestions(); j++){
                                %>
                                <tr>
                                    <td><%=j+1%></td>
                                    <td><%=userScores.get(j)%>/<%=scores.get(j)%></td>
                                    <td>
                                        <div class="cellDiv">
                                            <%
                                                int type = questions.get(j).getType();
                                                String userAns = quiz.getQuiz().getProcessedAnswer(userAnswers.get(j), type, true);
                                                if(type == 7){
                                                    ArrayList<String> ans = new ArrayList();
                                                    String tmp = "";
                                                    for(int i = 0; i < userAns.length(); i++){
                                                        if(i < userAns.length() - 1 && userAns.charAt(i) == '@' && userAns.charAt(i+1) == '#'){
                                                            i++;
                                                            ans.add(tmp);
                                                            tmp = "";
                                                        }else{
                                                            tmp += userAns.charAt(i);
                                                        }
                                                    }
                                                    ans.add(tmp);
                                                    for(int i = 0; i < ans.size(); i++){
                                            %>
                                            <%=ans.get(i)%>
                                            <br>
                                            <%
                                                }
                                            }else{
                                            %>
                                            <%=userAns%>
                                            <%
                                                }
                                            %>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="cellDiv">
                                            <%
                                                boolean caseS = questions.get(j).isCaseSensitive();
                                                String correctAns = quiz.getQuiz().getProcessedAnswer(correctAnswers.get(j), type, caseS);
                                                if(type == 7){
                                                    ArrayList<String> ans = new ArrayList();
                                                    String tmp = "";
                                                    for(int i = 0; i < correctAns.length(); i++){
                                                        if(i < correctAns.length() - 1 && correctAns.charAt(i) == '@' && correctAns.charAt(i+1) == '#'){
                                                            i++;
                                                            ans.add(tmp);
                                                            tmp = "";
                                                        }else{
                                                            tmp += correctAns.charAt(i);
                                                        }
                                                    }
                                                    ans.add(tmp);
                                                    for(int i = 0; i < ans.size(); i++){
                                            %>
                                            <%=ans.get(i)%>
                                            <br>
                                            <%
                                                }
                                            }else{
                                            %>
                                            <%=correctAns%>
                                            <%
                                                }
                                            %>
                                        </div>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </table>
                        </div>
                    </div>
                    <div class="buttonDiv">
                        <div class="scoreDiv">
                            <p>Score: <%=quiz.getQuiz().getUserScore()%>/<%=quiz.getQuiz().getMaxScore()%> =
                                <%=Math.toIntExact(Math.round(((double) quiz.getQuiz().getUserScore() / quiz.getQuiz().getMaxScore()) * 100))%>%
                            </p>
                            <p>Time: <%=quiz.getTime()%></p>
                        </div>
                        <%
                            request.getSession().setAttribute("challenge", quiz.getQuiz().getQuizName() + "//" + "Result: " +
                                    Integer.toString(Math.toIntExact(Math.round(((double) quiz.getQuiz().getUserScore() / quiz.getQuiz().getMaxScore()) * 100)))
                                    + "% in " + quiz.getTime() + "!");
                            request.getSession().setAttribute("challengeID", quiz.getQuizId());
                        %>
                        <a href="ChallangeFriend.jsp" class="sButton">Challenge Friend!</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <hr style="width: 1px; height: 840px;">
    <div class="rightPannel">
        <div class="outlineContainer">
            <div class="upperContainer">
                <div class="topPerformersTitle">
                    <div class="topPerfSwitcher">
                        <p class="topPerformersName">Top Performers</p>
                        <%
                            if (request.getSession().getAttribute("TopFriendsSwitch") == null || request.getSession().getAttribute("TopFriendsSwitch").equals("top")){
                        %>
                        <button name = "TopOrFriends" value = "top" class = "switch">
                            <hr style="width: 24px; height: 24px; border-radius: 50%; background: white; margin-left: 3px;">
                        </button>
                        <%
                        }else{
                        %>
                        <button name = "TopOrFriends" value = "friends" class = "switch" style = "direction: rtl;">
                            <hr style="width: 24px; height: 24px; border-radius: 50%; background: white; margin-right: 3px;">
                        </button>
                        <%
                            }
                        %>
                        <p class="topPerformersName">Friends</p>
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
                                ArrayList<Pair<String, Pair<Integer, String>>> ls;
                                if (request.getSession().getAttribute("TopFriendsSwitch") == null || request.getSession().getAttribute("TopFriendsSwitch").equals("top")) {
                                    ls = quizSummary.getTopPerfomers("All Time");
                                } else {
                                    ls = quizSummary.getFriendPerformers(user);
                                }

                                for (int i = 0; i < ls.size(); i++) {
                            %>
                            <tr>
                                <td><a href = "Profile.jsp?username=<%=ls.get(i).getKey()%>" class="perfLink"><%=ls.get(i).getKey()%></a></td>
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
            <div class="botContainer">
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
                <div class="recentPerfTableContainer">
                    <div class="recentPerfTableCard">
                        <table class="recentPerfTable">
                            <tr>
                                <th>Date</th>
                                <th>Score</th>
                                <th>Time</th>
                            </tr>
                            <%
                                ls = quizSummary.getMyHistory(user, ordering);
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
    </div>
</div>
</form>
</body>
<%
    }
%>
</html>