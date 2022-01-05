# Visual Transit Simulator: Project Iteration 2

## The Visual Transit Simulator Software

In the project iterations, you will be working on a visual transit simulator (VTS) software. The current VTS software models vehicle transit around the University of Minnesota campus. Specifically, the software simulates the behavior of vehicles and passengers on campus. The VTS software currently supports two types of vehicles: buses and trains. Vehicles provide service for a line. A line is made by two routes: an outbound and an inbound route. Vehicles go along a route, make stops, and pick up/drop off passengers. The simulation operates over a certain number of time units. In each time unit, the VTS software updates the state of the simulation by creating passengers at stops, moving vehicles along the routes, allowing a vehicle to pick up passengers at a stop, etc. The simulation is configured using a *configuration* file that specifies the simulation lines, the stops of the routes, and how likely it is that a passenger will show up at a certain stop at each time unit. Routes must be defined in pairs, that is, there should be both an outbound and inbound route and the routes should be specified one after the other. The ending stop of the outbound route should be at the same location as the starting stop of the inbound route and the ending stop of the inbound route should be at the same location as the starting stop of the outbound route. However, stops between the starting and ending stops of outbound and inbound routes can be at different locations. After a vehicle has passed a stop, it is possible for passengers to show up at stops that the vehicle has already passed. For this reason, the simulator supports the creation of multiple vehicles and these vehicles will go and pick up the new passengers. Each vehicle has its own understanding of its own route, but the stops have relationships with multiple vehicles serving the same line. Vehicles do not make more than one trip in the line they serve. When a vehicle finishes both of its routes (outbound and inbound), the vehicle exits the simulation.

The VTS software is divided into two main modules: the *visualization module* and the *simulator module*. The visualization module displays the state of the simulation in a browser, while the simulator module performs the simulation. The visualization module is a web client application that runs in a browser and it is written in Javascript and HTML. The visualization module code is inside the `<dir>/src/main/webapp/web_graphics` directory of this repo (where `<dir>` is the root directory of the repo). The simulator module is a web server application written in Java. The simulator module code is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project` directory. The simulator module is divided into two parts: *model classes* and the *webserver classes*. The model classes model real-world entities (e.g., the concept of a vehicle) and the code is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model` directory. The webserver classes include the code that orchestrates the simulation and is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/webserver` directory. The visualization module and the simulator module communicate with each other using [websockets](https://www.baeldung.com/java-websockets).

The user of the VTS software interacts with the visualization module using the browser and can specific how long the simulation will run (i.e., how many time units) and how often new vehicles will be added to a route in the simulation. The users also specifies when to start the simulation. The image below depicts the graphical user interface (GUI) of the VTS software.

![GUI of the VTS Software](/images/vts_iteration_1.png)

### VTS Software Details

#### Simulation Configuration
The simulation is based on the `<dir>/src/main/resources/config.txt` configuration file. The following excerpt of the configuration file defines a bus line.

```
LINE_START, BUS_LINE, Campus Connector

ROUTE_START, East Bound

STOP, Blegen Hall, 44.972392, -93.243774, .15
STOP, Coffman, 44.973580, -93.235071, .3
STOP, Oak Street at University Avenue, 44.975392, -93.226632, .025
STOP, Transitway at 23rd Avenue SE, 44.975837, -93.222174, .05
STOP, Transitway at Commonwealth Avenue, 44.980753, -93.180669, .05
STOP, State Fairgrounds Lot S-108, 44.983375, -93.178810, .01
STOP, Buford at Gortner Avenue, 44.984540, -93.181692, .01
STOP, St. Paul Student Center, 44.984630, -93.186352, 0

ROUTE_END

ROUTE_START, West Bound

STOP, St. Paul Student Center, 44.984630, -93.186352, .35
STOP, Buford at Gortner Avenue, 44.984482, -93.181657, .05
STOP, State Fairgrounds Lot S-108, 44.983703, -93.178846, .01
STOP, Transitway at Commonwealth Avenue, 44.980663, -93.180808, .01
STOP, Thompson Center & 23rd Avenue SE, 44.976397, -93.221801, .025
STOP, Ridder Arena, 44.978058, -93.229176, .05
STOP, Pleasant Street at Jones-Eddy Circle, 44.978366, -93.236038, .1
STOP, Bruininks Hall, 44.974549, -93.236927, .3
STOP, Blegen Hall, 44.972638, -93.243591, 0

