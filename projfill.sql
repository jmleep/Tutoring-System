USE leeperj28;

INSERT INTO STUDENT VALUES (2, 'Alex', 'Alexander', 1234567890, 0.00, '2011-09-21');
INSERT INTO STUDENT VALUES (3, 'Bobby', 'Brown', 23456768901, 300.00, '2012-12-13');
INSERT INTO STUDENT VALUES (4, 'Curtis', 'Collins', 3456789012, 200.00, '2012-04-23');
INSERT INTO STUDENT VALUES (5, 'Doug', 'Darwin', 4567890123, 400.00, '2012-07-12');

INSERT INTO TEACHER VALUES (2, 'Erin', 'Eiderson', 5676890123);
INSERT INTO TEACHER VALUES (3, 'Fred', 'Flanders', 6789012345);

INSERT INTO TUTORIAL VALUES (1, 'Math', 1);
INSERT INTO TUTORIAL VALUES (2, 'English', 3);
INSERT INTO TUTORIAL VALUES (3, 'Spanish', 2);
INSERT INTO TUTORIAL VALUES (4, 'French', 2);
INSERT INTO TUTORIAL VALUES (5, 'Programming', 1);

INSERT INTO ENROLL VALUES (1, 2, 'C');
INSERT INTO ENROLL VALUES (1, 5, 'B');
INSERT INTO ENROLL VALUES (2, 3, 'A');
INSERT INTO ENROLL VALUES (3, 4, 'D');
INSERT INTO ENROLL VALUES (3, 2, 'C');
INSERT INTO ENROLL VALUES (4, 5, 'A');
INSERT INTO ENROLL VALUES (5, 1, 'B');
INSERT INTO ENROLL VALUES (5, 3, 'C'); 