import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ThemeEvaluation {
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("Usage: ThemeEvaluation <csv-file>");
			System.exit(-1);
		}

		Reader reader = new FileReader(args[0]);
		Map<String, Integer> counts = new HashMap<>();
		boolean skippedFirst = false;
		for(CSVRecord record: CSVFormat.DEFAULT.parse(reader).getRecords()) {
			if(!skippedFirst) {
				skippedFirst = true;
				continue;
			}
			String values = record.get(1);
			if(values != null) {
				for(String entry: values.split(";")) {
					Integer count = counts.get(entry);
					if(count == null) {
						counts.put(entry, Integer.valueOf(1));
					} else {
						counts.put(entry, count + 1);
					}
				}
			}
		}
		
		counts.entrySet().stream()
			.sorted((e1, e2) -> e1.getValue() - e2.getValue())
			.forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));	
	}
}
