package br.com.jadson.snooper.gitlab.operations;

import br.com.jadson.snooper.utils.ConnectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Common functions to execute queries on gitlab using gitlab API https://docs.gitlab.com/ee/api/rest/
 */
abstract class AbstractGitLabQueryExecutor {

    /**
     * We can have the gitlab running in several places. The default is "gitlab.com", where the API url will be "https://gitlab.com/api/v4"
     * But, if you are using a private gitlab, like gitlab.ufrn.br, the API url will be "https://gitlab.ufrn.br/api/v4".
     *
     * Gitlab is not like GitHub where all projects has a unique domain.
     */
    protected String gitlabURL = "https://gitlab.com";

    protected String gitlabToken = "";
    protected int pageSize = 10;
    protected String queryParameters;
    protected boolean testEnvironment = false;

    /**
     * Ignore invalid SSL certificates to specific scenarios
     */
    protected boolean disableSslVerification = false;


    /**
     * Return the Gitlab URL to specific gitlab instance.
     *
     * The following is a basic example of a request to the fictional gitlab.example.com endpoint:
     * "https://gitlab.example.com/api/v4/projects"
     *
     * @return
     */
    public final String getGitLabAPIURL() {
        return gitlabURL +"/api/v4/projects/";
    }

    public void setPageSize(int pageSize) {
        if (pageSize >= 0 && pageSize <= 100) {
            this.pageSize = pageSize;
        } else {
            throw new RuntimeException("Invalid Page Size: " + pageSize + " see: https://docs.gitlab.com/ee/api/#pagination");
        }
    }

    public void setQueryParameters(String[] parametersArrays) {
        if (parametersArrays != null && parametersArrays.length != 0) {
            this.queryParameters = "";
            String[] var2 = parametersArrays;
            int var3 = parametersArrays.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String p = var2[var4];
                this.queryParameters = this.queryParameters + p + "&";
            }

        } else {
            throw new RuntimeException("Invalid Query Parameters: " + this.queryParameters);
        }
    }

    protected final void validateRepoName(String repoFullName) {
        if (repoFullName != null && !repoFullName.trim().equals("")) {
            if (!repoFullName.contains("/")) {
                throw new RuntimeException("Invalid GitHub repo full name: " + repoFullName + ". The name should be owner/repo");
            }
        } else {
            throw new RuntimeException("Invalid GitHub repo full name: " + repoFullName);
        }
    }

    /**
     * https://docs.gitlab.com/ee/api/rest/#personalprojectgroup-access-tokens
     * @return
     */
    protected HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        if (this.gitlabToken != null && ! this.gitlabToken.isEmpty() ) {
            // -> preferences -> Access Tokens
            // Authorization: Bearer <your_access_token>
            headers.set("Authorization", "Bearer "+this.gitlabToken);
        }

        return headers;
    }

    /*
     * If using namespaced API requests, make sure that the NAMESPACE/PROJECT_PATH is URL-encoded.
     * For example, / is represented by %2F:
     * GET /api/v4/projects/diaspora%2Fdiaspora
     */
    protected static String encodeProjectName(String repoFullName) {

        repoFullName = repoFullName.replace("/", "%2F");
        return repoFullName;
    }


    public void setGitlabToken(String gitlabToken) {
        this.gitlabToken = gitlabToken;
    }

    public void setGitlabURL(String gitlabURL) {
        this.gitlabURL = gitlabURL;
    }

    public void setTestEnvironment(boolean testEnvironment) {
        this.testEnvironment = testEnvironment;
    }

    public void setDisableSslVerification(boolean disableSslVerification) {
        this.disableSslVerification = disableSslVerification;
    }

    public String getQueryParameters() {
        return this.queryParameters;
    }


    /**
     * Check if should disalbbe ssl verification
     */
    protected void checkDisableSslVerification() {
        if(disableSslVerification) {
            try {
                new ConnectionUtils().disableSslVerification();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            }
        }
    }




}