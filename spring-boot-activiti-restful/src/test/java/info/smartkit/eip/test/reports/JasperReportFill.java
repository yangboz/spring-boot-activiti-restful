package info.smartkit.eip.test.reports;

/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * All rights reserved.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import info.smartkit.eip.models.DataBeanList;

/**
 * Application entry point
 * 
 * @author yangboz
 */
public class JasperReportFill
{
    public static String getClassPath()
    {
        return "/Users/yangboz/Documents/Git/north-american-adventure/RushuMicroService/eip-rushucloud/src/main/resources/";
    }

    private final static String JASPER_REPORT_BASE = getClassPath() + "reports/jasper_report_template";

    private final static String JRXML_SOURCE_FILE = JASPER_REPORT_BASE + ".jrxml";

    private final static String JASPER_DEST_FILE = JASPER_REPORT_BASE + ".jasper";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws JRException
    {
        // JasperCompileManager.compileReportToFile(JRXML_SOURCE_FILE, JASPER_DEST_FILE);
        String jasperDestFile = JasperCompileManager.compileReportToFile(JRXML_SOURCE_FILE);
        //
        String printFileName = null;
        DataBeanList DataBeanList = new DataBeanList();
        ArrayList dataList = DataBeanList.getDataBeanList();
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList, true);

        Map parameters = new HashMap();
        long start = System.currentTimeMillis();
        try {
            printFileName = JasperFillManager.fillReportToFile(JASPER_DEST_FILE, parameters, beanColDataSource);
            if (printFileName != null) {
                /**
                 * 1- export to PDF
                 */
                JasperExportManager.exportReportToPdfFile(printFileName, JASPER_REPORT_BASE + ".pdf");

                /**
                 * 2- export to HTML
                 */
                JasperExportManager.exportReportToHtmlFile(printFileName, JASPER_REPORT_BASE + ".html");

                /**
                 * 3- export to Excel sheet
                 */
                JRXlsExporter exporter = new JRXlsExporter();

                exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, printFileName);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, JASPER_REPORT_BASE + ".xls");

                exporter.exportReport();
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
        System.err.println("JasperReport Filling time : " + (System.currentTimeMillis() - start) + printFileName);
    }
}
