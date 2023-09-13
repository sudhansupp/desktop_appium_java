package coreUtilities.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class FileOperations 
{
	public JSONParser jsonParser;
	public JSONObject jsonObject;
	public Fillo fillo;
	public Connection connection;
	public Properties properties;
	
	
	/**
	 * This method is useful to read the excel sheet based on the query given as input. It'll return the values for the respective
	 * query in {@link Map} where the column name as a key( in capital letter) and the value as per the value entered in the excel. 
	 * @param file - {@link String} excel sheet location
	 * @param readQuery - {@link String} SQL like query to read the excel where the very first row will be treated as the column name
	 * @return {@link Map}
	 * @throws FilloException
	 */
	public Map<String, String> readExcel(String file, String readQuery) throws FilloException
	{
		fillo = new Fillo();
		connection = fillo.getConnection(file);
		Recordset rs = connection.executeQuery(readQuery);
		Map<String, String> excelData = new HashMap<String, String>();
		ArrayList<String> fieldNames = rs.getFieldNames();
		while(rs.next())
		{
			fieldNames.forEach(s->{
				try {
					excelData.put(s, rs.getField(s));
				} catch (FilloException e) {
					e.printStackTrace();
				}
			});
		}
		return excelData;
	}
	
	/**
	 * This method is useful to write in to an excel sheet.
	 * @param file - {@link String} excel sheet location
	 * @param writeQuery - {@link String} SQL like query to write in the excel where the very first row will be treated as the column name
	 * @throws FilloException
	 */
	public void writeExcel(String file, String writeQuery) throws FilloException
	{
		fillo = new Fillo();
		connection = fillo.getConnection(file);
		connection.executeUpdate(writeQuery);
	}
	
	/**
	 * This method is useful to read the JSON file which must be simple in structure
	 * @param file - {@link String} JSON file location
	 * @param readQuery - {@link String} - The main parent node
	 * @return {@link Map}
	 * @throws FilloException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> readJson(String file, String jsonNode) throws FileNotFoundException, IOException, ParseException
	{
		jsonParser = new JSONParser();
		jsonObject = (JSONObject)jsonParser.parse(new FileReader(file));
		return (Map<String, String>) jsonObject.get(jsonNode);
	}
	
	/**
	 * This method is useful to read the Property file which must be simple in structure
	 * @param file - {@link String} Property file location
	 * @return {@link Map}
	 * @throws FilloException
	 */
	public Map<String, String> readProperty(String file) throws FileNotFoundException, IOException
	{
		properties = new Properties();
		properties.load(new FileReader(file));
		Map<String, String> map = new HashMap<String, String>();
		map.putAll(properties.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), 
                                          e -> e.getValue().toString())));
		return map;
	}
	
	/**
	 * This method is useful to verify whether the given file is present in the desired directory or not.
	 * If file present then will return true else false.
	 * @param fileLocation
	 * @param fileName
	 * @param timeoutInSeconds
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean isFilePresentInDirectory(String fileLocation, String fileName, int timeoutInSeconds) throws Exception {
		boolean flag = false;
		int counter = 1;
		File dir = new File(fileLocation);
		while(!flag && counter <= timeoutInSeconds) {
			Thread.sleep(1000);
			File[] listFiles = dir.listFiles();
			flag = Arrays.asList(listFiles)
							.parallelStream()
							.anyMatch(file->file.getName().contains(fileName));
			counter++;
		}
		return flag;
	}
	
	/**
	 * This method is useful to delete the desired file from the desired location.
	 * @param fileLocation
	 * @param fileName
	 * @throws Exception
	 */
	public void deleteFileFromDirectoryIfExists(String fileLocation, String fileName) throws Exception {
		File dir = new File(fileLocation);
		File[] listFiles = dir.listFiles();
		Arrays.asList(listFiles)
				.parallelStream()
				.filter(file->file.getName().contains(fileName))
				.forEach(file->{
					String absolutePath = file.getAbsolutePath();
					try {
						Files.deleteIfExists(Paths.get(absolutePath));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}
	
	/**
	 * This method is useful to retrieve the absolute path of the given file
	 * @param fileLocation
	 * @param fileName
	 * @return {@link List}
	 */
	public List<String> getAbsoluteFilePath(String fileLocation, String fileName) {
		List<String> absolutePaths=new ArrayList<>();
		File dir = new File(fileLocation);
		File[] listFiles = dir.listFiles();
		Arrays.asList(listFiles)
				.parallelStream()
				.filter(file->file.getName().contains(fileName))
				.forEach(file->absolutePaths.add(file.getAbsolutePath()));
		return absolutePaths;
	}

}