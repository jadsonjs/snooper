/*
 *
 * snooper
 * GitHubSearchExecutor
 * @since 08/07/2021
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
     * Search of Github repositories
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
     */
    public List<GitHubRepoInfo> searchRepositories(String language, Integer starsInit, Integer starsEnd, Integer size, String sort, String order) {

        List<GitHubRepoInfo> projects = new ArrayList<>();

        if(! "asc".equals(order) && ! "desc".equals(order))
            return projects;


        pagination:
        for(int page = 1; page <= 100 ; page++) { // github allow just 100 pages

            System.out.println(" Searching project on github ( page "+page+" of 100 )"+" q=  language: "+language+" stars: "+starsInit+" to " +starsEnd+" size: "+size+" sort: "+sort+" order "+order);

            StringBuilder urlBuffer = generateQuery(language, starsInit, starsEnd, size, sort, order, page);

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

                    // For unauthenticated requests, the rate limit allows you to make up to 10 requests per minute.
                    Thread.sleep(6000);

                }else{
                    System.err.println( "["+code+"]"+"  ==>  "+connection.getResponseMessage());

                    Thread.sleep(10000); // 30 per minute with Basic OAuth ( github token )
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
    public List<GitHubRepoInfo> searchRepositories(String language, Integer starsInit, Integer size, String sort, String order) {
        return searchRepositories(language, starsInit, null, size, sort, order);
    }


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


    private StringBuilder generateQuery(String language, Integer starsInit, Integer starsEnd, Integer size, String sort, String order, int page) {

        StringBuilder urlBuffer = new StringBuilder("https://github.com/search?l=&");

        urlBuffer.append("o="+ order +"");
        urlBuffer.append("&p="+ page +"");

        // start the query
        urlBuffer.append("&q=");

        boolean plus = false;

        final String GREATER_THAN_OR_EQUAL_TO = "%3A%3E%3D";
        final String EQUAL_TO = "%3A";

        if(starsInit != null && starsInit > 0 && starsEnd != null && starsEnd > 0) {
            urlBuffer.append("stars"+EQUAL_TO+starsInit+".."+starsEnd);
            plus = true;
        }else {
            if (starsInit != null && starsInit > 0) {
                urlBuffer.append("stars" + GREATER_THAN_OR_EQUAL_TO + starsInit);
                plus = true;
            }
        }

        // size in KB
        if(size != null && size > 0) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;
            urlBuffer.append("size"+GREATER_THAN_OR_EQUAL_TO+ size);
        }

        if(language != null && ! language.isEmpty()) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;

            urlBuffer.append("language"+EQUAL_TO + language);
        }


        if(sort != null && ! sort.isEmpty()) {
            urlBuffer.append("&s="+ sort);
        }
        urlBuffer.append("&type=Repositories");

        return urlBuffer;
    }

}
