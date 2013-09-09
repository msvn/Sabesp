package com.prime.app.agvirtual.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.BancoConveniadoPagEletronicoDao;
import com.prime.app.agvirtual.entity.BancoConveniadoPagEletronico;
import com.prime.infra.dao.jpa.GenericHibernateJpaDao;
import com.prime.infra.dao.jpa.GenericJpaDao;

/**
 * Dao responsavel por todas chamadas ao CSI
 * 
 * @author gustavons
 * 
 */
@Repository
public class BancoConveniadoPagEletronicoDaoImpl extends GenericHibernateJpaDao<BancoConveniadoPagEletronico, Long> implements BancoConveniadoPagEletronicoDao {

	private static final Logger agvlogger = LoggerFactory.getLogger(BancoConveniadoPagEletronicoDaoImpl.class);

	public ArrayList<SelectItem> buscaBancoConveniado() {
		List<BancoConveniadoPagEletronico> listaResultado = findByQuery("select e from BancoConveniadoPagEletronico e ");

		ArrayList<SelectItem> listaFinal = new ArrayList<SelectItem>();
		if(listaResultado != null){
			for (BancoConveniadoPagEletronico bancoConveniadoPagEletronico : listaResultado) {
				listaFinal.add(new SelectItem(bancoConveniadoPagEletronico.getCdBanco().toString(),bancoConveniadoPagEletronico.getDsBanco()));
			}
		}
    	return listaFinal;
	}
	
	public BancoConveniadoPagEletronico findById(Long codBanco) {
		BancoConveniadoPagEletronico result = null;
		try{
			org.hibernate.Query query = getHibernateSession().createQuery("from BancoConveniadoPagEletronico e where e.cdBanco = :cod ");
			query.setLong("cod",codBanco);
			
			result = (BancoConveniadoPagEletronico) query.uniqueResult();            
        } catch(Exception e) {
             e.printStackTrace();
        } 
        
        return result;
	}

}
