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
 * br.com.jadson.snooper.github.data.commit
 * AssociationReleasePullRequestInfo
 * 26/01/21
 */
package br.com.jadson.snooper.github.data.association;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the association between commit and pull request
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class AssociationCommitPullRequestInfo {

    /**
     * Information about commit, like this:
     *
     * https://github.com/vuejs/vue/commit/0948d999f2fddf9f90991956493f976273c5da1f
     */
    public String commitUrl;

    public List<PullRequestNodeInfo> pullRequestNodeInfos = new ArrayList<>();

    public void addPullRequestInfo(PullRequestNodeInfo pullRequestNodeInfo) {
        if(pullRequestNodeInfos == null)
            pullRequestNodeInfos = new ArrayList<>();

        if( ! pullRequestNodeInfos.contains(pullRequestNodeInfo) )
            pullRequestNodeInfos.add(pullRequestNodeInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssociationCommitPullRequestInfo that = (AssociationCommitPullRequestInfo) o;
        return commitUrl.equals(that.commitUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commitUrl);
    }

    @Override
    public String toString() {
        return "AssociationReleasePullRequestInfo{" + "commitUrl='" + commitUrl + '\'' + ", pullRequestNodeInfos=" + pullRequestNodeInfos + '}';
    }
}
