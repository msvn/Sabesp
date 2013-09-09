package com.prime.app.agvirtual.service.impl;

import static com.prime.infra.util.WrapperUtils.isNull;
import static com.prime.infra.util.WrapperUtils.toInt;
import static com.prime.infra.util.WrapperUtils.toLong;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AgvTabAutoatendimentoDao;
import com.prime.app.agvirtual.dao.AutoAtendimentoAcaoAutoAtendimentoDao;
import com.prime.app.agvirtual.dao.AutoAtendimentoAcaoDao;
import com.prime.app.agvirtual.dao.AutoAtendimentoAcessadoAcaoDao;
import com.prime.app.agvirtual.dao.AutoAtendimentoAcessadoDao;
import com.prime.app.agvirtual.dao.AutoAtendimentoAcessadoPerguntaDao;
import com.prime.app.agvirtual.dao.AutoAtendimentoPergRespDao;
import com.prime.app.agvirtual.dao.PerguntaRespostaAutoAtendimentoDao;
import com.prime.app.agvirtual.entity.AcaoAutoAtendimento;
import com.prime.app.agvirtual.entity.AgvTabAutoatendimento;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcaoAutoAtendimento;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessado;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoAcao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessadoPergunta;
import com.prime.app.agvirtual.entity.AutoAtendimentoPergResp;
import com.prime.app.agvirtual.entity.BancoPagamentoEletronico;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.ConjuntoRespostaAutoAtendimento;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.entity.MotivoInsucesso;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.entity.PerguntaRespostaAutoAtendimento;
import com.prime.app.agvirtual.entity.SituacaoAtendimento;
import com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum;
import com.prime.app.agvirtual.service.AutoAtendimentoService;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.infra.BusinessException;


