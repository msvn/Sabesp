package com.prime.app.agvirtual.service;

import com.prime.app.agvirtual.entity.Atendimento;
import com.prime.app.agvirtual.entity.LogAcesso;

public interface LogAcessoService {
	public LogAcesso gravarLogAcesso(LogAcesso entity);
	public LogAcesso criarLogAcesso(Long cdItemMenu, String dsUsuario, Atendimento atendimento);
	public LogAcesso criarLogAcessoAtendimento(String codSessao, Long cdItemMenu, String dsUsuario, String nrIp);
	
}
