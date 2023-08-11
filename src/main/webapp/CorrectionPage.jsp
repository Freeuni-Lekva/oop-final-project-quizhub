<%@ page import="java.util.ArrayList" %>
<%@ page import="Usernames_DAO.UserQuiz.UserTakesQuiz" %>
<%@ page import="Questions_DAO.Question" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/QuizPageStyle.css" />
    <%
        UserTakesQuiz quiz = (UserTakesQuiz) request.getSession().getAttribute("quiz");
        Question curr = quiz.getQuiz().getCurrentQuestion();
        request.getSession().setAttribute("correction", "corrected");
    %>
</head>
<body style="overflow-y: hidden;">
<form action = "MultiPage" method = "POST">
<div class="top">
    <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
        <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
        <a href="Homepage.jsp">
            <p class="logo">QuizHub</p>
        </a>
    </div>
    <div class="endButtons">
        <button type="exit" class="eButton" name="button" value="exitButton">Exit</button>
        <button type="submit" class="sButton" name="button" value="submitButton">Submit</button>
    </div>
</div>
<hr style="width: 100%; height: 1px; color: #FFF;">
</hr>
<div class="title">
    <div class="TitleCornerLeft">
        <p class="QuizName"><%=quiz.getQuiz().getQuizName()%></p>
        <p class="Category">Category: <%=quiz.getQuiz().getCategory()%></p>
    </div>
    <div class="TitleCenter">
        <%
            int num = 1;
            if(quiz.getQuiz().hasNextQuestion()){
                num = quiz.getQuiz().getCurrentQuestionNumber();
            }else {
                num = quiz.getQuiz().getTotalNumberOfQuestions();
            }
        %>
        <p>Questions Answered: <%=num%>/<%=quiz.getQuiz().getTotalNumberOfQuestions()%></p>
    </div>
    <div class="TitleCornerRight">
        <div class="creator">
            <a href="profile.html" class="creatorLink"><%=quiz.getQuiz().getCreatorName()%></a>
            <img src="assets/creatorWhite.svg" alt="Creator Icon" height="40" ; />
        </div>
        <div class="QuizTags">
            <%
                ArrayList<String> tags = quiz.getQuiz().getTags();
                for(int i = 0; i < tags.size(); i++){%>
            <p class="QuizTag">#<%=tags.get(i)%></p>
            <%}%>
        </div>
    </div>
</div>
    <div class = "MultipleContent">
            <%
        int userScore = quiz.getQuiz().getUserScores().get(quiz.getQuiz().getUserScores().size() - 1);
        int maxScore = curr.getMaxScore();
        ArrayList<String> userAnswers = quiz.getQuiz().getUserAnswers().get(quiz.getQuiz().getUserAnswers().size() - 1);
        ArrayList<String> correctAnswers = curr.getAnswers();
        int type = curr.getType();
        String userAns = quiz.getQuiz().getProcessedAnswer(userAnswers, type);
        String correctAns = quiz.getQuiz().getProcessedAnswer(correctAnswers, type);
    %>
            <%
        if(userScore == 0){
    %>
        <div class = "scoreCard" style = "background: #FF3E3E;">
                <%
        }else if(userScore == maxScore){
    %>
            <div class = "scoreCard" style = "background: #2BC990;">
                <%
                }else{
                %>
                <div class = "scoreCard" style="background: #FDA522;">
                    <%
                        }
                    %>
                    <p class = "scoreTitle">Score <%=userScore%>/<%=maxScore%></p>
                    <div style = "display: flex; flex-direction: row;">
                        <div class = "ansCol">
                            <p>Your Answer</p>
                            <div class = "scoreDiv">
                                <p class = "scoreD" style="font-size: 36px;">
                                    <%
                                        if(type == 7){
                                            for(int i = 0; i < userAns.length(); i++){
                                                if(i < userAns.length() - 1 && userAns.charAt(i) == '@' && userAns.charAt(i+1) == '#'){
                                                    i++;
                                    %>
                                    <br>
                                    <%
                                    }else{
                                    %>
                                    <%=userAns.charAt(i)%>
                                    <%
                                            }
                                        }
                                    }else{
                                    %>
                                    <%=userAns%>
                                    <%
                                        }
                                    %>
                                </p>
                            </div>
                        </div>
                        <div class = "ansCol">
                            <p>Correct Answer</p>
                            <div class = "scoreDiv">
                                <p class = "scoreD" style="font-size: 36px;">
                                    <%
                                        if(type == 7){
                                            for(int i = 0; i < correctAns.length(); i++){
                                                if(i < correctAns.length() - 1 && correctAns.charAt(i) == '@' && correctAns.charAt(i+1) == '#'){
                                                    i++;
                                    %>
                                    <br>
                                    <%
                                    }else{
                                    %>
                                    <%=correctAns.charAt(i)%>
                                    <%
                                            }
                                        }
                                    }else{
                                    %>
                                    <%=correctAns%>
                                    <%
                                        }
                                    %>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <button type="next" class="nButton" name="button" value="nextButton"><img src="assets/nextButton.svg" alt="Next" width="90"
                                                                                          height="90"; /></button>
            </div>
</form>
</body>
</html>