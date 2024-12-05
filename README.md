# tastytrade_watchlist

### Runbook
1. Clone repo
2. Open the project in Android Studio
3. Run the `app` target against an Android device or emulator with a minimum of Android OS 11.

### What will be needed
- Testers will need an account that already has populated watchlists. I did not implement the Watchlist CRUD operations as part of Step 4c and 6.

### What this application does
- Authenticates against the service.
- Displays **existing** watchlists on the User.
- Allows someone to view symbols and prices for a specific watchlists. These watchlists update every five seconds.

### Things I most wish this application had
- Most importantly, I wish this had *any* dependency injection. If I was tasked with setting up a new app and/or framework, then adding DI is basically table stakes. Adding DI to an already-built app can be a large hurdle to overcome.
- Secondly, this should have unit testing of *at least* the viewModel Actions and any utilities. Testing VM Actions is to validate that the correct States are emitted on a given input is a good precedent to set for any sustainable app.
- Third, I wish I had a complex Compose example to show. Unfortunately, I'm fairly new to managing Compose states and did not, at least for the moment, spend time thinking about that in too much depth. I did however write some unused Composables.
- Fourth, it would have been nice to complete the challenge as requested. However, for parts incomplete, I have wrestled with what they would have uniquely demonstrated that isn't demonstrated by other parts.
- Fifth and finally, the look-and-feel design is horrendous! 🤢 I would have really liked to spend some time thinking about information architecture of the screens, layouts, font and color standardization, and light/dark modes.

### Everything else I wish I had time for, but were not a high priority
- Some StringLoader class for VMs and other areas with business logic that have no reason to have access to an entire Context can load resources. Writing one felt outside the scope of this project.
- Successful responses appear to follow the same `data` and `context` pattern. This is probably a contract that could be codified by an interface or similar.
- Not using Retrofit2? At the beginning of this assignment, this seemed like a good idea, but I spent too much time remembering how to use it. I have minor concerns with some of the features of Retrofit and my usage of coroutines.
- Establishing a resource pattern for light/dark elements. This is easy to retrofit/refactor in the future, but establishing it an app early would be a good forcing function for ensuring devs and designers are considering it with each feature.
- Logging! So many times during this task I wish I could have logged general events or edge cases to some system for investigation later. This is table stakes for any sensible production-ready application.
- While I think the navigation graph does greatly simplify and illustrate navigation paths within an app, and I would certainly use it again, this was my first time ever using it. Like Retrofit 2, I spent precious time learning about it. 
- Cleanup unused dependencies/libraries. I was planning on using Compose for some of the views, but ended up not having time.
- Implement or address leftover TODOs and FIXMEs. Often I didn't see what implementing them would have demonstrated.
- Address any warnings I may have missed. 
- Some region organization needs to be polished.
- More thought-out and universal handling of null/empty values for the models driving the views. Sometimes, I just have a null coalescing operator in the views themselves, but I'd rather better understand the contract with the API and Product team on how such things should be handled. I could then make a more informed decision on where/how to set default values and log anomalies appropriately. 
