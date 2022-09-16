package br.com.jadson.snooper.coveralls.operations;

import org.springframework.http.HttpHeaders;

public class AbstractCoverallsQueryExecutor {

    public static final String CODE_ALL_API_URL = "https://coveralls.io";

    /**
     * All to set fix parameters beyond of page and pageSize
     *
     * Executor e = new Executor();
     * e.setPageSize(10);
     * e.setQueryParameters(new String[]{"state=all"});
     *
     * this will generate a query:  ?state=all&page=X&per_page=10
     */
    protected String queryParameters;

    /** if is executing a test, or ir real query. */
    protected boolean testEnvironment = false;

    /**
     * Default page size of pagination
     * maximum 100
     * https://docs.github.com/en/free-pro-team@latest/rest/guides/traversing-with-pagination
     */
    protected int pageSize = 100;

    /**
     * Token to access coveall
     */
    protected String token = "";

    public enum CODE_ALL_SERVICE { GITHUB("github"), GITLAB("gitlab"), BITBUCKET("bitbucket");

        private String value;

        CODE_ALL_SERVICE(String value) {
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }

    /**
     * https://coveralls.io/api/docs
     * @return
     */
    protected HttpHeaders getDefaultHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        if( ! token.isEmpty() )
            headers.set("Authorization", "token "+token+"");
        return headers;
    }

    public void setPageSize(int pageSize) {
        if(pageSize < 0 || pageSize > 100)
            throw new RuntimeException("Invalid Page Size: "+pageSize+" see: https://docs.github.com/en/free-pro-team@latest/rest/guides/traversing-with-pagination");
        this.pageSize = pageSize;
    }

    public void setQueryParameters(String[] parametersArrays) {
        if(parametersArrays == null || parametersArrays.length == 0)
            throw new RuntimeException("Invalid Query Parameters: "+queryParameters);

        this.queryParameters = "";
        for (String p :parametersArrays){
            this.queryParameters += p+"&";
        }
    }

    public void setTestEnvironment(boolean testEnvironment) {
        this.testEnvironment = testEnvironment;
    }
}
