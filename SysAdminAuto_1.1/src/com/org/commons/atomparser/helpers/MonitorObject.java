package com.org.commons.atomparser.helpers;
 
import java.util.Hashtable;
 
/**
 * @author Sharath
 *
 */
public class MonitorObject {
      private String monitoredElement; // Is either DISK or PROCESS
      private String dateNTime;
      private Hashtable<String,String> utilizationTable;
      
      /**
     * @param dateNTime
     * @param monitoredElement
     */
    public MonitorObject(String dateNTime,String monitoredElement){
    	  this.dateNTime = dateNTime;
    	  this.monitoredElement = monitoredElement;
    	  this.utilizationTable = new Hashtable<String,String>();
      }
 
      /**
       * @return the monitoredElement
       */
      public String getMonitoredElement() {
            return monitoredElement;
      }
 
      /**
       * @return the dateNTime
       */
      public String getDateNTime() {
            return dateNTime;
      }
 
      /**
       * @return the utilizationTable
       */
      public Hashtable<String, String> getUtilizationTable() {
            return utilizationTable;
      }
 
      /**
       * @param utilizationTable the utilizationTable to set
       */
      public void setUtilizationTable(Hashtable<String, String> utilizationTable) {
            this.utilizationTable = utilizationTable;
      }
      
      /**
     * @param _rowString
     * @param _columnString
     * to set the values directly to the hash table.
     */
    public void setUtilizationValues(String _rowString,String _columnString){
    	  this.utilizationTable.put(_rowString,_columnString);
      }
 
 
}
 