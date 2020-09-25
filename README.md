# Snooper

Snooper is a project to collect data from repositories like GitHub.

#### Versions: 

 - 1.0 - Basic Queries from GitHub.

#### Authors:

    Jadson Santos - jadsonjs@gmail.com
    
    
### Dependencies
    
    Java 11
    Gradle 5.2.1
    Junit 5.6.0
    
### How do I get set up?

#### From the source code:

   Clone the project -> Import it as a gradle project on your IDE.

#### From the binary:

   Snooper has a binary distribution on **libs/snooper-X.Y.jar** directory.
   
   Include it on your classpath.    
    

### How to use

Examples of how to use:

```
# download the repository from github to local machine
    
    GitHubDownload gitHub = new GitHubDownload();

    String localRepo = gitHub.download("https://github.com/vuejs/vue-cli", "/tmp/");

# Clone the repository from github to local machine
    
    GitHubClone gitHub = new GitHubClone(" git hub access token ");

    String localRepo = gitHub.clone("https://github.com/vuejs/vue-cli","/tmp/");

# Get all pull request of a repository

    PullRequestQuery gitHubClient = new PullRequestQuery(" git hub access token ");
    gitHubClient.setPageSize(100);
    gitHubClient.setQueryParameters(new String[]{"state=all"});

    List<GitHubPullRequestInfo> list =  gitHubClient.pullRequests("vuejs/vue-cli");


# Get all releases of a repository

    ReleaseQuery gitHub = new ReleaseQuery(" git hub access token ");

    List<GitHubReleaseInfo> list =  gitHub.releases("vuejs/vue-cli");


# Get a pull request diff info

    PullRequestDiffQuery gitHub = new PullRequestDiffQuery(" git hub access token ");
    
    GitHubPullRequestDiffInfo info =  gitHub.pullRequestsDiff("vuejs/vue-cli", 5356l);


```

### How to run tests

 Run ```gradlew test``` command.
 
  
### Contribution guidelines

Be free to implement new queries or correct bugs and submit pull requests. Since, you write a correlated Unit Test that prove that your implementation is correct.

 
