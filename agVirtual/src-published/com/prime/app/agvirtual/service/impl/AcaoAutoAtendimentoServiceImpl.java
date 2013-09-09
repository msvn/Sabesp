package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AcaoAutoAtendimentoDao;
import com.prime.app.agvirtual.dao.ConjuntoRespotaAutoAtendimentoDao;
import com.prime.app.agvirtual.dao.PerguntaRespostaAutoAtendimentoDao;
import com.prime.app.agvirtual.entity.AcaoAutoAtendimento;
import com.prime.app.agvirtual.entity.ConjuntoRespostaAutoAtendimento;
import com.prime.app.agvirtual.entity.PerguntaAutoAtendimento;
import com.prime.app.agvirtual.entity.PerguntaRespostaAutoAtendimento;
import com.prime.app.agvirtual.service.AcaoAutoAtendimentoService;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.infra.BusinessException;
import static com.prime.infra.util.WrapperUtils.*;

@Service
public class AcaoAutoAtendimentoServiceImpl implements AcaoAutoAtendimentoService {
	private static final Logger logger = LoggerFactory.getLogger(AcaoAutoAtendimentoServiceImpl.class);
	
	private static final int MUDANCA_LIGACAO_AGUA = 2;
	
	@Autowired private PerguntaRespostaAutoAtendimentoDao pergRespDao;
	@Autowired private ConjuntoRespotaAutoAtendimentoDao conjRespDao;
	@Autowired private AcaoAutoAtendimentoDao acaoAutoAtendDao;
	
	@Transactional(readOnly = true)
	public AcaoAutoAtendimento findAcao(List<PerguntaAutoAtendimento> perguntasRespondidas , String diretoria) throws BusinessException, Exception {
		logger.info("Inicio AcaoAutoAtendimentoServiceImpl -> findAcao");
		List<Integer> listaCodPergResp = new ArrayList<Integer>();
		
		// percorre lista de perguntas
		PerguntaRespostaAutoAtendimento pergResp;
		int codRespSelecionado = 0;
		
		logger.info("Numero perguntas respondidas: " + perguntasRespondidas.size());
		for(PerguntaAutoAtendimento pergunta : perguntasRespondidas){
			if(pergunta.getCodRespostaSelecionado()!=null) codRespSelecionado = toInt(pergunta.getCodRespostaSelecionado());
			
			// encontra PerguntaResposta
			pergResp = pergRespDao.findByPerguntaIdRespostaId(pergunta.getCdPergunta(), codRespSelecionado);
			logger.info("Encontrado codigo pergunta resposta --> " + pergResp.getCdPerguntaResposta());
			listaCodPergResp.add(pergResp.getCdPerguntaResposta());
		}
		
		//--------------------------------------------------------------------------------------------------------
		// encontra Acao
		List<Object> listaCodConjuntoResp;
		if(perguntasRespondidas.get(0).getAutoAtendimento().getCdAutoatendimento() == MUDANCA_LIGACAO_AGUA) {
			listaCodConjuntoResp = conjRespDao.findByCodPerguntasRespostasLigacaoAgua(listaCodPergResp , diretoria);
		} else {
			listaCodConjuntoResp = conjRespDao.findByCodPerguntasRespostas(listaCodPergResp , diretoria);
			
			// filtra por diretoria
			if(listaCodConjuntoResp.size() > 1){
				filtraConjuntoPorDiretoria(listaCodConjuntoResp,diretoria);
			}
		}
		//--------------------------------------------------------------------------------------------------------
		
		logger.info("Localizando acao para conjuntoResposta id=" + listaCodConjuntoResp.get(0));
		AcaoAutoAtendimento acao = conjRespDao.findById(toLong(listaCodConjuntoResp.get(0))).getAcao();
		logger.info("Herrique : Localizada acao = " + acao.getCdAcao());
		logger.info("Acao tipo: " + acao.getTpAcao()) ;
		return 	conjRespDao.findById(toLong(listaCodConjuntoResp.get(0))).getAcao();
		
	}
	
	/**
	 * Filtra a lista de conjuntos , verifica se existe mais de 2 conjuntos , se sim sobe exceção de muitos ou nenhum conjutos para a resposta
	 * se existirem mais de 2 conjuntos , deve passar para a proxima pergunta.
	 * @param listaCodConjuntoResp
	 * @param diretoria
	 * @return
	 * @throws BusinessException, Exception
	 */
	private AcaoAutoAtendimento filtraConjuntoPorDiretoria(List<Object> listaCodConjuntoResp, String diretoria) throws BusinessException, Exception {
		
		if(listaCodConjuntoResp.size() > 2){
			throw new NotFoundBusinessException("Mais que dois conj resposta encontrados para"); //FIXME ARRUMAR COMENTS
		}
		
		ConjuntoRespostaAutoAtendimento conj1 = conjRespDao.findById(toLong(listaCodConjuntoResp.get(0)));
		ConjuntoRespostaAutoAtendimento conj2  = conjRespDao.findById(toLong(listaCodConjuntoResp.get(1)));
		
		if(conj1.getDsDiretoria().equals("T") && conj2.getDsDiretoria().equals("T")){
			throw new NotFoundBusinessException("Mais que dois conj resposta encontrados para"); //FIXME ARRUMAR COMENTS
		}
		
		if(conj1.getDsDiretoria().equals(diretoria)){
			return conj1.getAcao();
		}
		
		if(conj2.getDsDiretoria().equals(diretoria)){
			return conj2.getAcao();
		}
		
		throw new BusinessException("Não foi encontrado ação para a diretoria "+ diretoria +" , conjunto 1 = "+ conj1.getCdConjuntoResposta() + " conjunto 2 = " + conj2.getCdConjuntoResposta()  + "cadastrado na base "); 
	}

	@Transactional(readOnly = true)
	public AcaoAutoAtendimento findAcaoByAutoAtendimento(Long codAutoAtendimentoAcao) throws NotFoundBusinessException {
		logger.info("Inicio AcaoAutoAtendimentoServiceImpl -> findAcaoByAutoAtendimento(codAutoAtendimentoAcao)");
		AcaoAutoAtendimento autoAtendimento = acaoAutoAtendDao.findAcaoByAutoAtendimento(codAutoAtendimentoAcao);
		logger.info("Fim AcaoAutoAtendimentoServiceImpl -> findAcaoByAutoAtendimento(codAutoAtendimentoAcao)");
		return autoAtendimento;
	}

}
