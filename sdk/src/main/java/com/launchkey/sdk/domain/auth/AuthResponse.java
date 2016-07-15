/**
 * Copyright 2016 LaunchKey, Inc. All rights reserved.
 * <p/>
 * Licensed under the MIT License.
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the "LICENSE.txt" file accompanying
 * this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.launchkey.sdk.domain.auth;

/**
 * Object that represents the current state of an auth request within the Platform.
 */
public class AuthResponse {
    /**
     * Unique identifier for the authentication request generated by the Platform API.
     */
    private final String authRequestId;

    /**
     * Has the request been authorized by the user.
     */
    private final boolean authorized;

    /**
     * Hashed user identifier to track a specific user for an application. This value will be used by the
     * Logout Callback to identify the user logging out. This value may
     * be used in place of a username or White Label User identifier for authorization requests.
     */
    private final String userHash;

    /**
     * A string that uniquely identifies the user across the entire Organization to which the Application whose Application Key
     * was included in the request belongs. This will be returned if, and only if, the Application belongs to an
     * Organization.
     */
    private final String organizationUserId;

    /**
     * A value uniquely and permanently identifying the User associated with the auth_request within the Application whose
     * Application Key was included in the request belongs.
     */
    private final String userPushId;

    /**
     * Identifier that is unique for a single user identifying the device the user utilized to respond to the
     * auth request.
     */
    private final String deviceId;

    /**
     * @param authorized         The users response to the auth request.  True and False are authorized and declined
     *                           respectively.
     * @param authRequestId      Unique identifier for the authentication request for which this is the response.
     * @param userHash           Hashed identifier of the User that responded to the authentication request. This
     *                           value will be used by the De-Orbit Callback to identify the de-orbiting User. This value may
     *                           be used in place of a username or White Label User identifier for authorization requests.
     * @param organizationUserId Unique identifier of the User that responded to the authentication request within the
     *                           organization in which the Application for this request resides.  This will be returned if,
     *                           and only if, the Application belongs to an Organization.
     * @param userPushId         Unique identifier of the User that responded to the authentication request
     *                           within the Application to which this authentication request was made.
     * @param deviceId           Identifier for the device the User utilized to respond to the authentication request.
     *                           This value is unique to the User that responded to the authentication request and may
     *                           be duplicated in another User.
     */
    public AuthResponse(
            String authRequestId,
            boolean authorized,
            String userHash,
            String organizationUserId,
            String userPushId,
            String deviceId
    ) {
        this.authRequestId = authRequestId;
        this.authorized = authorized;
        this.userHash = userHash;
        this.organizationUserId = organizationUserId;
        this.userPushId = userPushId;
        this.deviceId = deviceId;
    }

    /**
     * Get the unique identifier for the authentication request for which this is the response.
     *
     * @return Authentication request ID
     */
    public String getAuthRequestId() {
        return authRequestId;
    }

    /**
     * Get the users response to the auth request.  True and False are authorized and declined respectively.
     *
     * @return Authorized response value
     */
    public boolean isAuthorized() {
        return authorized;
    }

    /**
     * Hashed identifier of the User that responded to the authentication request. This value will be used by the
     * De-Orbit Callback to identify the de-orbiting User.
     *
     * @return User hash
     */
    public String getUserHash() {
        return userHash;
    }

    /**
     * Get the unique identifier of the User that responded to the authentication request within the organization in
     * which the Application for this request resides.  This will be returned if, and only if, the Application belongs to an
     * Organization.
     *
     * @return Organization-User ID
     */
    public String getOrganizationUserId() {
        return organizationUserId;
    }

    /**
     * Get the unique identifier of the User that responded to the authentication request within the Application to
     * which this authorization request was made. This value may be used in place of a username or White Label
     * User identifier for authorization requests.
     * .
     * @return User push ID
     */
    public String getUserPushId() {
        return userPushId;
    }

    /**
     * Get the identifier for the device the User utilized to respond to the authentication request. This
     * value is unique to the User that responded to the authentication request and may be duplicated in another User.
     *
     * @return Device ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthResponse)) return false;

        AuthResponse that = (AuthResponse) o;

        if (authorized != that.authorized) return false;
        if (authRequestId != null ? !authRequestId.equals(that.authRequestId) : that.authRequestId != null)
            return false;
        if (userHash != null ? !userHash.equals(that.userHash) : that.userHash != null) return false;
        if (organizationUserId != null ? !organizationUserId.equals(that.organizationUserId) : that.organizationUserId != null) return false;
        if (userPushId != null ? !userPushId.equals(that.userPushId) : that.userPushId != null) return false;
        return !(deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null);

    }

    @Override public int hashCode() {
        int result = authRequestId != null ? authRequestId.hashCode() : 0;
        result = 31 * result + (authorized ? 1 : 0);
        result = 31 * result + (userHash != null ? userHash.hashCode() : 0);
        result = 31 * result + (organizationUserId != null ? organizationUserId.hashCode() : 0);
        result = 31 * result + (userPushId != null ? userPushId.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "AuthResponse{" +
                "authRequestId='" + authRequestId + '\'' +
                ", authorized=" + authorized +
                ", userHash='" + userHash + '\'' +
                ", organizationUserId='" + organizationUserId + '\'' +
                ", userPushId='" + userPushId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}