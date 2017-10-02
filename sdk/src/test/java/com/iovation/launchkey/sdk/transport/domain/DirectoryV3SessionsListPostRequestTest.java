package com.iovation.launchkey.sdk.transport.domain; /**
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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectoryV3SessionsListPostRequestTest {
    @Test
    public void getIdentifier() throws Exception {
        assertEquals("identifier", new DirectoryV3SessionsListPostRequest("identifier").getIdentifier());
    }

    @Test
    public void toJSON() throws Exception {
        assertEquals("{\"identifier\":\"UserId\"}",
                new ObjectMapper().writeValueAsString(new DirectoryV3SessionsListPostRequest("UserId")));
    }
}