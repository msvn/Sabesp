package com.prime.app.agvirtual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgenciaDao;
import com.prime.app.agvirtual.dao.EnderecoDao;
import com.prime.app.agvirtual.service.AgenciaService;
import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;

@Service
public class AgenciaServiceImpl implements AgenciaService {

	@Autowired
    private AgenciaDao agenciaDao;
	
	@Autowired
    private EnderecoDao enderecoDao;

	@Transactional(readOnly = true)
	public List<AgenciaTO> findByMunicipio(String cdMunicipio) {
		
		List<AgenciaTO> agencias = agenciaDao.findByMunicipio(cdMunicipio);
//		for (AgenciaTO agencia : agencias) {
//		    agencia.setEndereco(enderecoDao.findEnderecoAgencia(agencia,false));
//		}
		return agencias;
	}
	
	@Transactional(readOnly = true)
	public void findEnderecoByAgencia(AgenciaTO agencia) {
		agencia.setEndereco(enderecoDao.findEnderecoAgencia(agencia,false));
	} 
	
	@Transactional(readOnly = true)
	public void findEnderecoTodasAgencias(List<AgenciaTO> listaAgencia) {
		for (AgenciaTO agencia : listaAgencia) {
			agencia.setEndereco(enderecoDao.findEnderecoAgencia(agencia,false));
		}
	}
	
	@Transactional
	public AgenciaTO findAgenciaMaisProximaByRgiRolEspecial(DadosImoveisTO dadosImoveisTO) {
		return agenciaDao.findAgenciaMaisProximaByRgiRolEspecial(dadosImoveisTO);
	}

	public AgenciaTO findAgenciaMaisProximaByRgiRolComum(DadosImoveisTO dadosImoveisTO) {
		return agenciaDao.findAgenciaMaisProximaByRgiRolComum(dadosImoveisTO);
	}

	
}