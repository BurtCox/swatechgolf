package swatechgolf.utility;

/**
 * @author Burt Cox (e41887)
 *
 * Description: Defines a key/value pair.
 * 
 * Create Date: Feb 13, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
public class KeyValue {
   private String _key;
   private String _value;
   
   public KeyValue(String key, String value) {
      super();
      _key = key;
      _value = value;
   }
   
   public String getKey() {
      return _key;
   }
   public void setKey(String key) {
      _key = key;
   }
   public String getValue() {
      return _value;
   }
   public void setValue(String value) {
      _value = value;
   }
   
   public boolean hasValue() {
	   return (_value != null);
   }
}
