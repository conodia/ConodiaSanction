package fr.blixow.conodia.managers;

import fr.pandaguerrier.conodiagameapi.ConodiaGameAPI;
import org.json.simple.JSONObject;

public class BanManager {
  private int id = 0;
  private final String targetName;
  private final String authorName;
  private final String reason;
  private final long durationLeft;
  private final long endTime;
  private final long startTime;
  private boolean isActive;
  private final boolean isPermanent;

  private BanManager(String targetName, String authorName, String reason, long endTime, long startTime, boolean isPermanent, boolean isActive, int id) {
    this.targetName = targetName;
    this.authorName = authorName;
    this.reason = reason;
    this.durationLeft = endTime - startTime;
    this.endTime = endTime;
    this.startTime = startTime;
    this.isActive = isActive;
    this.isPermanent = isPermanent;
    this.id = id;
  }

  public BanManager(String targetName, String authorName, String reason, long endTime, long startTime, boolean isPermanent) {
    this.targetName = targetName;
    this.authorName = authorName;
    this.reason = reason;
    this.durationLeft = endTime - startTime;
    this.endTime = endTime;
    this.startTime = startTime;
    this.isActive = true;
    this.isPermanent = isPermanent;
  }

  public void handle() {
    JSONObject payload = (JSONObject) ConodiaGameAPI.getInstance().getApiManager().post("/sanctions", toJson()).get("sanction");
    CacheManager.getBanCache().add(this);

    this.id = Integer.parseInt(payload.get("id").toString());
  }

  public void undo() {
    ConodiaGameAPI.getInstance().getApiManager().destroy("/sanctions/" + id + "/ban", new JSONObject());
    CacheManager.getBanCache().remove(this);
  }

  private JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("type", "ban");
    json.put("playerName", targetName);
    json.put("authorName", authorName);
    json.put("reason", reason);
    json.put("endTime", isPermanent ? null : endTime);
    json.put("startTime", startTime);
    json.put("isActive", isActive);
    json.put("isPermanent", isPermanent);

    return json;
  }

  public String getTargetName() {
    return targetName;
  }
  public String getAuthorName() {
    return authorName;
  }
  public String getReason() {
    return reason;
  }
  public long getEndTime() {
    return endTime;
  }
  public boolean isActive() {
    return isActive;
  }
  public boolean isPermanent() {
    return isPermanent;
  }

  static BanManager from(JSONObject payload) {
    return new BanManager(
      (String) payload.get("player_name"),
      (String) payload.get("author_name"),
      (String) payload.get("reason"),
      payload.get("end_time") != null ? Long.parseLong(payload.get("end_time").toString()) : 0,
      Long.parseLong(payload.get("start_time").toString()),
      (boolean) payload.get("is_permanent"),
      (boolean) payload.get("is_active"),
      Integer.parseInt(payload.get("id").toString())
    );
  }
}
