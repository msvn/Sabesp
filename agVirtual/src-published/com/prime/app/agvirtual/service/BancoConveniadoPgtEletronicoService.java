package com.prime.app.agvirtual.service;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

import com.prime.app.agvirtual.entity.BancoConveniadoPagEletronico;


public interface BancoConveniadoPgtEletronicoService {
	
	ArrayList<SelectItem> buscaBancoConveniado();
	
	public BancoConveniadoPagEletronico findById(Long codBanco);
}
