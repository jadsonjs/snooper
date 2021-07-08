/*
 *
 * snooper
 * GitHubSearchExecutor
 * @since 08/07/2021
 */
package br.com.jadson.snooper.github.operations;

import br.com.jadson.snooper.github.data.repo.GitHubRepoInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
     * @param language
     * @param stars
     * @param sort
     * @param order
     * @return
     */
    public List<GitHubRepoInfo> searchRepositories(String language, Integer stars, Integer size, String sort, String order) {

        List<GitHubRepoInfo> projects = new ArrayList<>();

        if(! "asc".equals(order) && ! "desc".equals(order))
            return projects;

        int page = 1;

        StringBuilder urlBuffer = generateQuery(language, stars, size, sort, order, page);

        try {
            //Thread.sleep(500);
            URL url = new URL(urlBuffer.toString());
            // create a urlconnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();

            if(code == 200){
                String htmlCode = connection.getResponseMessage();

                projects.addAll(extractRepositoriesInfo(htmlCode));

            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }


        return projects;
    }

    private List<GitHubRepoInfo> extractRepositoriesInfo(String htmlCode) {
        List<GitHubRepoInfo> projects = new ArrayList<>();

        Document html = Jsoup.parse(htmlCode);
        Elements divs = html.getElementsByClass("f4");

        for (Element div : divs) {
            Element link = div.getElementsByTag("a").first();

            GitHubRepoInfo project = new GitHubRepoInfo();
            String[] names = link.attr("href").split("/");
            project.name = names[0];
            project.full_name = link.attr("href");
            project.url = "https://github.com/"+link.attr("href");
            projects.add(project);

        }

        return projects;
    }


    private StringBuilder generateQuery(String language, Integer stars, Integer size, String sort, String order, int page) {

        StringBuilder urlBuffer = new StringBuilder("https://github.com/search?l=&");

        urlBuffer.append("o="+ order +"");
        urlBuffer.append("&p="+ page +"");

        // start the query
        urlBuffer.append("&q=");

        boolean plus = false;

        if(stars != null && stars > 0) {
            urlBuffer.append("stars%3A>=" + stars);
            plus = true;
        }

        // size in KB
        if(size != null && size > 0) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;
            urlBuffer.append("size%3A>=" + size);
        }

        if(language != null && ! language.isEmpty()) {
            if(plus){
                urlBuffer.append("+");
            }
            plus = true;

            urlBuffer.append("language%3A" + language);
        }


        if(sort != null && ! sort.isEmpty()) {
            urlBuffer.append("&s="+ sort);
        }
        urlBuffer.append("&type=Repositories");

        return urlBuffer;
    }

}
