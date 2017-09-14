package swatechgolf.sponsor_registration_file_enhancer;

import java.util.ArrayList;
import java.util.List;

public class FileRecords {
	private List<FileRecord> _records = new ArrayList<>();
	
	public void add(FileRecord fileRecord) {
		_records.add(fileRecord);
	}
	
	public List<FileRecord> getRecords() {
		_records.sort(null);
		return _records;
	}
	
	public boolean isComplete() {
	   Integer lastRecordNumber = 0;
	   boolean hasEndOfFile = false;
	   
	   for (FileRecord fileRecord : _records) {
	      if (fileRecord.getRecordNumber() != lastRecordNumber+1) {
	         return false;
	      }
	      
	      if (fileRecord.getRecord().equalsIgnoreCase("End Of File")) {
	         hasEndOfFile = true;
	      }
	      lastRecordNumber = fileRecord.getRecordNumber();
	   }
	   
	   if (!hasEndOfFile) {
	      return false;
	   }
	   
	   return true;
	}
}
