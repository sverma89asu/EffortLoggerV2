package tu14.services;

import tu14.api.Backend;
import tu14.api.RawData;
import tu14.api.request.CreateRequest;
import tu14.api.request.GetRequest;
import tu14.model.PlanningPokerSession;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PlanningPokerSessionService {

    private String sessionName;
    private int storyId;
    private int pokerId;
    public boolean inSession = false;
    public boolean ownsSession = false;

    public boolean createSession(String sessionName, int storyId) {
        if (inSession) return false;

        try {
            RawData<PlanningPokerSession> sessions =
                    Backend.getInstance().send(new CreateRequest().table("planningpoker")
                                                       .body(new PlanningPokerSession(sessionName, storyId)), PlanningPokerSession.class).get();

            if (!sessions.ok()) return false;

            PlanningPokerSession session = sessions.castSafe().get(0);

            this.sessionName = session.name;
            this.storyId = session.storyId;
            this.pokerId = (int) session.id;

        } catch (InterruptedException | ExecutionException e) {
            return false;
        }

        ownsSession = true;
        return true;
    }

    public boolean joinSession(String sessionName) {
        if (inSession) return false;

        try {
            RawData<PlanningPokerSession> rawSessions =
                    Backend.getInstance().send(new GetRequest().table("planningpoker"),
                                               PlanningPokerSession.class).get();

            if (!rawSessions.ok()) return false;

            List<PlanningPokerSession> sessions = rawSessions.castSafe();

            for (PlanningPokerSession session : sessions) {
                if (session.name.equals(sessionName)) {
                    this.sessionName = session.name;
                    this.storyId = session.storyId;
                    this.pokerId = (int) session.id;

                    ownsSession = false;

                    System.out.println(this.pokerId);
                    return true;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }

        return false;
    }

    public void leaveSession() {
        this.inSession = false;
    }

    public boolean sendStoryRound(String notes, int storyPoints) {
        // TODO actually do this in java instead of in JS with fetch()
        return false;
    }

    public int getPokerId() {
        return pokerId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }
}
