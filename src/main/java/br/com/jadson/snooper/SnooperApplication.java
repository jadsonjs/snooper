package br.com.jadson.snooper;

import br.com.jadson.snooper.github.PullRequestQuery;
import br.com.jadson.snooper.model.pull.GitHubPullRequestInfo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootApplication
public class SnooperApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SnooperApplication.class, args);
	}

	/**
	 * Start the execution of application
	 */
	@Override
	public void run(String... args) throws Exception {

		System.out.println("############################################################");
		System.out.println("#                           snooper                        #");
		System.out.println("############################################################");

		// java -jar sonner.jar PullRequestQuery vuejs/vuejs aaaaaaaaaaaaaa

		String queryClass = args[0];
		String repoName = args[1];
		String token = args[2];

		if(queryClass.equals("PullRequestQuery")){
			PullRequestQuery query = new PullRequestQuery(token);
			List<GitHubPullRequestInfo> info = query.pullRequests(repoName);
			for (GitHubPullRequestInfo i : info){
				System.out.println(i);
			}
		}


	}

}
