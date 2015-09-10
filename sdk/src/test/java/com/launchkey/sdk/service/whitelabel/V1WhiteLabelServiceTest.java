package com.launchkey.sdk.service.whitelabel;

import com.launchkey.sdk.service.V1ServiceTestBase;
import com.launchkey.sdk.service.error.InvalidResponseException;
import com.launchkey.sdk.transport.v1.domain.UsersRequest;
import com.launchkey.sdk.transport.v1.domain.UsersResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.security.GeneralSecurityException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Copyright 2015 LaunchKey, Inc.  All rights reserved.
 * <p/>
 * Licensed under the MIT License.
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the "LICENSE.txt" file accompanying
 * this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class V1WhiteLabelServiceTest extends V1ServiceTestBase {

    protected V1WhiteLabelService service;
    protected final String decryptedUsersResponse = "{\"qrcode\": \"https://dashboard.launchkey.com/qrcode/zje0ja5\"," +
            "\"code\":\"zje0ja5\"}";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        when(transport.users(any(UsersRequest.class))).thenReturn(
                new UsersResponse(
                        new UsersResponse.UsersResponseResponse(
                                base64.encodeAsString("Encoded encrypted cipher".getBytes()),
                                base64.encodeAsString("Encoded encrypted data".getBytes())
                        )
                )
        );
        when(crypto.decryptRSA(any(byte[].class))).thenReturn("myciphermyciphermyciphermycipheriviviviviviviviv".getBytes());

        when(crypto.decryptAES(any(byte[].class), any(byte[].class), any(byte[].class)))
                .thenReturn(decryptedUsersResponse.getBytes());
        service = new V1WhiteLabelService(transport, crypto, pingResponseCache, rocketKey, secretKey);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        service = null;
    }


    @Test
    public void testChecksPingResponseCache() throws Exception {
        service.pairUser("identifier");
        verifyChecksPingResponseCache();
    }

    @Test
    public void testDoesNotCallPingWhenPingResponseCacheReturnsResponse() throws Exception {
        when(pingResponseCache.getPingResponse()).thenReturn(pingResponse);

        service.pairUser("identifier");
        verifyDoesNotCallPingWhenPingResponseCacheReturnsResponse();
    }

    @Test
    public void testDoesCallPingWhenPingResponseCacheReturnsNull() throws Exception {
        service.pairUser("identifier");
        verifyDoesCallPingWhenPingResponseCacheReturnsNull();
    }

    @Test
    public void testAddsPingResponseToPingResponseCacheWhenPingCallReturnsValue() throws Exception {
        service.pairUser("identifier");
        verifyAddsPingResponseToPingResponseCacheWhenPingCallReturnsValue();
    }

    @Test
    public void testPassesAuthsRequestToTransportAuthsCall() throws Exception {
        service.pairUser("identifier");
        verify(transport).users(any(UsersRequest.class));
    }

    @Test
    public void testPassesCorrectRocketIdInPairRequest() throws Exception {
        service.pairUser("identifier");
        ArgumentCaptor<UsersRequest> argumentCaptor = ArgumentCaptor.forClass(UsersRequest.class);
        verify(transport).users(argumentCaptor.capture());
        assertEquals(rocketKey, argumentCaptor.getValue().getRocketKey());
    }

    @Test
    public void testPassesCorrectRsaEncryptedSecretKeyInUsersRequest() throws Exception {
        service.pairUser("identifier");
        ArgumentCaptor<UsersRequest> argumentCaptor = ArgumentCaptor.forClass(UsersRequest.class);
        verify(transport).users(argumentCaptor.capture());
        assertEquals(base64.encodeAsString("RSA Encrypted Value".getBytes()), argumentCaptor.getValue().getSecretKey());
    }

    @Test
    public void testUsesPemFromPingToCreatePublicKey() throws Exception {
        service.pairUser("identifier");
        verifyUsesPemFromPingToCreatePublicKey();
    }

    @Test
    public void testRsaEncryptedWithCorrectPublicKeyToCreateSecretKeyInUsersRequest() throws Exception {
        service.pairUser("identifier");
        verifyRsaEncryptedWithCorrectPublicKeyToCreateSecretKey();
    }

    @Test
    public void testRsaEncryptedJsonWithCorrectDataToCreateSecretKeyInUsersRequest() throws Exception {
        Date start = new Date();
        service.pairUser("identifier");
        Date end = new Date();

        verifyRsaEncryptedJsonWithCorrectDataToCreateSecretKey(start, end);
    }

    @Test
    public void testPassesIdentifierInUsersRequest() throws Exception {
        service.pairUser("identifier");
        ArgumentCaptor<UsersRequest> argumentCaptor = ArgumentCaptor.forClass(UsersRequest.class);
        verify(transport).users(argumentCaptor.capture());
        assertEquals("identifier", argumentCaptor.getValue().getIdentifier());
    }

    @Test
    public void testDecryptsCipherInUsersResponseWithRSA() throws Exception {
        service.pairUser("identifier");
        ArgumentCaptor<byte[]> messageCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(crypto).decryptRSA(messageCaptor.capture());
        assertEquals("Encoded encrypted cipher", new String(messageCaptor.getValue()));
    }

    @Test
    public void testUsesKeyAndIVFromDecryptedCipherToDecryptData() throws Exception {
        service.pairUser("identifier");

        ArgumentCaptor<byte[]> keyCaptor = ArgumentCaptor.forClass(byte[].class);
        ArgumentCaptor<byte[]> ivCaptor = ArgumentCaptor.forClass(byte[].class);
        ArgumentCaptor<byte[]> messageCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(crypto).decryptAES(messageCaptor.capture(), keyCaptor.capture(), ivCaptor.capture());
        assertEquals("Unexpected message value", "Encoded encrypted data", new String(messageCaptor.getValue()));
        assertEquals("Unexpected key value", "myciphermyciphermyciphermycipher", new String(keyCaptor.getValue()));
        assertEquals("Unexpected IV value", "iviviviviviviviv", new String(ivCaptor.getValue()));
    }

    @Test
    public void testReturnsExpectedDataInPairResponse() throws Exception {
        PairResponse expected = new PairResponse("zje0ja5", "https://dashboard.launchkey.com/qrcode/zje0ja5");
        PairResponse actual = service.pairUser("identifier");
        assertEquals(expected, actual);
    }

    @Test(expected = InvalidResponseException.class)
    public void testCatchesGeneralSecurityExceptionDecryptingAESAndThrowsInvalidResponseException() throws Exception {
        when(crypto.decryptAES(any(byte[].class), any(byte[].class), any(byte[].class))).thenThrow(new GeneralSecurityException());
        service.pairUser("identifier");

    }

    @Test(expected = InvalidResponseException.class)
    public void testCatchesIOErrorParsingUnparseableDataAndThrowsInvalidResponseException() throws Exception {
        when(crypto.decryptAES(any(byte[].class), any(byte[].class), any(byte[].class))).thenReturn("XXX".getBytes());
        service.pairUser("identifier");
    }
}