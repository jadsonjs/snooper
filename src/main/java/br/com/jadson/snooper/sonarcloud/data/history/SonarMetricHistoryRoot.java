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
 * br.com.jadson.snooper.sonarcloud.data.history
 * SonarMetricHistoryRoot
 * 06/01/21
 */
package br.com.jadson.snooper.sonarcloud.data.history;

import br.com.jadson.snooper.sonarcloud.data.SonarPaging;

import java.util.ArrayList;
import java.util.List;

/**
 * Information return when consult the history of metric on sonar cloud.
 * Example:
 * https://sonarcloud.io/api/measures/search_history?component=simgrid_simgrid&metrics=coverage&from=2021-01-05T00:00:00%2B0200&to=2021-01-06T23:59:59%2B0200
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class SonarMetricHistoryRoot {

    /**
     * history of metric
     */
    public List<MeasureHistoryInfo> measures = new ArrayList<>();

    public SonarPaging paging;

}