ROUTE_END

LINE_END


STORAGE_FACILITY_START

BUSES, 5
TRAINS, 2

STORAGE_FACILITY_END
```

The configuration line `LINE_START, BUS_LINE, Campus Connector` defines the beginning of the information belonging to a simulated line. The configuration line `ROUTE_START, East Bound` defines a the beginning of the information defining the outbound route. (The outbound route is always defined before the inbound route). The subsequent configuration lines are the stops in the route. Each stop has a name, a latitude, a longitude, and the probability to generate a passenger at the stop. For example, for `STOP, Blegen Hall, 44.972392, -93.243774, .15`, `Blegen Hall` is the name of the stop, `44.972392` is the latitude, `-93.243774` is the longitude, and `.15` (i.e., `0.15`) is the probability to generate a passenger at the stop. The last stop in a route has a probability to generate a passenger always equal to zero. The information inside `STORAGE_FACILITY_START` and `STORAGE_FACILITY_END` provide the number of buses and trains available for the simulation.

#### Running the VTS Software
To run the VTS software, you have to first start the simulator module and then start the visualization module. To start the simulator module, go to `<dir>` and run `./gradlew appRun`. To start the visualization module, open a browser and paste this link `http://localhost:7777/project/web_graphics/project.html` in its search bar. To stop the simulator module, press the enter/return key in the terminal where you started the module. To stop the visualization module, close the tab of browser where you started the module. In rare occasions, you might experience some issues in starting the simulator module because a previous instance of the module is still running. To kill old instances, run `ps aux | grep gretty | awk '{print $2}' | xargs -L 1 kill` and this command will terminate previous instances. (The command is killing the web server container running the simulator module.) The command works on CSE lab machines.

