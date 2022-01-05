Group 24: 
Names: Sai tallapragada(talla037), Andy Chen(chen6640), Sree Pemma(pemma003), Samuel Wong(wong0613)
All of Our Java Class Files and testing files are loathed in the src folder inside the Project 1 directory. This ReadMe file is also inside the Project 1 directory. Note, all the CSV files used for System Integration testing and Unit Testing are located in a folder called testing inside the Project 1 folder in our repo(Project1/testing).. The testing logs are also located in the Project1/testing folder inside our  testing folder as well. Note, all the automated testing files used for unit testing are located in the src folder inside the Project 1 directory of our repo(Project1/src). The javdoc comments are there in almost every file for all the methods. The auto generated javadoc comments are in the directory called documentation in the Project1 directory(Project1/documentation). Furthermore, the BugList.pdf contains a list of bugs we found and this can be found inside the Project1 directory of our repo.

                                             Compilation and Running Our program(Use a Linux terminal For This Part)
                                             
Compilation: javac Main.java
Running the Program: java Main
After running the main file you will be prompted to input the filename. Input filename into the window (if file is not in the same directory as the program, input filepath). After running the program, audit.txt and mediaFile.txt should be present in the same directory as the program(Project1/src). The results of the election should also be displayed in the terminal.

 
                                                              Unit testing For Our Program
                                                              
 In order to run automated tests, run JUnit tests in Intellij All test classes are labeled as ClassName_Test.java.
We need to have the packages necessary to run Junit so if you guys already have it, you guys can skip the steps and go directly to testing. The Testing logs in the testing folder should have the name of each unit(function/method) that was tested. The Unit testing has the id= UT1 for example and System Integration testing has an id=SUT1 for example.
We are going to try to add org.junit to our class file path in order for us to be able to do automated unit testing. We struggled with this so we have come up with two ways to do this. The First way is a very long process. The second way is a very less time consuming so if you would like to try the second way provided believe first to save time, feel free to. You can also add the org.junit to the class file path if you know how to do that yourself.
 
 
First way(Preffered Way- We did it This way and it works so please use the 5 steps below.)
Steps: 
1) Open up IntelliJ on the repo-Team24(Preffered) or the Project1 Folder. Click open(If it prompts you to open as project then do it, other wise don't wory about it. <br/>
2) Once IntelliJ is Open, first go to the top left hand corner and click on File.  <br/>
3) Click on Project Structure.  <br/>
4) Go to the "Libraries" group, click the little green plus (look up), and choose "From Maven...".  <br/>
5) Next, search for "junit" and select a version that is 4 or above. We chose junit:junit:4.13.2. Don't choose anything that says Snap shot.
Do not select the download option as you don't want to do that. If the download option is clicked(ticked), then make sure that it is not selected. Simply click ok.
Hit Apply and OK. Keep hitting OK until you're back to the code.
Now, You can simply Try running the tests by going to their respective files and pressing the little green play button which should appear next to each testing class in each testing file.
 
If this does not work, you can try an alternative method.

Second way
Steps: 
Build the Program(You may have errors)
Go to a testing file
Put your cursor on any one of the import org.junit statements on the top of any one of the testing files if there is an error showing.
IntelliJ will have a Red bulb to the left and it will suggest you to add Junit into the class path if you click on it.
We are trying to add juniti into our class file path so we can run our unit tests.
 
To Run Our Unit tests, Please Use IntelliJ. There should be a green play button available for every test and you can also run all the tests for a class at once by clicking on the green play button next to the class name.


                                                           System Testing For Our Program:
 
Please refer to the test logs which will talk about the different system tests we did and with what files. Our test logs should also explain this.
For system testing these are manual tests and require the user to run the main class and input the path to these test files manually. These files can all be found in the testing subdirectory. 
 Please Note that when you input the file name of a test csv file, you must input the file path to that file located in the testing folder located inside the Project 1 directory.
 
Note, just compile the main program using the instructions from the beginning and give it the file path to the test csv files as specified in the testing logs to replicate our System Integration testing. Every test is specified in the test logss.

