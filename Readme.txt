
The zip file name is 'CRUD-GradeBook-ototade-Netbeans'.
There are two separate projects in the zip file.
One is for the server, named as 'CRUD_server'.
The other one is the client, named as 'CRUD_client'.
I have used Netbeans as the IDE and Glassfish as my server. So you need to use the Netbeans-Glassfish combination.

Extract both the projects.

Instructions to Run the project - 

1. Right click on the server project and select 'Clean and Build' to build the server project.

2. Right click on the client project and select 'Clean and Build' to build the client program.

3. Now you need to run the server, by right clicking and selecting 'Run'. This will pop up a new browser window which you need to close. The server is now running.

4. You can now run the client by right clicking and selecting 'Run'. The client is a GUI program developed using Java Swing.

The client GUI has two parts, 'Instructor Options' and 'Student Options'.

For Instructor Options, the client provides CRUD functionality for GradeItems, CRUD functionality for giving scores to students for individual GradeItems, functionality for retrieving all the grades for a particular student based on his ID, and the functionality to retireve the entire GradeBook.

For Student Options, the client provides GET functionality wherein the student can retrieve his score for any GradeItem using his StudentID.

The client GUI also provides proper response codes and location URIs for appropriate requests.

This is how you can build and run the client and server.