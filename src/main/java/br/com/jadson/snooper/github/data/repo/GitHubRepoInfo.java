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
 * br.com.jadson.snooper.github.data.repo
 * GitHubRepoInfo
 * 18/12/20
 */
package br.com.jadson.snooper.github.data.repo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Github Repository INFO
 *
 * {
 *     "id": 134147639,
 *     "node_id": "MDEwOlJlcG9zaXRvcnkxMzQxNDc2Mzk=",
 *     "name": "webauthn4j",
 *     "full_name": "webauthn4j/webauthn4j",
 *     "private": false,
 *     "owner": {
 *         "login": "webauthn4j",
 *         "id": 38367941,
 *         "node_id": "MDEyOk9yZ2FuaXphdGlvbjM4MzY3OTQx",
 *         "avatar_url": "https://avatars0.githubusercontent.com/u/38367941?v=4",
 *         "gravatar_id": "",
 *         "url": "https://api.github.com/users/webauthn4j",
 *         "html_url": "https://github.com/webauthn4j",
 *         "followers_url": "https://api.github.com/users/webauthn4j/followers",
 *         "following_url": "https://api.github.com/users/webauthn4j/following{/other_user}",
 *         "gists_url": "https://api.github.com/users/webauthn4j/gists{/gist_id}",
 *         "starred_url": "https://api.github.com/users/webauthn4j/starred{/owner}{/repo}",
 *         "subscriptions_url": "https://api.github.com/users/webauthn4j/subscriptions",
 *         "organizations_url": "https://api.github.com/users/webauthn4j/orgs",
 *         "repos_url": "https://api.github.com/users/webauthn4j/repos",
 *         "events_url": "https://api.github.com/users/webauthn4j/events{/privacy}",
 *         "received_events_url": "https://api.github.com/users/webauthn4j/received_events",
 *         "type": "Organization",
 *         "site_admin": false
 *     },
 *     "html_url": "https://github.com/webauthn4j/webauthn4j",
 *     "description": "A portable Java library for WebAuthn and Apple App Attest server side verification",
 *     "fork": false,
 *     "url": "https://api.github.com/repos/webauthn4j/webauthn4j",
 *     "forks_url": "https://api.github.com/repos/webauthn4j/webauthn4j/forks",
 *     "keys_url": "https://api.github.com/repos/webauthn4j/webauthn4j/keys{/key_id}",
 *     "collaborators_url": "https://api.github.com/repos/webauthn4j/webauthn4j/collaborators{/collaborator}",
 *     "teams_url": "https://api.github.com/repos/webauthn4j/webauthn4j/teams",
 *     "hooks_url": "https://api.github.com/repos/webauthn4j/webauthn4j/hooks",
 *     "issue_events_url": "https://api.github.com/repos/webauthn4j/webauthn4j/issues/events{/number}",
 *     "events_url": "https://api.github.com/repos/webauthn4j/webauthn4j/events",
 *     "assignees_url": "https://api.github.com/repos/webauthn4j/webauthn4j/assignees{/user}",
 *     "branches_url": "https://api.github.com/repos/webauthn4j/webauthn4j/branches{/branch}",
 *     "tags_url": "https://api.github.com/repos/webauthn4j/webauthn4j/tags",
 *     "blobs_url": "https://api.github.com/repos/webauthn4j/webauthn4j/git/blobs{/sha}",
 *     "git_tags_url": "https://api.github.com/repos/webauthn4j/webauthn4j/git/tags{/sha}",
 *     "git_refs_url": "https://api.github.com/repos/webauthn4j/webauthn4j/git/refs{/sha}",
 *     "trees_url": "https://api.github.com/repos/webauthn4j/webauthn4j/git/trees{/sha}",
 *     "statuses_url": "https://api.github.com/repos/webauthn4j/webauthn4j/statuses/{sha}",
 *     "languages_url": "https://api.github.com/repos/webauthn4j/webauthn4j/languages",
 *     "stargazers_url": "https://api.github.com/repos/webauthn4j/webauthn4j/stargazers",
 *     "contributors_url": "https://api.github.com/repos/webauthn4j/webauthn4j/contributors",
 *     "subscribers_url": "https://api.github.com/repos/webauthn4j/webauthn4j/subscribers",
 *     "subscription_url": "https://api.github.com/repos/webauthn4j/webauthn4j/subscription",
 *     "commits_url": "https://api.github.com/repos/webauthn4j/webauthn4j/commits{/sha}",
 *     "git_commits_url": "https://api.github.com/repos/webauthn4j/webauthn4j/git/commits{/sha}",
 *     "comments_url": "https://api.github.com/repos/webauthn4j/webauthn4j/comments{/number}",
 *     "issue_comment_url": "https://api.github.com/repos/webauthn4j/webauthn4j/issues/comments{/number}",
 *     "contents_url": "https://api.github.com/repos/webauthn4j/webauthn4j/contents/{+path}",
 *     "compare_url": "https://api.github.com/repos/webauthn4j/webauthn4j/compare/{base}...{head}",
 *     "merges_url": "https://api.github.com/repos/webauthn4j/webauthn4j/merges",
 *     "archive_url": "https://api.github.com/repos/webauthn4j/webauthn4j/{archive_format}{/ref}",
 *     "downloads_url": "https://api.github.com/repos/webauthn4j/webauthn4j/downloads",
 *     "issues_url": "https://api.github.com/repos/webauthn4j/webauthn4j/issues{/number}",
 *     "pulls_url": "https://api.github.com/repos/webauthn4j/webauthn4j/pulls{/number}",
 *     "milestones_url": "https://api.github.com/repos/webauthn4j/webauthn4j/milestones{/number}",
 *     "notifications_url": "https://api.github.com/repos/webauthn4j/webauthn4j/notifications{?since,all,participating}",
 *     "labels_url": "https://api.github.com/repos/webauthn4j/webauthn4j/labels{/name}",
 *     "releases_url": "https://api.github.com/repos/webauthn4j/webauthn4j/releases{/id}",
 *     "deployments_url": "https://api.github.com/repos/webauthn4j/webauthn4j/deployments",
 *     "created_at": "2018-05-20T12:14:36Z",
 *     "updated_at": "2020-12-14T13:25:10Z",
 *     "pushed_at": "2020-12-17T17:02:41Z",
 *     "git_url": "git://github.com/webauthn4j/webauthn4j.git",
 *     "ssh_url": "git@github.com:webauthn4j/webauthn4j.git",
 *     "clone_url": "https://github.com/webauthn4j/webauthn4j.git",
 *     "svn_url": "https://github.com/webauthn4j/webauthn4j",
 *     "homepage": "https://webauthn4j.github.io/webauthn4j/en/",
 *     "size": 21294,
 *     "stargazers_count": 147,
 *     "watchers_count": 147,
 *     "language": "Java",
 *     "has_issues": true,
 *     "has_projects": false,
 *     "has_downloads": true,
 *     "has_wiki": false,
 *     "has_pages": true,
 *     "forks_count": 30,
 *     "mirror_url": null,
 *     "archived": false,
 *     "disabled": false,
 *     "open_issues_count": 6,
 *     "license": {
 *         "key": "apache-2.0",
 *         "name": "Apache License 2.0",
 *         "spdx_id": "Apache-2.0",
 *         "url": "https://api.github.com/licenses/apache-2.0",
 *         "node_id": "MDc6TGljZW5zZTI="
 *     },
 *     "forks": 30,
 *     "open_issues": 6,
 *     "watchers": 147,
 *     "default_branch": "master",
 *     "temp_clone_token": null,
 *     "organization": {
 *         "login": "webauthn4j",
 *         "id": 38367941,
 *         "node_id": "MDEyOk9yZ2FuaXphdGlvbjM4MzY3OTQx",
 *         "avatar_url": "https://avatars0.githubusercontent.com/u/38367941?v=4",
 *         "gravatar_id": "",
 *         "url": "https://api.github.com/users/webauthn4j",
 *         "html_url": "https://github.com/webauthn4j",
 *         "followers_url": "https://api.github.com/users/webauthn4j/followers",
 *         "following_url": "https://api.github.com/users/webauthn4j/following{/other_user}",
 *         "gists_url": "https://api.github.com/users/webauthn4j/gists{/gist_id}",
 *         "starred_url": "https://api.github.com/users/webauthn4j/starred{/owner}{/repo}",
 *         "subscriptions_url": "https://api.github.com/users/webauthn4j/subscriptions",
 *         "organizations_url": "https://api.github.com/users/webauthn4j/orgs",
 *         "repos_url": "https://api.github.com/users/webauthn4j/repos",
 *         "events_url": "https://api.github.com/users/webauthn4j/events{/privacy}",
 *         "received_events_url": "https://api.github.com/users/webauthn4j/received_events",
 *         "type": "Organization",
 *         "site_admin": false
 *     },
 *     "network_count": 30,
 *     "subscribers_count": 10
 * }
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepoInfo {

    public int id;
    public String node_id;
    public String name;
    public String full_name;
    @JsonProperty("private")
    public boolean repo_private;
    public GitHubOwnerInfo owner;
    public String html_url;
    public String description;
    public boolean fork;
    public String url;
    public String forks_url;
    public String keys_url;
    public String collaborators_url;
    public String teams_url;
    public String hooks_url;
    public String issue_events_url;
    public String events_url;
    public String assignees_url;
    public String branches_url;
    public String tags_url;
    public String blobs_url;
    public String git_tags_url;
    public String git_refs_url;
    public String trees_url;
    public String statuses_url;
    public String languages_url;
    public String stargazers_url;
    public String contributors_url;
    public String subscribers_url;
    public String subscription_url;
    public String commits_url;
    public String git_commits_url;
    public String comments_url;
    public String issue_comment_url;
    public String contents_url;
    public String compare_url;
    public String merges_url;
    public String archive_url;
    public String downloads_url;
    public String issues_url;
    public String pulls_url;
    public String milestones_url;
    public String notifications_url;
    public String labels_url;
    public String releases_url;
    public String deployments_url;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date created_at;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date updated_at;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date pushed_at;

    public String git_url;
    public String ssh_url;
    public String clone_url;
    public String svn_url;
    public String homepage;
    public int size;
    public int stargazers_count;
    public int watchers_count;
    public String language;
    public boolean has_issues;
    public boolean has_projects;
    public boolean has_downloads;
    public boolean has_wiki;
    public boolean has_pages;
    public int forks_count;
    public Object mirror_url;
    public boolean archived;
    public boolean disabled;
    public int open_issues_count;
    public GitHubLicenseInfo license;
    public int forks;
    public int open_issues;
    public int watchers;
    public String default_branch;
    public Object temp_clone_token;
    public GitHubOrganizationInfo organization;
    public int network_count;
    public int subscribers_count;

}
