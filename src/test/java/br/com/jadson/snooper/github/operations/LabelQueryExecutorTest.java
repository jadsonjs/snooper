package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.LabelInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LabelQueryExecutorTest {

    // To Execute this code
    // On intellij: edit configuration -> environment variable -> GITHUB_TOKEN=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    String token = System.getenv("GITHUB_TOKEN");

    @Test
    void labels() {

        LabelQueryExecutor executor = new LabelQueryExecutor();
        executor.setTestEnvironment(true);
        executor.setGithubToken(token);
        List<LabelInfo> list =  executor.labels("octocat/hello-world");

        Assertions.assertTrue(list.size() > 0);
        Assertions.assertTrue("bug".equals(list.get(0).name));
    }
}