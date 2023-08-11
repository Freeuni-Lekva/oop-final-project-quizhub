USE my_database;
DROP TABLE IF EXISTS QuestionsTable;
CREATE TABLE QuestionsTable

(id BIGINT,
 type BIGINT,
 question CHAR(64),
 possibleAnswers CHAR(64),
 answer CHAR(64),
 ordered BIGINT,
 caseSensitive BIGINT);

-- id, name, creator, category, description,create_date, random, onePage, immediateCorrection

DROP TABLE IF EXISTS QuizTable;
CREATE TABLE QuizTable
(id BIGINT,
 name CHAR(64),
 creator CHAR(64),
 category CHAR(64),
 description CHAR(64),
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
(tags CHAR(64),
 id BIGINT);

DROP TABLE IF EXISTS Users;
CREATE TABLE Users
(username CHAR(64),
 password CHAR(64),
 administrator BOOLEAN);

DROP TABLE IF EXISTS FriendRequests;
CREATE TABLE FriendRequests
(username_from CHAR(64),
 username_to CHAR(64));

DROP TABLE IF EXISTS Friends;
CREATE TABLE Friends
(username_from CHAR(64),
 username_to CHAR(64));

DROP TABLE IF EXISTS UserQuiz;
CREATE TABLE UserQuiz
(username CHAR(64),
 id BIGINT(64),
 create_time TIMESTAMP);

DROP TABLE IF EXISTS Messages;
CREATE TABLE Messages
(id BIGINT,
 username_from CHAR(64),
 username_to CHAR(64),
 date TIMESTAMP,
 type enum('CHALLENGE','GENERAL') NOT NULL,
 unread BIGINT,
 quiz_id BIGINT,
 subject CHAR(64),
 text CHAR(64));

DROP TABLE IF EXISTS Achievements;
CREATE TABLE Achievements
(username CHAR(64),
 id BIGINT(64));

DROP TABLE IF EXISTS Announcements;
CREATE TABLE Announcements
(id BIGINT,
 username CHAR(64),
 subject CHAR(64),
 text CHAR(64));

DROP TABLE IF EXISTS Rankings;
CREATE TABLE Rankings
(id BIGINT,
 quiz_id BIGINT,
 username CHAR(64),
 score BIGINT,
 start_time TIMESTAMP,
 end_time TIMESTAMP);

DROP TABLE IF EXISTS Reviews;
CREATE TABLE Reviews
(username CHAR(64),
 id BIGINT,
 text CHAR(64),
 date TIMESTAMP);