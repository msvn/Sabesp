 package com.prime.infra.web.image;
 
 import java.io.IOException;
 import java.io.OutputStream;
 import java.util.AbstractMap;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.collections.map.LRUMap;
 import org.apache.commons.io.IOUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.web.context.WebApplicationContext;
 import org.springframework.web.context.support.WebApplicationContextUtils;
 
 public class ImageServlet extends HttpServlet
 {
   private Logger logger;
   private WebApplicationContext appCtx;
   protected AbstractMap cachedImages;
   public static final String IMAGE_PARAM = "im";
   public static final String UNIQUE_ID = "uniqueId";
   public static final String MIME_TYPE = "mimeType";
   public static final String JPG_MIME = "image/jpg";
   public static final String PNG_MIME = "image/png";
   public static final String GIF_MIME = "image/gif";
   public static final String MAX_CACHED_IMAGES = "com.prime.infra.web.image.MaxCachedImages";
   private static final int DEFAULT_MAX_CACHED = 5;
 
   public ImageServlet()
   {
/*  31 */     this.logger = LoggerFactory.getLogger(ImageServlet.class);
   }
 
   public void init(ServletConfig config)
     throws ServletException
   {
/*  49 */     super.init(config);
/*  50 */     if (this.logger.isDebugEnabled()) {
/*  51 */       this.logger.debug("Initialize ImageServlet");
     }
/*  53 */     this.cachedImages = new LRUMap(getMaxCachedImages(), true);
/*  54 */     this.appCtx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
   }
 
   protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException
   {
/*  59 */     super.service(arg0, arg1);
   }
 
   protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException
   {
/*  65 */     doPost(req, resp);
   }
 
   protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException
   {
/*  73 */     if (this.logger.isDebugEnabled()) {
/*  74 */       this.logger.debug("Retrieving image...");
     }
/*  76 */     OutputStream out = resp.getOutputStream();
     try {
/*  78 */       String mimeType = req.getParameter("mimeType");
/*  79 */       resp.setContentType((mimeType != null) ? mimeType : "image/jpg");
 
/*  81 */       String imageClass = req.getParameter("im");
/*  82 */       String uniqueId = req.getParameter("uniqueId");
/*  83 */       ImageSupport imageSupport = getImageClass(req, imageClass);
 
/*  85 */       String mapKey = imageClass + "/" + uniqueId;
 
/*  87 */       byte[] content = (byte[])(byte[])this.cachedImages.get(mapKey);
 
/*  89 */       if (content == null) {
/*  90 */         content = imageSupport.loadImage(uniqueId);
/*  91 */         this.cachedImages.put(mapKey, content);
       }
 
/*  94 */       resp.setContentLength(content.length);
/*  95 */       IOUtils.write(content, out);
     }
     finally {
/*  98 */       out.flush();
/*  99 */       out.close();
     }
/* 101 */     if (this.logger.isDebugEnabled())
/* 102 */       this.logger.debug("Image Retrieve Finished.");
   }
 
   protected ImageSupport getImageClass(HttpServletRequest req, String param)
   {
/* 107 */     if (param == null) {
/* 108 */       throw new IllegalArgumentException("Param not well defined");
     }
 
/* 111 */     Object paramObj = this.appCtx.getBean(param);
 
/* 113 */     if (paramObj instanceof ImageSupport) {
/* 114 */       return ((ImageSupport)paramObj);
     }
 
/* 117 */     throw new IllegalArgumentException("Param is not compatible.");
   }
 
   protected int getMaxCachedImages() {
/* 121 */     String maxCached = getInitParameter("com.prime.infra.web.image.MaxCachedImages");
/* 122 */     int value = (maxCached != null) ? Integer.valueOf(maxCached).intValue() : 5;
/* 123 */     this.logger.debug("Configuring max cache to {}", Integer.valueOf(value));
/* 124 */     return value;
   }
 }