package com.example.density_check_web_service;

import com.example.density_check_web_service.config.ExcelColumnName;
import com.example.density_check_web_service.domain.Situations.dto.SituationsDto;
import com.example.density_check_web_service.domain.Situations.dto.SituationsResponseDto;
import com.example.density_check_web_service.service.SituationsService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class SituationsController {
    private final SituationsService situationsService;

    @ResponseBody
    @GetMapping("/situations")
    public List<SituationsResponseDto> findAll()
    {
        return situationsService.findAll();
    }

    @GetMapping("/situations/{loc}")
    public String findAllByLoc(@PathVariable int loc, RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("graphData", situationsService.findAllByLoc(loc));
        return "redirect:/situation";
    }

    @GetMapping("/poiExcel")
    public void poiTest(HttpServletResponse response) throws Exception{

        Workbook wb = new SXSSFWorkbook(); // 엑셀파일 객체 생성
        String[] loc = {"A", "B", "C", "D"};
        for (int i=0; i<loc.length; i++) {
            Sheet sheet = wb.createSheet(loc[i]); //시트 생성
            sheet.setDefaultColumnWidth(22);   // 디폴트 너비 설정
//            sheet.setDefaultRowHeight((short) 500);     // 디폴트 높이 설정

            CellStyle headerCellStyle = wb.createCellStyle(); // 데이터스타일은 테두리를 만들어보자 // 셀 스타일 생성
            Font font = wb.createFont(); // 폰트 스타일 생성

            font.setBold(true); // 글자 진하게
            font.setFontHeight((short) (14 * 16)); // 글자 크기
//            font.setFontName("맑은 고딕"); // 글씨체

//            headerCellStyle.setWrapText(true); //문자열을 입력할때 \n 같은 개행을 인식해준다.
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 수직 가운데 정렬
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER); // 수평 가운데 정렬
            headerCellStyle.setFont(font); // 스타일에 font 스타일 적용하기

            headerCellStyle.setBorderRight(BorderStyle.MEDIUM); //오른쪽 테두리
            headerCellStyle.setBorderLeft(BorderStyle.MEDIUM); //왼쪽 테두리
            headerCellStyle.setBorderTop(BorderStyle.MEDIUM); // 상단 테두리
            headerCellStyle.setBorderBottom(BorderStyle.MEDIUM); // 하단 테두리

            headerCellStyle.setFillForegroundColor((short) 102);
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            //헤더 만들기
            Row headerRow = sheet.createRow(0);
            int headerCol = 0;
            List<String> headerName = Arrays.stream(SituationsDto.class.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(ExcelColumnName.class))
                    .map(field -> field.getAnnotation(ExcelColumnName.class).name())
                    .collect(Collectors.toList());
            for (String h : headerName) {
                Cell headerCell = headerRow.createCell(headerCol++);
                headerCell.setCellValue(h);
                headerCell.setCellStyle(headerCellStyle);
            }

            //데이터 삽입하기
            int rowNum = 1;
            Row dataRow = null; // for문을 돌려주기위해.
            Cell dataCell = null;

            List<SituationsDto> datalist = situationsService.findAllByLocForExcel(i);

            for (Object d: datalist) {
                dataRow = sheet.createRow(rowNum++);

                int j = 0;
                for (Field field : SituationsDto.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    dataCell = dataRow.createCell(j++);
                    if(j < 4) {
                        dataCell.setCellValue(Integer.parseInt(String.valueOf(field.get(d))));
                    } else {
                        dataCell.setCellValue(String.valueOf(field.get(d)));
                    }
                }
            }
        }

        /* 엑셀 파일 생성 */
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=log.xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.close();
    }
}
