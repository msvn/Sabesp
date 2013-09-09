package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.BancoConveniadoPagEletronicoDao;
import com.prime.app.agvirtual.entity.BancoConveniadoPagEletronico;
import com.prime.app.agvirtual.service.BancoConveniadoPgtEletronicoService;

@Service
public class BancoConveniadoPgtEletronicoServiceImpl implements BancoConveniadoPgtEletronicoService {
	
	@Autowired
	private BancoConveniadoPagEletronicoDao dao;

	@Transactional(readOnly = true)
	public ArrayList<SelectItem> buscaBancoConveniado() {
		return dao.buscaBancoConveniado();
	}

	@Transactional(readOnly = true)
	public BancoConveniadoPagEletronico findById(Long codBanco) {
		return dao.findById(codBanco);
	}

}
