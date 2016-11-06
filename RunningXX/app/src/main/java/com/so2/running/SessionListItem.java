package com.so2.running;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SessionListItem
{

   private int id;
   private String sessionName;
   private String date;
   private String distance;
   private String averageSpeed;
   private String averagePace;
   private String duration;
   private ArrayList<Float> speeds;
   private ArrayList<Float> altitudes;
   private String jsonSpeeds;
   private String jsonAltitudes;
   private String jsonTimes;
   private String jsonPaces;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getSessionName() {
      return sessionName;
   }

   public void setSessionName(String sessionName) {
      this.sessionName = sessionName;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getDistance() {
      return distance;
   }

   public void setDistance(String distance) {
      this.distance = distance;
   }

   public String getAverageSpeed() {
      return averageSpeed;
   }

   public void setAverageSpeed(String averageSpeed) {
      this.averageSpeed = averageSpeed;
   }

   public String getAveragePace() {
      return averagePace;
   }

   public void setAveragePace(String averagePace) {
      this.averagePace = averagePace;
   }

   public String getDuration() {
      return duration;
   }

   public void setDuration(String duration) {
      this.duration = duration;
   }

   public ArrayList<Float> getSpeeds() {
      return speeds;
   }

   public void setSpeeds(ArrayList<Float> speeds) {
      this.speeds = speeds;
   }

   public ArrayList<Float> getAltitudes() {
      return altitudes;
   }

   public void setAltitudes(ArrayList<Float> altitudes) {
      this.altitudes = altitudes;
   }

   public void setJsonSpeeds(String jsonSpeeds)
   {
      this.jsonSpeeds = jsonSpeeds;
   }

   public String getJsonSpeeds()
   {
      return jsonSpeeds;
   }

   public void setJsonAltitudes(String jsonAltitudes)
   {
      this.jsonAltitudes = jsonAltitudes;
   }

   public String getJsonAltitudes()
   {
      return  jsonAltitudes;
   }

   public void setJsonTimes(String jsonTimes)
   {
      this.jsonTimes = jsonTimes;
   }

   public String getJsonTimes()
   {
      return jsonTimes;
   }

   public void setJsonPaces(String jsonPaces)
   {
      this.jsonPaces = jsonPaces;
   }

   public String getJsonPaces()
   {
      return jsonPaces;
   }

   /**
    * Put session data in JSON object
    * @return JSON Objtect
    */
   public JSONObject toJSONObject()
   {
      JSONObject jsonObject = new JSONObject();
      try{
         jsonObject.put("id", id);
         jsonObject.put("sessionName", sessionName);
         jsonObject.put("date", date);
         jsonObject.put("distance", distance);
         jsonObject.put("averageSpeed", averageSpeed);
         jsonObject.put("averagePace", averagePace);
         jsonObject.put("speeds", jsonSpeeds);
         jsonObject.put("altitudes", jsonAltitudes);
         jsonObject.put("duration", duration);
         jsonObject.put("paces", jsonPaces);
         jsonObject.put("times", jsonTimes);

      }
      catch (JSONException e) {
         // Auto-generated catch block
         e.printStackTrace();
      }
      return jsonObject;
   }
}
