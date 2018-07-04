package com.olympics;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OlympicsMedals {

	public static WebDriver driver;

	@BeforeClass // runs once for all tests
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
	}

	@Test(priority = 1)
	public void sortedByRank() throws InterruptedException {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		Thread.sleep(3000);
		List<String> rankList = new ArrayList<>();
		rankList = Utility.readTableColumnData("8", 1, 10);
		System.out.println(rankList);

		Set<Integer> rank = new HashSet<>();
		for (String str : rankList) {
			rank.add(Integer.valueOf(str));
		}
		SortedSet<Integer> sortedRank = new TreeSet<>(rank);
		System.out.println(sortedRank);
		Assert.assertTrue(rank.equals(sortedRank));

	}

	@Test(priority = 2)
	public void sortedByNames() throws InterruptedException {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']//th[2]"))
				.click();
		List<String> nameList = new ArrayList<>();

		nameList = Utility.readNamesOfCountry("8", 10);
		System.out.println(nameList);

		Set<String> noc = new HashSet<>(nameList);
		SortedSet<String> sortedNoc = new TreeSet<>(noc);
		Assert.assertTrue(noc.equals(sortedNoc));

	}

	@Test(priority = 3)
	public void notAscend() throws InterruptedException {
		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		Thread.sleep(3000);
		List<String> rankList2 = new ArrayList<>();
		rankList2 = Utility.readTableColumnData("8", 1, 10);
		List<Integer> rank = new ArrayList<>();
		rank = Utility.stringToInteger(rankList2);
		SortedSet<Integer> sortedRank2 = new TreeSet<>(rank);
		Assert.assertFalse(rank==sortedRank2);

	}

	@Test(priority = 4)
	public void theMost() throws InterruptedException {

		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		Thread.sleep(3000);
		List<String> gold = new ArrayList<>();
		gold = Utility.readTableColumnData("8", 2, 10);
		int maxGold = Utility.maxNumOfMedal(gold, "gold");
		Assert.assertEquals(maxGold, 46);

		List<String> silver = new ArrayList<>();
		silver = Utility.readTableColumnData("8", 3, 10);
		int maxSilver =Utility.maxNumOfMedal(silver, "silver");
		Assert.assertEquals(maxSilver, 37);

		List<String> bronze = new ArrayList<>();
		bronze = Utility.readTableColumnData("8", 4, 10);
		int maxBronze = Utility.maxNumOfMedal(bronze, "bronze");
		Assert.assertEquals(maxBronze, 38);

		List<String> total = new ArrayList<>();
		total = Utility.readTableColumnData("8", 5, 10);
		int maxTotal =Utility.maxNumOfMedal(total, "total");
		Assert.assertEquals(maxTotal, 121);
	}
	
	@Test(priority = 5)
	public void siverMedal18() {

		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		List<String> silverMedals = new ArrayList<>();
		silverMedals = Utility.readTableColumnData("8", 3, 10);
		List <String> silver18 = new ArrayList<>();
		silver18 =Utility.silverMedal18(silverMedals);
		List <String> expected = new ArrayList<>();
		expected.add(" China (CHN)");
		expected.add(" France (FRA)");
		Assert.assertEquals(silver18,expected );
	}
	
	
	@Test(priority = 6)
	public void getIndex() {

		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		List<String> countryNames = new ArrayList<>();
		countryNames = Utility.readNamesOfCountry("8", 10);
		int indexOfJapan = Utility.getIndexOfCountries(countryNames, " Japan (JPN)");
		Assert.assertEquals(indexOfJapan, 7);
	}
	
	
	
	@Test(priority = 7)
	public void sumOfBronze()  {

		driver.get("https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table");
		List<String> bronze = new ArrayList<>();
		bronze = Utility.readTableColumnData("8", 4, 10);
		Map <String,String> totalBronze = Utility.getSumOfBronze18(bronze);
		
		Map<String,String> expected = new HashMap<>();
		expected.put(" Australia (AUS)", " Italy (ITA)");
		System.out.println(totalBronze);
		Assert.assertEquals(totalBronze, expected);
		
		driver.close();
	}
	
	
	
	
}
