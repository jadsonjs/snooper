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
 * br.com.jadson.snooper.travisci.data.repository
 * TravisRepoInfo
 * 17/12/20
 */
package br.com.jadson.snooper.travisci.data.repo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Information about repository on travis CI
 *
 * @see {https://docs.travis-ci.com/api/?http#repositories}
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravisRepoInfo {

    public int id;
    // project name "jadsonjs/continuous-delivery"
    public String slug;
    public boolean active;
    public String description;
    public int last_build_id;
    public int last_build_number;
    public String last_build_state;
    public long last_build_duration;
    public String last_build_language;
    public String last_build_started_at;
    public String last_build_finished_at;
    public String github_language;

}
