<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="Usernames_DAO.models.profile" %>
<%@ page import="Usernames_DAO.models.Achievement" %>
<%@ page import="java.util.List" %>
<%@ page import="Usernames_DAO.message.MessageManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Usernames_DAO.message.Message" %>
<%@ page import="Questions_DAO.Quiz" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/InboxStyle.css" />
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
        request.getSession().setAttribute("recentSwitch", null);
        request.getSession().setAttribute("challenge", null);
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
    profile profile = new profile((String)request.getSession().getAttribute("user"));
    profile.setNotification("message", false);
    profile.setNotification("request", false);
    profile.setNotification("challange", false);
%>
<body style="overflow-y: hidden;">
<form action="Search" method="POST">
<div class="top">
    <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
        <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
        <a href="Homepage.jsp">
            <p class="logo">QuizHub</p>
        </a>
    </div>
    <div style="display: flex; flex-direction: row; gap: 30px; padding-right: 30px;">
        <div style="position: relative;">
            <input placeholder="Search..." class="search" name = "searchField" />
            <button type="submit" class="cButton" name = "searchButton" value="search"><img src="assets/search.svg" alt="Search button" width="34"
                                                       height="34" ; /></button>
        </div>
        <a href="QuizCreate.jsp" class="addButton"><img src="assets/addQuiz.svg" alt="Add quiz button" width="50"
                                                        height="50" ; /></a>
        <div style="position: relative;">
            <a href="Inbox.jsp" style="margin-top: -2px"><img src="assets/inbox.svg" alt="Inbox button" height="52"
                                                               ; /></a>
        </div>
        <a href="Profile.jsp"><img src="assets/profile.svg" alt="Profile button" width="50" height="50" ; /></a>
    </div>
