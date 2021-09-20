NAME OF THE TEAM: 
COOLIPHANTS

TEAM MEMBERS:
OLANIPEKUN AKINTOLA
JACK MILLAR
LUCIE KONECNA

WHERE ALL YOUR DOCUMENTATION IS LOCATED:
The project documentations are within the documentaion folder in the same root folder that contains the source code.

HOW TO RUN THE PROJECT:
The Library Management application is launched using Docker using the following commands in sequence
mvn package
docker-compose build 
docker-compose up.

We used docker toolkit to run our app which means entering 192.168.99.100:8080 to the browser. This launches our Jedi Temple Archives, the Library Management application, in the browser. 

A non-registerd member of the public can browse the catalogue by clicking on the catalogue button on the library's homepage. They are directed to the catalogue's page where they are able to type in the 'Search this site' box and click the submit button. They are presented with a result of possible choices according to their search criteria.For a new user that wants to become a member of the Jedi Temple Archives, they would need to initially get the membership. This involves clicking the SignUp button on the homepage of our application. This would direct them to a signup page where they would need to complete a form. They would complete the following field; Name, Age, Address and Password and would have to specify whether they are a User or Admin. Afterwards they would click the Submit button.  The user would then be taken to a page displaying user details and we now have the logout being displayed instead of the login button. 

Now that I am a member of Jedi Temple Archive, I can logout and login again to test the member login feature. On login as a member, I am on a user page where I can see a user history button. This is also the same page to view member profile. To update user profile, you would have to re-enter your name and password and the only field that could be updated is the address. It is not possible to update user's age.By clicking User History button, it shows the reading history for a particular user. This includes the current list of loans (including return dates). I can go and reserve artifacts by clicking the home button and then the catalogue button. On the catalogue page, we can search for an artifact by typing a known word in the title in the search box. It displays all artifacts that have the typed word in its title. This helps narrow down the number of items that the user would search through in order to pick the artifact of interest. This feature would help when you have thousands of item in the library. The library has been made such that the search cannot only be conducted by the Title field but also by the Description and Media type field. Also on the catalogue page, we are able to view artifacts that are both available and on loan which displays the loan and reserve button respectively. On clicking both buttons, the reserve button is replaced by a text saying, 'You have reserved this Item' while the Loan button is replaced by Renew button. The dummy artifacts involved in this process is the Blade Squadron Part 1 and Part 2 respectively. I can use the Renew button when the artifact is approaching his return date.On navigating back to the Reading History page by clicking Profile and then User History buttons consecutively, the user can now see displayed both artifacts. The status for Blade Squadron 1 is Reservation because the artifact in question is currently on loan to a fictitious member of our library and the Checked Out date shows when this reservation was made. This would be of help when the artifact has been returned and two members have placed a reservation for the same artifact by processing the time of reservations to determine which user of those two users would be loaned the artifact by the library.
Blade Squadron Part 2 has displaying the Checkout date and Return date and the status on this user's page is Loan because the user is currently in possession of the artifact.

Jedi Archives Temple has just recruited a new Librarian and we want to set them as an Admin on the Library Management Sytem. They would also be clicking on the SignUp button which also brings them to the Signup page. They would be required to complete all the fields but unlike the library customers, they would click on the Admin radio button. On clicking the submit button, a user detail page is displayed where the user role is specified as admin. On this page, the librarian can search for a particular member of a library by entering the member's name in the 'user search' box. The librarian is taken to the member's profile where they are able to edit the member's profile. This is the same process as the member editing their own profile. They can view both the current and historical loans of a given member by clicking the User History button button on this page. This displays the reading history page for a given member. They are able to renew and reserve artifacts through accessing the member's profile and therefore having the same authorisation priviledge for that given member. To search for an artifact, the library can navigate to the catalogue page of our library app and then saerch for the artifact by Title, Description or Media type. If the librarian knows that the artifact is Scroll, they can type Scroll into the 'search this site' box and we are presented with a list of artifacts that are scrolls. To add and remove an artifact from a library, the librarian navigates to the catalogue page. Each item that has not yet being loaned out has the option to either delete the artifact or loan out the item. When we click on the loan out button, the buttons that were initially present for the artifact would be replaced by a return item button. On other hand, if we do choose to remove an artifact, we can click on the delete button. The artifacts disappears from our list of result for artifacts in the possession of Jedi Temple Archives. To add an artifacts, we click on the Create Media button which brings us to a field where we put in all the needed details that would be displayed for our artifacts. The needed details covers the Title, About, Media Type and whether the artifact is child appropriate. When we click the submit button, the newly added artifact is displayed on our catalogue result list with an automatically generated ID number. We can update the artifact by clicking on the Update Media. This can be of relevant if an artifact's rating has being changed after initially coming into the possession of the library. The government of the day has now set an artifact that we have down as Standard as now being Child Appropriate. We can type in the artifacts details on the update page and click on 'Set Child Appropriate'. On the catalogue page that is being to the admin, the librarian can keep track of artifacts as being recorded on loan or returned when they click on the Loan out or Return Item button respectively.
The Unique Selling Point of the Jedi Archives Temple Library Management System, amongst others is the functionality that is able to show all the previously removed artifacts from our catalogue. This is in the event the library wants to reinstate those items. We can just tick the check box for 'Show Deleted Media' and click the search button. It displays all the deleted item with a Restore button to reinstate the artifacts on our the library app catalogue's page. 


# Jedi_Archives

	M0: Member Login
	M1: To view their current list of loans (inc. return dates)
	M2: To renew loans (so long as they are not reserved for other members)
	M3: To reserve artifacts that are available
	M4: To reserve artifacts that are on loan
	M5 :To view your loan history.
	M6: To search for artifacts of interest.
 	M7: To view and update their member profile.

Librarians should be able to login to an Administrator Portal that should provide the following
functionality:
	L0: Librarian Login
	L1: To add / remove artifacts from the catalogue.
	L2: To search for members.
	L3: To view the current loans of a given member.
	L4: To view the historical loans of a given member.
	L5: To renew loans of members.
	L6: To reserve artifacts for members.
	L7: To record artifacts as being on loan.
	L8: To record artifacts as being returned.
	L9: To search for artifacts.
	L10: To edit member profiles

Members of the public should be able to access the following functionality through the website:
	G1: Search the catalogue
	G2: Join the library

Documentation Required
You should submit all of the following items via Gitlab:
   D1: Wireframe design of your website
   D2: List of User Stories in who, what, why form (i.e. a product backlog)
   D3: Your website
   D4: A Final Report 
   D5: README file
