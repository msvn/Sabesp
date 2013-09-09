package com.prime.app.agvirtual.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AtendimentoDao;
import com.prime.app.agvirtual.dao.impl.AtendimentoDaoImpl;
import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.service.AtendimentoService;
import com.prime.infra.BusinessException;


@Service
public class AtendimentoServiceImpl implements AtendimentoService {
	private static final Logger logger = LoggerFactory.getLogger(AtendimentoServiceImpl.class);
	
	@Autowired
	private AtendimentoDao atendimentoDao;

	@Transactional
	public Atendimento abreAtendimento(String codSessao, String nrIp) {
		Atendimento atendimento = null;
		
		atendimento = atendimentoDao.findByCodSessao(codSessao);
		if(atendimento!=null){
			logger.info("Localizado atendimento para codigo sessao = " + codSessao);
			return atendimento;
		}
			
		logger.info("Criando auto atendimento para codigo sessao = " + codSessao);
		atendimento = new Atendimento();
		atendimento.setCdSessao(codSessao);
		atendimento.setCdStaAtendimento(SituacaoAtendimentoEnum.EMANDAMENTO.getCodigo()); // TODO - modificar tipo na classe atendimento
		atendimento.setDtInclusao(new Date());
		atendimento.setNrIp(nrIp); 				 
		
		atendimento = atendimentoDao.persist(atendimento);
		
		return atendimento;
	}
	
	@Transactional
	public boolean concluirAtendimento(String codSessao, Long codSituacao) throws BusinessException{
		if(codSessao == null || codSituacao == null || "".equals(codSessao)){
			StringBuffer sb = new StringBuffer();
			sb.append("Atendimento nao concluido. Codigo da sessao ou Codigo da situacao do atendimento invalidos. codSituacao=");
			sb.append(codSessao);
			sb.append(" codSituacao=");
			sb.append(codSituacao);
			
			logger.warn(sb.toString());
		}
		
		Atendimento atendimento = atendimentoDao.findByCodSessao(codSessao);
		
		if(atendimento==null){
			throw new BusinessException("Nao e possivel concluir atendimento pois nao ha atendimento aberto para sessao" + codSessao);
		}
		
		atendimento.setDtFinal(new Date());
		atendimento.setCdStaAtendimento(codSituacao);
		
		atendimentoDao.persist(atendimento);
		
		return true;
	}


}
