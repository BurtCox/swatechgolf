package swatechgolf.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Burt Cox (e41887)
 *
 * Description:
 * 
 * Create Date: Jun 6, 2017
 *
 * Copyright 2017 Southwest Airlines. All rights reserved.
 */
public class InMemoryFile {
   private String _fileName;
   List<String> _fileRecords = new ArrayList<>();

   public InMemoryFile(String fileName) {
      _fileName = fileName;
   }
   
   public void add(String record) {
      _fileRecords.add(record);
   }
   
   public List<String> getRecords() {
      return _fileRecords;
   }

   public String getFileName() {
      return _fileName;
   }

   public void setFileName(String fileName) {
      _fileName = fileName;
   }
}
