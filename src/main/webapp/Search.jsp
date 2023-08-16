<%@ page import="Usernames_DAO.models.search" %>
<%@ page import="Questions_DAO.Quiz" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="Usernames_DAO.models.profile" %>
<%@ page import="Usernames_DAO.models.Achievement" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/SearchStyle.css" />
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
        request.getSession().setAttribute("recentSwitch", null);
        request.getSession().setAttribute("profilePage", null);
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
    String searched = request.getParameter("search");
    search search = new search();
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
            <input placeholder="<%=searched%>" class="search" name = "searchField" />
            <button class="cButton" name = "searchButton" value="search"><img src="assets/search.svg" alt="Search button" width="34" height="34" ; /></button>
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
<div class="content">
    <div class="leftPannel">
        <div class="leftTitle">
            <p>Matched Quizzes and Tags:</p>
        </div>
        <%
            ArrayList<Pair<Quiz,Integer>> quiz = search.getSearchedQuiz(searched);
            if(quiz.size() < 3){
        %>
        <div class="leftResults">
            <%
                }else{
            %>
            <div class = "leftResults" style = "padding-left: 15px;">
            <%
                }
                for(int i = 0; i < quiz.size(); i++){
                    Pair<Quiz,Integer> curr = quiz.get(i);
                    Quiz currquiz = curr.getKey();
            %>
            <div class="QuizDescCard">
                <div class="QuizCardTitle" style="overflow: hidden; min-height: 40px;">
                    <a href = "QuizSummary.jsp?quizID=<%=curr.getValue()%>" class="QuizName" style = "max-width: 850px; overflow: hidden;"><%=currquiz.getQuizName()%></a>
                        <div class="creator">
                            <a href="Profile.jsp?username=<%=currquiz.getCreatorName()%>" class="creatorLink"><%=currquiz.getCreatorName()%></a>
                            <img src="assets/creatorWhite.svg" alt="Creator Icon" height="40" ; />
                        </div>
                </div>
                <div class="QuizCardTitle">
                    <p class="Category">Category: <%=currquiz.getCategory()%></p>
                    <div class="QuizTags" style="max-width: 700px; overflow: hidden;">
                        <%ArrayList<String> tags = currquiz.getTags();
                            for(int j = 0; j < tags.size(); j++){%>
                        <a href="Search.jsp?search=<%=tags.get(j)%>" class="QuizTag">#<%=tags.get(j)%></a>
                        <%
                            }
                        %>
                    </div>
                </div>
                <div class="quizContent">
                    <p class="text"><%=currquiz.getDescription()%></p>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
    <div class="rightPannel">
        <div class="rightTitle">
            <p>Matched Users:</p>
        </div>
        <%
            ArrayList<String> users = search.getUsers(searched);
            if(users.size() < 3){
        %>
            <div class="rightResults" style="align-items: flex-end;">
        <%
            }else{
        %>
            <div class="rightResults">
        <%
            }
            for(int j = 0; j < users.size(); j++){
                profile = new profile(users.get(j));
                User curru = User.getUser(users.get(j));
        %>
            <div class = "profileCard">
                <div class = "profileCardHalf">
                    <a href = "Profile.jsp?username=<%=curru.getUsername()%>" class="userLink"><%=curru.getUsername()%></a>
                    <img src = "assets/pfp.svg" alt="Profile Pic" class="pfp">
                </div>
                <div class = "profileCardHalf">
                    <div class="data">
                        <p>Completed <%=profile.getCompletedQuizes()%> Quizzes.</p>
                        <p>Created <%=profile.getCreatedQuizes()%> Quizzes.</p>
                        <p>Has <%=profile.getFriendCount()%> Friends.</p>
                    </div>
                    <div class="awards">
                        <%
                            int awardsNum = profile.AwardCount();
                            if(awardsNum != 0){
                                List<Achievement> awards = profile.getAchievment();

                        %>
                        <div class="awardsln">
                            <%
                                for(int i = 0; i < Math.min(3, awardsNum); i++){
                                    Achievement curr = awards.get(i);
                                    int type = curr.getAchievment_id();
                                    if(type == 5){
                                        type = 6;
                                    }else if(type == 6){
                                        type = 5;
                                    }
                            %>
                            <img src="assets/medal<%=type%>.svg" alt="Medal icon" width="40" ; />
                            <%
                                }
                            %>
                        </div>
                        <%
                            if(awardsNum > 3){
                        %>
                        <div class="awardsln">
                            <%
                                for(int i = 3; i < awardsNum; i++){
                                    Achievement curr = awards.get(i);
                                    int type = curr.getAchievment_id();
                                    if(type == 5){
                                        type = 6;
                                    }else if(type == 6){
                                        type = 5;
                                    }
                            %>
                            <img src="assets/medal<%=type%>.svg" alt="Medal icon" width="40" ; />
                        <%
                                    }
                        %>
                        </div>
                        <%
                                }
                            }
                        %>
                    </div>
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