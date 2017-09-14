package swatechgolf.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author Burt Cox (e41887)
 *
 * Description: Splits a record into a key/value pair
 * 
 * Create Date: Feb 15, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
@Component
public class KeyValueParser {

   public Map<String, String> parse(List<String> records) {
      Map<String, String> keyValues = new HashMap<>();
      
      for (String record : records) {
         KeyValue keyValue = parse(record);
         keyValues.put(keyValue.getKey(), keyValue.getValue());
      }
      
      return keyValues;
   }
   
	public KeyValue parse(String record) {
	   String[] values = record.split(":", 2);
	   String key = null;
	   String value = null;
	   
	   if (values.length > 0) {
	      key = values[0].trim();
	   }
	   
	   if (values.length > 1) {
	      value = values[1].trim();
	   }
		
	   return new KeyValue(key, value);
	}

}
