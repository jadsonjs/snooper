/*
 * Universidade Federal do Rio Grande do Norte
 * Instituto Metrópole Digital
 * Diretoria de Tecnologia da Informação
 *
 * snooper
 * UserQueryExecutorTest
 * @since 02/08/2021
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.users.GitHubUserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Exest user information
 *
 * @author Jadson Santos - jadson.santos@ufrn.br
 */
class UserQueryExecutorTest {

    /**
     *
     */
    @Test
    void userInfoTest() {
        UserQueryExecutor executor = new UserQueryExecutor();
        executor.setTestEnvironment(true);
        GitHubUserInfo info =  executor.user("jadsonjs");
        Assertions.assertEquals("jadsonjs", info.login);
        Assertions.assertEquals("Jadson Santos", info.name);
        Assertions.assertEquals("https://jadsonjs.wordpress.com/", info.blog);
    }
}
