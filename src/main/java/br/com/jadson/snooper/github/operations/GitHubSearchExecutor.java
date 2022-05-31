/*
 *
 * snooper
 * GitHubSearchExecutor
 * @since 08/07/2021
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import br.com.jadson.snooper.github.data.repo.GitHubSearchRoot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class use the operation of search on github.
 * https://github.com/search/advanced
 *
 * This is a HTML search, so this class process HTML code, is not a service on github api.
 *
 * @author Jadson Santos - jadson.santos@ufrn.br
 */
public class GitHubSearchExecutor extends AbstractGitHubQueryExecutor{


    /**
     * Really use github api do search project with pagination and filter by language and stars
     * https://docs.github.com/pt/rest/search#search-repositories
     *
     * Example of query:  https://api.github.com/search/repositories?q=language%3AJavaScript+stars%3A200..300+size%3A%3E%3D10000&sort=stars&order=desc&page=1&per_page=100
     *                    https://api.github.com/search/repositories?q=language%3AJavaScript+stars%3A200..300+size%3A%3E%3D10000&sort=stars&order=desc&page=1&per_page=100
     * "Repository Javascript  + 200 to 300 stars + size over 10.000(10MB), sort by stars order descedent , page 1, 10 results per_page"
     *
     * sortstring
     *
     *
     * @param language
     * @param starsInit
     * @param starsEnd
     * @param size  size:1000 identifica os repositórios que têm exatamente 1 MB.
     *              size:>=30000 identifica os repositórios que têm no mínimo 30 MB.
     *              size:<50 identifica os repositórios que têm menos de 50 KB.
     *              size:50..120 identifica os repositórios que têm entre 50 KB e 120 KB.
     * @param sort Pode ser uma das ações a seguir: stars, forks, help-wanted-issues, updated
     * @param order Pode ser uma das ações a seguir: desc, asc
     * @return
     */
    public List<GitHubRepoInfo> searchRepositoriesApi(String language, Integer starsInit, Integer starsEnd, Integer size, String sort, String order) {

        List<GitHubRepoInfo> projects = new ArrayList<>();

        if(! "asc".equals(order) && ! "desc".equals(order))
            return projects;

        int page = 1;

        List<GitHubRepoInfo> allRepoInfo = new ArrayList<>();

        ResponseEntity<GitHubSearchRoot> result;

        do {

            String query = generateApiQuery(language, starsInit, starsEnd, size, sort, order, page).toString();

            System.out.println(" Searching project on github: "+query);

            RestTemplate restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(getDefaultHeaders());

            // https://github.com/spring-projects/spring-framework/issues/10187
            // encode %A3 and %3A%3E%3D in the URL
            URI uri = null;
            try {
                uri = new URI(query);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return projects;
            }

            result = restTemplate.exchange(uri, HttpMethod.GET,  entity,  GitHubSearchRoot.class );

            allRepoInfo.addAll(  Arrays.asList( result.getBody().items ) );

            page++;

            // 30 per minute with Basic OAuth ( github token )
            // 2s
            if(! testEnvironment){
                try { Thread.sleep(6000);} catch (InterruptedException e) { e.printStackTrace(); }
            }

                                                                             // Only the first 1000 search results are available
        }while ( result != null   &&   result.getBody().items.length > 0  &&  (page * pageSize) <= 1000   &&   !testEnvironment);

        return allRepoInfo;
    }



