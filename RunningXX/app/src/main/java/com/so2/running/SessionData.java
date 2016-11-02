/**
 * This class manages the operations related to the collected data during a training session.
 */

package com.so2.running;

import org.json.JSONArray;

public class SessionData
{

   private long sessionTime;               //Training session lenght
   private float instantSpeed;             //Athlet instant speed
   private float averageSpeed;             //Athlet average speed on entire session
   private float averageSpeedLastXMeter;   //Athlet average speed on last meters
   private double averagePaceLastXMeter;   //Athlet average pace on last meters
   private float speedSum;                 //Sum of all instant speeds, used to get average speed
   private JSONArray sessionSpeeds = new JSONArray();
   private JSONArray sessionAltitudes= new JSONArray();
   private JSONArray sessionTimes= new JSONArray();
   private JSONArray sessionPaces= new JSONArray();
   private int counter;                    //Number of instant speed detection, used to get average speed

   /**
    * SessionData constructor
    */
   public SessionData ()
   {
      sessionTime = 0;
      instantSpeed = 0;
      averagePaceLastXMeter = 0;
      averageSpeed = 0;
      averageSpeedLastXMeter = 0;
      speedSum = 0;
      counter = 0;
   }

   JSONArray getSessionTimes ()
   {
      return sessionTimes;
   }

   void setSessionTimes (long time)
   {
      sessionTimes.put(Long.toString(time/1000));
   }

   JSONArray getSessionPaces ()
   {
      return sessionPaces;
   }

   void setSessionPaces (double pace)
   {
      sessionPaces.put(String.format("%.02f", pace));
   }

   JSONArray getSessionAltitudes ()
   {
      return sessionAltitudes;
   }

   void setSessionAltitudes (double altitude)
   {
      sessionAltitudes.put(String.format("%.02f", altitude));
   }

   JSONArray getSessionSpeeds ()
   {
      return sessionSpeeds;
   }

   void setSessionSpeeds (float speed)
   {
      sessionSpeeds.put(String.format("%.02f", speed));
   }

   /**
    * Calculates the total distance covered by the athlete
    * @return Total distance covered in meters
    * */
   public float getSessionDistance ()
   {
      averageSpeed = this.getAverageSpeed();
      return (averageSpeed * (sessionTime / 1000));
   }

   /**
    * Get the session lenght
    * @return Session lenght in milliseconds
    */
   public float getSessionTime ()
   {
      return sessionTime;
   }

   /**
    * Update the session duration
    * @param newSessionTime new duration value
    */
   public void updateSessionTime (long newSessionTime)
   {
      this.sessionTime = newSessionTime;
   }

   /**
    * Get athlete instant speed
    * @return Instant speed in m/s
    */
   public float getInstantSpeed ()
   {
      return instantSpeed;
   }

   /**
    * Set athlete instant speed and update speedSum value
    * @param speed Speed detection
    */
   public void setInstantSpeed (float speed)
   {
      this.instantSpeed = speed;

      speedSum += speed;
      counter ++;
   }

   /**
    * Calculates athlete average speed
    * @return Average speed in m/s
    */
   public float getAverageSpeed ()
   {
      return speedSum / counter;
   }

   /**
    * Calculate athlete average pace
    * @return Average pace in min/Km
    */
   public double getAveragePace ()
   {
      return (Math.pow(this.getAverageSpeed() * 3.6, -1.0)) * 60;
   }

   /**
    * Resets class attributes
    */
   public void reset ()
   {
      sessionTime = 0;
      instantSpeed = 0;
      averagePaceLastXMeter = 0;
      averageSpeed = 0;
      averageSpeedLastXMeter = 0;
      speedSum = 0;
      counter = 0;
   }

   /**
    * Set average speed on last meters
    * @param newAverageSpeed Average speed in m/s
    */
   public void setAverageSpeedLastXMeter (float newAverageSpeed)
   {
      this.averageSpeedLastXMeter = newAverageSpeed;
   }

   public float getAverageSpeedLastXMeter ()
   {
      return this.averageSpeedLastXMeter;
   }

   /**
    * Set average pace on last meters
    * @param newAveragePace Average pace in min/Km
    */
   public void setAveragePaceLastXMeter (double newAveragePace)
   {
      this.averagePaceLastXMeter = newAveragePace;
   }

   public double getAveragePaceLastXMeter ()
   {
      return this.averagePaceLastXMeter;
   }
}

