GAME SKELETON README TUTORIAL

WHAT IS IT?
Game Skeleton is a very basic "skeleton" or framework for creating a game application based on the Mtgame gaming engine using the Netbeans Platform 

WHAT DOES IT PROVIDE?
Running the basic "GameSkeleton" starts a Netbeans Platform Application. The application consists of a Netbeans window containing an editor top component that is solely comprised of a canvas. That canvas renders a 3D world with a floor and an axis along with rotating light and shadows. Also, the tools to render a grid and a teapot are provided. These are added as classes in the "edu.isocial.gameskeleton.core.builders" package.

DEPENDENCIES
---Mtgame
---JOGL
---jme, jbullet, javolution
---gluegen-rt

HOW IT WAS SETUP...

CREATING NEW PROJECT
---File > New Project
---Select the "NetBeans Modules" folder in the Categories window, then select the "Netbeans Platform Application" in the Projects window and click "Next" 
---Input your Project Name and make sure your Project Location is where you want your project to be saved; then click "Finish"
---Your project should be created and should show up in the Projects viewer in Netbeans

INCLUDING MODULES
---To add a new module, right click on the "Modules" folder under your project and click "Add New..."
---Edit the Project Name and Project Location (should be in your project folder) to your liking (these are the name and location of your new module)
---Input your preferred code name base (EX: edu.isocial.gameskeleton.core) and click "Finish"
---Now, under your modules folder in your main project, you should have a new module. You can double click that module at any time to open it.

*NOTE ON MODULES: A main project consists of multiple modules, which are pieces to the puzzle that is your project. Each piece/module acts as a sub-project. Each sub-project can have multiple packages, classes, and libraries. A module can act as a piece of your project that is simply full of classes, or it can act as an API module that other pieces depend on. It can also be implemented as a library (or multiple libraries) that other modules depend on. What does "depend" mean? It means that a module requires a class, API, or library from another module for use in itself, so it needs to "depend" on that module. On a very basic level, this means you are adding a module as a library to another module. (Note that I use piece and module interchangeably; it my context, they mean the same)

INCLUDING LIBRARIES
---When adding libraries to your project, you must add a new module, and that module will hold the libraries you choose to add
---The process is very similar to the "Including Modules" process above.
---Right click on the "Modules" folder under your project and click "Add New Library..."
---Click "Browse…" to find the jar file you want to add as a library (you can add multiple)
---Like before, edit the name and location of your "Library Module" to your liking and click "Next"
---Also like before, input your preferred code name base (EX: edu.isocial.gameskeleton.jogl) and click "Finish"
---Your library will show up just as any other module

ADDING DEPENDENCIES
---Like stated before, on a basic level adding a modular dependency is like adding a library, whether the module actually consists of libraries, other classes, or APIs
---Under any module, right click on the "Libraries" folder and click "Add Module Dependency..."
---Select or search for the module you want to depend on and select it (can select multiple)
---Click "Ok"
---Now that module will show up in the "Libraries" folder as a dependency

MODULES AND DEPENDENCY SETUP FOR A GAME APPLICATION
---As listed above, there are 6 important libraries required for your game application. Those are Mtgame, JOGL, jme, javolution, jbullet, and gluegen-rt. Jme, javolution, and jbullet are packaged in one module.
---There is a dependency hierarchy between these libraries that looks like this:
			      
	              |------> JOGL --> gluegen-rt
	   |------> Mtgame --> jme, javolution, jbullet --> JOGL --> gluegen-rt
	PROJECT---> jme, javolution, jbullet --> JOGL
	   |------> JOGL --> gluegen-rt

---This may look pretty confusing (I did the best I could with TextEdit), so let me explain. Your main project depends on three modules: Mtgame; JOGL; and jme, javolution, jbullet. Each one of those modules depends on other modules as well. Mtgame depends on two modules: JOGL and jme, javolution, jbullet. The Jme, javolution, jbullet module depends on one other module: JOGL. Finally, JOGL depends on one other module: gluegen-rt (a graphics utility library). So in summary, your main project depends on multiple library modules which depend on other library modules themselves in order to work properly. (RECALL: when adding a library module as a dependency, you are adding a module containing libraries that are used in the module you are adding it too). 

WHERE TO FIND THE JAR FILES TO ADD THE LIBRARIES
---Mtgame: GameSkeleton/MTGame/release/modules/ext
---jme, javolution, jbullet: GameSkeleton/JME_Javolution_JBullet/release/modules/ext
---JOGL: GameSkeleton/JOGL/release/modules/ext
---gluegen-rt: GameSkeleton/Gluegen-RT/release/modules/ext

IMPORTANT: ADDING NATIVE LIBRARY PATH
---One thing I've learned while creating "GameSkeleton"-like projects in Netbeans is that you need to add the native library path for jogl/gluegen-rt to your project, which means you have to add the .jnlib files in your project folder somewhere.
---In the GameSkeleton Project, I added them to GameSkeleton/Gluegen-RT/release/modules/lib 
---After adding those files, you have to add this line to the Project Properties file in the "Important Files" folder under your main project: 
	run.args.extra=-J-Djava.library.path=../Gluegen-RT/release/modules/lib
*NOTE: you can add the .jnlib files anywhere within your project folder. Just make sure to change your run.args.extra line accordingly, aka update the library path
---This prevents any gluegen-rt/jogl native library path errors when running your application

GAMESKELETONCORE, THE ABSTRACT CLASS
---GameSkeletonCORE is the main, blueprint class that can be extended to create a Game Application of your liking. It provides  three important base components: the constructor, which runs the program when the application starts; the createDefaultSceneComponents method which sets up the camera, axis, light, and floor; and the setupRenderBuffer method, used by the constructor to create the Render Buffer. These three components can be used by default to setup the basic floor/axis/light 3D world that shows up on the canvas when GameSkeleton is run. If you want to begin using, changing and/or adding things to the application, you can extend GameSkeletonCORE to create your own "custom" main class. 
---I am going to use the MyGameCore class in GameSkeleton as an example
---As I mentioned earlier, Netbeans Platform Applications are made up of Top Components, which are windows within the application. 
---When I create MyGameCore and have it extend GameSkeletonCORE, it not only inherits from GameSkeletonCORE, but it also inherits from TopComponent, because GameSkeletonCORE inherits from TopComponent. The annotations above MyGameCore and the four methods in the "Required Top Component Methods" fold are required when creating a TopComponent.
---As you can see, in MyGameCore's constructor, it calls the GameSkeletonCORE's (super class) constructor, which does many important things to setup the game application (e.g. creating the worldManager, setting up the renderBuffer and canvas, and creating the default scene). 
---The two methods overridden from GameSkeletonCORE, the abstract class are createUI and createScene. The createUI method in MyGameCore creates the DefaultGameFrame and adds the ContentPane (which is basically adding the canvas and frame rate panel to the top component). However, you can add whatever you want to this method to manipulate/change/add to the Top Component. The createScene method can be used to create/add what you want to the scene that shows up on the canvas. 
---In summary, the abstract GameSkeletonCORE class is a base class to follow/build upon (extend) to create a Game Application. The MyGameCore class is a simple example of extending that base class, creating a Top Component, and implementing the abstract methods. 
