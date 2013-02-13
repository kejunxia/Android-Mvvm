Android-Mvvm
============

A variation of MVVM implementation for android app

See http://ideaventure.blogspot.com.au/2013/02/android-mvvm-implementation.html too.

Model

-<b>Models</b><br/>
for holding states and data. Models can be referenced and manipulated by View models.
ViewModel State Model

-<b>ViewModels State Model</b><br/>
represent the state of views but delegated by ViewModel. It has one to one relationship to ViewModel. ViewModel State Model can be considered as the data of ViewModel which can be bound and updated by ViewModel. As ViewModel State Model only hold the data of ViewModel, it's very easy to serialize into JSON string and re-populated from JSON String.

This is very useful because this make things easy to store the state of views in onSaveInstanceState(Bundle outState) callback in Android activity and fragment. I met the problem that when you leave the app in the background for a long time and when you active the app later, the app crashes. In that case I used static fields to hold state of views. However the Android will clear the static fields when it needs to recollect resources, and then when you are back to the app the static data become null and app crashed. Another difficulty is it's very very tricky to detect if the app if back from the background and ever been killed by the Android OS. So be careful to use static variable to store state. Instead, using a easy serializable java object and save the state in onSaveInstanceState(Bundle outState) and restore them in onCreate, onCreateView or other initializing calls to restore data. If it's too slow, try to use Parcelable.

Another reason to avoid using static field is that, it makes fragment and activity more independent. To use one you just need to give them the initial binding data in a Intent rather than relying on some classes holding the static fields.

-<b>ViewModel</b><br/>
ViewModels incorporate with views. Views register callbacks to ViewModels and then when they call method of ViewModels, the ViewModel execute a procedure and then fire the call back to update Views. When a ViewModel bind a data it should update the View referencing it.
As ViewModels can be registered by multiple views, it's very easy to keep all related views up to date all the time.

-<b>ViewModelEevnt</b><br/>
Interfaces define viewmodel events. View register these events to the ViewModel which is dependent by the View. When view wants to do something, it calls viewModel.doSomething() and then the view received the event "onSomethingHapped()". 

-<b>View</b><br/>
Views incorporate with ViewModels. It can be a concrete view such as a button, a text view, a custom view, a fragment, a activity and etc. Also it can be thought as a abstract concept. For example, the whole application which does have visible UI components can be considered as a view as well. Let's call it AppView. AppView can use a UserViewModel and register LoggedInEvent. And keep the UserViewModel as a app wide variable by a static field or property. Then when other activity/fragment or whatever issued a login call then the AppView will receive logged in event callback and do corresponding updates. And show the UIs for logged users by for example removing current fragments for guest users and load fragments for logged in users. In this case it works a little similar as Controller in MVC pattern.
