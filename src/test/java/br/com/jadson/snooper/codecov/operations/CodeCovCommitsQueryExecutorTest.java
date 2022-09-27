package br.com.jadson.snooper.codecov.operations;

import br.com.jadson.snooper.codecov.data.CodeCovCommit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class CodeCovCommitsQueryExecutorTest {

    @Test
    void getProjectCommit() {
        List<CodeCovCommit> commits = new CodeCovCommitsQueryExecutor().getCommits("microsoft/msphpsql", CodeCovCommitsQueryExecutor.CODE_COV_BASE.GITHUB,
                LocalDateTime.of(2021, 11, 26, 0, 0, 0),
                LocalDateTime.of(2022, 02, 22, 23, 59, 50));

        Assertions.assertTrue(commits.size() > 0);
    }
}