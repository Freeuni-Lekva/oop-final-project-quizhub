<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="Usernames_DAO.models.profile" %>
<%@ page import="Usernames_DAO.models.Achievement" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="Questions_DAO.Quiz" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="Usernames_DAO.manager.FriendshipManager" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/ProfileStyle.css" />
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
<div class="content">
    <%
        User user = User.getUser((String) request.getSession().getAttribute("user"));
        String username = request.getParameter("username");
        if(username == null || username.equals("")){
            username = user.getUsername();
        }
        profile = new profile(username);
        String myUsername = (String) request.getSession().getAttribute("user");
        request.getSession().setAttribute("profilePage", username);
    %>
    <div class="left">
        <div class="cards">
            <div class="ProfileCard">
                <img src="assets/pfp.svg" alt="Profile Pic" class="pfp" />
                <a href="Profile.jsp?username=<%=username%>" class="username"><%=username%></a>
                <div class="data">
                    <p>Completed <%=profile.getCompletedQuizes()%> Quizzes.</p>
                    <p>Created <%=profile.getCreatedQuizes()%> Quizzes.</p>
                    <p>Has <%=profile.getFriendCount()%> Friends.</p>
                    <p>Highest Score: <%=profile.getHighestScore()%>%.</p>
                    <p>Shortest Time: <%=profile.ShortestTime()%>.</p>
                    <%
                        int awardsNum = profile.AwardCount();
                        if(awardsNum == 0){
                    %>
                    <p>Received 0 Awards.</p>
                    <%
                        }else{
                    %>
                    <p>Received <%=awardsNum%> Awards:</p>
                    <%
                        }
                    %>
                </div>
                <div class="awards">
                    <%
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
            <div class="FriendsCard">
                <div class="friendsTitle">
                    <p>Friends List:</p>
                </div>
                <div class="friendsList">
                    <%
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
                    <div class="friendsln">
                        <div class="friendName">
                            <img src="assets/creatorWhite.svg" alt="Medal icon" width="35" ; />
                            <a href="Profile.jsp?username=<%=curr.getUsername()%>" class="friendLink"><%=curr.getUsername()%></a>
                        </div>
                        <%
                            if (achtype != 0) {
                        %>
                        <img src="assets/medal<%=achtype%>.svg" alt="Medal icon" width="35" ; />
                        <%
                            }
                        %>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <form action="Profile" method="POST">
        <div class="buttons">
            <%
                if(username.equals(myUsername)){
            %>
            <button type="submit" class="rButton" name = "logOutButton" value="val">Log Out</button>
            <%
                }else{
                    FriendshipManager fr = new FriendshipManager();
                    if(!fr.areFriend(myUsername, username)){
                        if(fr.FriendRequestSent(myUsername,username)){
            %>
            <button type="submit" class="rButton" name = "cancelButton" value="val">Cancel Request</button>
            <%
                        }else if(fr.FriendRequestSent(username, myUsername)){
            %>
            <button type="submit" class="aButton" name = "accFriend" value="val">Accept</button>
            <button type="submit" class="rButton" name = "rejFriend" value="val">Reject</button>
            <%
                        }else{
            %>
            <button type="submit" class="aButton" name = "addFriend" value="val">Add Friend</button>
            <%
                        }
                    }else{
            %>
            <button type="submit" class="rButton" name = "removeButton" value="val">Remove Friend</button>
            <a href ="Inbox.jsp?username=<%=username%>" class="mButton" value="val">Send Message</a>
            <%
                    }
                    if(User.getUser(myUsername).isAdmin() && !User.getUser(username).isAdmin()){
            %>
            <button type="submit" class="adminButton" name = "addAsAdmin" value="val">Add as Administrator</button>
            <button type="submit" class="rButton" name = "removeUser" value="val">Remove User</button>
            <%
                    }else if(User.getUser(myUsername).isAdmin() && User.getUser(username).isAdmin() && !username.equals("admin")){
            %>
            <button type="submit" class="adminButton" name = "removeAdmin" value="val">Remove Administrator</button>
            <%
                    }
                }
            %>
        </div>
        </form>
    </div>
    <div class="HistoryTableContainer">
        <div class="HistoryHeader">
            <div class="HistoryTitle">
                <p>History</p>
            </div>
            <hr class="ln">
            <div class="ScoreTitle">
                <p>Score</p>
            </div>
        </div>
        <div class="HistoryData">
            <%
                List<Pair<Quiz,Pair<Integer,Integer>>> history = profile.getUserHistory(username);
                for(int i = history.size()-1; i >= 0; i--){
            %>
            <div class="historyln">
                <%
                    Pair<Quiz, Pair<Integer, Integer>> curr = history.get(i);
                    Quiz quiz = curr.getKey();
                    if(curr.getValue().getValue() != -1){
                %>
                <div class="historylndata">
                    <img src="assets/done.svg" alt="Quiz icon" style="min-width: 40px; min-height: 40px;" />
                    <p>Taken Quiz: <a href="QuizSummary.jsp?quizID=<%=curr.getValue().getKey()%>" class="friendLink"
                                      style="text-decoration-line: underline;"><%=quiz.getQuizName()%></a></p>
                </div>
                <div class="historylnscore">
                    <p><%=curr.getValue().getValue()%>%</p>
                </div>
                <%
                    }else{
                %>
                <div class="historylndata">
                    <img src="assets/create.svg" alt="Quiz icon" style="min-width: 40px; min-height: 40px;" />
                    <p>Created Quiz: <a href="QuizSummary.jsp?quizID=<%=curr.getValue().getKey()%>" class="friendLink"
                                      style="text-decoration-line: underline;"><%=quiz.getQuizName()%></a></p>
                </div>
                <div class="historylnscore">
                    <p>X</p>
                </div>
                <%
                    }
                %>
            </div>
            <hr style="width: auto; height: 1px; background: #9795ff; border: 1px solid #9795ff;">
            <%
                }
            %>
        </div>
    </div>
</div>
</body>
<%
    }
%>
</html>