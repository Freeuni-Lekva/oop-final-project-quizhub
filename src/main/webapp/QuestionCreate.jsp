<%@ page import="Usernames_DAO.UserQuiz.UserCreatesQuiz" %>
<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/QuestionCreateStyle.css" />
    <%
        UserCreatesQuiz quiz = (UserCreatesQuiz) request.getSession().getAttribute("new quiz");
        boolean selector = request.getSession().getAttribute("pageType").equals("selector");
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
%>
<body style="overflow-y: hidden;">
<form action="QuestionCreate" method="POST">
    <div class="top">
        <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
            <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
            <a href="Homepage.jsp">
                <p class="logo">QuizHub</p>
            </a>
        </div>
        <div class="endButtons">
            <button type="exit" class="eButton" name="exit-button" value="exitButton">Exit</button>
        </div>
    </div>
    <hr style="width: 100%; height: 1px; color: #FFF;">
    <div class="title">
        <div class="TitleCornerLeft" style = "max-width: 700px; overflow: hidden;">
            <p class="QuizName"><%=quiz.getQuizName()%></p>
            <p class="Category">Category: <%=quiz.getCategory()%></p>
        </div>
        <div class="TitleCornerRight" style = "max-width: 700px; overflow: hidden;">
            <div class="creator">
                <a href="profile.html" class="creatorLink"><%=quiz.getCreator_name()%></a>
                <img src="assets/creatorWhite.svg" alt="Creator Icon" height="40" ; />
            </div>
            <div class="QuizTags">
                <%
                    ArrayList<String> tags = quiz.getTags();
                    for(int i = 0; i < tags.size(); i++){%>
                <p class="QuizTag">#<%=tags.get(i)%></p>
                <%}%>
            </div>
        </div>
    </div>
    <div style = "display: flex; width: 100%; align-items: center; justify-content: center; position: relative;">
        <div class="TitleCenter" style = "position: absolute; top: -80px; width: fit-content;">
            <p>Number of Questions: <%=quiz.getNumberOfQuestions()%></p>
            <p>Maximum Score: <%=quiz.getMaxScore()%></p>
        </div>
    </div>
    <div class="MultipleContent">
        <div class="Mcard">
            <div class="questionDiv">
                <%
                    if(selector){
                %>
                <div class="questionSelector">
                    <p class="selectorTitle">Select question type or publish quiz:</p>
                    <div class="selectorBlock">
                        <div class="selectorColumn">
                            <div>
                                <label class="QtypeChoice">
                                    <input type="radio" checked="checked" name="Qtype" value="questionResponse">
                                    Question - Response
                                </label>
                            </div>
                            <div>
                                <label class="QtypeChoice">
                                    <input type="radio" name="Qtype" value="multipleChoice">
                                    Multiple Choice
                                </label>
                            </div>
                            <div>
                                <label class="QtypeChoice">
                                    <input type="radio" name="Qtype" value="multiAnswer">
                                    Multi-answer Questions
                                </label>
                            </div>
                        </div>
                        <div class="selectorColumn">
                            <div>
                                <label class="QtypeChoice">
                                    <input type="radio" name="Qtype" value="fillBlank">
                                    Fill in the Blank
                                </label>
                            </div>
                            <div>
                                <label class="QtypeChoice">
                                    <input type="radio" name="Qtype" value ="pictureResponse">
                                    Picture -Response
                                </label>
                            </div>
                            <div>
                                <label class="QtypeChoice">
                                    <input type="radio" name="Qtype" value="matching">
                                    Matching
                                </label>
                            </div>
                        </div>
                    </div>
                    <div style="display: flex; justify-content: center;">
                        <label class="QtypeChoice">
                            <input type="radio" name="Qtype" value="multiChoiceMultiAns">
                            Multiple Choice with Multiple Answers
                        </label>
                    </div>
                    <%
                        if(quiz.getNumberOfQuestions() != 0){
                    %>
                    <div class="finish">
                        <button type="submit" class="fButton" name="finish-button" value="finish-button">Finish & Publish</button>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                }else{
                    int type = (Integer) request.getSession().getAttribute("QuestionType");
                    if(type == 1){
                %>
                <div class="questionResponse">
                    <div style="width:1200px;">
                        <label class="caseSensitive">
                            Answer is case-sensitive:
                            <input type="checkbox" name="caseSensitive" value="caseS">
                        </label>
                    </div>
                    <div class="questionResponseA">
                        <textarea class="questionResponseTextArea" name = "question" placeholder="Type question here..."></textarea>
                    </div>
                    <div class="questionResponseA">
                        <textarea class="questionResponseTextArea" name = "answer" placeholder="Type correct answer here..."></textarea>
                    </div>
                </div>
                <%
                }else if(type == 2){
                %>
                <div class="fillInBlank">
                    <div style="width:1200px;">
                        <label class="caseSensitive">
                            Answers are case-sensitive:
                            <input type="checkbox" name="caseSensitive" value="caseS">
                        </label>
                    </div>
                    <div style="width:1200px;">
                        <p>Use ,,___'' to present blank spaces!</p>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "question" placeholder="Type question here..."></textarea>
                    </div>
                    <div style="width:1200px;">
                        <p>Write only words that go in blank spaces! Divide them with ,,//''!</p>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "answer" placeholder="Type correct answer here..."></textarea>
                    </div>
                </div>
                <%
                }else if(type == 3){
                %>
                <div class="multipleChoice">
                    <div style="width:1200px;">
                        <label class="caseSensitive">
                            The sequence of answers can be randomized:
                            <input type="checkbox" name="randomize" value ="random">
                        </label>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "question" placeholder="Type question here..."></textarea>
                    </div>
                    <div style="width:1200px;">
                        <p>Type all options! Divide them with ,,//''!</p>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "possibleAnswers" placeholder="Type options here..."></textarea>
                    </div>
                    <div class="correctAnsInp">
                        Choose correct answer from options!
                        <input type="text" name ="answer" placeholder="Type correct answer here...">
                    </div>
                </div>
                <%
                }else if(type == 4){
                %>
                <div class="pictureResponse">
                    <div style="width:1200px;">
                        <label class="caseSensitive">
                            Answer is case-sensitive:
                            <input type="checkbox" name="caseSensitive" value ="caseS">
                        </label>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "question" placeholder="Type question here..."></textarea>
                    </div>
                    <input type="text" id="picture" name = "url" placeholder="Enter image link here..." style="width: 1200px;">
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "answer" placeholder="Type correct answer here..."></textarea>
                    </div>
                </div>
                <%
                }else if(type == 5){
                %>
                <div class="multiResponse">
                    <div style="width:1200px; display: flex; justify-content: space-between;">
                        <label class="caseSensitive">
                            The sequence of answers can be randomized:
                            <input type="checkbox" name="randomize" value ="random">
                        </label>
                        <label class="caseSensitive">
                            Answer is case-sensitive:
                            <input type="checkbox" name="caseSensitive" value ="caseS">
                        </label>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "question" placeholder="Type question here..."></textarea>
                    </div>
                    <div style="width:1200px;">
                        <p>Type all correct answers! Divide them with ,,//''!</p>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "answer" placeholder="Type correct answer here..."></textarea>
                    </div>
                </div>
                <%
                }else if(type == 6){
                %>
                <div class="multipleChoiceMultipleAnswer">
                    <div style="width:1200px;">
                        <label class="caseSensitive">
                            The sequence of answers can be randomized:
                            <input type="checkbox" name="randomize" value ="random">
                        </label>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "question" placeholder="Type question here..."></textarea>
                    </div>
                    <div style="width:1200px;">
                        <p>Type all options! Divide them with ,,//''!</p>
                    </div>
                    <div class="fillBlankA">
                        <textarea class="fillBlankTextArea" name = "possibleAnswers" placeholder="Type options here..."></textarea>
                    </div>
                    <div class="correctAnsInp">
                        Choose correct answers from options! Divide them with ,,//''!
                        <input type="text" name ="answer" placeholder="Type correct answers here...">
                    </div>
                </div>
                <%
                }else{
                    int rowNum = (Integer) request.getSession().getAttribute("rowNum");
                    if(rowNum == -1) {
                %>
                <div class="matchingQuestion">
                    <p>Enter number of pairs:</p>
                    <input type="text" name="rowNum" class="matchingA">
                </div>
                <%
                }else{
                %>
                <div class="matching">
                    <div style="width:1200px;">
                        <label class="caseSensitive">
                            The sequence of the right list can be randomized:
                            <input type="checkbox" name="randomize" value="random">
                        </label>
                    </div>
                    <div style="width:1200px;">
                        <p>Enter already matched pairs:</p>
                    </div>
                    <div class="pairs">
                        <%
                            for(int i = 0; i < rowNum; i++){
                        %>
                        <div class="pairRow">
                            <div class="matchingAns">
                                <textarea class="matchingTextArea" name = "left<%=i%>" placeholder="Type text here..."></textarea>
                            </div>
                            <hr style="width: 100px; height: 3px; background-color: #11023D;">
                            <div class="matchingAns">
                                <textarea class="matchingTextArea" name = "right<%=i%>" placeholder="Type text here..."></textarea>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
                <%
                            }
                        }
                    }
                %>
            </div>
        </div>
        <button type="next" class="nButton"><img src="assets/nextButton.svg" alt="Next" width="90" height="90"
                                                 ; /></button>
    </div>
</form>
</body>
<%
    }
%>
</html>