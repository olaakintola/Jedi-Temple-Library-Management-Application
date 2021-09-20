package ie.ucd.archive.testSet;

import ie.ucd.archive.model.Media;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class MediaReader {

    private ArrayList<Media> media;

    public void readXLSX(int mediaCount, String filePath) {
        //System.out.println("In readXLSX");
        media = new ArrayList<>(mediaCount);
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            ArrayList<Integer> relevantRowNumbers = getRandomRowNumbers(mediaCount, rowCount);
            //System.out.println("File input obtained, random rows:" + Arrays.toString(relevantRowNumbers));
            for (int i = 0; i < mediaCount; ++i) {
                Row row = sheet.getRow(relevantRowNumbers.get(i));
                Media newMedia = parseRowIntoMedia(row);
                if (newMedia == null) {
                    relevantRowNumbers.remove(i);
                    --i;
                    relevantRowNumbers.add(getRandomNumber(rowCount));
                }
                else {
                    media.add(newMedia);
                }
                //System.out.println("Forming a new media from row:" + rowNumber + " " + newMedia.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> getRandomRowNumbers(int rowsWanted, int rowsProvided) {
        ArrayList<Integer> randomRowNumbers = new ArrayList<>(rowsWanted);
        Random rand = new Random();
        //System.out.println("In getRandomRows,rand:" + rand.nextInt(rowsProvided) + " and rows wanted/provided:" + rowsWanted + " " + rowsProvided);
        for(int i = 0; i < rowsWanted; ++i) {
            int rowNum = rand.nextInt(rowsProvided);
            if (rowNum == 0) ++rowNum;
            randomRowNumbers.add(rowNum);
        }
        return randomRowNumbers;
    }

    private int getRandomNumber(int range) {
        Random rand = new Random();
        return rand.nextInt(range);
    }

    private Media parseRowIntoMedia(Row row) {
        //System.out.println("parsing row");
        Media newMedia = new Media();
        Cell currentCell;

        String cellString0;
        currentCell = row.getCell(0);
        if(currentCell == null) cellString0 = "";
        else cellString0 = currentCell.getStringCellValue();
        //System.out.println(cellString0);
        newMedia.setTitle(cellString0);

        String cellString1;
        currentCell = row.getCell(1);
        if(currentCell == null) cellString1 = "";
        else cellString1 = currentCell.getStringCellValue();
        //System.out.println(cellString1);

        String cellString2;
        currentCell = row.getCell(2);
        if(currentCell == null) cellString2 = "";
        else cellString2 = currentCell.getStringCellValue();
        //System.out.println(cellString2);
        newMedia.setMediatype(cellString2);

        String cellString3;
        currentCell = row.getCell(3);
        if(currentCell == null) cellString3 = "";
        else cellString3 = currentCell.getStringCellValue();
        //System.out.println(cellString3);

        newMedia.setIs_child_suitable(generateRandomChildRating());
        newMedia.setMediatext(generateRandomMediaText(cellString3, cellString1));

        if(!mediaHasNullAttributes(cellString0, cellString2)) {
            return newMedia;
        }
        return null;
    }

    private boolean generateRandomChildRating() {
        Random rand = new Random();
        return rand.nextInt() % 2 == 0;
    }

    private boolean mediaHasNullAttributes(String attr0, String attr1) {
        return attr0.equals("") || attr1.equals("");
    }

    private String generateRandomMediaText(String author, String subject) {
        String mediaText = author;
        String[] links = {"'s introduction to ","'s analysis of ","'s guide to ","'s breakdown of ","'s research on ","'s course on ", "'s findings on the topic of ", ": foundations of ", "'s discussion on ", "'s essentials of "};
        Random rand = new Random();
        mediaText += links[rand.nextInt(9)];
        mediaText += subject;
        if(author.isEmpty() || subject.isEmpty()) mediaText = "No Description Available";
        return mediaText;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }
}
