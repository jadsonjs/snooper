package br.com.jadson.snooper.codecov.operations;

import br.com.jadson.snooper.utils.DateUtils;

public abstract class AbstractCodeCovQueryExecutor {

    public static final String CODE_COV_API_URL = "https://codecov.io/api";

    protected int limit = 250;

    protected String queryParameters = "";

    protected DateUtils dateUtils = new DateUtils();

    /**
     * Kinds of platform supported by CodeCove
     */
    public enum CODE_COV_BASE{ GITHUB("gh"), GITLAB("gl"), BITBUCKET("bb");

        private String name;

        CODE_COV_BASE(String name) {
            this.name = name;
        }

        public String getName(){
            return this.name;
        }
    }

}
