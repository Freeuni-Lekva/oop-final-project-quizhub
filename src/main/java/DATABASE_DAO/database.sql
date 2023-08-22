# For database-side testing, switch from using the "my_database" to the "test_database".
# This change will ensure that our tests don't affect the production database and help maintain data integrity.
# Remember to update any relevant configurations and connection strings accordingly.

USE my_database;
DROP TABLE IF EXISTS QuestionsTable;
CREATE TABLE QuestionsTable

(id BIGINT,
 type BIGINT,
 question TEXT,
 possibleAnswers TEXT,
 answer TEXT,
 ordered BIGINT,
 caseSensitive BIGINT);


DROP TABLE IF EXISTS QuizTable;
CREATE TABLE QuizTable
(id BIGINT,
 name TEXT,
 creator TEXT,
 category TEXT,
 description TEXT,
 create_date TIMESTAMP,
 random BOOLEAN,
 onePage BOOLEAN,
 immediateCorrection BOOLEAN,
 practiceMode BOOLEAN);

DROP TABLE IF EXISTS Quiz_QuestionTable;
CREATE TABLE Quiz_QuestionTable
(id BIGINT,
 question_id BIGINT);

DROP TABLE IF EXISTS Tags_QuizTable;
CREATE TABLE Tags_QuizTable
(tags TEXT,
 id BIGINT);

DROP TABLE IF EXISTS Users;
CREATE TABLE Users
(username TEXT,
 password TEXT,
 administrator BOOLEAN,
 challengeNotification BOOLEAN,
 messageNotification BOOLEAN,
 requestNotification BOOLEAN);

DROP TABLE IF EXISTS FriendRequests;
CREATE TABLE FriendRequests
(username_from TEXT,
 username_to TEXT,
 date TIMESTAMP);

DROP TABLE IF EXISTS Friends;
CREATE TABLE Friends
(username_from TEXT,
 username_to TEXT);

DROP TABLE IF EXISTS UserQuiz;
CREATE TABLE UserQuiz
(username TEXT,
 id BIGINT(64),
 create_time TIMESTAMP);

DROP TABLE IF EXISTS Messages;
CREATE TABLE Messages
(username_from TEXT,
 username_to TEXT,
 date TIMESTAMP,
 isChallenge BOOLEAN,
 quiz_id BIGINT,
 text TEXT);

DROP TABLE IF EXISTS Achievements;
CREATE TABLE Achievements
(username TEXT,
 id BIGINT(64));

DROP TABLE IF EXISTS Announcements;
CREATE TABLE Announcements
(id BIGINT,
 username TEXT,
 subject TEXT,
 text TEXT);

DROP TABLE IF EXISTS Rankings;
CREATE TABLE Rankings
(id BIGINT,
 quiz_id BIGINT,
 username TEXT,
 score BIGINT,
 start_time TIMESTAMP,
 end_time TIMESTAMP);


