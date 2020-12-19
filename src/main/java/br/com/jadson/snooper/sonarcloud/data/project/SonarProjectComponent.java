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
 * br.com.jadson.snooper.sonarcloud.data.project
 * SonarProjectComponent
 * 28/10/20
 */
package br.com.jadson.snooper.sonarcloud.data.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

/**
 * Represent a project on sonar cloud.
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SonarProjectComponent {

    public String organization;
    public String key;
    public String name;
    public String visibility;

    /**
     * Qdd Analyses on sonar for this project.
     * (VASSALLO et al., 2018) state that at least 20 quality inspections on SonarCloud are required to consider that a project actively uses SonarCloud.
     */
    public int qtdAnalyses;

    /**
     * If this sonar project exist on github, we keep a link here
     */
    public String githubLink;

    /**
     * If this sonar project exist on travisCI, we keep a link here
     */
    public String travisCILink;

    /**
     * Identity if the project has enough builds on travis CI
     */
    public int qtdBuilds;

    /**
     * Identity if the project is relevant
     */
    public int qtdStars;

    /**
     * Identity if the project has enought PR
     */
    public int qtdPullRequest;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SonarProjectComponent that = (SonarProjectComponent) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "SonarProjectComponent{" + "key='" + key + '\'' + ", name='" + name + '\'' + '}';
    }
}
