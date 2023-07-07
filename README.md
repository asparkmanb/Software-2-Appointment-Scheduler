# Software-2-Appointment-Scheduler

This program uses Java with a mySQL database connection to create,  modify, update, and delete appointment schedules as well as customer information. 


Prerequisites
For this program to run smoothly, there are a few prerequisites that the user must meet. 
1.	Windows PC running Windows 10.
2.	A MySQL database setup with a database named client_schedule and a username of sqlUser and password of Passw0rd!
3.	Java 17
4.	IntelliJ 2021.1.1.3

Installation and Using the Application
1.	To install the application, first download the included Capstone.zip file. 
2.	Navigate to the downloaded folder, right-click and hit “Extract All”
 
3.	Open the unzipped folder
4.	Inside, you will find a folder titled “C195 3”.
5.	Open IntelliJ
6.	Inside IntelliJ, press File – Open
7.	Navigate to the “C195 3” folder from earlier and open it
 
8.	Press “new window” when prompted
9.	This will open the project inside IntelliJ.
10.	To run the application, ensure that the “main” file is selected and press the play button in the top right of the screen. 
 
11.	You will then be greeted with the login screen.
 
12.	If the program fails to start, check the prerequisites and ensure all are met. Also, ensure that the MySQL database is running. 




Login
1.	Once the application is open, you will be presented firstly with a login screen. 
2.	This screen has fields for a username, a password, and also displays the timezone that the current user is in. 
3.	To login, enter your username and password credentials in the respective fields. 
4.	To test the login if you do not have a user account, please use the user “test” and the password “test”.
 
5.	Press Login to enter the application or Exit to close the program. 

Homepage
Appointments
This is the homepage. Here you will see a table of all appointments in the database. You will also see on the left side the ability to create new appointments by filling in the data. There are options to update and delete appointments at the bottom. At the top of the page, you can navigate to the customers page or the reports page. 
 

Creating A New Appointment
1.	To create a new appointment, all fields on the left must be filled out. The Start Date and End Date will display a calendar view to select the day. Start Time, End Time, Customer ID, User ID, and Contact are drop down options with pre-populated data for selection.
2.	Once all these fields are entered with information, press the Add button on the bottom to add the new appointment to the table. The table will automatically populate the new appointment. 
Modifying An Existing Appointment
1.	This program comes with the ability to modify appointments that are already present.
2.	Select an appointment in the table, this will highlight the appointment
3.	Press the Update Appointment at the bottom of the screen
 
4.	The data for the selected appointment will now be inserted into the fields on the left. To update, change any of this data to your liking. 
 
5.	Once the information is correct, press the Save button to update the appointment. 

Deleting an Appointment
1.	To delete an appointment, select the appointment in the table and highlight it. 
2.	Press the Delete Appointment button at the bottom of the screen
 
3.	A confirmation dialog will be displayed. If you wish to continue deleting the appointment, press the OK button. If you wish to cancel, press cancel and the appointment will remain. 
 
4.	If you chose to delete the appointment, a dialog box will display stating which appointment has successfully been deleted. Press OK to continue.
 

Customers
To enter the customers page, press the View Customers button at the top of the homepage. This will land you on the default customers page. On this page, there will be a table that is like the appointments table. It will list all the current patients. On the left side, much like the appointments page, is the option to add new patients. A modify customer button and delete customer button will be found at the bottom of the page. 
 
Creating A New Patient
1.	To create a new patient, all fields on the left must be filled out. Country and State are drop-down options with pre-populated data for selection.
2.	Once all these fields are entered with information, press the Add button on the bottom to add the new patient to the table. The table will automatically populate the new patient. 
 
Modifying An Existing Patient
1.	This program comes with the ability to modify patients that are already present.
2.	Select a patient in the table, this will highlight the patient
3.	Press the Modify Customer at the bottom of the screen
 
4.	The data for the selected patient will now be inserted into the fields on the left. To update, change any of this data to your liking. 
 
5.	Once the information is correct, press the Save button to update the patient.
Deleting a Patient
1.	To delete a patient, select the patient in the table and highlight it. 
 
2.	Press the Delete Customer button at the bottom of the screen
 
3.	A confirmation dialog will be displayed. If you wish to continue deleting the patient's record, press the OK button. If you wish to cancel, press cancel and the appointment will remain. 
 
4.	If you chose to delete the patient, a dialog box will display stating which patient has successfully been deleted. Press OK to continue.
 

 
Reports
To enter the reports page, press the View Reports button at the top of the homepage. This will land you on the default reports page. This page will be helpful to management when they want to see custom reports from the office. There are three main reports that will be shown below.
 
Viewing Total Number of Appointments by Type and Month Report
1.	This report will display the number of appointments by a certain type given a certain month.
2.	To begin creating this report, click the drop-down titled month and choose the given month you would like to run the report for. 
3.	Press the type drop-down, and chose the type of appointment that you would like to generate the report for.
 
4.	Once both fields have been selected, press the Generate Button.
5.	The report will be displayed to the right of the screen, and if there are no appointments with this type in the selected month, a message stating that no appointments were found.
 

Viewing Total Number of Appointments per User
1.	This report will generate the number of appointments that each User has scheduled.
2.	To begin, select the drop-down menu containing all of the users and select which one you would like to run the report for.
3.	Press the Generate button and the report will be shown to the right of the page. 
 

Viewing The Schedule for Each Contact
1.	 This report will display the schedule for a specified contact. The report will populate in the table shown.
2.	To generate this report, select the drop-down menu and chose which contact you would like to see more information for. 
3.	Once you have selected a contact, press the Show Appointments button to generate the report in the table below.
 

![image](https://github.com/asparkmanb/Software-2-Appointment-Scheduler/assets/26096483/1dee7e78-7763-4243-a7da-b8f370e8fdc6)
