<%@ page import="Usernames_DAO.models.User" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Usernames_DAO.UserQuiz.UserTakesQuiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Questions_DAO.Question" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>

<head>
  <link rel="icon" href="logo.png" />
  <title>QuizHub</title>
  <link rel="stylesheet" type="text/css" href="css/QuizPageStyle.css" />
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
  UserTakesQuiz quiz = (UserTakesQuiz) request.getSession().getAttribute("quiz");
  if (quiz == null){
    User user = User.getUser((String) request.getSession().getAttribute("user"));
    int page_id = Integer.parseInt(request.getParameter("quizID"));
    int quiz_id = page_id/10;
    Timestamp t = new Timestamp(System.currentTimeMillis());
    boolean practiceMode = page_id%10 == 1;
    try {
      quiz = new UserTakesQuiz(user, quiz_id, t, practiceMode);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    request.getSession().setAttribute("quiz", quiz);
  }
  request.getSession().setAttribute("LastDaySwitch", null);
  request.getSession().setAttribute("order", null);
  request.getSession().setAttribute("quizSummaryPage", null);
  if(quiz.getQuiz().isPracticeMode()){
%>
<body style="overflow-y: hidden; background: #3B3B47;">
  <%
  }else{
%>
<body style="overflow-y: hidden;">
<%
  }
%>
<%
  if(!quiz.getQuiz().isOnePage()){
%>
<form action = "MultiPage" method = "POST">
  <div class="top">
    <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
      <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
      <a href="Homepage.jsp">
        <p class="logo">QuizHub</p>
      </a>
    </div>
    <div class = "endButtons">
      <button type="exit" class="eButton" name="button" value="exitButton">Exit</button>
      <%
        if (!quiz.getQuiz().isPracticeMode()) {
      %>
      <button type="submit" class="sButton" name="button" value="submitButton">Submit</button>
      <%
        }
      %>
    </div>
  </div>
  <hr style="width: 100%; height: 1px; color: #FFF;">
  </hr>
  <div class="title">
    <div class="TitleCornerLeft" style = "max-width: 740px; overflow: hidden;">
      <p class="QuizName"><%=quiz.getQuiz().getQuizName()%></p>
      <p class="Category">Category: <%=quiz.getQuiz().getCategory()%></p>
    </div>
    <div class="TitleCornerRight" style = "max-width: 740px; overflow: hidden;">
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
  <div style = "display: flex; width: 100%; align-items: center; justify-content: center; position: relative";>
    <div class="TitleCenter" style = "position: absolute; top: -80px; width: fit-content;">
      <%
        if(quiz.getQuiz().isPracticeMode()){
      %>
      <p>Practice Mode</p>
      <%
      }else{
      %>
      <p>Questions Answered: <%=quiz.getQuiz().getCurrentQuestionNumber() - 1%>/<%=quiz.getQuiz().getTotalNumberOfQuestions()%></p>
      <%
        }
      %>
    </div>
  </div>
  <!-- !!!Multiple Page Code!!! -->
  <div class = "MultipleContent">
    <div class = "Mcard">
      <div class="questionDiv">
        <%
          Question curr = quiz.getQuiz().getCurrentQuestion();
          int type = curr.getType();
          if(type == 1){
        %>
        <div class="questionResponse">
          <div class="questionResponseQ">
            <p><%=curr.getQuestion()%></p>
          </div>
          <div class="questionResponseA">
              <textarea name = "questionResponseAns" class="questionResponseTextArea"
                        placeholder="Type your answer here..."></textarea>
          </div>
        </div>
        <%
        }else if(type == 2){
          ArrayList<String> cuts = curr.getTexts();
        %>
        <div class="fillInTheBlank">
          <div class="fillInTheBlankTitle">
            <p>Fill in the blank spaces:</p>
          </div>
          <div class="fillInTheBlankDiv">
            <div class="fillInTheBlankContent">
              <p>
                <%
                  for(int i = 0; i < cuts.size() - 1; i++){
                %>
                <%=cuts.get(i)%> <input type="text" name = "fillInBlank<%=i%>">
                <%
                  }
                %>
                <%=cuts.get(cuts.size() - 1)%>
              </p>
            </div>
          </div>
        </div>
        <%
        }else if(type == 3){
        %>
        <div class="multipleChoice">
          <div class="multipleChoiceTitle">
            <p>Select only one correct option:</p>
          </div>
          <div class="multipleChoiceQ">
            <p><%=curr.getQuestion()%></p>
          </div>
          <div class="multipleChoiceA">
            <%
              ArrayList<String> possibleAnswers = curr.getPossibleAnswers();
              for(int i = 0; i < possibleAnswers.size(); i++){
            %>
            <label class="multipleChoiceAns">
              <input type="radio" name="multiChoice" value="<%=i%>">
              <%=possibleAnswers.get(i)%>
            </label>
            <%
              }
            %>
          </div>
        </div>
        <%
        }else if(type == 4) {
        %>
        <div class="pictureResponse">
          <div class="pictureResponseQuest">
            <img src= "<%=curr.getImage()%>" alt="Question Picture" class="pictureResponseP" ; />
            <div class="pictureResponseQ">
              <p><%=curr.getQuestion()%></p>
            </div>
          </div>
          <div class="pictureResponseA">
              <textarea name = "pictureResponseAns" class="pictureResponseTextArea"
                        placeholder="Type your answer here..."></textarea>
          </div>
        </div>
        <%
        }else if(type == 5) {
        %>
        <div class="multiAnswer">
          <div class="multiAnswerQ">
            <p><%=curr.getQuestion()%></p>
          </div>
          <div class="multiAnswerA">
            <%
              for(int i = 0; i < curr.getAnswers().size(); i++){
            %>
            <div class="multiAnswerAns">
              <textarea name = "multiAnswer<%=i%>" class="multiAnswerTextArea" placeholder="Type your answer here..."></textarea>
            </div>
            <%
              }
            %>
          </div>
        </div>
        <%
        }else if(type == 6){
        %>
        <div class="multipleChoice">
          <div class="multipleChoiceTitle">
            <p>You can select multiple answers:</p>
          </div>
          <div class="multipleChoiceQ">
            <p><%=curr.getQuestion()%></p>
          </div>
          <div class="multipleChoiceA">
            <%
              ArrayList<String> possibleAnswers = curr.getPossibleAnswers();
              for(int i = 0; i < possibleAnswers.size(); i++){
            %>
            <label class="multipleChoiceAns">
              <input type="checkbox" name="multiChoiceMultiAns<%=i%>" value="<%=i%>">
              <%=possibleAnswers.get(i)%>
            </label>
            <%
              }
            %>
          </div>
        </div>
        <%
        }else if(type == 7){
          ArrayList<String> rows = curr.getPossibleAnswers();
        %>
        <div class = "matching">
          <div class = "matchingTitle">
            <p><%=curr.getQuestion()%></p>
          </div>
          <div class = "matchingColumns">
            <div class = matchColumnLeft>
              <%for(int i = 0; i < rows.size()/2; i++){%>
              <div class = "matchColumnRow">
                <div class = "matchingQDiv">
                  <p class = "matchingQ"><%=rows.get(i)%></p>
                </div>
                <input type="text" name="<%="matching" + Integer.toString(i)%>" class = "matchingA">
              </div>
              <%}%>
            </div>
            <div class = matchColumnRight>
              <%for(int i = 0; i < rows.size()/2; i++){%>
              <div class = "matchColumnRow">
                <p class = "matchingNum"><%=i+1%></p>
                <div class = "matchingQDiv">
                  <p class = "matchingQ"><%=rows.get(rows.size()/2 + i)%></p>
                </div>
              </div>
              <%}%>
            </div>
          </div>
        </div>
        <%}%>
      </div>
    </div>
    <button type="next" class="nButton" name="button" value="nextButton"><img src="assets/nextButton.svg" alt="Next" width="90"
                                                                              height="90"; /></button>
  </div>
</form>
<%
}else{
%>
<form action = "OnePage" method = "POST">
  <div class="top">
    <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
      <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
      <a href="Homepage.jsp">
        <p class="logo">QuizHub</p>
      </a>
    </div>
    <div class = "endButtons">
      <button type="exit" class="eButton" name="button" value="exitButton">Exit</button>
      <button type="submit" class="sButton" name="button" value="submitButton">Submit</button>
    </div>
  </div>
  <hr style="width: 100%; height: 1px; color: #FFF;">
  </hr>
  <div class="title">
    <div class="TitleCornerLeft" style = "max-width: 700px; overflow: hidden;">
      <p class="QuizName"><%=quiz.getQuiz().getQuizName()%></p>
      <p class="Category">Category: <%=quiz.getQuiz().getCategory()%></p>
    </div>
    <div class="TitleCornerRight" style = "max-width: 700px; overflow: hidden;">
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
  <div style = "display: flex; width: 100%; align-items: center; justify-content: center; position: relative;">
    <div class="TitleCenter" style = "position: absolute; top: -80px; width: fit-content;">
      <p>Total Number of Questions: <%=quiz.getQuiz().getTotalNumberOfQuestions()%></p>
    </div>
  </div>
  <div class=content>
    <div class="contentCard">
      <%
        if(quiz.getQuiz().getTotalNumberOfQuestions() == 1){
      %>
      <div class="contentDiv" style="align-items: center; justify-content: center">
        <%
        }else{
        %>
        <div class="contentDiv">
          <%
            }
            ArrayList<Question> questions = quiz.getQuiz().getQuestionList();
            for(int j = 0; j < questions.size(); j++){
          %>
          <div class="questionDiv">
            <%
              Question curr = questions.get(j);
              int type = curr.getType();
              if(type == 1){
            %>
            <div class="questionResponse">
              <div class="questionResponseQ">
                <p><%=curr.getQuestion()%></p>
              </div>
              <div class="questionResponseA">
              <textarea name = "<%=j%>questionResponseAns" class="questionResponseTextArea"
                        placeholder="Type your answer here..."></textarea>
              </div>
            </div>
            <%
            }else if(type == 2){
              ArrayList<String> cuts = curr.getTexts();
            %>
            <div class="fillInTheBlank">
              <div class="fillInTheBlankTitle">
                <p>Fill in the blank spaces:</p>
              </div>
              <div class="fillInTheBlankDiv">
                <div class="fillInTheBlankContent">
                  <p>
                    <%
                      for(int i = 0; i < cuts.size() - 1; i++){
                    %>
                    <%=cuts.get(i)%> <input type="text" name = "<%=j%>fillInBlank<%=i%>">
                    <%
                      }
                    %>
                    <%=cuts.get(cuts.size() - 1)%>
                  </p>
                </div>
              </div>
            </div>
            <%
            }else if(type == 3){
            %>
            <div class="multipleChoice">
              <div class="multipleChoiceTitle">
                <p>Select only one correct option:</p>
              </div>
              <div class="multipleChoiceQ">
                <p><%=curr.getQuestion()%></p>
              </div>
              <div class="multipleChoiceA">
                <%
                  ArrayList<String> possibleAnswers = curr.getPossibleAnswers();
                  for(int i = 0; i < possibleAnswers.size(); i++){
                %>
                <label class="multipleChoiceAns">
                  <input type="radio" name="<%=j%>multiChoice" value="<%=i%>">
                  <%=possibleAnswers.get(i)%>
                </label>
                <%
                  }
                %>
              </div>
            </div>
            <%
            }else if(type == 4) {
            %>
            <div class="pictureResponse">
              <div class="pictureResponseQuest">
                <img src= "<%=curr.getImage()%>" alt="Question Picture" class="pictureResponseP" ; />
                <div class="pictureResponseQ">
                  <p><%=curr.getQuestion()%></p>
                </div>
              </div>
              <div class="pictureResponseA">
              <textarea name = "<%=j%>pictureResponseAns" class="pictureResponseTextArea"
                        placeholder="Type your answer here..."></textarea>
              </div>
            </div>
            <%
            }else if(type == 5) {
            %>
            <div class="multiAnswer">
              <div class="multiAnswerQ">
                <p><%=curr.getQuestion()%></p>
              </div>
              <div class="multiAnswerA">
                <%
                  for(int i = 0; i < curr.getAnswers().size(); i++){
                %>
                <div class="multiAnswerAns">
                  <textarea name = "<%=j%>multiAnswer<%=i%>" class="multiAnswerTextArea" placeholder="Type your answer here..."></textarea>
                </div>
                <%
                  }
                %>
              </div>
            </div>
            <%
            }else if(type == 6){
            %>
            <div class="multipleChoice">
              <div class="multipleChoiceTitle">
                <p>You can select multiple answers:</p>
              </div>
              <div class="multipleChoiceQ">
                <p><%=curr.getQuestion()%></p>
              </div>
              <div class="multipleChoiceA">
                <%
                  ArrayList<String> possibleAnswers = curr.getPossibleAnswers();
                  for(int i = 0; i < possibleAnswers.size(); i++){
                %>
                <label class="multipleChoiceAns">
                  <input type="checkbox" name="<%=j%>multiChoiceMultiAns<%=i%>" value="<%=i%>">
                  <%=possibleAnswers.get(i)%>
                </label>
                <%
                  }
                %>
              </div>
            </div>
            <%
            }else if(type == 7){
              ArrayList<String> rows = curr.getPossibleAnswers();
            %>
            <div class = "matching">
              <div class = "matchingTitle">
                <p><%=curr.getQuestion()%></p>
              </div>
              <div class = "matchingColumns">
                <div class = matchColumnLeft>
                  <%for(int i = 0; i < rows.size()/2; i++){%>
                  <div class = "matchColumnRow">
                    <div class = "matchingQDiv">
                      <p class = "matchingQ"><%=rows.get(i)%></p>
                    </div>
                    <input type="text" name="<%=Integer.toString(j) + "matching" + Integer.toString(i)%>" class = "matchingA">
                  </div>
                  <%}%>
                </div>
                <div class = matchColumnRight>
                  <%for(int i = 0; i < rows.size()/2; i++){%>
                  <div class = "matchColumnRow">
                    <p class = "matchingNum"><%=i+1%></p>
                    <div class = "matchingQDiv">
                      <p class = "matchingQ"><%=rows.get(rows.size()/2 + i)%></p>
                    </div>
                  </div>
                  <%}%>
                </div>
              </div>
            </div>
            <%}%>
          </div>
          <%
            }
          %>
        </div>
      </div>
    </div>
      <%
    }
  %>
</form>
</body>
<%
    }
%>
</html>