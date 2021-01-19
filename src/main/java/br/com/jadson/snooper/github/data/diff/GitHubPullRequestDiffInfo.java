/*
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
 * br.com.jadson.snooper.github.data.diff
 * GitHubPullRequestDiffInfo
 * 23/09/20
 */
package br.com.jadson.snooper.github.data.diff;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Information pull request Diff
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubPullRequestDiffInfo {

    public Long id;
    public String diff_url;
    public String patch_url;
    public String issue_url;

    public Long number;
    public String state;
    public Boolean locked;
    public String title;

    public GitHubPullRequestUserInfo user;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date created_at;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date updated_at;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date closed_at;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date merged_at;

    public Long comments;
    public Long review_comments;
    public Boolean maintainer_can_modify;
    public Long commits;
    public Long additions;
    public Long deletions;
    public Long changed_files;

}
