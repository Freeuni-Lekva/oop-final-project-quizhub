<%@ page import="Usernames_DAO.UserQuiz.UserTakesQuiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Questions_DAO.Question" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/QuizResultStyle.css" />
    <%
        UserTakesQuiz quiz = (UserTakesQuiz) request.getSession().getAttribute("quiz");
        request.getSession().setAttribute("result page", 1);
    %>
</head>

<body style="overflow-y: hidden;">
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
                <div class="QuizCardTitle" style="overflow: hidden;">
                    <p class="QuizName" style = "max-width: 850px; overflow: hidden;"><%=quiz.getQuiz().getQuizName()%></p>
                    <div class="creator">
                        <a href="profile.html" class="creatorLink"><%=quiz.getQuiz().getCreatorName()%></a>
                        <img src="assets/creator.svg" alt="Creator Icon" height="40" ; />
                    </div>
                </div>
                <div class="QuizCardTitle">
                    <p class="Category">Category: <%=quiz.getQuiz().getCategory()%></p>
                    <div class="QuizTags">
                        <%
                            ArrayList<String> tags = quiz.getQuiz().getTags();
                            for(int i = 0; i < tags.size(); i++){%>
                        <p class="QuizTag">#<%=tags.get(i)%></p>
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
                        <button type="submit" class="sButton">Challenge Friend!</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <hr style="width: 1px; height: 840px;">
    </hr>
    <div class="rightPannel">
        <div class="outlineContainer">
            <div class="upperContainer">
                <div class="topPerformersTitle">
                    <div class="topPerfSwitcher">
                        <p class="topPerformersName">Top Performers</p>
                        <label class="switch">
                            <input type="checkbox">
                            <span class="slider round"></span>
                        </label>
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
            <div class="botContainer">
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
                <div class="recentPerfTableContainer">
                    <div class="recentPerfTableCard">
                        <table class="recentPerfTable">
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
    </div>
</div>
</body>

</html>