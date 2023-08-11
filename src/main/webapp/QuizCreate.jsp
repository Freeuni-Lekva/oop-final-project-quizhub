<!DOCTYPE html>
<html>

<head>
    <link rel="icon" href="logo.png" />
    <title>QuizHub</title>
    <link rel="stylesheet" type="text/css" href="css/QuizCreateStyle.css" />
</head>

<body style="overflow-y: hidden;">
<form action = "QuizCreate" method = "POST">
    <div class="top">
        <div style="display: flex; flex-direction: row; gap: 5px; align-items: center; padding: 10px">
            <a href="Homepage.jsp"><img src="logo.png" alt="QuizHub Logo" width="80" height="80" ; /></a>
            <a href="Homepage.jsp">
                <p class="logo">QuizHub</p>
            </a>
        </div>
        <div class = "endButtons">
            <button type="exit" class="eButton" name="exit-button" value="exitButton">Exit</button>
        </div>
    </div>
    <hr style="width: 100%; height: 1px; color: #FFF;">
    </hr>
    <div class="content">
        <div class="leftPannel">
            <p class="title">Choose Quiz Properties<br>And Options:</p>
            <p class="description">Determine if all the questions should appear on a single webpage, with a single
                submit button, or if the quiz should display a single question allow the user to submit the answer, then
                display another question.</p>
            <div class="checkRow">
                <label class="checker">Single Page
                    <input type="radio" checked="checked" name="page" value = "SinglePage">
                    <span class="checkmark"></span>
                </label>
                <label class="checker">Multiple Page
                    <input type="radio" name="page" value = "MultiPage">
                    <span class="checkmark"></span>
                </label>
            </div>
            <p class="description">Determine either randomize the order of the questions or to always present them in
                the same order.</p>
            <div class="checkRow">
                <label class="checker">Ordered
                    <input type="radio" checked="checked" name="ordering" value = "Ordered">
                    <span class="checkmark"></span>
                </label>
                <label class="checker">Randomized
                    <input type="radio" name="ordering" value = "Randomized">
                    <span class="checkmark"></span>
                </label>
            </div>
            <p class="description">Determine whether the user will receive immediate feedback on an answer, or if the
                quiz will only be graded once all the questions have been seen and responded to. This option can be only
                used for multiple page quizzes.</p>
            <label class="checkBox">Correction
                <input type="checkbox" name="feedback" value = "Correction">
                <span class="checkboxmark"></span>
            </label>
            <p class="description">Determine whether or not the quiz can be taken in practice mode.
                This option can be only used for multiple page quizzes.</p>
            <label class="checkBox">Practice Mode
                <input type="checkbox" name="PracticeMode" value = "PracticeMode">
                <span class="checkboxmark"></span>
            </label>
        </div>
        <div class="rightPannel">
            <div class="pannelRow">
                <p>Enter quiz name:</p>
                <div class="nameInput">
                    <input type="text" name="name" class = "nameInp" placeholder="Type name here...">
                </div>
            </div>
            <div class="pannelRow">
                <p>Choose category:</p>
                <div class="categoryDiv">
                    <label class="categoryChoice">
                        <input type="radio" name="category" id="art" value = "art">
                        Art
                    </label>
                    <label class="categoryChoice">
                        <input type="radio" name="category" id="history" value = "history">
                        History
                    </label>
                    <label class="categoryChoice">
                        <input type="radio" name="category" id="geography" value = "geography">
                        Geography
                    </label>
                    <label class="categoryChoice">
                        <input type="radio" name="category" id="literature" value="literature">
                        Literature
                    </label>
                    <label class="categoryChoice">
                        <input type="radio" name="category" id="science" value="science">
                        Science
                    </label>
                    <label class="categoryChoice">
                        <input type="radio" checked="checked" name="category" id="other" value = "other">
                        Other
                    </label>
                </div>
            </div>
            <div class="pannelRow">
                <p>Enter tags:</p>
                <div class="tags">
                    <div class="tagInput">
                        <input type="text" name ="tag1" class = "tagInp" placeholder="Type tag here...">
                    </div>
                    <div class="tagInput">
                        <input type="text" name ="tag2" class = "tagInp" placeholder="Type tag here...">
                    </div>
                    <div class="tagInput">
                        <input type="text" name ="tag3" class = "tagInp" placeholder="Type tag here...">
                    </div>
                </div>
            </div>
            <div class="pannelRow">
                <p>Write quiz description:</p>
            </div>
            <div class="descDiv">
                <textarea class="descTextArea" name = "description"
                          placeholder="Type description here..."></textarea>
            </div>
            <div style = "width: 1100px; display: flex; justify-content: center;">
                <button type="submit" class="sButton" style ="cursor: pointer;">Save details and move to<br>the questions section!</button>
            </div>
        </div>
    </div>
</form>
</body>

</html>