# tastytrade_watchlist

### Things for which I wish I had time
- *Any* dependency injection. If I was tasked with setting up a new app and/or framework, then adding DI is basically table stakes. Adding DI to an already-built app can be a large hurdle to overcome.
- Unit testing of (at least) the viewModel Actions and any utilities. Testing VM Actions is to validate that the correct States are emitted on a given input. This is a good precedent to set for any sustainable app.
- Some StringLoader class for VMs and other areas with business logic that have no reason to have access to a Context can load resources. Writing one felt outside the scope of this project.
- A Compose example. I have used Compose a few times in a production app, but my skills here are still nascent at this time.
- Not using Retrofit2? At the beginning of this assignment, this seemed like a good idea, but I spent too much time remembering how to use it.
- Establishing a resource pattern for light/dark elements. This is easy to retrofit/refactor in the future, but establishing it an app early would be a good forcing function for ensuring devs and designers are considering it with each feature.
- Logging! So many times during this task I wish I could have logged general events or edge cases to some system for investigation later.
- Figure out why my Ripple didn't work on my button drawables
- It is absolutely *hideous* at the moment, and I'd like a few extra days just to make it look... half way decent.
- The AuthRepo does too much deciding if a network response was "good". For a real app, this would need to be extracted to a singular place outside all of the repositories.
- While I think the navigation graph does greatly simplify and illustrate navigation paths within an app, and I would certainly use it again, this was my first time ever using it. Like Retrofit 2, I spent precious time learning about it.

### TODO
- Cleanup remaining TODOs
- Cleanup deps; lots of extras at the moment.
- Maybe remove network state permissions

### Tuesday Morning To Do
- Remove the up/back arrow from 'Your Watchlists'
- Add logout functionality
- Add loading indicator to auth
- 