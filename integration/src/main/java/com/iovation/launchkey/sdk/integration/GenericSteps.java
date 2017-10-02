/**
 * Copyright 2017 iovation, Inc.
 * <p>
 * Licensed under the MIT License.
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the "LICENSE.txt" file accompanying
 * this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iovation.launchkey.sdk.integration;


import com.google.inject.Singleton;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.runtime.java.guice.ScenarioScoped;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Singleton
public class GenericSteps {
    private Exception currentException;

    @After
    public void reset() {
        currentException = null;
    }

    void setCurrentException(Exception exception) {
        currentException = exception;
    }

    @Then("^an? (.+) exception is thrown$")
    public void anExceptionIsThrown(Class expectedExceptionClass) throws Throwable {
        assertThat("An exception was expected but not thrown!", expectedExceptionClass, is(notNullValue()));
        assertThat(currentException, is(instanceOf(expectedExceptionClass)));
    }

    @Then("^there are no errors$")
    public void thereAreNoErrors() throws Throwable {
        assertThat(currentException, is(nullValue()));
    }
}
