# Java Memories JPA & JFX



## Project Description
A Java Project where I built an Application that acts as a Picture Sorter using Exif-Data from the Picture, and saves the information in a local Derby DataBase. 
It also allows Clients to create Tags and Albums, tag Pictures and search for Pictures based on Tags. All of this in a User-Interface created with JavaFX.

In the future I would like to finish the project by allowing Users to add Pictures to Albums in a definded order, I just need to figure out a good way to implement it on the User-Interface.
## Running the App

This is a Maven Project so in order to run the JUnit tests we need to run: 

```Bash
mvn test
```

### To run the actual Project we need to run the command:
```Bash
 mvn javafx:run  -Djavafx.args="initdb"
````

From then on we can re-Run the program with just:
```Bash
mvn javafx:run
```

### Note:

If the project doesn't run or raises the Warning Message (El Warning): "No Metamodels Found" you need to go into your IDE (in my case Eclipse) and run Project > Maven > Update Project, then delete the 'Data' and the 'fotosManaged' directory and run try running the project again with the argument: _"initdb"_.


## GUI Usage:

#### Adding Tags to Pictures:
In order to add a Tag to a Picture at least one Picture must be selected (when 'clicked', a cyan border will appear arround the selected picture), after all the Pictures that are to be tagged are selected one or more Tags can be selected (these will appear next to the selected pictures).

#### Searching Pictures With Tags:
If no Pictures are selected and we select one (or more) Tags, the selected Tags will appear in the Search section.
After the desired Tags are added to the search section we can:
-  **'Reset'** : Which will bring back up the list of the most recent Pictures.
-  **'Search All'** : Which will bring up a list of Pictures with all the Tags in the Search section.
- **'Search Any'** : Which will bring up a list of Pictures with any of the Tags in the Search section.


## Authors

- [@manuelbarral](https://github.com/MannyBarral)
- @tl - Thibault Langolis (Teacher, FCUL/DI)


