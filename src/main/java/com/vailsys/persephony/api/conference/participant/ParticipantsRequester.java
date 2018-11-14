package com.vailsys.persephony.api.conference.participant;

import com.google.gson.JsonSyntaxException;
import com.vailsys.persephony.api.APIAccountRequester;
import com.vailsys.persephony.api.PersyException;
import com.vailsys.persephony.api.PersyJSONException;

import java.util.HashMap;

import static com.vailsys.persephony.json.PersyGson.gson;

/**
 * This class represents the set of wrappers around the Persephony Participants API.
 * It provides methods to handle all the operations supported by the Persephony
 * Participants API.
 */
public class ParticipantsRequester extends APIAccountRequester {
    private static final String pathHead = "Participants";
    /** The default path for the Participants endpoint. */
    private final String path;
    /** The accountId for the acting account. */
    private final String actingAccountId;

    /**
     * Creates a ParticipantsRequester. For most SDK users ParticipantsRequesters will be created automatically by the {@link com.vailsys.persephony.api.PersyClient} but is available for more advanced users who only require the features in this specific requester and not the rest of the features of the {@link com.vailsys.persephony.api.PersyClient}.
     *
     * @param credAccountId The accountId to use as authentication credentials
     * in the HTTP Basic Auth header for requests made by this requester.
     * @param credAuthToken The authToken to use as authentication credentials
     * in the HTTP Basic Auth header for requests made by this requester.
     * @param actingAccountId The accountId to act as. This can be the same as
     * the {@code credAccountId} or the accountId of a subaccount of the {@code credAccountId}.
     * @param conferencePath The path for the conference endpoint this ParticipantsRequester is under.
     */
    public ParticipantsRequester(String credAccountId, String credAuthToken, String actingAccountId, String conferencePath) {
        super(credAccountId, credAuthToken);
        this.actingAccountId = actingAccountId;
        this.path = APIAccountRequester.constructPath(conferencePath, pathHead);
    }

    /**
     * Retrieve the {@code actingAccountId}.
     *
     * @return The {@code actingAccountId}
     */
    public String getActingAccountId() {return this.actingAccountId; }

    /**
     * Retrieve the {@code path} value generated by the ParticipantsRequester. This is
     * the URL path used in requests to Persephony.
     *
     * @return The {@code path}
     */
    public String getPath() { return this.path; }

    private String getParticipantPath(String participantId) {
        return APIAccountRequester.constructPath(this.getPath(), participantId);
    }

    /**
     * Allows developers using the SDK to change which instance of the Persephony API that the ParticipantsRequester points to.
     *
     * @param newUrl The new URL to use in place of the default APIRequester.PERSY_URL
     */
    public void setPersyUrl(String newUrl) { super.setPersyUrl(newUrl); }

    /**
     * Retrieve a list of participants associated with the conference.
     * This wraps an HTTP GET request to the Persephony API's
     * /Accounts/$accountId/Conferences/$conferenceId/Participants endpoint. This will retrieve all participants for
     * the conference.
     *
     * @return An in-language representation of Persephony's paginated list
     * response. This will be a paginated list of participant instances as returned by
     * the Persephony API.
     *
     * @see com.vailsys.persephony.api.conference.participant.Participant
     * @throws PersyException when the request fails or the JSON is invalid.
     */
    public ParticipantList get() throws PersyException {
        return new ParticipantList(this.getCredentialAccountId(), this.getCredentialAuthToken(), this.GET(this.getPath()));
    }

    /**
     * Retrieve a list of participants associated with the conference.
     * This wraps an HTTP GET request to the Persephony API's
     * /Accounts/$accountId/Conferences/$conferenceId/Participants endpoint. This will retrieve all participants for
     * the conference.
     *
     * @param filters An object containing a number of possible ways to filter the participants returned by Persephony.
     *
     * @return An in-language representation of Persephony's paginated list
     * response. This will be a paginated list of participant instances as returned by
     * the Persephony API.
     *
     * @see com.vailsys.persephony.api.conference.participant.ParticipantsSearchFilters
     * @throws PersyException when the request fails or the JSON is invalid.
     */
    public ParticipantList get(ParticipantsSearchFilters filters) throws PersyException {
        HashMap<String, String> filtersMap;

        try {
            filtersMap = gson.fromJson(gson.toJson(filters), APIAccountRequester.GETMapType);
        } catch (JsonSyntaxException jse) {
            throw new PersyJSONException(jse);
        }

        return new ParticipantList(this.getCredentialAccountId(), this.getCredentialAuthToken(), this.GET(this.getPath(), filtersMap));
    }

    /**
     * Retrieve a single participant from Persephony.
     *
     * @param participantId The {@code callId} of the desired participant.
     *
     * @return The participant matching the {@code callId} provided.
     * @throws PersyException when the request fails or the JSON is invalid.
     */
    public Participant get(String participantId) throws PersyException {
        return Participant.fromJson(this.GET(this.getParticipantPath(participantId)));
    }

    /**
     * Update the existing participant associated with the {@code callId}.
     * This wraps an HTTP POST request to the Persephony API's
     * /Accounts/$accountId/Conferences/$conferenceId/Participants/$callId endpoint.
     *
     * @param participantId The {@code callId} of the desired participant.
     * @param options The {@code ParticipantUpdateOptions} to change in the target participant.
     * @see com.vailsys.persephony.api.conference.participant.ParticipantUpdateOptions
     *
     * @return The participant matching the {@code callId} provided.
     * @throws PersyException when the request fails or the JSON is invalid.
     */
    public Participant update(String participantId, ParticipantUpdateOptions options) throws PersyException {
        return Participant.fromJson(this.POST(this.getParticipantPath(participantId), gson.toJson(options)));
    }

    /**
     * Delete the participant associated with the {@code callId}.
     * This wraps an HTTP DELETE to the Persephony API's
     * /Accounts/$accountId/Conferences/$conferenceId/Participants/$callId endpoint.
     *
     * @param participantId The {@code callId} of the desired participant.
     * @throws PersyException when the request fails.
     */
    public void remove(String participantId) throws PersyException {
        this.DELETE(this.getParticipantPath(participantId));
    }
}