package com.prime.app.agvirtual.dao;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.BancoConveniadoPagEletronico;


/**
 * 
 * @author gustavons
 *
 */
public interface BancoConveniadoPagEletronicoDao {

	ArrayList<SelectItem> buscaBancoConveniado();
	
	BancoConveniadoPagEletronico findById(Long codBanco);
	

}
