package org.dep.example.pages;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefectlessDummyPage extends AbstractPage {

    private static Date TIMESTAMP_WHEN_FILE_WAS_DOWNLOADED;

    public DefectlessDummyPage(WebDriver driver) {
        super(driver);
    }

    public static Date getTimestampWhenFileWasDownloaded() {
        return TIMESTAMP_WHEN_FILE_WAS_DOWNLOADED;
    }

    public static void setTimestampWhenFileWasDownloaded(Date timestampWhenFileWasDownloaded) {
        TIMESTAMP_WHEN_FILE_WAS_DOWNLOADED = timestampWhenFileWasDownloaded;
    }

    public void open() {
        driver.get("C:\\Users\\matei\\Google Drive\\DEFECTLESS\\How to validate downloaded file name when it contains timestamp\\index.html");
    }

    public void checkMap(Map<Object, Object> map) {
        System.out.println(map.get("A"));
    }

    public void clickOnDownloadButton() {
        driver.findElement(By.cssSelector("#dwn-btn")).click();
        Date currentDate = new Date();
        setTimestampWhenFileWasDownloaded(currentDate);
    }

    public void checkThatTheFileHasBeenDownloadedToTheDefaultFolder(String expectedUserName, String expectedBeginningOfFileName, String expectedEndOfFileName) {
        //wait a little because if we dont wait, the test fails because the code runs faster than windows can manage downloaded files
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //get most recent file name
        String actualFileName = getMostRecentFileName();

        //get beginning of file name by regex
        Pattern beginningOfFileNamePattern = Pattern.compile("[^_]+");
        Matcher beginningOfFileNameMatcher = beginningOfFileNamePattern.matcher(actualFileName);
        if (beginningOfFileNameMatcher.find()) {
            beginningOfFileNameMatcher.group(0);
        }
        String actualBeginningOfFileName = beginningOfFileNameMatcher.group(0);
        System.out.println("Actual beginning of file name by regex: " + actualBeginningOfFileName);

        //get username and file format (getting everything after the last underscore)
        String actualEndOfFileName = actualFileName.substring(actualFileName.lastIndexOf("_") + 1).trim();
        System.out.println("Actual end of file name by substring method: " + actualEndOfFileName);

        //get date from file name with regex
        Pattern dateFromFileNamePattern = Pattern.compile("_(\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])_(?:[01]\\d|2[0123])\\-(?:[012345]\\d)\\-(?:[012345]\\d))_");
        Matcher dateFromFileNameMatcher = dateFromFileNamePattern.matcher(actualFileName);
        if (dateFromFileNameMatcher.find()) {
            dateFromFileNameMatcher.group(1);
        }
        //save date from file name as String
        String dateInActualFileNameAsString = dateFromFileNameMatcher.group(1);
        //convert date (from file name) to Date so that I can use it in isWithinRange method
        Date dateInActualFileNameAsDate = null;
        try {
            dateInActualFileNameAsDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").parse(dateInActualFileNameAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //compare beginning of file name
        Assert.assertEquals(actualBeginningOfFileName, expectedBeginningOfFileName);
        //compare end of file name
        Assert.assertEquals(actualEndOfFileName, expectedUserName + expectedEndOfFileName);
        //compare the date
        Assert.assertTrue(isWithinRange(dateInActualFileNameAsDate));
    }

    private boolean isWithinRange(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DefectlessDummyPage.getTimestampWhenFileWasDownloaded());
        //perform addition/subtraction
        calendar.add(Calendar.SECOND, -10);
        //convert calendar back to Date
        Date beforeDate = calendar.getTime();
        System.out.println("Before date is (isWithinRange method): "+beforeDate);
        System.out.println("Date in the actual file name is(isWithinRange method): "+date);
        System.out.println("A little after than the file was downloaded (isWithinRange method): "+DefectlessDummyPage.getTimestampWhenFileWasDownloaded());
        return !(date.before(beforeDate) || date.after(DefectlessDummyPage.getTimestampWhenFileWasDownloaded()));
    }

    private String getMostRecentFileName() {
        String fileName = "";
        try {
            File file = new File(System.getProperty("user.home") + "/Downloads");
            File[] list = file.listFiles((FileFilter) FileFileFilter.FILE);
            Arrays.sort(list, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            fileName = list[0].getName();
            System.out.println("Filename is: " + list[0].getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
