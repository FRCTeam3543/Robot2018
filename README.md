# Team 3543 FRC 2018 Robot

This README is written in [Markdown](https://daringfireball.net/projects/markdown/syntax)

*FIRST FIRST FIRST*!!!  READ THIS!

If you do not read this and follow the steps in detail, the computer genie will not grant *any* of your wishes.  Read it *carefully*!

## Getting started

### Install the software 

Here is a list of software you need to install for Java development (the usual windows way, please accept all the defaults):

* [GitHub Desktop](https://desktop.github.com/) allows you to interact with GitHub where we store and collaborate on the code.  Also make sure you have a GitHub user account and you have been added to FRCTeam3543.
* [Visual Studio Code](https://code.visualstudio.com/download) - this is a great code editor for editing some types of files
* [Tools for FRC Java Programming](https://wpilib.screenstepslive.com/s/currentCS/m/java) - this is your IDE for writing Java code
* [Slack](https://slack.com/downloads) is for team chat.  You can also run it in a web browser.  You should have received an invitation to our slack group, if not talk to Mr. Cudmore.

### Setting up Windows to work with your GitHub account

1.  Open GitHub Desktop
2.  Log in using your GitHub user and password.
3.  In the upper right, select the Gear, then Options.  Note the "Clone Path".  That is where your checked-out projects will be stored.  You can change it or leave it at the default.
4.  Click the "+" in the upper left to add a repository.  Select "Clone" and then Select FRCTeam3543 > Code-2016-17.  *IF* you don't see this it means your GitHub user has not yet been added to the team, contact Mr. Cudmore.
5.  At the top of the center panel, click the fork icon ("Create new branch").  Enter `YourName` after your user (not actually "YourName"), and select "based on" to be `master`.
6.  Click the "Publish" button.  Once it completes, go to [this project on GitHub](https://github.com/FRCTeam3543/Code-2016-17) in your browser, and click the "Branch" dropdown -- your branch should be listed now!  You will do all your personal work in the branch and then *merge* your code back into the master branch later on.

### Getting ready to code

1.  Open your file manager and browser to the folder where Code-2016-17 was cloned in the previous steps (the "Clone path").
1.  Right click the Code-2016-17 folder and select "Open with Code".  This launches the project in Visual Studio Code.
1.  See specific language instructions in the [python](python/) and [java](java/) folders.

### Making changes to code

Alright, so here's where `git` (and GitHub) really comes in handy.  When you work with `git`, you can create oodles of _branches_ that allow you to work independently on a feature in the code, and then _merge_ that new feature into the main (*master*) branch when it's ready.  If you type `git status` in the shell, you will probably see you are on *master* branch right now.  Even if you change code though, it will only change it on your _local_ master branch.  To share your changes on GitHub, you would need to _push_ your changes up to the remote repository.

The following instructions show you how to interact with `git` using the shell.  You can also do most of the same things right inside Visual Studio code, and you might find that easier.  Complete the following exercise, then check out the [guide for using git inside VS Code](https://code.visualstudio.com/Docs/editor/versioncontrol).

### Let's code something!

The great thing about managing code with `git` is it keeps track of all your branches and changes, along with comments and notes about who changed what and when.  If someone changes something in a way that's going to collide with someone else's, it won't allow it!

In the following exercise, you will *make a change* to this README file in your code editor, *commit* the change to git, then *push* your change to GitHub.

First, open the `README.md` file in Visual Studio code by clicking on the file in the file browser on the left.

Next, find this text in the file and complete the following: 

```
(In the editor, everyone type their first name or initials below this line in the README as an exercise)
```

Now, save the file using File > Save or Ctrl+S.  In Visual Studio Code, you will notice your changes are tracked with a little notification on the git icon in the left sidebar (3rd icon from the top).  Click it and you will get a git menu.  Hover over the README.md file and click the "+" button to tell git you want to commit your change.  Now type a short message about your changes (e.g: "added my name to the list") and click the _checkmark_ icon to commit your changes.  

Now, push your changes to GitHub.  Switch to the GitHub Desktop application.  Make sure Code-2016-17 is selected on the left.  You should see your recent changes listed in the center panel.  Click the "Sync" button.  

Once that completes, go to [the GitHub site](https://github.com/FRCTeam3543/Code-2016-17) and click the "Branch: " dropdown, then select your branch name.  Scroll down the page until you see the list of names in the README.  There you are!

## Pull requests

When it is time to merge your changes into the `master` code branch (this is the one the robot will run), you use a "pull request".  This means "pull my changes into the master".  The master branch is protected so that all changes need to be code-reviewed by a teammate and then approved by an administrator.

Rules on "pull requests":

*  all pull requests into master need to be code-reviewed and approved by another team member before they can be merged (Github will enforce this)
*  you should make sure to "pull" changes from the master into your branch often (using the "Sync" button in GitHub Desktop), especially just before you are pushing up code that you intend to be merged via a pull request. If you get CONFLICT warnings ask for help on slack.  We'll cross that bridge when we come to it!

To create a pull request:

1.  Go to [the GitHub site](https://github.com/FRCTeam3543/Robot2018) and click the "Branch: " dropdown, then select your branch name
1.  If you have changes to merge, you can click the "Compare and Pull Request" button.  This will launch the pull request form, and will compare your code to the master branch to see if it can be automatically merged.  
  If not - you need to use the "Sync" button on your GitHub Desktop and pull the latest changes from master, resolve any conflicts (where git can't figure out what to do), and then Sync again.  Then try your pull request again.
1. Fill in the details about your pull request (what it is and why) and then click "Create Pull Request"  
1. Pull requests automatically get posted to our #software slack channel, so someone will see it!

## Woohoo?

Did you get this far?  Go on #general in slack and type WOOHOO!!  Or, if you got this far and s__t's not working, go on #general in slack and ask for help!

## More tools and resources

*  `git` will be your best friend and is a key tool nearly all professional programmers use (alas, some are forced to use crappier competitor's tools).  Here are some more resources describing all the cool things it can do: [git - the simple guide](http://rogerdudler.github.io/git-guide/) and [Git Beginners Guide for Dummies](https://backlogtool.com/git-guide/en/).
* [2018 FRC Control System](https://wpilib.screenstepslive.com/s/4485) is the official guide for getting going
