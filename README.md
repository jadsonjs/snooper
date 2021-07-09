# Snooper

Snooper is a project to collect data from repositories like GitHub, TravisCI and Sonar.

This tool supports the **GitHub API**, **TravisCI API** and **Sonar Cloud API**.

#### Versions: 

 - 0.23 - Improve Clone and Download options
 - 1.00 - Github Search

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

#############################################
##### GitHub #####
#############################################


# download the repository from github to local machine
    
    DownloadGitHubExecutor executor = new DownloadGitHubExecutor();
    String localRepo = executor.download("jadsonjs/snooper", "/tmp");


# Clone the repository from github to local machine
    
    CloneGitHubExecutor executor = new CloneGitHubExecutor(githubToken);
    String localRepo = executor.clone("jadsonjs/snooper", "/tmp");
    

# Get all Commits of a repository

     CommitQueryExecutor executor = new CommitQueryExecutor();
     executor.setGithubToken(githubToken);
     executor.setPageSize(100);
     List<GitHubCommitInfo> commits = executor.getCommits("jadsonjs/snooper");


# Get all Pull Requests of a repository

    PullRequestQueryExecutor executor = new PullRequestQueryExecutor();
    executor.setPageSize(100);
    executor.setQueryParameters(new String[]{"state=all"});
    
    List<GitHubPullRequestInfo> list =  executor.pullRequests("jadsonjs/snooper");



# Get all Issues of a repository

    IssueQueryExecutor executor = new IssueQueryExecutor();
    executor.setPageSize(100);
    executor.setQueryParameters(new String[]{"state=all"});
    
    List<GitHubIssueInfo> list =  executor.pullRequests("jadsonjs/snooper");
    
    

# Get all Releases of a repository

    ReleaseQueryExecutor executor = new ReleaseQueryExecutor(" git hub access token ");
    executor.setPageSize(100);
    executor.setQueryParameters(new String[]{"state=all"});
    List<GitHubReleaseInfo> list =  executor.releases("jadsonjs/snooper");



# Get a pull request diff info

    PullRequestDiffQuery gitHub = new PullRequestDiffQuery(" git hub access token ");
    GitHubPullRequestDiffInfo info =  gitHub.pullRequestsDiff("jadsonjs/snooper", 5356l);



# Search github project of language Java, with 100 stars or more, 1MB or more sort by stars, order by desc

   GitHubSearchExecutor executor = new GitHubSearchExecutor();
   executor.setGithubToken(githubToken);

   // java projects with more the 100 stars and 1MB
   List<GitHubRepoInfo> listOfProjects = search.searchRepositories("Java", 100, 1000, "stars", "desc");   


#############################################
##### TRAVIS #####
#############################################

# Get All builds from Travis-CI of a project

    TravisCIQueryExecutor executor = new TravisCIQueryExecutor();
    List<TravisBuildsInfo> builds = executor.getBuilds("jadsonjs/snooper");


#############################################
##### Sonar #####
#############################################


# Get projects on Sonar Cloud

   int pageSize = 500;
   String language = "Java";
   String sortBy = "ncloc";
   SonarCloudProjectsQueryExecutor executor = new SonarCloudProjectsQueryExecutor(pageSize);
   query.getSonarProjects(language, sortBy)



```

### How to run tests

 Run ```gradlew test``` command.
 
  
### Contribution guidelines

Be free to implement new queries or correct bugs and submit pull requests. Since, you write a correlated Unit Test that prove that your implementation is correct.

 
