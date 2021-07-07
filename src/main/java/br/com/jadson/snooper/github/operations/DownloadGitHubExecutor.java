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
 * GitHubDownload
 * 24/09/20
 */
package br.com.jadson.snooper.github.operations;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;

/**
 * To make download of the github repositories.
 *
 * Download just download the repository files. But it is not a clone.
 * You can not commit or pull changes into local code.
 *
 * To clone the full repository use: @see {@link CloneGitHubExecutor}
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
@Component
public class DownloadGitHubExecutor {

    /**
     * Download the content of a github repository to local directoty in a zip file
     * @param repoURL
     * @param localPath
     * @return
     */
    public String download(String repoFullName, String localPath) {
        return download(repoFullName, localPath, false);
    }

    /**
     * Download the content of a github repository to local directoty in a zip file
     *
     * @param repoFullName
     * @param localPath
     * @param unzip if after download the repo content also unzip it.
     * @return
     */
    public String download(String repoFullName, String localPath, boolean unzip) {

        if(repoFullName == null || repoFullName.isEmpty())
            throw new IllegalArgumentException("Invalid repo url: "+repoFullName);

        if(localPath == null || localPath.isEmpty())
            throw new IllegalArgumentException("Invalid Local Path: "+localPath);

        if(! localPath.endsWith("/") )
            throw new IllegalArgumentException("Local Path should ends with slash '/' ");



        String downloadURL = "https://github.com/"+repoFullName+"/archive/master.zip";

        //String repoName = repoURL.substring(repoURL.lastIndexOf("/")+1, repoURL.length());

        String localZipProjectName = localPath + repoFullName + "-master.zip";

        if(! new File(localZipProjectName).exists() ) {

            System.out.println("Downloading: " + downloadURL + " to: " + localZipProjectName);

            try {
                new FileOutputStream(localZipProjectName).getChannel().transferFrom(
                        Channels.newChannel(new URL(downloadURL).openStream()), 0, Long.MAX_VALUE);

                // include a ".zip" in the file name
                renameFile(localZipProjectName.substring(0, localZipProjectName.lastIndexOf(".")), localZipProjectName);

                if(unzip) {
                    String localUnZipRepoName = localZipProjectName.substring(0, localZipProjectName.lastIndexOf(".")); // removing  ".zip" extension
                    unzip(localUnZipRepoName, localZipProjectName, localPath);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Repository "+repoFullName+" is already saved locally.");
        }


        return localZipProjectName;
    }


    private void renameFile(String oldName, String newName) {
        File oldFile =new File( oldName );
        File newFile =new File( newName );
        oldFile.renameTo(newFile);
        oldFile.delete();
    }

    private void unzip(String localUnZipRepoName, String localZipRepoName, String localPath){
        /*
         * If yet not extract and zip file length > 0 bytes (it is a valid zip file).
         */
        if(! new File(localUnZipRepoName).exists() && new File(localZipRepoName).length() > 0) {
            try {
                // extract the zip file to output directory  test-master.zip (File) -> test-master (Directory) into localPath
                new ZipFile(localZipRepoName).extractAll(localPath);
            }catch(ZipException zipex){
                System.out.println("Erro unzip file: "+zipex.getMessage());
            }
        }
    }

}
