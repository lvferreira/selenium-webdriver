package rahulshettyacademy.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class DataReader {

	public List<HashMap<String, String>> getJsonDataToMap() throws IOException {
		// Define the file path to the JSON file
		String filePath = System.getProperty("user.dir") 
				+ "//data//PurchaseOrder.json";

		// Read JSON file content to a string
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir") + filePath),
				StandardCharsets.UTF_8);

		// Convert JSON string to List of HashMaps using Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(
				jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data; // Return the List of HashMaps
	}
}