#### Simulation Workflow
Because the VTS software is a web application, the software does not have a `main` method. When you load the visualization module in the browser, the visualization module opens a connection to the simulator module (using a websocket). The opening of the connection triggers the execution of the `WebServerSession.onOpen` method in the simulator module. When you click `Start` in the GUI of the visualization module, the module starts sending messages/commands to the simulator module. The messages/commands exchanged by the two modules are [JSON objects](https://www.w3schools.com/js/js_json_objects.asp). You can see the messages/commands created by the visualization module insdie `<dir>/src/main/webapp/web_graphics/sketch.js`. The simulator module processes messages received by the visualization model inside the `WebServerSession.onMessage` method. The simulator module sends messages to the visualization module using the `WebServerSession.sendJson` method. Finally, once you start the simulation you can restart it only by reloading the visualization module in the browser (i.e., reloading the web page of the visualization module).

## Tasks and Deliverables
In this project iteration, you will need to further understand, extend, and test the VTS software. The tasks of this project iteration can be grouped into three types of activities: creating the software documentation, making software changes, and testing. The following table provides a summary of the tasks you need to perform in this project iteration. For each task, the table reports the task ID, the activity associated with the task, a summary description of the task, the deliverable associated with the task, and the major deliverable that includes the task deliverable.

| ID | Activity | Task Summary Description | Task Deliverable | Deliverable |
|---------|----------|--------------------------|------------------|----------------------|
| Task 1 | Software documentation | Update the UML class diagram for the model classes | UML Class Diagram | HTML Javadoc |
| Task 2 | Software documentation | Update the UML class diagram for the webserver classes | UML Class Diagram | HTML Javadoc |
| Task 3 | Software documentation | Create a Javadoc documentation for the code in the simulator module | HTML Javadoc | HTML Javadoc|
| Task 4 | Software documentation | Make sure that the code conforms to the Google Java code style guidelines | Source Code | Source Code |
| Task 5 | Software changes | Refactoring 1 - Refactor duplicate methods in `Bus` and `Train` | Source Code | Source Code |
| Task 6 | Software changes | Refactoring 2 - Remove `private List<Route> routes` attribute in `VisualTransitSimulator`  | Source Code | Source Code |
| Task 7 | Software changes | Feature 1 - Represent `SmallBus`, `LargeBus`, `ElectricTrain`, and `DieselTrain` in the simulator | Source Code | Source Code |
| Task 8 | Software changes | Feature 2 - Generate buses according to strategies | Source Code | Source Code |
| Task 9 | Software changes | Feature 3 - Generate trains according to strategies | Source Code | Source Code |
| Task 10 | Software changes | Feature 4 - Observe detailed CO2 consumption of a vehicle | Source Code | Source Code |
| Task 11 | Software changes | Feature 5 - Simulate line issues | Source Code | Source Code |
| Task 12 | Testing | Create unit tests for all the model classes | Test Code | Test Code |


### Suggested Timeline for the Tasks

We suggest you define a tasks-oriented timeline for your team so that you can make progress throughout this project iteration. The schedule for the project iteration is very tight, and it is important that the team keeps up with the project. We suggest the following timeline. However, you are free to define your own timeline. All the major deliverables are due at the end of the project iteration. If you have questions on the management of your progress in the project iteration, please contact us on Piazza using the group we created and we will be happy to take it from there.

| Date | Milestone Description | Tasks |
|-----------------|-----|-----------------|
| 11/08/2021 at 8:00 am | Software documentation and testing | [Task 1], [Task 2], [Task 12] |
| 11/15/2021 at 8:00 am | Software changes | [Task 5],[Task 6],[Task 7],[Task 8],[Task 9],[Task 3],[Task 4] |
| 11/22/2021 at 8:00 am | Software changes, testing, and revision of the software documentation | [Task 10],[Task 11],[Task 12],[Task 1],[Task 2],[Task 3],[Task 4] |

### Tasks Detailed Description
This section details the tasks that your team **needs to perform** in this project iteration.

#### Task 1: Update the UML class diagram for the model classes
In this task, you should update the UML class diagram of the model classes (i.e., classes in the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model` directory) to account for the changes/additions required in this project iteration. If necessary, provide a description inside `<dir>/doc/overview.html` to clarify the subtleties that are essential to understand the diagram and the design decisions you made.

You should update the diagram image in `<dir>/doc/diagrams`. You can generate the HTML Javadoc documentation, which includes this diagram, by running `./gradlew clean javadoc`.

#### Task 2: Update the UML class diagram for the webserver classes
In this task, you should update the UML class diagram of the webserver classes (i.e., classes in the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/webserver` directory) to account for the changes/additions required in this project iteration. If necessary, provide a description inside `<dir>/doc/overview.html` to clarify the subtleties that are essential to understand the diagram and the design decisions you made.

You should update the diagram image in `<dir>/doc/diagrams`. You can generate the HTML Javadoc documentation, which includes this diagram, by running `./gradlew clean javadoc`.

#### Task 3: Create a Javadoc documentation for the code in the simulator module
You should create Javadoc comments according to the Google Java code style guidelines we provided. In other words, add comments where the Google Java code style guidelines require your team to do so. You can generate the HTML Javadoc documentation by running `./gradlew javadoc` (or `./gradlew clean javadoc`).

#### Task 4: Make sure that the code conforms to the Google Java code style guidelines
Consistency in code organization, naming conventions, file structure, and formatting makes code easier to read and integrate. In this project, the team will follow the Google Java code style guidelines. These guidelines are provided in the `<dir>/config/checkstyle/google_checks.xml` code style file. The team needs to make sure that the code produced in this project iteration (both source and test code) complies with the rules in `<dir>/config/checkstyle/google_checks.xml`. Both source and test code should conform to the rules. You can check if the code conforms to the code style rules by running `./gradlew check` or (`./gradlew clean check`).

#### Task 5: Refactoring 1 - Refactor duplicate methods in `Bus` and `Train`

In project iteration 1, you might have noticed that certain methods have the same implementation in the `Bus` and `Train` classes. In this task, you should move the implementation of those methods (e.g., `update()`) to their super class (i.e., `Vehicle`).

Your team needs to create a GitHub issue to indicate that your team is working on this refactoring (in this project iteration we are using issues to work on features). The title of the issues should be "Refactoring 1 - Refactor duplicate methods in Bus and Train". This task needs to be performed in a branch called `Refactoring1`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Refactoring1` branch into the `main` branch.


#### Task 6: Refactoring 2 - Remove `private List<Route> routes` attribute in `VisualTransitSimulator`

In the current codebase, the `VisualTransitSimulator` class has both the `List<Route> routes` and `List<Line> lines` attributes. In this refactoring, you should delete the `List<Route> routes` attribute and make suitable changes so that the class only uses the `List<Line> lines` attribute and the information contained in the list.

Your team needs to create a GitHub issue to indicate that your team is working on this refactoring. The title of the issues should be "Refactoring 2 - Remove provide List<Route> routes attribute in VisualTransitSimulator". This task needs to be performed in a branch called `Refactoring2`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Refactoring2` branch into the `main` branch.


#### Task 7: Feature 1 - Represent `SmallBus`, `LargeBus`, `ElectricTrain`, and `DieselTrain` in the simulator

The simulator should now support the use of four types of vehicles: `SmallBus`, `LargeBus`, `ElectricTrain`, and `DieselTrain`. `SmallBus` and `LargeBus` need to extend the `Bus` class. A small bus has a capacity of 20 passengers. A large bus has a capacity of 80 passengers. The CO2 consumption of a small bus is one unit for every passenger present on the bus at a certain time unit plus a constant value of one CO2 unit. The CO2 consumption of a large bus is one unit for every passenger present on the bus at a certain time unit plus a constant value of three CO2 units. `ElectricTrain` and `DieselTrain` need to extend the `Train` class.  Electric trains have zero CO2 consumption. Diesel trains consume two CO2 units for every passenger present on the train at a certain time unit plus a constant value of six CO2 units.

To know the number of small buses, large buses, electric trains, and diesel trains available for the simulation, the simulator module should be updated to read the following information from the configuration file (the number of buses/trains might change if a different configuration is provided).

```
STORAGE_FACILITY_START

SMALL_BUSES, 3
LARGE_BUSES, 2
ELECTRIC_TRAINS, 1
DIESEL_TRAIN, 5

STORAGE_FACILITY_END

```

All the classes associated with this feature need to be model classes (i.e., inside `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model`). Your team needs to create a GitHub issue to indicate that your team is working on this feature. The title of the issues should be "Feature 1 - Represent SmallBus, LargeBus, ElectricTrain, and DieselTrain in the simulator". This task needs to be performed in a branch called `Feature1`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature1` branch into the `main` branch.


#### Task 8: Feature 2 - Generate buses according to strategies

To enhance the simulator, your team needs to create the "time of day" bus deployment strategy. The "time of day" bus deployment strategy determines the size of the bus based on the time of the day. Specifically, this bus deployment strategy needs to adhere to the following specifications:

```
Determine the current local time when the simulation is initialized
If the current local time is:
8am or later but before 4pm 
		Deploy the bus using BusStrategyDay
4pm or later but before 8am
		Deploy the bus using BusStrategyNight
```

The deployment strategies you need to implement are as follows:

```
BusStrategyDay deploys buses in the following repeating sequence:
	large, large, small, large, large, small, etc. (the sequence keeps repeating)
BusStrategyNight deploys buses in the following repeating sequence:
	small, small, small, large, small, small, small, large, etc. (the sequence keeps repeating)
```
Each time the simulation is required to create a new bus, it must use the type of bus returned by the strategy that is currently in effect as long as the type of bus is available in the storage facility. Otherwise, no bus should be created and one can be generated only at the next time unit in which a bus should be generated. Note, when a strategy function/method is called for the strategies, it will return the type of bus in a repeating sequence. One way to implement this functionality is to enable each strategy to keep track of its state. You might also find useful to use the `java.time.LocalDateTime` [class](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html) from the standard Java library to implement the feature associated with this task.

All the classes associated with this feature need to be model classes (i.e., inside `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model`). This feature needs to be implemented using the factory method and the strategy design patterns. Your team needs to create a GitHub issue to indicate that your team is working on this feature. The title of the issues should be "Feature 2 - Generate buses according to strategies". This task needs to be performed in a branch called `Feature2`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature2` branch into the `main` branch.

#### Task 9: Feature 3 - Generate trains according to strategies

To enhance the simulator, your team needs to create the "time of day" train deployment strategy. The "time of day" train deployment strategy determines the type of train based on the time of the day. Specifically, this train deployment strategy needs to adhere to the following specifications:

```
Determine the current local time when the simulation is initialized
If the current local time is:
8am or later but before 4pm 
		Deploy the train using TrainStrategyDay
4pm or later but before 8am
		Deploy the train using TrainStrategyNight
```

The deployment strategies you need to implement are as follows:

```
TrainStrategyDay deploys trains in the following repeating sequence:
	electric, electric, electric, diesel, electric, electric, electric, diesel, etc. (the sequence keeps repeating)
TrainStrategyNight deploys train in the following repeating sequence:
	electric, diesel, electric, diesel, etc. (the sequence keeps repeating)
```
Each time the simulation is required to create a new train, it must use the type of train returned by the strategy that is currently in effect as long as the type of train is available in the storage facility. Otherwise, no train should be created and one can be generated only at the next time unit in which a train should be generated. Note, when a strategy function/method is called for the strategies, it will return the type of train in a repeating sequence. One way to implement this functionality is to enable each strategy to keep track of its state. You might also find useful to use the `java.time.LocalDateTime` [class](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html) from the standard Java library to implement the feature associated with this task.

All the classes associated with this feature need to be model classes (i.e., inside `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model`). This feature needs to be implemented using the factory method and the strategy design patterns. Your team needs to create a GitHub issue to indicate that your team is working on this feature. The title of the issues should be "Feature 3 - Generate trains according to strategies". This task needs to be performed in a branch called `Feature3`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature3` branch into the `main` branch.


#### Task 10: Feature 4 - Observe detailed CO2 consumption of a vehicle

In this task, you need to extend the simulator module so that the VTS software is able to display the CO2 consumption from each of the latest five time units. The user can requests this information by clicking on a vehicle in the visualization module. The information displayed in the visualization module for a bus must have the following format:

```
Vehichle ID
-----------------------------
* Type: TYPE
* Position: (X,Y)
* Passengers: NUM_PASS
* CO2: CTU, CTU-1, CTU-2, CTU-3, CTU-4
```

In the above text, `TYPE` is either `BUS` or `TRAIN` based on the type of selected vehicle, `X` is the longitude, `Y` is the latitude of the bus, `NUM_PASS` is the current number of passengers on the vehichle, `CTU` is the CO2 consumption at the current time unit, `CTU-1` is the CO2 consumption at the previous time unit, etc. For example, if the CO2 consumption for a vehicle was 3, 10, 5, 8, 9 at time units 7, 8, 9, 10, 11 then the displayed CO2 line should be `* CO2: 9, 8, 5, 10, 3`.

This feature must be implemented using the observer design pattern. This feature must also suitably extend the command pattern used by other parts of the simulator. This feature must respond to the `registerVehicle` command that contains the `id` of the observed vehicle. This command is received as a [JSON object](https://www.w3schools.com/js/js_json_objects.asp) by the simulator module. The JSON object has the following format: `{"command":"registerVehicle","id":"VEHICLE_ID"}` (`VEHICLE_ID` is the vehicle identifier). This feature should provide the desired information to the visualization module using a JSON object. The JSON object has this format: `{"command":"observedVehicle","text":"ABOVE_INFO"}` (`ABOVE_INFO` is the text reported above). Receiving and sending JSON objects is a feature already implemented in the simulator module and used by other commands. The team can suitable identify how to write the code to process and send JSON objects for this feature by analyzing the simulator module code.

Your team needs to create a GitHub issue to indicate that your team is working on this feature. The title of the issues should be "Feature 4 - Observe detailed CO2 consumption of a vehicle". This task needs to be performed in a branch called `Feature4`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature4` branch into the `main` branch.

#### Task 11: Feature 5 - Simulate line issues

In this feature, we would like to simulate the behavior of issues happening on bus and train lines. The visualization module was extended to support the feature by adding a dropdown menu below the Start button. The dropdown menu contains the bus and train lines associated with the simulation. After the simulation has started, the user should be able to select a line from the dropdown menu to simulate an issue on the line. The issues affectes the movement of vehicles on that line. That is, vehicles on that line should not move for 10 time units from the time the line was selected by the user. For example, if a bus line was selected at time unit 23, then the buses on that line would not move until time step 33. This feature must respond to the `lineIssue` command that contains the `id` of the user-selected line. This command is received as a [JSON object](https://www.w3schools.com/js/js_json_objects.asp) by the simulator module and has this format: `{"command":"lineIssue","id":"LINE_ID"}` (`LINE_ID` is the line identifier).

This feature must suitably extend the command pattern used by other parts of the simulator. Your team needs to create a GitHub issue to indicate that your team is working on this feature. The title of the issues should be "Feature 5 - Simulate line issues". This task needs to be performed in a branch called `Feature5`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature5` branch into the `main` branch.

#### Task 12: Create unit tests for all the model classes
In this project iteration, your team needs to create unit tests for all the model classes. We are interested in seeing at least one test cases per method and your team does not need to create test cases for getter and setter methods. Your team has to document what each test is supposed to do by adding a Javadoc comment to the test. A sample set of test cases is provided in the `<dir>/src/test/java/edu/umn/cs/csci3081w/project/model` folder. In this task, your team can also reuse (and we encourage you to do so) the test cases that the team built during project iteration 1. We encourage your team to write the test cases before making any change to the codebase. These tests should all pass. After creating the test cases, you can perform the refactoring task and use the tests to ensure that your team did not introduce errors in the code. (Some test cases might also need refactoring.) When you add the new features, you should also add new test cases for the features (when applicable). All the test cases you create should pass. Your team can create test cases in any branch but the final set of test cases should be in the `main` branch. You can run tests with the command `./gradlew clean test`.

#### Important Notes

To complete the tasks of this project iteration, your team can reuse any of the design documents, code, and tests that your team created in project iteration 1. However, beware of the following points:

* You might have added classes, methods, and attributes that are not present in this repo.
* You should preserve and extend the functionality provided by this repo.
	
Additionally, take note that:
* Small and large buses must be implemented using two different classes.
* Electric and diesel trains must be implemented using two different classes.
* You should design and implement your classes for Tasks 8, and 9 so that you are able to test them. In other words, you need to be able to set the current time used by the simulator for testing purposes.

## Submission
All the team members should submit the commit ID of the solution to this project iteration on Gradescope. The commit ID should be from the `main` branch of this repo. We use the commit ID to be sure that all team members agree on the final solution submitted. If you use a group submission, then only one commit ID is ok.

### General Submission Reminders
* Use GitHub issues and pull requests as you develop your solution.
* Use the branches we listed to produce your team solution.
* Make sure the files of your solution are in the repo.
* Do no add the content of the `build` directory to the repo.
* Make sure your code compiles.
* Make sure the code conforms to the Google Java code style guidelines we provided.
* Make sure the HTML Javadoc documentation can be generated using `./gradlew clean javadoc`
* Make sure all test cases pass.

## Assessment
The following list provides a breakdown of how this project iteration will be graded.

* Software documentation: 25 points
* Software changes: 55 points
* Testing: 20 points

## Resources

* [A Guide to the Java API for WebSocket](https://www.baeldung.com/java-websockets)
* [JSON objects](https://www.w3schools.com/js/js_json_objects.asp)
* [Google Maps](http://maps.google.com)
* [LocalDateTime class from the standard Java library](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html)
