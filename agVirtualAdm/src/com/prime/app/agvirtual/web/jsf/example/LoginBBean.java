package com.prime.app.agvirtual.web.jsf.example;
//package com.prime.agvirtual.web.jsf.example;
//
//import java.io.Serializable;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.event.ActionEvent;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.prime.agvirtual.entity.Cliente;
//import com.prime.agvirtual.service.LoginException;
//import com.prime.agvirtual.service.LoginService;
//import com.prime.agvirtual.web.jsf.util.FacesBundleUtil;
//import com.prime.infra.web.jsf.BasicBBean;
//import com.icesoft.faces.context.DisposableBean;
//
//@Component
//@Scope("session")
//public class LoginBBean extends BasicBBean implements DisposableBean, Serializable {
//
//    private final Logger logger = LoggerFactory.getLogger(LoginBBean.class);
//    private static final long serialVersionUID = -2033112207050298888L;
//
//    @Autowired
//    private transient LoginService loginService;
//
//    private Cliente cliente = new Cliente();
//    private boolean autorizado;
//    private boolean pagPendente;
//
//    public LoginBBean() {
//        logger.debug("New loginBBean");
//    }
//
//    public String login() {
//        try {
//            execLogin(this.cliente);
//            return getForwardPage();
//        }
//        catch (LoginException e) {
//            String message = FacesBundleUtil.getInstance().getString("abc.not.autorized");
//            addMessage(FacesMessage.SEVERITY_ERROR, message, message);
//            return ERROR;
//        }
//    }
//
//    public void logout(ActionEvent event) {
//        logger.info("Invalidating HttpSession...");
//        HttpSession session = getHttpSession(false);
//        session.invalidate();
//        try {
//            getHttpResponse().sendRedirect("/" + getHttpRequest().getContextPath());
//        }
//        catch (Exception ex) {
//            /* ignored */
//        }
//        logger.info("Bye!");
//    }
//
//    private void execLogin(Cliente cliente) throws LoginException {
//        this.cliente = loginService.login(cliente);
//        logger.debug("Cliente " + cliente + " authenticated successfully.");
//        autorizado = true;
//    }
//
//    public boolean isAutorizado() {
//        logger.debug("Cliente " + cliente + " is authenticated? " + autorizado);
//        return autorizado;
//    }
//
//    public Cliente getCliente() {
//        return cliente;
//    }
//
//    public void setPagPendente(boolean pagPendente) {
//        this.pagPendente = pagPendente;
//    }
//
//    public boolean isPagPendente() {
//        return pagPendente;
//    }
//
//    public String getForwardPage() {
//        boolean pagPendente = isPagPendente();
//        this.pagPendente = false;
//        return (pagPendente ? "Pagamento" : SUCCESS);
//    }
//
//    public void checkCliente(Cliente cliente) throws LoginException {
//        execLogin(cliente);
//        this.cliente = cliente;
//    }
//
//    public void dispose() throws Exception {
//        logger.debug(LoginBBean.class + " disposing...");
//        cliente = null;
//    }
//}
