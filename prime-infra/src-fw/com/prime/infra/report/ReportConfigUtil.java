 package com.prime.infra.report;
 
 import java.io.InputStream;
 import java.io.PrintWriter;
 import java.util.Map;
 import javax.servlet.ServletContext;
 import net.sf.jasperreports.engine.JRAbstractExporter;
 import net.sf.jasperreports.engine.JRDataSource;
 import net.sf.jasperreports.engine.JRException;
 import net.sf.jasperreports.engine.JRExporterParameter;
 import net.sf.jasperreports.engine.JasperFillManager;
 import net.sf.jasperreports.engine.JasperPrint;
 import net.sf.jasperreports.engine.export.JRHtmlExporter;
 import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
 
 public class ReportConfigUtil
 {
   public static JasperPrint fillReport(InputStream reportFile, Map<String, Object> parameters, JRDataSource jrDataSource)
     throws JRException
   {
/* 22 */     if ((true) && (reportFile == null)) throw new AssertionError("reportFile cannot be null");
/* 23 */     if ((true) && (jrDataSource == null)) throw new AssertionError("jrDataSource cannot be null");
 
/* 25 */     return JasperFillManager.fillReport(reportFile, parameters, jrDataSource);
   }
 
   public static String getJasperFilePath(ServletContext context, String compileDir, String jasperFile)
   {
/* 30 */     if ((true) && (context == null)) throw new AssertionError("context cannot be null");
/* 31 */     if ((true) && (compileDir == null)) throw new AssertionError("compileDir cannot be null");
/* 32 */     if ((true) && (jasperFile == null)) throw new AssertionError("jasperFile cannot be null");
 
/* 34 */     return context.getRealPath(compileDir + jasperFile);
   }
 
   private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, PrintWriter out) throws JRException
   {
/* 39 */     if ((true) && (exporter == null)) throw new AssertionError("exporter cannot be null");
/* 40 */     if ((true) && (jasperPrint == null)) throw new AssertionError("jasperPrint cannot be null");
/* 41 */     if ((true) && (out == null)) throw new AssertionError("out cannot be null");
 
/* 43 */     exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
/* 44 */     exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
 
/* 46 */     exporter.exportReport();
   }
 
   public static void exportReportAsHtml(JasperPrint jasperPrint, PrintWriter out) throws JRException
   {
/* 51 */     if ((true) && (jasperPrint == null)) throw new AssertionError("jasperPrint cannot be null");
/* 52 */     if ((true) && (out == null)) throw new AssertionError("out cannot be null");
 
/* 54 */     JRHtmlExporter exporter = new JRHtmlExporter();
/* 55 */     exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
 
/* 57 */     exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
 
/* 60 */     exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
 
/* 63 */     exportReport(exporter, jasperPrint, out);
   }
 }