package Servlets;

import Questions_DAO.*;
import Usernames_DAO.UserQuiz.UserCreatesQuiz;
import Usernames_DAO.manager.AdminManager;
import Usernames_DAO.manager.accountManager;
import Usernames_DAO.models.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AccManagerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        accountManager AccountManager = null;
        try {
            AccountManager = new accountManager();
            boolean newlyCreated = AccountManager.addAcc("admin", "admin");
            User user = new User("admin", true);
            user.promoteToAdmin();

            if (newlyCreated) {
                AdminManager admin = new AdminManager("admin");
                admin.addAnnouncement("admin", "Welcome to QuizHub!", "Step into a world of knowledge, " +
                                                                                        "curiosity, and fun. Get ready to test " +
                                                                                        "your wits, learn fascinating facts, and" +
                                                                                        " challenge your friends. With a galaxy " +
                                                                                        "of exciting quizzes to conquer, your quest" +
                                                                                        " for wisdom starts here. But wait, there's" +
                                                                                        " more – not only can you take quizzes, but" +
                                                                                        " you can also create your own! Share your " +
                                                                                        "expertise and explore topics you're passionate" +
                                                                                        " about. Join us in the thrill of discovery and" +
                                                                                        " competition. Let the quizzing begin!");
                UserCreatesQuiz quiz1 = new UserCreatesQuiz(user);
                quiz1.setQuizName("Fun Facts Fiesta");
                quiz1.setTags("fun facts", "for you", "medium");
                quiz1.setCategory("other");
                quiz1.setDescription("Welcome to 'Fun Facts Fiesta'! Are you ready to dive into a world of intriguing information? " +
                                    "This quiz is your gateway to discovering captivating facts that will leave you amazed. Challenge" +
                                    " yourself with questions spanning various topics and uncover nuggets of knowledge you might never" +
                                    " have encountered before. Unlock new insights, share surprising facts, and embark on a journey of" +
                                    " discovery with 'Fun Facts Fiesta'!");
                quiz1.setOnePage(false);
                quiz1.setRandom(false);
                quiz1.setImmediateCorrection(true);
                quiz1.setPracticeMode(true);
                quiz1.addQuestion(new QuestionMultiChoice("How many hearts does an octopus have?", "1//2//3//4", "3",
                                                                false, true));
                quiz1.addQuestion(new QuestionResponse("What is the collective noun for a group of flamingos?", "flamboyance",
                                                            false, false));
                quiz1.addQuestion(new QuestionFillBlank("A day on ___ is longer than its year. This planet takes about 243 Earth days to rotate on its axis, but only about 225 Earth days to orbit the sun.",
                                                            "Venus", true, true));
                quiz1.addQuestion(new QuestionMatching("Match these two columns:", "A group of zebras is called a//A group of jellyfish is called a//A group of owls is called a//A group of kangaroos is called a//A group of crows is called a//A group of dolphins is called a//A group of penguins is called a//" +
                        "parliament//pod//smack//murder//mob//dazzle//colony", "A group of zebras is called a//dazzle//A group of jellyfish is called a//smack//A group of owls is called a//parliament//A group of kangaroos is called a//mob//A group of crows is called a//murder//A group of dolphins is called a//pod//A group of penguins is called a//colony", false, false));
                quiz1.addQuestion(new QuestionResponse("What is the world's largest desert?", "Antarctic Desert", false, false));
                quiz1.addQuestion(new QuestionMultiChoiceMultiAnswer("How many triangles can be obtained from a quadrilateral using a single straight cut?", "1//2//3//4//5//6", "1//2//3", false, true));
                quiz1.addQuestion(new QuestionFillBlank("___ is exactly one number in English that has the same number of letters as the number itself", "Four", true, false));
                quiz1.FinishAndPublish();

                UserCreatesQuiz quiz2 = new UserCreatesQuiz(user);
                quiz2.setQuizName("MathMind Challenge");
                quiz2.setDescription("Test Your Numeric Navigations");
                quiz2.setCategory("Science");
                quiz2.setTags("Math", "Easy", "Short");
                quiz2.setOnePage(true);
                quiz2.setRandom(false);
                quiz2.setImmediateCorrection(false);
                quiz2.setPracticeMode(false);
                quiz2.addQuestion(new QuestionPictureResponse("How many triangles do you see?", "assets/triangles.jpg", "27", false, true));
                quiz2.addQuestion(new QuestionMultiChoice("Given the sequence \"12345678910111213141516...\". what is the 83th digit of sequence?", "3//4//5//6//7", "6", false, true));
                quiz2.addQuestion(new QuestionResponse("Monocarp the snail is climbing on a tree which is 26m tall. During daytime, Monocarp climbs 4m upwards and once it sleeps, it slips down by 3m. How many days does it take Monocarp to reach the top of the tree?", "23", false, false));
                quiz2.addQuestion(new QuestionFillBlank("There are ___ prime numbers between 1 and 100", "25", true, true));
                quiz2.addQuestion(new QuestionMultiChoiceMultiAnswer("Prime divisors of 7! are:", "2//3//5//7//11//13", "2//3//5//7", false, true));
                quiz2.FinishAndPublish();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        servletContextEvent.getServletContext().setAttribute("accManager", AccountManager);
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
