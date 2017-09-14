package swatechgolf.file_input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.swacorp.ags.file.ExcludeFileSpecFileFilter;
import com.swacorp.ags.file.FileFilters;
import com.swacorp.ags.file.FileList;

import swatechgolf.utility.InMemoryFile;
import swatechgolf.utility.Message;

@Component
public class FileInputAdapter {
	private Path _inputPath;
	private Path _archivePath;
	private int _fileCheckInterval = 300000;
	@Autowired private FileInputOutputTransformer _fileRecordTransformer;
	@Autowired private FileInputMessageRouter _messageRouter;
	Logger _logger = LoggerFactory.getLogger(getClass().getSimpleName());

	
	public void setInput(String inputPath) {
		_inputPath = Paths.get(inputPath);
	}

	public void setArchiveDir(String archivePath) {
	   _archivePath = Paths.get(archivePath);
	}
	
	@Async
	public void start() {
	   while (true) {
	      FileFilters fileFilters = createFileFilters();
	      boolean recursive = true;
	      List<File> files = FileList.getFiles(_inputPath, recursive, fileFilters);
	      try {
	         processFileSet(files);
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      
	      try {
            Thread.sleep(_fileCheckInterval);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
	   }
	}

	private void processFileSet(List<File> files) throws IOException {
		for (File file : files) {
			processFile(file);
		}
	}

	private void processFile(File file) throws IOException {
	   InMemoryFile inMemoryFile = new InMemoryFile(file.getName()); 
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;

		while ((line = reader.readLine()) != null) {
		   inMemoryFile.add(line);
		}

		Message message = _fileRecordTransformer.transform(file, inMemoryFile);
		_messageRouter.publish(message);
		reader.close();
		boolean createDirIfNeeded = true;
		FileUtils.moveFileToDirectory(file, _archivePath.toFile(), createDirIfNeeded);
	}

	private FileFilters createFileFilters() {
		FileFilters fileFilters = new FileFilters();
		fileFilters.add(new ExcludeFileSpecFileFilter(".csv"));
		fileFilters.add(new ExcludeFileSpecFileFilter(".xlsx"));
		return fileFilters;
	}
}
