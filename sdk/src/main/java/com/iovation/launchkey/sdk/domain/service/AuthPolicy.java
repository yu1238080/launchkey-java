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

package com.iovation.launchkey.sdk.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to represent an authentication policy which will be utilized for providing dynamic policies per request.
 */
public class AuthPolicy {
    private final int requiredFactors;
    private final boolean requireKnowledgeFactor;
    private final boolean requireInherenceFactor;
    private final boolean requirePossessionFactor;
    private final List<Location> locations;

    /**
     * Create an service policy which only has geofence location requirements.
     * @param locations List of acceptable locations in which the user's device may be located.
     */
    public AuthPolicy(List<Location> locations) {
        this(false, false, false, locations);
    }

    /**
     * Create an service which requires a number factors to be used.
     * @param requiredFactors The number of unique factors to require.
     */
    public AuthPolicy(int requiredFactors) {
        this(requiredFactors, new ArrayList<Location>());
    }

    /**
     * Create an service policy that includes the number of factors and geofence locations
     * @param requiredFactors The number of unique factors to require.
     * @param locations List of acceptable locations in which the user's device may be located.
     */
    public AuthPolicy(int requiredFactors, List<Location> locations) {
        this.requiredFactors = requiredFactors;
        this.locations = Collections.unmodifiableList(new ArrayList<Location>(locations));
        requirePossessionFactor = false;
        requireInherenceFactor = false;
        requireKnowledgeFactor = false;
    }

    /**
     * Create an getServiceService policy that determines which getServiceService factors are required.
     * @param requireKnowledgeFactor Is a knowledge factor required
     * @param requireInherenceFactor Is an inherence factor required
     * @param requirePossessionFactor Is a possession factor required
     */
    public AuthPolicy(boolean requireKnowledgeFactor, boolean requireInherenceFactor, boolean requirePossessionFactor) {
        this(requireKnowledgeFactor, requireInherenceFactor, requirePossessionFactor, new ArrayList<Location>());
    }

    /**
     * Create an getServiceService policy that includes determination of which getServiceService factors are required as well as geofence locations.
     * @param requireKnowledgeFactor Is a knowledge factor required
     * @param requireInherenceFactor Is an inherence factor required
     * @param requirePossessionFactor Is a possession factor required
     * @param locations List of acceptable locations in which the user's device may be located.
     */
    public AuthPolicy(boolean requireKnowledgeFactor, boolean requireInherenceFactor, boolean requirePossessionFactor, List<Location> locations) {
        this.requiredFactors = 0;
        this.requireKnowledgeFactor = requireKnowledgeFactor;
        this.requireInherenceFactor = requireInherenceFactor;
        this.requirePossessionFactor = requirePossessionFactor;
        this.locations = Collections.unmodifiableList(new ArrayList<Location>(locations));
    }

    /**
     * Get the number of service factors required by this policy
     * @return The number of service factors required by this policy
     */
    public int getRequiredFactors() {
        return requiredFactors;
    }

    /**
     * Is a knowledge factor required by this policy
     * @return Is a knowledge factor required by this policy
     */
    public boolean isKnowledgeFactorRequired() {
        return requireKnowledgeFactor;
    }

    /**
     * Is an inherence factor required by this policy
     * @return Is an inherence factor required by this policy
     */
    public boolean isInherenceFactorRequired() {
        return requireInherenceFactor;
    }

    /**
     * Is a possession factor required by this policy
     * @return Is an possession factor required by this policy
     */
    public boolean isPossessionFactorRequired() {
        return requirePossessionFactor;
    }

    /**
     * Which geofence locations are acceptable for this policy
     * @return The geofence locations that are acceptable for this policy
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Location utilized for Geofencing
     */
    public static class Location {
        private final double longitude;
        private final double latitude;
        private final double radius;

        /**
         * @param radius Radius of the location in meters
         * @param latitude Latitude of the geolocation
         * @param longitude Longitude of the geolocation
         */
        public Location(double radius, double latitude, double longitude) {
            this.radius = radius;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        /**
         * Get the longitude of the geolocation
         * @return The longitude of the geolocation
         */
        public double getLongitude() {
            return longitude;
        }

        /**
         * Get the latitude of the geolocation
         * @return The latitude of the geolocation
         */
        public double getLatitude() {
            return latitude;
        }

        /**
         * Get the radius in meters
         * @return The radius in meters
         */
        public double getRadius() {
            return radius;
        }
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthPolicy)) return false;

        AuthPolicy that = (AuthPolicy) o;

        if (requiredFactors != that.requiredFactors) return false;
        if (requireKnowledgeFactor != that.requireKnowledgeFactor) return false;
        if (requireInherenceFactor != that.requireInherenceFactor) return false;
        if (requirePossessionFactor != that.requirePossessionFactor) return false;
        return locations.equals(that.locations);

    }

    @Override public int hashCode() {
        int result = requiredFactors;
        result = 31 * result + (requireKnowledgeFactor ? 1 : 0);
        result = 31 * result + (requireInherenceFactor ? 1 : 0);
        result = 31 * result + (requirePossessionFactor ? 1 : 0);
        result = 31 * result + locations.hashCode();
        return result;
    }

    @Override public String toString() {
        return "AuthPolicy{" +
                "requiredFactors=" + requiredFactors +
                ", requireKnowledgeFactor=" + requireKnowledgeFactor +
                ", requireInherenceFactor=" + requireInherenceFactor +
                ", requirePossessionFactor=" + requirePossessionFactor +
                ", locations=" + locations +
                '}';
    }
}