    /**
     * Search of Github repositories access HTML page. Not recommended !!!!
     *
     *
     * https://github.com/search?l=&o=desc&q=stars%3A%3E%3D100+language%3AJava&s=stars&type=Repositories
     * https://github.com/search?l=&o=desc&q=language%3AJava&s=stars&type=Repositories
     * https://github.com/search?l=&o=asc&q=stars%3A5000..10000+language%3AJava&type=Repositories&p=10
     * @param language
     * @param starsInit
     * @param starsEnd
     * @param sort
     * @param order
     * @return
     *
     * @Deprecated use api search
     */
    @Deprecated
    public List<GitHubRepoInfo> searchRepositories(String language, Integer starsInit, Integer starsEnd, Integer size, String sort, String order) {

        List<GitHubRepoInfo> projects = new ArrayList<>();

        if(! "asc".equals(order) && ! "desc".equals(order))
            return projects;


        pagination:
        for(int page = 1; page <= 100 ; page++) { // github allow just 100 pages

            StringBuilder urlBuffer = generateHTMLQuery(language, starsInit, starsEnd, size, sort, order, page);

            System.out.println(" Searching project on github: "+urlBuffer);

            HttpURLConnection connection = null;

            try {

                URL url = new URL(urlBuffer.toString());
                // create a urlconnection object
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if(! githubToken.isEmpty())
                    connection.setRequestProperty("Authorization", "token "+githubToken+"");

                connection.connect();
                int code = connection.getResponseCode();



                if (code == 200) {
                    connection.getInputStream();

                    BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) connection.getContent()));

                    StringBuilder htmlCode = new StringBuilder();

                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        htmlCode.append(inputLine);

                    List<GitHubRepoInfo> ps = extractRepositoriesInfo(htmlCode.toString());
                    if(ps.size() == 0)
                        break pagination; // when page < 100 but the project list already finished, github resturn a empty page.

                    projects.addAll(ps);

                    if(testEnvironment)
                        break pagination;

                    // 30 per minute with Basic OAuth ( github token )
                    // 6s
                    Thread.sleep(6000);

                }else{
                    System.err.println( "["+code+"]"+"  ==>  "+connection.getResponseMessage());

                    // 30 per minute with Basic OAuth ( github token )
                    // 12s
                    Thread.sleep(2*6000);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                if(connection != null)
                    connection.disconnect();
            }
        }


        return projects;
    }

    /**
     * Suport to interval of data
     * @param language
     * @param starsInit
     * @param size
     * @param sort
     * @param order
     * @return
     */
    @Deprecated
    public List<GitHubRepoInfo> searchRepositories(String language, Integer starsInit, Integer size, String sort, String order) {
        return searchRepositories(language, starsInit, null, size, sort, order);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private List<GitHubRepoInfo> extractRepositoriesInfo(String htmlCode) {

        List<GitHubRepoInfo> projects = new ArrayList<>();

        Document html = Jsoup.parse(htmlCode);
        Elements divs = html.getElementsByClass("f4");

        for (Element div : divs) {

            if(! div.tagName().equals("div") || ! div.hasClass("text-normal")){
                continue; // is not the div with name of project
            }

            Element link = div.getElementsByTag("a").first();

            GitHubRepoInfo project = new GitHubRepoInfo();
            String[] names = link.attr("href").split("/");
            project.name = names[2];
            project.full_name = names[1]+"/"+names[2];
            project.url = "https://github.com/"+project.full_name;
            projects.add(project);

        }

        return projects;
    }

    /**
     * https://docs.github.com/pt/rest/search#search-repositories
     * https://docs.github.com/pt/rest/search#constructing-a-search-query
     *
     * https://docs.github.com/pt/search-github/searching-on-github/searching-for-repositories
     *
     * q=language:assembly+stars:10..20&sort=stars&order=desc
     * q=language:assembly+stars:>=500&sort=stars&order=desc
     *
     *
     * @param language
     * @param starsInit
     * @param starsEnd
     * @param size
     * @param sort
     * @param order
     * @param page
     * @return
     */
    private StringBuilder generateApiQuery(String language, Integer starsInit, Integer starsEnd, Integer size, String sort, String order, int page) {

        StringBuilder urlBuffer = new StringBuilder("https://api.github.com/search/repositories?");

        // start the query
        urlBuffer.append("q=");

        boolean plus = false;
        // https://www.w3schools.com/tags/ref_urlencode.asp
        // https://docs.github.com/pt/search-github/searching-on-github/searching-for-repositories
        // :>=
        final String COLON_GREATER_THAN_OR_EQUAL_TO = "%3A%3E%3D";
        // :
        final String COLON = "%3A";

        if(language != null && ! language.isEmpty() ) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;

            // # and ++ not allow in URL query
            if(language.equals("C#") || language.equals("c#"))
                language = "CSharp";

            if(language.equals("C++") || language.equals("c++"))
                language = "cpp";


            urlBuffer.append("language"+COLON + language);
        }

        if(starsInit != null && starsInit > 0 && starsEnd != null && starsEnd > 0) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;
            urlBuffer.append("stars"+COLON+starsInit+".."+starsEnd);
        }else {
            if (starsInit != null && starsInit > 0) {
                if(plus){
                    urlBuffer.append("+");
                }
                plus = true;
                urlBuffer.append("+stars" + COLON_GREATER_THAN_OR_EQUAL_TO + starsInit);

            }
        }

        // size in KB
        if(size != null && size > 0) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;
            urlBuffer.append("size"+COLON_GREATER_THAN_OR_EQUAL_TO+ size);
        }


        /////////////// end query ////////////////


        if(sort != null && ! sort.isEmpty()) {
            urlBuffer.append("&sort="+ sort);
        }

        urlBuffer.append("&order="+ order);

        urlBuffer.append("&page="+ page +"");
        urlBuffer.append("&per_page="+ pageSize +"");

        return urlBuffer;
    }




    private StringBuilder generateHTMLQuery(String language, Integer starsInit, Integer starsEnd, Integer size, String sort, String order, int page) {

        StringBuilder urlBuffer = new StringBuilder("https://github.com/search?l=&");

        urlBuffer.append("o="+ order +"");
        urlBuffer.append("&p="+ page +"");

        // start the query
        urlBuffer.append("&q=");

        boolean plus = false;

        // :>=
        final String COLON_GREATER_THAN_OR_EQUAL_TO = "%3A%3E%3D";
        // :
        final String COLON = "%3A";

        if(starsInit != null && starsInit > 0 && starsEnd != null && starsEnd > 0) {
            urlBuffer.append("stars"+COLON+starsInit+".."+starsEnd);
            plus = true;
        }else {
            if (starsInit != null && starsInit > 0) {
                urlBuffer.append("stars" + COLON_GREATER_THAN_OR_EQUAL_TO + starsInit);
                plus = true;
            }
        }

        // size in KB
        if(size != null && size > 0) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;
            urlBuffer.append("size"+COLON_GREATER_THAN_OR_EQUAL_TO+ size);
        }

        if(language != null && ! language.isEmpty()) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;

            urlBuffer.append("language"+COLON + language);
        }


        if(sort != null && ! sort.isEmpty()) {
            urlBuffer.append("&s="+ sort);
        }
        urlBuffer.append("&type=Repositories");

        return urlBuffer;
    }

}
