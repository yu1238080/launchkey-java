/**
 * Copyright 2017 iovation, Inc. All rights reserved.
 * <p>
 * Licensed under the MIT License.
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the "LICENSE.txt" file accompanying
 * this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iovation.launchkey.sdk.crypto.jwe;

import org.jose4j.json.JsonUtil;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.lang.JoseException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.LinkedHashMap;
import java.util.Map;

public class Jose4jJWEService implements JWEService {
    private final RSAPrivateKey privateKey;

    /**
     * @param privateKey  RSA Private Key of the RSA public/private key pair that will be used to decrypt the
     *                    Content Encryption Key (CEK) when decrypting.
     */
    public Jose4jJWEService(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override public String decrypt(String data) throws JWEFailure {
        return decrypt(data, privateKey);
    }


    @Override
    public String decrypt(String data, PrivateKey privateKey) throws JWEFailure {
        String decrypted;
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setKey(privateKey);
        try {
            jwe.setCompactSerialization(data);
            decrypted = jwe.getPlaintextString();
        } catch (JoseException e) {
            throw new JWEFailure("An error occurred attempting to decrypt a JWE", e);
        }
        return decrypted;
    }

    @Override public String encrypt(String data, PublicKey publicKey, String keyId, String contentType) throws JWEFailure {
        String encrypted;
        JsonWebEncryption jwe = new JsonWebEncryption();
        try {
            jwe.setKey(publicKey);
            jwe.setPlaintext(data);
            jwe.setKeyIdHeaderValue(keyId);
            jwe.setContentTypeHeaderValue(contentType);
            jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.RSA_OAEP_256);
            jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_256_CBC_HMAC_SHA_512);
            encrypted = jwe.getCompactSerialization();
        } catch (JoseException e) {
            throw new JWEFailure("An error occurred attempting to encrypt a JWE", e);
        }
        return encrypted;
    }

    @Override
    public Map<String, String> getHeaders(String data) throws JWEFailure {
        try {
            String headerSegment = data.split("\\.")[0];
            JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setCompactSerialization(data);
            String headersJSON = jwe.getHeaders().getFullHeaderAsJsonString();
            Map<String, Object> objectMap = JsonUtil.parseJson(headersJSON);
            Map<String, String> headers = new LinkedHashMap<>(objectMap.size());
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                headers.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            return headers;
        } catch (Exception e) {
            throw new JWEFailure("Unable to parse data for JWE Header!", e);
        }
    }
}
