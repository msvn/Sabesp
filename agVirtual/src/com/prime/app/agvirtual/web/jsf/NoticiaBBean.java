package com.prime.app.agvirtual.web.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.service.NoticiaService;
import com.prime.app.agvirtual.to.NoticiaTO;
import com.prime.infra.web.jsf.BasicBBean;

/**
 * BackBean com controles da funcinalidade Conteúdo Página Inicial.
 */
@Component
@Scope(value="session")
public class NoticiaBBean extends BasicBBean {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6603620243632318153L;

	/**
	 * Log.
	 */
	private static final Logger agvlogger = LoggerFactory.getLogger(NoticiaBBean.class);
	
	@Autowired
    private NoticiaService noticiaService;
	
	private static final String CODIGO_NOTICIA = "cdNoticia";

	private static final String OUTCOME_NOTICIA = "NOTICIA";
	
	private NoticiaTO noticia = new NoticiaTO();
	
	private List<NoticiaTO> listaNoticia = new ArrayList<NoticiaTO>();
	
	private boolean exibeGrupoNoticia = true;
	
	private boolean exibeGrupoNoticias = true;
	

	public void afterPropertiesSet() throws Exception {

	}
	
	public void detalhar(ActionEvent e) {
        String cdNoticia = getRequestParameterMap(CODIGO_NOTICIA);
        
        carregar();
		
		for (NoticiaTO noticiaTemp : getListaNoticia()) {
			if (Long.parseLong(cdNoticia) == noticiaTemp.getCdNoticia()) {
				setNoticia(noticiaTemp);
				break;
			}
		} 
		
		setExibeGrupoNoticia(true);
		setExibeGrupoNoticias(false);
	}
	
	public String detalhar() {
        String cdNoticia = getRequestParameterMap(CODIGO_NOTICIA);
        
        carregar();
		
		for (NoticiaTO noticiaTemp : getListaNoticia()) {
			if (Long.parseLong(cdNoticia) == noticiaTemp.getCdNoticia()) {
				setNoticia(noticiaTemp);
				break;
			}
		} 
		
		setExibeGrupoNoticia(true);
		setExibeGrupoNoticias(false);

		return OUTCOME_NOTICIA;
	}
	
	public String carregarNoticias(){
		carregar();
		setExibeGrupoNoticia(false);
		setExibeGrupoNoticias(true);

		return OUTCOME_NOTICIA;
	}
	
	private void carregar() {
		listaNoticia = noticiaService.findAll();
	}

	public NoticiaTO getNoticia() {
		return noticia;
	}

	public void setNoticia(NoticiaTO noticia) {
		this.noticia = noticia;
	}

	public List<NoticiaTO> getListaNoticia() {
		return listaNoticia;
	}

	public void setListaNoticia(List<NoticiaTO> listaNoticia) {
		this.listaNoticia = listaNoticia;
	}

	public boolean isExibeGrupoNoticia() {
		return exibeGrupoNoticia;
	}

	public void setExibeGrupoNoticia(boolean exibeGrupoNoticia) {
		this.exibeGrupoNoticia = exibeGrupoNoticia;
	}

	public boolean isExibeGrupoNoticias() {
		return exibeGrupoNoticias;
	}

	public void setExibeGrupoNoticias(boolean exibeGrupoNoticias) {
		this.exibeGrupoNoticias = exibeGrupoNoticias;
	}
	
	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}
}
