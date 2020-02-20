# UbisecureExercise

Hi, I decided to use Android since I already have all the tools available. 
However, due to the AndroidX updates to the libraries, especially with Android unit testing that 
depricates a lot of the previous ways to pass context to testing blocks, I couldn't write unit tests for this application. 
However I did try to put a small test in to show that I understand how to use Junit 4.
Also with the AndroidX update the new in-app mapview creates a build conflict with older android support libraries so I couldn't put it in-app, 
I could only make an intent to open that coordinates on the Google Map application.

For the API I tried to use the API from digitraffic.fi but I tried to request some API calls from Postman and didn't get any response 
so I used Postman to host a server with a single HTTP PUT that returns a JSON
{
 "name": "Intercity 3",
 "destination": "Tampere",
 "speed": 65.3",
 "coordinates": [60.5039, 25.1335]
}
which is your example. I did use an async thread so it should update the info on the screen as the app fetches the JSON.

Overall I'm not as happy with the result as I could be but due to the time constraints(I'm having mid term exams) and I wanted to put this out as soon as possible, 
I'll put this up here first. I'll start picking up AndroidX after this exam period and if there are any questions or anything you wanted to ask or if there are 
particular feature you'd like to see please let me know soon. 
