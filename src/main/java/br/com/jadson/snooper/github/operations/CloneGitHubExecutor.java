/*
 * Federal University of Rio Grande do Norte
 * Department of Informatics and Applied Mathematics
 * Collaborative & Automated Software Engineering (CASE) Research Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 * snooper
 * br.com.jadson.snooper.github
 * GitHubClone
 * 24/09/20
 */
package br.com.jadson.snooper.github.operations;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to clone a github repository.
 *
 * To just download the content of repository: @see {@link DownloadGitHubExecutor}
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class CloneGitHubExecutor {

    /**
     * token of github to use as credentials.
     *
     */
    private final String githubToken;


    public CloneGitHubExecutor(String githubToken){
        if(githubToken == null || githubToken.trim().equals(""))
            throw new RuntimeException("Invalid GitHub Token: "+githubToken);

        this.githubToken = githubToken;
    }

    /**
     * Clone a project to local path.
     *
     * When clone you can you have all the repository in local machine. Can commit and pull.
     *
     * @param repoFullName
     * @param localPath
     * @return
     * @throws IOException
     */
    public String clone(String repoFullName, String localPath){

        if(repoFullName == null || repoFullName.isEmpty())
            throw new IllegalArgumentException("Invalid repo url: "+repoFullName);

        if(localPath == null || localPath.isEmpty())
            throw new IllegalArgumentException("Invalid Local Path: "+localPath);

        String fullPath = localPath+"/"+repoFullName;

        CredentialsProvider cp = new UsernamePasswordCredentialsProvider(githubToken, "");

        if(! new File(fullPath).exists() ) {
            try {
                System.out.println("Cloning repository: https://github.com/"+ repoFullName + " to: " + fullPath);
                Git.cloneRepository().setURI("https://github.com/"+repoFullName).setDirectory(new File(fullPath)).setCloneAllBranches(true).setCredentialsProvider(cp).call();
            }catch(Exception ex){
                ex.printStackTrace();

                /* IMPORTANT: if there is a error because of certificate, skip ssl verify
                 * We have to wait give an exception because the local gitconfig file just exist after try to clone the repository
                 *
                 * It is not possible to use de API to set this information and the user does not have a home directory to create .gitconfig file.
                 */
                if(ex.getMessage().contains("cannot open git-upload-pack")){

                    String localGitConfig = localPath+"/"+".git"+"/"+"config";
                    File localGitConfigFile = new File(localPath);
                    if( localGitConfigFile.exists() ){
                        try {
                            createSkipSSLVerifyConfiguration(localGitConfig);
                        } catch (IOException e) {
                            throw new RuntimeException("Erro creating file "+localGitConfig+". "+e.getMessage());
                        }
                    }

                    throw new RuntimeException("SSL Problem: Creating skip SSL verify configuration file, try to clone again.");
                }

            }
        }else{
            System.out.println("Repository "+fullPath+" is already saved locally.");
        }

        return fullPath;
    }

    /**
     * We need to create a local file config to skip SSL certificate
     *
     * @param filePath
     * @throws IOException
     */
    private void createSkipSSLVerifyConfiguration(String filePath) throws IOException{
        FileWriter writer = new FileWriter(filePath, true);
        writer.write("[http]"+"\n");
        writer.write("\t"+"sslverify = false"+"\n");
        writer.close();
    }

}
