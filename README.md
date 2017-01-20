Project SER316

# spring17project
This is the initial dump for the ser316 spring 2017 project. It is a slightly modified version of the Memoranda project from memoranda.sf.net

The purpose of this project is to enhance the Memoranda application to support Time Management for Software Engineers. In essence, to augment Memoranda with features that support the time management concepts in the Personal Software Process, which you learned some of last year in SER215.

Memoranda is a project that has largely been dormant since 2007, so why are we using it? This codebase has been used in the past, it is of sufficient size and scope to challenge your skills in understanding a complete application (it has somewhere in the neighborhood of 25k lines of code). It also has features that just about anyone who has ever used a calendaring application can understand. Further, there are some documents from a former student project (one of the best ever done in former ser316/cst316) that we can leverage to help you bootstrap your Agile process.

In the docs directory are a few starting documents that may help you get going on your User Stories. The first is a Project Inception document. This short document describes the vision of the project, and can serve as a guide as you write your Sprint Goals. One is a SRS - Software Requirements Specification - which has 21 use cases that you can readily translate to user stories. However you are not constrained to just use these nor are you required to use all of them. 

Also in the docs directory is an initial set of analysis models - class diagrams and sequence diagrams in UML, that a previous team did as part of their solution process. These were included only in the event that you may want to use these as a head start on your design and implementation work. You are absolutely not required to use or implement anything in the analysis document!

Your first step should be to clone the github repository (easiest way is to clone directly into your Eclipse Workspace folder). You should then create a new Java project and uncheck default directory and browse to the cloned directory. Clicking finish should import the whole project with all references to the libraries and so forth. There are other possibilities to set up your project but they might include setting your BuildPath. You can use any method you like but don't change the project structure (like copy/pasting files to different folders).

You should then be able to run the project. The included ant script should work, and you should also be able to run it directly from within Eclipse (Start.java) or the command line or from the .bat/.sh scripts provided. Hint: After running and closing the window from Memoranda make sure you also terminate the the program in eclipse if it is still running (closing the window does not always terminate the program).

Try to change minimal things to see how everything works. After that go back to the initial version and start your SCRUM process and document all changes through github and Taiga (like I said, if it isn’t in github/taiga it does not count).

Enjoy!
Dr. Mehlhase
