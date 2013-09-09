 package com.prime.infra.web.jsf;
 
 import com.prime.infra.report.ExportOption;
 import com.prime.infra.report.ReportConfigUtil;
 import com.prime.infra.web.jsf.component.ExportOptionSelectItem;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.faces.context.ExternalContext;
 import javax.faces.context.FacesContext;
 import javax.faces.model.SelectItem;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import net.sf.jasperreports.engine.JRDataSource;
 import net.sf.jasperreports.engine.JRException;
 import net.sf.jasperreports.engine.JasperPrint;
 
 public abstract class ReportCrudServiceBBean<T, ID extends Serializable> extends CrudServiceBBean<T, ID>
   implements Serializable
 {
/*  37 */   public List<SelectItem> listExportOptions = new ArrayList();
   private ExportOption exportOption;
 
   public ReportCrudServiceBBean()
   {
/*  39 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.PDF));
/*  40 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.RTF));
/*  41 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.EXCEL));
/*  42 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.ODT));
/*  43 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.XML));
 
/*  46 */     this.exportOption = ExportOption.PDF;
   }
 
   public ExportOption getExportOption()
   {
/*  52 */     return this.exportOption;
   }
 
   public void setExportOption(ExportOption exportOption) {
/*  56 */     this.exportOption = exportOption;
   }
 
   public String render()
   {
     try
     {
/*  66 */       prepareReport();
/*  67 */       return getExportOption().toString();
     }
     catch (JRException e) {
/*  70 */       e.printStackTrace();
/*  71 */       return null;
     }
     catch (IOException e) {
/*  74 */       e.printStackTrace(); }
/*  75 */     return null;
   }
 
   protected void prepareReport()
     throws JRException, IOException
   {
/*  86 */     ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
 
/*  88 */     HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
 
/*  90 */     JasperPrint jasperPrint = ReportConfigUtil.fillReport(getReportStream(), getReportParameters(), getJRDataSource());
 
/*  93 */     request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
   }
 
   public List<SelectItem> getListExportOptions()
   {
/* 100 */     return this.listExportOptions;
   }
 
   protected abstract Map<String, Object> getReportParameters();
 
   protected abstract JRDataSource getJRDataSource();
 
   protected abstract InputStream getReportStream();
 }