package swatechgolf.sponsor_registration_file_enhancer;

public class FileRecord implements Comparable<FileRecord> {
	private Integer _recordNumber;
	private String _record;
	
	public FileRecord(Integer recordNumber, String record) {
		super();
		_recordNumber = recordNumber;
		_record = record;
	}

	public Integer getRecordNumber() {
		return _recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		_recordNumber = recordNumber;
	}

	public String getRecord() {
		return _record;
	}

	public void setRecord(String record) {
		_record = record;
	}

	@Override
	public int compareTo(FileRecord fileRecord) {
		// TODO Auto-generated method stub
		return _recordNumber.compareTo(fileRecord.getRecordNumber());
	}
	
}
