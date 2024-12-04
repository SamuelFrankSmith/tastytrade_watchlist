# tastytrade_watchlist

### Runbook
1. Clone repo
2. Open the project in Android Studio
3. Run the `app` target against an Android device or emulator with a minimum of Android OS 11.

### Things for which I wish I had time
- *Any* dependency injection. If I was tasked with setting up a new app and/or framework, then adding DI is basically table stakes. Adding DI to an already-built app can be a large hurdle to overcome.
- Unit testing of (at least) the viewModel Actions and any utilities. Testing VM Actions is to validate that the correct States are emitted on a given input. This is a good precedent to set for any sustainable app.
- Some StringLoader class for VMs and other areas with business logic that have no reason to have access to a Context can load resources. Writing one felt outside the scope of this project.
- Successful responses appear to follow the same `data` and `context` pattern. This is probably a contract that could be codified by an interface or similar.
- A Compose example. I have used Compose a few times in a production app, but my skills here are still nascent at this time.
- Not using Retrofit2? At the beginning of this assignment, this seemed like a good idea, but I spent too much time remembering how to use it.
- Establishing a resource pattern for light/dark elements. This is easy to retrofit/refactor in the future, but establishing it an app early would be a good forcing function for ensuring devs and designers are considering it with each feature.
- Logging! So many times during this task I wish I could have logged general events or edge cases to some system for investigation later.
- It is absolutely *hideous* at the moment, and I'd like a few extra days just to make it look... half way decent.
- While I think the navigation graph does greatly simplify and illustrate navigation paths within an app, and I would certainly use it again, this was my first time ever using it. Like Retrofit 2, I spent precious time learning about it.
- Putting logout on the Fragments was honestly short-sighted, and this should be extracted to a more generic appbar.
- Cleanup unused dependencies/libraries. I was planning on using Compose for some of the views, but ended up not having time.
- Cleanup or address leftover TODOs
- Address any warnings I may have missed.
- Some region organization needs to be polished.
- Finally, I wish I had time to finish the assignment.
- More thought-out and universal handling of null/empty values for the models driving the views. Ultimately, I just have a null coalescing operator in the views themselves, but I'd rather better understand the contract with the API and Product team on how such things should be handled. I could then make a more informed decision on where/how to set default values and log anomalies appropriately. 
