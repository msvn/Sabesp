package com.prime.app.agvirtual.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.web.filter.GenericFilterBean;

@Repository
public class SecurityFilter extends GenericFilterBean {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
        ServletException {
        if (isAutorizado(req)) {
            if (!isValid(req)) {
                toHttpResponse(res).sendRedirect(getContext(req).append("/pages/sistema/main.iface").toString());
                return;
            }
        }
        else {
            if (isUriSecure(req)) {
                toHttpResponse(res).sendRedirect(getContext(req).append("/pages/login.iface").toString());
                return;
            }
        }
        chain.doFilter(req, res);
    }

    protected boolean isAutorizado(ServletRequest req) {
        HttpSession session = toHttpRequest(req).getSession();
        if (session != null) {
        	   //TODO verificar
//            LoginBBean loginBBean = (LoginBBean) session.getAttribute("loginBBean");
//            return (loginBBean != null ? loginBBean.isAutorizado() : false);
        }
        return false;
    }

    protected boolean isValid(ServletRequest req) {
        String uri = toHttpRequest(req).getRequestURI();
        return (uri.indexOf("login") < 0 && uri.indexOf("clienteForm") < 0 ? true : false);
    }

    protected boolean isUriSecure(ServletRequest req) {
        String uri = toHttpRequest(req).getRequestURI();
        return (uri.indexOf("sistema") == -1 ? false : true);
    }

    protected StringBuilder getContext(ServletRequest req) {
        return new StringBuilder().append(toHttpRequest(req).getContextPath());
    }

    protected HttpServletRequest toHttpRequest(ServletRequest req) {
        return (HttpServletRequest) req;
    }

    protected HttpServletResponse toHttpResponse(ServletResponse res) {
        return (HttpServletResponse) res;
    }

}
