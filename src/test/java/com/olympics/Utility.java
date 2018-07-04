package com.olympics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;

public class Utility {
	// takes table id, column number and row count and returns the data in that
	// column as a String
	public static List<String> readTableColumnData(String id, int colNum, int rowCount) {

		List<String> columnEntries = new ArrayList<>();

		for (int rowNum = 1; rowNum <= rowCount; rowNum++) {
			String xpath = "//table[" + id + "]/tbody/tr[" + rowNum + "]/td[" + colNum + "]";

			String tdData = OlympicsMedals.driver.findElement(By.xpath(xpath)).getText();
			columnEntries.add(tdData);

		}
		return columnEntries;
	}

	// takes table id, row count and returns the data in that table header as a
	// String
	public static List<String> readNamesOfCountry(String tableId, int rowCount1) {
		List<String> countryNames = new ArrayList<>();
		for (int rowNum = 1; rowNum <= rowCount1; rowNum++) {
			String xpath = "//table[" + tableId + "]/tbody/tr[" + rowNum + "]/th";

			String thData = OlympicsMedals.driver.findElement(By.xpath(xpath)).getText();
			countryNames.add(thData);

		}
		return countryNames;

	}

	// convert a String list to an Integer list
	public static List<Integer> stringToInteger(List<String> columnEntries) {
		List<Integer> arr = new ArrayList<>();
		for (String str : columnEntries) {
			arr.add(Integer.valueOf(str));
		}

		return arr;
	}

	// returns max number of the specified medal in the table column
	public static int maxNumOfMedal(List<String> list, String medal) {
		List<Integer> in = new ArrayList<>();
		in = stringToInteger(list);
		Integer max = Collections.max(in);
		Integer index = in.indexOf(max);
		String s = readNamesOfCountry("8", 10).get(index);
		System.out.println(s + " has " + max + " " + medal + " medals");
		return max;
	}

	// returns names of countries that received 18 silver medals
	public static List<String> silverMedal18(List<String> ls) {
		List<String> names = new ArrayList<>();

		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i).equals("18")) {
				String str = readNamesOfCountry("8", 10).get(i);
				names.add(str);
				System.out.println(str + " has 18 silver medals");
			}

		}
		return names;
	}

	// returns column and row number of a specified country from a list of countries
	public static int getIndexOfCountries(List<String> list1, String countryName) {

		int index = list1.indexOf(countryName);
		System.out.println("Index of " + countryName + " is (" + (index + 1) + ",2)");
		return (index + 1);

	}

	// returns list of two countries that have a sum of 18 bronze medals
	public static Map<String, String> getSumOfBronze18(List<String> bronzeList) {

		List<Integer> bronzeListInt = new ArrayList<>();
		bronzeListInt = stringToInteger(bronzeList);
		Map<String, String> bronze = new HashMap<>();

		List<String> str = new ArrayList<>();
		str = readNamesOfCountry("8", 10);

		for (int i = 0; i < bronzeList.size() - 1; i++) {
			for (int j = i + 1; j < bronzeList.size(); j++) {
				if (bronzeListInt.get(i) + bronzeListInt.get(j) == 18) {

					bronze.put(str.get(i), str.get(j));

				}
			}

		}
		return bronze;
	}
}
