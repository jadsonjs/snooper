# Snooper

Snooper is a project to miner data from repositories like GitHub, TravisCI and Sonar.

These tools are supported by Snooper
  - **GitHub API**: https://docs.github.com/pt/rest
  - **GitHub Actions API**: https://docs.github.com/en/rest/actions
  - **Sonar Cloud API**: https://sonarcloud.io/web_api
  - **CodeCov API**: https://docs.codecov.com/reference/authorization
  - **Coveralls API**: https://docs.coveralls.io/api-introduction
  - **TravisCI API**: https://docs.travis-ci.com/api/


<img src="https://github.com/jadsonjs/snooper/blob/master/snooper-and-blabber.png" width="800">
@copyright hanna barbera

#### Versions: 

 - 1.0 - Github Search
 - 1.8 - CodeCov Support
 - 2.0 - Github Actions and Coveralls Support

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
#####              GitHub               #####
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
#####          GitHub Actions            #####
#############################################

# list all workflows os a project

   GHActionWorkflowsExecutor executor = new GHActionWorkflowsExecutor();
   executor.setPageSize(100);
   executor.setGithubToken(githubToken);

   List<WorkflowInfo> list =  executor.getWorkflows("jadsonjs/snooper");


# get all runs of a repository between dates

   GHActionRunsExecutor executor = new GHActionRunsExecutor();
   LocalDateTime startCIDate = LocalDateTime.of(2022, 7, 1, 0, 0, 0);
   LocalDateTime endCIDate = LocalDateTime.of(2022, 7, 30, 23, 59, 59);
   // created=2022-07-01..2022-07-30
   executor.setQueryParameters(new String[]{ "created=" + new DateUtils().toIso8601(startCIDate)+".."+new DateUtils().toIso8601(endCIDate) });
   executor.setPageSize(10);

List<RunsInfo> list =  executor.runs("jadsonjs/snooper");

# get all runs of a specific workflow

   GHActionRunsExecutor executor = new GHActionRunsExecutor();
   executor.setPageSize(100);
   executor.setGithubToken(githubToken);

   List<RunsInfo> list =  executor.runs("jadsonjs/snooper", 27792816);


# get last run of a specific of a repository


   GHActionRunsExecutor executor = new GHActionRunsExecutor();
   executor.setPageSize(100);
   executor.setGithubToken(githubToken);

   RunsInfo lastRunInfo =  executor.lastRun("jadsonjs/snooper");


# get first run of a specific of a repository


   GHActionRunsExecutor executor = new GHActionRunsExecutor();
   executor.setPageSize(100);
   executor.setGithubToken(githubToken);

   RunsInfo firstRunInfo =  executor.firstRun("jadsonjs/snooper");


#############################################
#####            COVERALLS              #####
#############################################

# the all coverage of a GitHub project

   String token = "sfOEwmr232sdf203r0033"
   CoverallsBuildsQueryExecutor executor = new CoverallsBuildsQueryExecutor(token);
   
   List<CoverallsBuildInfo> builds =  executor.getBuildsInfo("microsoft/msphpsql", AbstractCoverallsQueryExecutor.CODE_ALL_SERVICE.GITHUB);
  
   for(CoverallsBuildInfo info : builds){
     // this field "covered_percent" has coverage information
     System.out.println(info.covered_percent)
  }
 
#############################################
#####            CODECOV               #####
#############################################  
  
# get coverage information of Github project

  List<CodeCovCommit> commits = new CodeCovCommitsQueryExecutor()
        .getCommits("microsoft/msphpsql", CodeCovCommitsQueryExecutor.CODE_COV_BASE.GITHUB,
                LocalDateTime.of(2021, 11, 26, 0, 0, 0),
                LocalDateTime.of(2022, 02, 22, 23, 59, 50));
                
  for(CodeCovCommit commit : commits){
     // this field "c" has coverage information
     System.out.println(commit.parent_totals.c)
  }              


#############################################
#####           Sonar                  #####
#############################################


# Get java project order by ncloc (number of lines of code)
   
    SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
    List<SonarProjectInfo> projects = query.getSonarProjects("java", "ncloc");
    
# get projects of an organization    

   SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();

   List<SonarOrganizationProjectInfo> projects = query.getProjectsOfOrganization("microsoft");
   
   for (SonarOrganizationProjectInfo pi : projects){
      System.out.println("key: "+pi.key);
      System.out.println("name: "+pi.name);
      System.out.println("project: "+pi.project);
      System.out.println("organization: "+pi.organization);
      System.out.println("qualifier: "+pi.qualifier);
      System.out.println("language: "+pi.language);
   }
   
# get specific measure of a project

   SonarCloudProjectsQueryExecutor query = new SonarCloudProjectsQueryExecutor();
   ProjectMeasuresRoot data = query.getProjectMeasure("pipe-line-demo", "coverage");

   System.out.println(data.component.measures.get(0).metric);
   
  
# get a list of specif measure of a project between dates
 
   SonarCloudMetricHistoryQueryExecutor query = new SonarCloudMetricHistoryQueryExecutor();
   LocalDateTime from = LocalDateTime.of(2021,01,05,00,00,00);
   LocalDateTime to = LocalDateTime.of(2021,01,06,23,59,59);
   List<SonarHistoryEntry> historyEntries = query.getProjectMetricHistory("simgrid_simgrid", "coverage", from, to);          

#############################################
#####            TRAVIS CI              #####
#############################################

# Get All builds from Travis-CI of a project

    TravisCIQueryExecutor executor = new TravisCIQueryExecutor();
    List<TravisBuildsInfo> builds = executor.getBuilds("jadsonjs/snooper");





```

### How to run tests

 Run ```gradlew test``` command.
 
  
### Contribution guidelines

Be free to implement new queries or correct bugs and submit pull requests. Since, you write a correlated Unit Test that prove that your implementation is correct.

 
