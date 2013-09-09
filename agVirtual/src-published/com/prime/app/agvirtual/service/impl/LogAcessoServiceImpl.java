package com.prime.app.agvirtual.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabItemMenuAcessoDao;
import com.prime.app.agvirtual.dao.LogAcessoDao;
import com.prime.app.agvirtual.entity.AgvTabItemMenuAcesso;
import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.app.agvirtual.entity.LogAcesso;
import com.prime.app.agvirtual.service.AtendimentoService;
import com.prime.app.agvirtual.service.LogAcessoService;

@Service
public class LogAcessoServiceImpl implements LogAcessoService{
	private static final Logger logger = LoggerFactory.getLogger(LogAcessoServiceImpl.class);
	
	@Autowired private LogAcessoDao logAcessoDao;
	@Autowired private AgvTabItemMenuAcessoDao itemMenuAcessoDao;
	@Autowired private AtendimentoService atendimentoService;
	
	@Transactional
	public LogAcesso gravarLogAcesso(LogAcesso entity) {
		return logAcessoDao.persist(entity);
	}
	
	/**
	 * Cria log de acesso
	 * 
	 * @param cdItemMenu
	 * @param dsUsuario
	 * @param atendimento
	 * @return
	 */
	@Transactional
	public LogAcesso criarLogAcesso(Long cdItemMenu, String dsUsuario, Atendimento atendimento){
		logger.info("Criando log acesso para atendimento " + atendimento.getCdAtendimento() + " e codSessao = " + atendimento.getCdSessao());
		
		LogAcesso logAcesso = new LogAcesso();
		logAcesso.setAtendimento(atendimento);
		logAcesso.setDataAcesso(new Date());
		logAcesso.setDescricaoUsuario(dsUsuario);
		AgvTabItemMenuAcesso itemMenuAcessado = itemMenuAcessoDao.findById(cdItemMenu, Boolean.FALSE);
		logAcesso.setItemMenu(itemMenuAcessado);
		
		logAcessoDao.persist(logAcesso);
		
		return logAcesso;
	}	
	
	/**
	 * Cria log de acesso e atendimento
	 * 
	 * @param codSessao
	 * @param cdItemMenu
	 * @param dsUsuario
	 * @param nrIp
	 * @return
	 */
	@Transactional
	public LogAcesso criarLogAcessoAtendimento(String codSessao, Long cdItemMenu, String dsUsuario, String nrIp){
		LogAcesso logAcesso = null;
		
		logAcesso = new LogAcesso();
		logAcesso.setDataAcesso(new Date());
		logAcesso.setDescricaoUsuario(dsUsuario);
		
		AgvTabItemMenuAcesso itemMenuAcessado = itemMenuAcessoDao.findById(cdItemMenu, Boolean.FALSE);
		logAcesso.setItemMenu(itemMenuAcessado);
		
		Atendimento atendimento = atendimentoService.abreAtendimento(codSessao, nrIp);
		logAcesso.setAtendimento(atendimento);
		
		// log
		String msg = "Criando log acesso para codSessao = " + codSessao + ", " + " cdItemMenu=" + cdItemMenu;
		logger.info(msg); System.out.println(msg);
		
		logAcesso = logAcessoDao.persist(logAcesso);
		
		return logAcesso;
	}
	
}