@Service
public class AutoAtendimentoServiceImpl implements AutoAtendimentoService {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoAtendimentoServiceImpl.class);
	
	private static final Long MOTIVO_INSUCESSO_EM_ANDAMENTO = 3L;
	
	@Autowired private AgvTabAutoatendimentoDao autoAtendimentoDao;
	@Autowired private AutoAtendimentoPergRespDao autoAtendimentoPergRespDao;
	@Autowired private AutoAtendimentoAcaoDao autoAtendimentoAcaoDao;
	
	@Autowired private AutoAtendimentoAcessadoDao autoAtendimentoAcessadoDao;
	@Autowired private AutoAtendimentoAcessadoPerguntaDao aaAcessadoPerguntaDao;
	@Autowired private AutoAtendimentoAcessadoAcaoDao aaAcessadoAcaoDao;
	@Autowired private AutoAtendimentoAcaoAutoAtendimentoDao aaAcaoAutoAtendimentoDao;
	
	@Autowired 
	private PerguntaRespostaAutoAtendimentoDao perguntaRespostaAAtendimentoDao;

	@Transactional(readOnly = true)
	public AgvTabAutoatendimento findById(Long id) {
		AgvTabAutoatendimento aa = null;
		
		aa = findAAPergRespById(id);
		if(aa!=null) {
			logger.debug("Encontrado Auto Atendimento PergResp id=" + id);
			return aa;
		}
		
		aa = findAAAcaoById(id); 
		if(aa!=null){
			logger.debug("Encontrado Auto Atendimento Acao id=" + id);
			return aa;
		}
			
		logger.debug("Auto Atendimento nao encontrado, id=" + id);
		return aa;
	}	
	
	@Transactional(readOnly = true)
	public AutoAtendimentoPergResp findAAPergRespById(Long codigo) {
		 return autoAtendimentoPergRespDao.findById(codigo, Boolean.FALSE);
	}

	@Transactional(readOnly = true)
	public AutoAtendimentoAcao findAAAcaoById(Long codigo) {
		 return autoAtendimentoAcaoDao.findById(codigo, Boolean.FALSE);
	}

	@Transactional(readOnly = true)
	public AcaoAutoAtendimento findAcao(int codPergunta, int codResposta , String dsDiretoria) throws BusinessException {
		PerguntaRespostaAutoAtendimento pergResp = perguntaRespostaAAtendimentoDao.findByPerguntaIdRespostaId(codPergunta, codResposta);
		
		List<ConjuntoRespostaAutoAtendimento> conjResp = pergResp.getListaConjuntoResposta();
		if(conjResp==null){
			throw new BusinessException("Base configurada de maneira incorreta, numero de ConjuntoResposta esperados não existe");
		}
		
		//Se existirem apenas 2 conjutos verificar se tem a mesma diretoria
		if(conjResp.size() == 2){
			//Se são de diretorias diferentes , existe uma ação a ser tomada para este conjunto de respostas
			if(!conjResp.get(0).getDsDiretoria().equals(conjResp.get(1).getDsDiretoria())){
				//itera para achar a resposta
				for (Iterator iterator = conjResp.iterator(); iterator.hasNext();) {
					ConjuntoRespostaAutoAtendimento conjuntoRespostaAutoAtendimento = (ConjuntoRespostaAutoAtendimento) iterator.next();
					logger.info("CONJUNTO-->"+conjuntoRespostaAutoAtendimento.getCdConjuntoResposta()+" ACAO_--->"+conjuntoRespostaAutoAtendimento.getAcao().getDsAcao() + " DIRETORIA---->"+conjuntoRespostaAutoAtendimento.getDsDiretoria());
					if(conjuntoRespostaAutoAtendimento.getDsDiretoria().equals("M")){ //FIXME dsDiretoria
						return conjuntoRespostaAutoAtendimento.getAcao();
					}
				}
			}
		}else if(conjResp.size() == 1){
			return conjResp.get(0).getAcao();
		}
		
//		if(conjResp.size()!=1) throw new BusinessException("Base configurada de maneira incorreta, numero de ConjuntoResposta esperados = 1, numero encontrado = " + conjResp.size());
		
		return null;
	}
	
	@Transactional(readOnly = true)
	public AcaoAutoAtendimento findAcao(List<PerguntaAutoAtendimento> perguntas, String dsDiretoria) throws NotFoundBusinessException, BusinessException{
		AcaoAutoAtendimento retorno = null;
		
		// localiza todas as perguntas respostas
		List<Integer> perguntasRespostasCds = new ArrayList<Integer>();
		int codPergunta;
		int codResposta;
		for(PerguntaAutoAtendimento pergunta : perguntas){
			codPergunta = pergunta.getCdPergunta();
			codResposta = toInt(pergunta.getCodRespostaSelecionado());
			PerguntaRespostaAutoAtendimento pergResp = perguntaRespostaAAtendimentoDao.findByPerguntaIdRespostaId(codPergunta, codResposta);
			perguntasRespostasCds.add(pergResp.getCdPerguntaResposta());
		}
		
		
		
		return retorno;
	}

	/**
	 * Abre auto atendimento. 
	 * 
	 * Caso necessario, implementar aqui as regras para abertura do Auto Atendimento
	 * 
	 */
	@Transactional
	public AutoAtendimentoAcessado abrirAutoAtendimento(AutoAtendimentoAcessado aaa) {
		// set situacao em andamento
		SituacaoAtendimento situacao = new SituacaoAtendimento();
		situacao.setCodigo(com.prime.app.agvirtual.enums.SituacaoAtendimentoEnum.EMANDAMENTO.getCodigo());
		aaa.setSituacaoAtendimento(situacao);
		
		// set 
		MotivoInsucesso motivo = new MotivoInsucesso();
		motivo.setCodigo(3L);
		aaa.setMotivoInsucesso(motivo);
		
		// set dt inicio
		aaa.setDtInicio(new Date(System.currentTimeMillis()));
		
		return autoAtendimentoAcessadoDao.persist(aaa);
	}

	/**
	 * Armazena dados do solicitante
	 * 
	 * @throws BusinessException 
	 */
	@Transactional
	public AutoAtendimentoAcessado adicionarDadosSolicitanteAutoAtendimento(
			AutoAtendimentoAcessado aaa, DadosImoveisTO dadosImoveis) throws BusinessException {

		Imovel imovel = dadosImoveis.getImovel();
		Cliente cliente = dadosImoveis.getSolicitante();
		AgenciaTO agenciaTO = dadosImoveis.getAgenciaTO();

		if(isNull(aaa)){
			throw new BusinessException("Auto Atendimento Acessado nulo.");
		}
		if(isNull(imovel)){
			throw new BusinessException("Imovel nulo.");
		}
		if(isNull(cliente)){
			throw new BusinessException("cliente nulo.");
		}
//		if(isNull(agenciaTO)){
//			throw new BusinessException("agenciaTO nulo.");
//		}			
		
		aaa.setCdMunicipio(toInt(cliente.getCdMunicipio()));
		aaa.setCdAtendComercial(null) ; //
		aaa.setCdRgi(toLong(imovel.getDsRgi()));
		aaa.setEmail(cliente.getDsEmail());
		aaa.setRelacaoSolicitante(cliente.getCodCategoria());
		aaa.setSocilicitante(cliente.getNmCLiente());
		aaa.setDocumento(cliente.getDocumentoPreechido());
		aaa.setTelefone(cliente.getDdd1() + cliente.getTelefone1());
//		aaa.setObservacao(null);     // ver de onde vem
		
//		if(toLong(cliente.getTelefone1())!=null){
//			aaa.setTelefone(toLong(cliente.getTelefone1()).toString());
//		}
		
		if(agenciaTO!=null){
			aaa.setCdUnidadeNegocio(agenciaTO.getCdUnidCom());
		}else{
			logger.error("CdUnidadeNegocio nao carregado pois agenciaTO esta nulo!");
		}
		
		return autoAtendimentoAcessadoDao.persist(aaa);
	}

	/**
	 * Fecha auto atendimetno
	 */
	@Transactional
	public AutoAtendimentoAcessado fecharAutoAtendimento(AutoAtendimentoAcessado aaa, Long cdSituacao) {
		aaa.setDtFim(new Date());
		// set situacao
		SituacaoAtendimento situacao = new SituacaoAtendimento();
		situacao.setCodigo(cdSituacao);
		aaa.setSituacaoAtendimento(situacao);
		
		return autoAtendimentoAcessadoDao.persist(aaa);
	}

	/**
	 * Fecha auto atendimento com motivo de insucesso
	 */
	@Transactional
	public AutoAtendimentoAcessado fecharAutoAtendimentoInsucesso(AutoAtendimentoAcessado aaa, Long cdSituacao, Long cdMotivoInsucesso) {
		// set situacao
		SituacaoAtendimento situacao = new SituacaoAtendimento();
		situacao.setCodigo(cdSituacao);
		
		// set motivo insucesso
		MotivoInsucesso motivo = new MotivoInsucesso();
		motivo.setCodigo(cdMotivoInsucesso);
		aaa.setMotivoInsucesso(motivo);
	
		aaa.setDtFim(new Date());
		
		return autoAtendimentoAcessadoDao.persist(aaa);
	}

	// ##########
	
	@Transactional
	public AutoAtendimentoAcessadoAcao abrirAAAcessadoAcao(AutoAtendimentoAcessadoAcao aaa) {
		aaa.setSituacaoAtendimento(criarSituacao(SituacaoAtendimentoEnum.EMANDAMENTO));
		aaa.setMotivoInsucesso(criarMotivoInsucesso(MOTIVO_INSUCESSO_EM_ANDAMENTO));
		aaa.setDtInicio(new Date(System.currentTimeMillis()));
		
		return aaAcessadoAcaoDao.persist(aaa);
	}

	@Transactional
	public AutoAtendimentoAcessadoPergunta abrirAAAcessadoPergunta(
			AutoAtendimentoAcessadoPergunta aaa) {
		aaa.setSituacaoAtendimento(criarSituacao(SituacaoAtendimentoEnum.EMANDAMENTO));
		aaa.setMotivoInsucesso(criarMotivoInsucesso(MOTIVO_INSUCESSO_EM_ANDAMENTO));
		aaa.setDtInicio(new Date(System.currentTimeMillis()));
		
		return aaAcessadoPerguntaDao.persist(aaa);
	}

	@Transactional
	public AutoAtendimentoAcessadoAcao fecharAAAcessadoAcao(AutoAtendimentoAcessadoAcao aaa,
			Long cdAcao, Long cdBanco, String acatamentoCSI, SituacaoAtendimentoEnum situacao,
			Long cdMotivoInsucesso) {
		AutoAtendimentoAcessadoAcao retorno;
		
		aaa.setDtFim(new Date(System.currentTimeMillis()));
		aaa.setMotivoInsucesso(criarMotivoInsucesso(cdMotivoInsucesso));
		aaa.setSituacaoAtendimento(criarSituacao(situacao));
		
		// Persiste Auto Atendimento Acessado
		retorno = aaAcessadoAcaoDao.persist(aaa);
		// Persiste Log da Acao Executada
		aaAcaoAutoAtendimentoDao.persist(criarAAAcaoAutoAtendimento(aaa,cdAcao,cdBanco,acatamentoCSI));
		
		return retorno;
	}

	@Transactional
	public AutoAtendimentoAcessadoPergunta fecharAAAcessadoPergunta(AutoAtendimentoAcessadoPergunta aaa,
			Long cdConjuntoResposta, SituacaoAtendimentoEnum situacao, 
			Long cdMotivoInsucesso) {
		
		aaa.setDtFim(new Date(System.currentTimeMillis()));
		aaa.setMotivoInsucesso(criarMotivoInsucesso(cdMotivoInsucesso));
		aaa.setSituacaoAtendimento(criarSituacao(situacao));
		aaa.setConjuntoResposta(criarConjuntoResposta(cdConjuntoResposta));
		
		// Persiste Auto Atendimento Pergunta
		return aaAcessadoPerguntaDao.persist(aaa);		
	}
	
	private SituacaoAtendimento criarSituacao(SituacaoAtendimentoEnum tipoSituacao){
		// set situacao em andamento
		SituacaoAtendimento situacao = new SituacaoAtendimento();
		situacao.setCodigo(tipoSituacao.getCodigo());
		
		return situacao;
	}
	
	private ConjuntoRespostaAutoAtendimento criarConjuntoResposta(Long cdConjuntoResposta){
		ConjuntoRespostaAutoAtendimento conjuntoResposta = new ConjuntoRespostaAutoAtendimento();
		conjuntoResposta.setCdConjuntoResposta(cdConjuntoResposta);
		return conjuntoResposta;
	}
	
	private MotivoInsucesso criarMotivoInsucesso(Long cdMotivoInsucesso){
		MotivoInsucesso motivo = new MotivoInsucesso();
		motivo.setCodigo(cdMotivoInsucesso);				
		return motivo;
	}
	
	private AutoAtendimentoAcaoAutoAtendimento criarAAAcaoAutoAtendimento(AutoAtendimentoAcessadoAcao aaa,
			Long cdAcao, Long cdBanco, String acatamentoCSI){
		
		AutoAtendimentoAcaoAutoAtendimento aaAcaoAutoAtendimento = new AutoAtendimentoAcaoAutoAtendimento();
		
		aaAcaoAutoAtendimento.setAutoAtendimentoAcessado(aaa);
		aaAcaoAutoAtendimento.setAcao(criarAcao(cdAcao));
		aaAcaoAutoAtendimento.setCdAcatamentoCSI(acatamentoCSI);
		aaAcaoAutoAtendimento.setBanco(criarBanco(cdBanco));
		
		return aaAcaoAutoAtendimento;
	}
	
	private AcaoAutoAtendimento criarAcao(Long cdAcao){
		AcaoAutoAtendimento acao = new AcaoAutoAtendimento();
		acao.setCdAcao(cdAcao);
		
		return acao;
	}
	
	private BancoPagamentoEletronico criarBanco(Long cdBanco){
		BancoPagamentoEletronico banco = new BancoPagamentoEletronico();
		banco.setCodigo(cdBanco);
		
		return banco;
	}

	/**
	 * Adiciona Rgi ao Auto Atendimento Acessado
	 */
	@Transactional
	public AutoAtendimentoAcessado adicionarRgiAutoAtendimentoAcessado(
			Long cdAAAcessado, String cdRgi) throws BusinessException {

		AutoAtendimentoAcessado aaAcessado = autoAtendimentoAcessadoDao.findById(cdAAAcessado, Boolean.FALSE);
		
		if(aaAcessado==null) return null;
		
		aaAcessado.setCdRgi(toLong(cdRgi));

		return aaAcessado;
	}
	
	// ##########
}
