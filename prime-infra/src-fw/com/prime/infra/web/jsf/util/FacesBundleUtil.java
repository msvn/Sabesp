package com.prime.infra.web.jsf.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

public class FacesBundleUtil {
    private final ResourceBundle bundle;
    private static final FacesBundleUtil INSTANCE = new FacesBundleUtil();

    private FacesBundleUtil() {
        Locale defaultLocale = getDefaultLocale();
        bundle = ResourceBundle.getBundle(getBaseName(), defaultLocale);
    }

    public static FacesBundleUtil getInstance() {
        return INSTANCE;
    }

    protected Locale getDefaultLocale() {
        return FacesContext.getCurrentInstance().getApplication()
            .getDefaultLocale();
    }

    protected String getBaseName() {
        return FacesContext.getCurrentInstance().getApplication()
            .getMessageBundle();
    }

    public String getString(String key, Object... args) {
        return MessageFormat.format(bundle.getString(key), args);
    } 
}
