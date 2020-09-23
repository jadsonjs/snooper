/*
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
 * br.com.jadson.snooper.dtos
 * GitHubPullRequestInfo
 * 22/09/20
 */
package br.com.jadson.snooper.model.pull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Information about pull request
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubPullRequestInfo {

    public String url;

    public String id;

    public GitHubPullRequestUserInfo user;

    public String title;

    public long number;

    public String state;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date created_at;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date updated_at;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date closed_at;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date merged_at;

    public String merge_commit_sha;

    public String commits_url;

    public String review_comments_url;

    public String review_comment_url;

    public String comments_url;

    public String statuses_url;

    public String author_association;

    @Override
    public String toString() {
        return "GitHubPullRequestInfo{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", number=" + number +
                ", state='" + state + '\'' + ", created_at=" + created_at + ", closed_at=" + closed_at + '}';
    }

}
