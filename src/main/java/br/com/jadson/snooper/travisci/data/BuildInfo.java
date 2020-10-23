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
 * br.com.jadson.snooper.travisci.data
 * BuildData
 * 29/09/20
 */
package br.com.jadson.snooper.travisci.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Build info for travis CI
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildInfo {

    public Integer id;      // Value uniquely identifying the build.

    public String number;	//Incremental number for a repository's builds.

    public String state;		//Current state of the build.

    public Integer duration;		//Wall clock time in seconds.

    public String event_type;		//Event that triggered the build.

    public String previous_state;		// State of the previous build (useful to see if state changed).

    public String pull_request_title; 	// Title of the build's pull request.

    public Integer pull_request_number;	// Number of the build's pull request.

    public String started_at;		// When the build started.

    public String finished_at;		// When the build finished.

    @JsonProperty("private")
    public Boolean _private;		// Whether or not the build is private.

    public String priority;	    // The build's priority.

    // Repository repository		GitHub user or organization the build belongs to.
    //branch	Branch	The branch the build is associated with.
    //tag	String	The build's tag.
}
