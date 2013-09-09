package com.prime.app.agvirtual.service;

import com.prime.app.agvirtual.entity.AcaoAutoAtendimento;
import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessado;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoAcao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoPergunta;
import com.prime.app.agvirtual.entity.AutoAtendimentoPergResp;
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.infra.BusinessException;

public interface AutoAtendimentoService {

	// find by ID
	public AgvTabAutoatendimento findById(Long codigo);
	public AutoAtendimentoPergResp findAAPergRespById(Long codigo);
	public AutoAtendimentoAcao findAAAcaoById(Long codigo);
	
	// abre AA
	public AutoAtendimentoAcessadoAcao abrirAAAcessadoAcao(AutoAtendimentoAcessadoAcao aaa);
	public AutoAtendimentoAcessadoPergunta abrirAAAcessadoPergunta(AutoAtendimentoAcessadoPergunta aaa);
	
	// fecha AA
	public AutoAtendimentoAcessadoAcao fecharAAAcessadoAcao(AutoAtendimentoAcessadoAcao aaa, Long cdAcao, Long cdBanco, String servicoCSI, SituacaoAtendimentoEnum situacao, Long cdMotivoInsucesso);
	public AutoAtendimentoAcessadoPergunta fecharAAAcessadoPergunta(AutoAtendimentoAcessadoPergunta aaa, Long cdConjuntoResposta, SituacaoAtendimentoEnum situacao, Long cdMotivoInsucesso); 
	
	// demais
	public AutoAtendimentoAcessado adicionarDadosSolicitanteAutoAtendimento(AutoAtendimentoAcessado aaa, DadosImoveisTO dadosImoveis) throws BusinessException;
	public AutoAtendimentoAcessado adicionarRgiAutoAtendimentoAcessado(Long cdAAAcessado, String cdRgi) throws BusinessException;
	
	// Metodos nao utilizados mais
	public AcaoAutoAtendimento findAcao(int codPergunta, int codResposta , String dsDiretoria) throws BusinessException;
	
	public AutoAtendimentoAcessado abrirAutoAtendimento(AutoAtendimentoAcessado aaa);
	public AutoAtendimentoAcessado fecharAutoAtendimento(AutoAtendimentoAcessado aaa, Long cdSituacao);
	
	public AutoAtendimentoAcessado fecharAutoAtendimentoInsucesso(AutoAtendimentoAcessado aaa, Long cdSituacao, Long cdMotivoInsucesso);
	//####

}
