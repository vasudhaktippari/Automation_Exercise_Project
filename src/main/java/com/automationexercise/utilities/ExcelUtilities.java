package com.automationexercise.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {

    private static final String EXCEL_PATH =
        Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "Testdata", "dynamicdata.xlsx")
             .toString();

    public static Object[][] getData(String sheetName) throws IOException {
        // default: skip blank rows
        return getData(sheetName, /*preserveBlankRows=*/false);
    }

    /**
     * Reads a sheet into Object[][] using:
     *  - Effective column count based on non-blank header cells (row 0)
     *  - Effective last row = last row (from bottom) with any non-blank cell in effective columns
     *  - Optionally preserves blank rows in the middle if preserveBlankRows = true
     */
    public static Object[][] getData(String sheetName, boolean preserveBlankRows) throws IOException {
        try (FileInputStream fs = new FileInputStream(EXCEL_PATH);
             XSSFWorkbook wb = new XSSFWorkbook(fs)) {

            XSSFSheet sheet = wb.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            DataFormatter fmt = new DataFormatter();

            Row header = sheet.getRow(0);
            if (header == null) return new Object[0][0];

            // 1) Effective column count from non-blank header cells
            int lastHeaderCol = header.getLastCellNum();
            int effectiveCols = 0;
            for (int c = 0; c < lastHeaderCol; c++) {
                String hv = fmt.formatCellValue(header.getCell(c)).trim();
                if (!hv.isEmpty()) effectiveCols = c + 1;
            }
            if (effectiveCols == 0) return new Object[0][0];

            // 2) Find effective last row: scan bottom-up for any non-blank cell within effectiveCols
            int physicalLast = sheet.getLastRowNum();
            int effectiveLastRow = 0; // at least header row
            for (int r = physicalLast; r >= 1; r--) { // start from last, skip header at r=0
                Row row = sheet.getRow(r);
                if (row == null) continue;
                if (rowHasAnyValue(row, effectiveCols, fmt)) {
                    effectiveLastRow = r;
                    break;
                }
            }
            if (effectiveLastRow < 1) return new Object[0][0]; // no data rows

            // 3) Collect rows 1..effectiveLastRow
            List<Object[]> out = new ArrayList<>();
            for (int r = 1; r <= effectiveLastRow; r++) {
                Row row = sheet.getRow(r);
                String[] values = new String[effectiveCols];

                boolean anyValue = false;
                for (int c = 0; c < effectiveCols; c++) {
                    Cell cell = (row == null) ? null : row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    String val = fmt.formatCellValue(cell).trim();
                    if (!val.isEmpty()) anyValue = true;
                    values[c] = val;
                }

                if (preserveBlankRows) {
                    // keep the row (even if empty within effective columns)
                    out.add(values);
                } else {
                    // skip fully blank rows
                    if (anyValue) out.add(values);
                }
            }

            Object[][] data = new Object[out.size()][effectiveCols];
            for (int i = 0; i < out.size(); i++) data[i] = out.get(i);
            return data;
        }
    }

    private static boolean rowHasAnyValue(Row row, int effectiveCols, DataFormatter fmt) {
        if (row == null) return false;
        for (int c = 0; c < effectiveCols; c++) {
            Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (!fmt.formatCellValue(cell).trim().isEmpty()) return true;
        }
        return false;
        }
}
