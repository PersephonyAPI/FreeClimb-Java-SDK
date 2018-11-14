package com.vailsys.persephony.api.phoneNumber;

import com.google.gson.JsonSyntaxException;
import com.vailsys.persephony.api.APIRequester;
import com.vailsys.persephony.api.PersyException;
import com.vailsys.persephony.api.PersyJSONException;

import java.util.HashMap;

import static com.vailsys.persephony.json.PersyGson.gson;

/**
 * This class represents the set of wrappers around the Persephony AvailablePhoneNumbers API.
 * It provides methods to handle all the operations supported by the Persephony AvailablePhoneNumbers API.
 */
public class AvailablePhoneNumberRequester extends APIRequester {
    private static final String pathHead = "AvailablePhoneNumbers";
    /** The default path for the AvailablePhoneNumber endpoint. */
    private final String path;
    /** The accountId for the acting account. */
    private final String actingAccountId;

    /**
     * Creates an AvailablePhoneNumberRequester. For most SDK users AvailablePhoneNumberRequesters will be
     * created automatically by the {@link com.vailsys.persephony.api.PersyClient} but is available for more advanced users who only require the features in this specific requester and not the rest of the features of the {@link com.vailsys.persephony.api.PersyClient}.
     *
     * @param credAccountId The accountId to use as authentication credentials in the HTTP Basic Auth header for requests made by this requester.
     * @param credAuthToken The authToken to use as authentication credentials in the HTTP Basic Auth header for requests made by this requester.
     * @param actingAccountId The accountId to act as. This can be the same as {@code credAccountId} or the accountId of a subaccount of the {@code credAccountId}.
     * @throws PersyException when the request fails or the JSON is invalid.
     */
    public AvailablePhoneNumberRequester(String credAccountId, String credAuthToken, String actingAccountId) throws PersyException {
        super(credAccountId, credAuthToken);
        this.actingAccountId = actingAccountId;
        this.path = APIRequester.constructAbsolutePath(pathHead);
    }

    /**
     * Retrieve the {@code actingAccountId}.
     *
     * @return The {@code actingAccountId}.
     */
    public String getActingAccountId() {
        return actingAccountId;
    }

    /**
     * Retrieve the {@code path} value generated by the AvailablePhoneNumberRequester. This is the URL path used in requests to Persephony.
     *
     * @return The {@code path}.
     */
    public String getPath() {
        return path;
    }

    /**
     * Allows developers using the SDK to change which instance of the Persephony API that the AvailablePhoneNumberRequester points to.
     *
     * @param newUrl The new URL to use in place of the default APIRequester.PERSY_URL
     */
    public void setPersyUrl(String newUrl){ super.setPersyUrl(newUrl);}

    /**
     * Retrieve a list of AvailablePhoneNumbers for purchase. This wraps an HTTP GET request to the Persephony API's /AvailablePhoneNumbers.
     *
     * @return An in-language representation of Persephony's paginated list response. This will be a paginated list of available phone numbers as returned by the Persephony API.
     * @throws PersyException when the request fails or the JSON is invalid.
     */
    public AvailablePhoneNumberList get() throws PersyException {
        return new AvailablePhoneNumberList(this.getCredentialAccountId(), this.getCredentialAuthToken(), this.GET(this.getPath()));
    }

    /**
     * Retrieve a list of AvailablePhoneNumbers for purchase. This wraps an HTTP GET request to the Persephony API's /AvailablePhoneNumbers.
     *
     * @param filters an object containing a number of possible ways to filter the available phone numbers returned by Persephony.
     * @return An in-language representation of Persephony's paginated list response. This will be a paginated list of available phone numbers as returned by the Persephony API.
     *
     * @see com.vailsys.persephony.api.phoneNumber.AvailablePhoneNumberSearchFilters
     * @throws PersyException when the request fails or the JSON is invalid.
     */
    public AvailablePhoneNumberList get(AvailablePhoneNumberSearchFilters filters) throws PersyException {
        HashMap<String, String> filtersMap;

        try{
            filtersMap = gson.fromJson(gson.toJson(filters), APIRequester.GETMapType);
        } catch (JsonSyntaxException jse){
            throw new PersyJSONException(jse);
        }

        return new AvailablePhoneNumberList(this.getCredentialAccountId(), this.getCredentialAuthToken(), this.GET(this.getPath(), filtersMap));
    }
}