</div>
</form>
<hr style="width: 100%; height: 1px; color: #FFF;">
<div class="content">
    <div class="leftPannel">
        <div class="leftChat">
            <form action="ChatSearch" method="POST">
            <div style="position: relative;">
                <%
                    if(request.getParameter("search") == null || request.getParameter("search").equals("")){
                %>
                <input placeholder="Search..." class="searchChat" name = "searchField" />
                <%
                    }else{
                %>
                <input placeholder="<%=request.getParameter("search")%>" class="searchChat" name = "searchField" />
                <%
                    }
                %>
                <button class="sButton" type="submit" name = "searchButton" value = "val"><img src="assets/searchWhite.svg" alt="Search button" style="width: auto;"
                                             ; /></button>
            </div>
            </form>
            <div class="chatPeople">
                <%
                    MessageManager mm = new MessageManager();
                    String me = (String) request.getSession().getAttribute("user");
                    String currUser;
                    ArrayList<String> chatGuys;
                    if(request.getParameter("search") != null && !request.getParameter("search").equals("")){
                        String searched = request.getParameter("search");
                        chatGuys = mm.getMessagesSearch(me, searched);
                        currUser = null;
                    }else {
                        currUser = request.getParameter("username");
                        chatGuys = mm.getRecievedUsers(me);
                    }
                    if(currUser != null){
                    %>
                <a href="Inbox.jsp?username=<%=currUser%>" class="chatGuy" style="background: #9795FF;">
                    <img src="assets/pfp.svg" alt="Profile Pic" class="chatGuyPfp" ; />
                    <p class="chatGuyName"><%=currUser%></p>
                </a>
                <%
                    }
                    for(int i = 0; i < chatGuys.size(); i++){
                        if(currUser != null && currUser.equals(chatGuys.get(i)))
                            continue;
                        if(currUser == null && i == 0){
                            currUser = chatGuys.get(i);
                            String cUser = chatGuys.get(i);
                %>
                <a href="Inbox.jsp?username=<%=cUser%>" class="chatGuy" style="background: #9795FF;">
                    <img src="assets/pfp.svg" alt="Profile Pic" class="chatGuyPfp" ; />
                    <p class="chatGuyName"><%=cUser%></p>
                </a>
                <%
                        }else{
                            String cUser = chatGuys.get(i);
                %>
                <a href="Inbox.jsp?username=<%=cUser%>" class="chatGuy">
                    <img src="assets/pfp.svg" alt="Profile Pic" class="chatGuyPfp" ; />
                    <p class="chatGuyName"><%=cUser%></p>
                </a>
                <%
                        }
                    }
                %>
            </div>
        </div>
        <form action="Chat" method="POST">
        <div class="rightChat">
            <%
                List<Achievement> awards;
                if(currUser != null){
                request.getSession().setAttribute("currUser", currUser);
            %>
            <div class="chatUser">
                <a href="Profile.jsp?username=<%=currUser%>" class="chatUserName">
                    <img src="assets/chatUser.svg" alt="Profile Pic" width="80" />
                    <p class="chatUserNameLink"><%=currUser%></p>
                </a>
                <div>
                    <%
                        profile prof = new profile(currUser);
                        awards = prof.getAchievment();
                        for(int i = 0; i < awards.size(); i++){
                            Achievement currA = awards.get(i);
                            int type = currA.getAchievment_id();
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
            </div>
            <ul id = "messages">
            <div class="chat">
                <%
                    ArrayList<Message> chat = mm.getMessages(me, currUser);
                    for(int i = 0; i < chat.size(); i++){
                        Message currsms = chat.get(i);
                        if(currsms.isChallenge()){
                            if(currsms.getFrom().equals(me)){
                        %>
                        <div class="challangeBox">
                            <div class="cupDiv">
                                <img src="assets/cup.svg" alt="Challange icon" height="130" ; />
                            </div>
                            <div class="challangeData">
                                <p class="challangeText">You Challenged <%=currUser%>!</p>
                                <a href="QuizSummary.jsp?quizID=<%=currsms.getQuiz_id()%>" class="challangeQuiz"><%=currsms.getQuizName()%></a>
                                <p class="challangeText">Your <%=currsms.getText()%></p>
                            </div>
                        </div>
                        <%
                            }else{
                        %>
                        <div class="challangeBox">
                            <div class="cupDiv">
                                <img src="assets/cup.svg" alt="Challange icon" height="130" ; />
                            </div>
                            <div class="challangeData">
                                <p class="challangeText"><%=currUser%> Challenged You!</p>
                                <a href="QuizSummary.jsp?quizID=<%=currsms.getQuiz_id()%>" class="challangeQuiz"><%=currsms.getQuizName()%></a>
                                <p class="challangeText"><%=currUser%>'s <%=currsms.getText()%></p>
                            </div>
                        </div>
                        <%
                            }
                        }else{
                            if(currsms.getFrom().equals(me)){
                        %>
                        <div class="rightsmsdiv">
                            <p class="rightsms"><%=currsms.getText()%></p>
                        </div>
                        <%
                            }else{
                        %>
                        <div class="leftsmsdiv">
                            <p class="leftsms"><%=currsms.getText()%></p>
                        </div>
                        <%
                            }
                        }
                    }
                        %>
            </div>
            </ul>
            <div class="sendDiv">
                <input type="text" name="sms" class="nameInp" placeholder="Type message here...">
                <button type = "submit" class="sendButton" name = "sendButton" value="val"><img src="assets/send.svg" alt="Send icon" height="50" ; /></button>
            </div>
            <%
                }
            %>
        </div>
    </form>
    </div>

    <form action="FriendRequests" method="POST">
    <div class="rightPannel">
        <div class ="titleDiv">
            <p class="rightTitle">Friend Requests:</p>
        </div>
        <div class="rightResults">
            <%
                String user = (String) request.getSession().getAttribute("user");
                ArrayList<String> requests = mm.getFriendRequests(user);
                for(int j = 0; j < requests.size(); j++){
                    String username = requests.get(j);
                    profile = new profile(username);
            %>
            <div class="fullCard">
                <div class="profileCard">
                    <div class="profileCardHalf">
                        <a href="Profile.jsp?username=<%=username%>" class="userLink"><%=username%></a>
                        <img src="assets/pfp.svg" alt="Profile Pic" class="pfp">
                    </div>
                    <div class="profileCardHalf">
                        <div class="data">
                            <p>Completed <%=profile.getCompletedQuizes()%> Quizzes.</p>
                            <p>Created <%=profile.getCreatedQuizes()%> Quizzes.</p>
                            <p>Has <%=profile.getFriendCount()%> Friends.</p>
                        </div>
                        <div class="awards">
                            <%
                                int awardsNum = profile.AwardCount();
                                if(awardsNum != 0){
                                    awards = profile.getAchievment();

                            %>
                            <div class="awardsln">
                                <%
                                    for(int i = 0; i < Math.min(3, awardsNum); i++){
                                        Achievement currAch = awards.get(i);
                                        int type = currAch.getAchievment_id();
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
                                        Achievement currAch = awards.get(i);
                                        int type = currAch.getAchievment_id();
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
                <div class="frButtons">
                    <button class="accButton" name = "accButton<%=username%>" value = "val">
                        <img src="assets/accept.svg" alt="Accept icon" width="50" ; />
                    </button>
                    <button class="rejButton" name = "rejButton<%=username%>" value = "val">
                        <p>X</p>
                    </button>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
    </form>
</div>
</div>
</body>
<%
    }
%>
</html>