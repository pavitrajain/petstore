package io.swagger.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestDataUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(TestDataUtil.class);
    private static final String testDataFileLocation = System.getProperty("user.dir") + "/src/test/resources/testData/petDetails.xlsx";
    private static File file;
    private static XSSFSheet workSheet;
    private static  XSSFWorkbook workBook;

    /**
     * Method to load excel test data file
     */
    private static void loadTestDataFile() {
        try {
            file = new File(testDataFileLocation);
            FileInputStream fis = new FileInputStream(file);
            workBook = new XSSFWorkbook(fis);
            workSheet = workBook.getSheet("pet");
            fis.close();
        } catch (IOException e) {
            APILogger.logInfo(LOGGER,"Unable to read test data from file available at: " + testDataFileLocation);
            e.printStackTrace();
        }
    }

    /**
     * Method to get first pet id from excel test data file which is not yet removed
     * @return - pet id which is not removed or null
     */
    public static String getPet() {
        loadTestDataFile();

        try {
            APILogger.logInfo(LOGGER,"Reading data from sheet: pet");
            int rowCount;
            int row = workSheet.getLastRowNum();
            Row data;
            String value, flag;
            for(rowCount = 1; rowCount<=row; rowCount++) {
                data = workSheet.getRow(rowCount);
                value = CellUtil.getCell(data,0).getStringCellValue();
                flag = CellUtil.getCell(data,1).getStringCellValue();
                if(!(flag.equalsIgnoreCase("Yes") || flag.equalsIgnoreCase("Y"))) {
                    return value;
                }
            }
            return null;
        } catch (NullPointerException e) {
            APILogger.logInfo(LOGGER,"Sheet: pet is not available in test data file present at: " + testDataFileLocation);
            return null;
        }
    }

    /**
     * Method to update removed flag for a pet id in the excel test data file
     * @param petId - pet id
     */
    public static void updateRemovedFlag(String petId) {
        loadTestDataFile();

        Row data;
        int rowCount, row = workSheet.getLastRowNum();
        String pet;

        LOGGER.debug("Total row count: " + row);
        for(rowCount = 1; rowCount <= row; rowCount++) {
            data = workSheet.getRow(rowCount);
            pet = CellUtil.getCell(data, 0).getStringCellValue();
            if(pet.equals(petId)) {
                CellUtil.getCell(data,1).setCellValue("Yes");
                break;
            }
        }

       try {
           FileOutputStream fout = new FileOutputStream(file);
           LOGGER.debug("Updating test data in the sheet: pet");
           workBook.write(fout);
           fout.close();
        } catch (IOException e) {
           APILogger.logInfo(LOGGER,"Unable to update test data in the sheet: pet");
            e.printStackTrace();
        }
    }

    /**
     * Method to add pet id in the excel test data file
     * @param petId - pet id
     */
    public static void addPetId(String petId) {
        loadTestDataFile();

        int row = workSheet.getLastRowNum();
        LOGGER.debug("Total row count: " + row);
        Row data = workSheet.createRow(row + 1);
        data.createCell(0).setCellValue(petId);

        try {
            FileOutputStream fout = new FileOutputStream(file);
            LOGGER.debug("Updating test data in the sheet: pet");
            workBook.write(fout);
            fout.close();
        } catch (IOException e) {
            APILogger.logInfo(LOGGER,"Unable to update test data in the sheet: pet");
            e.printStackTrace();
        }
    }
}