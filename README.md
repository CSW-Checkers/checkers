# checkers

## Workflow Process

### First time set-up only
1. Create fork of CSW-Checkers/checkers repository to your personal account using the fork button.
2. Navigate to newly forked repository. github.com/your_user_name/checkers
3. Clone this repository to your local machine using the `git clone` command with the Clone URL on your repository page.

### Iterative workflow
0. Move your card on Trello from the To Do column to the In Progress column.
1. (Recommended) Create new branch for the work you are about to do. Give it a meaningful name. Switch to that branch.
2. Make your code changes on your local machine.
3. Use `git add` to add the desired files to be staged for commit.
4. Use `git commit` to commit the staged files. Use a good, descriptive message.
5. Use `git push` to push your commits up to GitHub.
6. Verify that your changes were pushed successfully. If you are on a new branch, you'll have to switch the branch on GitHub to see these changes.
7. Repeat steps 2-6 until your work is complete for the particular task.

### Ready to Create a Pull Request
1. Verify your code compiles and runs well. Note any known bugs or incomplete features that may be missed otherwise.
2. Create a new Pull Request on GitHub with a descriptive title and message information.
3. Move your card on Trello to the Code Review column.
4. Only when both of the other team members have reviewed your code should it be merged in to the group repository. If they are taking a while, feel free to prod them on Slack.
5. Merge in the code.
6. Move your card on Trello to the Done column. 

## Unit Tests
### Setting up the project
1. Download the two JARs on [this page](https://github.com/junit-team/junit/wiki/Download-and-Install)
2. Place the two JAR files in a location they can stay. I placed mine in a folder called "junit" that I made inside the eclipse folder.
3. Open Eclipse, right click on the Project, and go to Properties. From here, go to Java Build Path, and then select "Add External JARs..." and add each JAR.
4. You should now be able to run the unit tests by going into a test file, right clicking in the file, and select Run As... JUnit Test.