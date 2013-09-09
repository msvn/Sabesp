package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.AgvTabDocumentoDao;
import com.prime.app.agvirtual.entity.AgvTabDocumento;
import com.prime.app.agvirtual.enums.TipoDocumento;
import com.prime.app.agvirtual.to.DocumentoTO;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;

@Repository
public class AgvTabDocumentoDaoimpl extends GenericHibernateJpaDao<AgvTabDocumento, Long> implements AgvTabDocumentoDao {
	
	public List<DocumentoTO> findAll() {
    	List<AgvTabDocumento> listaResultado = findByQuery("select e from AgvTabDocumento e");
    	return parseTO(listaResultado);
    }
	
	/**
	 * Faz o Parse do Entity para o Objeto Transfer Object
	 * @param listaResultado
	 * @return
	 */
	private List<DocumentoTO> parseTO(List<AgvTabDocumento> listaResultado){
		
		ArrayList<DocumentoTO> listaFinal =  new ArrayList<DocumentoTO>();
		for (Iterator<AgvTabDocumento> iterator = listaResultado.iterator(); iterator.hasNext();) {
			AgvTabDocumento object = (AgvTabDocumento) iterator.next();
			DocumentoTO element =  new DocumentoTO();
			element.setCdDocumento(object.getCdDocumento());
			element.setDsDocumento(object.getDsDocumento());
			element.setNmDocumento(object.getNmDocumento());
			if(object.getTipoDocumento().intValue() == (TipoDocumento.DOCUMENTO.ordinal())){
				element.setNmFisicoDocumento(object.getNmFisicoDocumento());
				element.setTpDocumentoTela(TipoDocumento.DOCUMENTO);
			}else{
				element.setDsLink(object.getDsLink());
				element.setTpDocumentoTela(TipoDocumento.LINK);
			} 
			element.setTipoDocumento(object.getTipoDocumento().toString());
			element.setTipoPessoa(object.getTipoPessoa().toString());
			listaFinal.add(element);
		}
		return listaFinal ;
	}
}
