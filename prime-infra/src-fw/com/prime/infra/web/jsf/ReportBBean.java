 package com.prime.infra.web.jsf;
 
 import com.prime.infra.report.ExportOption;
 import com.prime.infra.report.ReportConfigUtil;
 import com.prime.infra.web.jsf.component.ExportOptionSelectItem;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.faces.application.FacesMessage;
 import javax.faces.context.ExternalContext;
 import javax.faces.context.FacesContext;
 import javax.faces.model.SelectItem;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import net.sf.jasperreports.engine.JRDataSource;
 import net.sf.jasperreports.engine.JRException;
 import net.sf.jasperreports.engine.JasperPrint;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public abstract class ReportBBean extends BasicBBean
 {
/*  37 */   public List<SelectItem> listExportOptions = new ArrayList();
   private Logger logger;
   private ExportOption exportOption;
 
   public ReportBBean()
   {
/*  40 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.PDF));
/*  41 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.RTF));
/*  42 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.EXCEL));
/*  43 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.ODT));
/*  44 */     this.listExportOptions.add(new ExportOptionSelectItem(ExportOption.XML));
 
/*  47 */     this.logger = LoggerFactory.getLogger(ReportBBean.class);
/*  48 */     this.exportOption = ExportOption.PDF;
   }
 
   public ExportOption getExportOption()
   {
/*  54 */     return this.exportOption;
   }
 
   public void setExportOption(ExportOption exportOption) {
/*  58 */     this.exportOption = exportOption;
   }
 
   public String render()
   {
     try
     {
/*  68 */       this.logger.debug("Preparing report....");
/*  69 */       prepareReport();
/*  70 */       if (this.logger.isDebugEnabled()) {
/*  71 */         this.logger.debug("Report prepared successful! Redirect to servlet type " + getExportOption());
       }
/*  73 */       return getExportOption().toString();
     }
     catch (Exception e) {
/*  76 */       this.logger.error("Could not render report using " + getReportParameters() + " and export option " + getExportOption(), e);
 
/*  78 */       addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()); }
/*  79 */     return null;
   }
 
   protected void prepareReport()
     throws JRException, IOException
   {
/*  90 */     ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
 
/*  92 */     HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
 
/*  94 */     JasperPrint jasperPrint = ReportConfigUtil.fillReport(getReportStream(), getReportParameters(), getJRDataSource());
 
/*  97 */     request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
   }
 
   public List<SelectItem> getListExportOptions()
   {
/* 104 */     return this.listExportOptions;
   }
 
   protected abstract Map<String, Object> getReportParameters();
 
   protected abstract JRDataSource getJRDataSource();
 
   protected abstract InputStream getReportStream();
 }