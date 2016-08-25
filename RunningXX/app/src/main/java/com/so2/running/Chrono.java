package com.so2.running;

import android.os.SystemClock;
import android.widget.Chronometer;



public class Chrono {

   private Chronometer instancedChrono;    //Chronometer object
   private long resumeTime = 0;            //initialization of chronometer resume time after a pause
   private long sessionLenght = 0;         //initialization of session lenght variable

   /**
    * Constructor of Chronometer object
    * @param instance Chronometer instance
    * */
   public Chrono (Chronometer instance) {
        instancedChrono = instance;
   }

   /**
    * Start counting time
    */
   public void startChronometer() {
      instancedChrono.setBase(SystemClock.elapsedRealtime());
      instancedChrono.start();
   }

   /**
    * Stop counting time and update session lenght
    */
   public void stopChronometer() {
      instancedChrono.stop();
      sessionLenght =  (SystemClock.elapsedRealtime() - instancedChrono.getBase());
   }

   /**
    * Pause counting time and update session lenght
    */
   public void pauseChronometer() {
      resumeTime = SystemClock.elapsedRealtime();
      instancedChrono.stop();
      sessionLenght =  (SystemClock.elapsedRealtime() - instancedChrono.getBase());
   }

   /**
    * Resume counting time after a pause
    */
   public void resumeChronometer() {
      resumeTime = SystemClock.elapsedRealtime() - resumeTime;
      instancedChrono.setBase(instancedChrono.getBase() + resumeTime);
      instancedChrono.start();
   }

   /**
    * Get chronometer time on last pause or stop
    * @return Session lenght in milliseconds
    */
   public long getTimeInMillis() {
        return sessionLenght;
   }

   /**
    * Get actual chronometer time
    * @return Time in milliseconds
    */
   public long getTimeOnSession() {
      return (SystemClock.elapsedRealtime() - instancedChrono.getBase());
   }

   /**
    * Format the session lenght in hours:minutes:seconds
    * @return Formatted session lenght
    */
   public String getSessionLenght () {

      String result;

      long seconds = (sessionLenght / 1000) % 60;
      long minutes = (sessionLenght / 60000) % 60;
      long hours = sessionLenght / 3600000;

      result = "" + hours + ":" + minutes + ":" + seconds;

      return result;
   }

}