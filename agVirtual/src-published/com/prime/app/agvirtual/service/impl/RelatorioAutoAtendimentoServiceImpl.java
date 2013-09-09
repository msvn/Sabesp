package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AutoAtendimentoAcessadoDao;
import com.prime.app.agvirtual.entity.AutoAtendimentoAcessado;
import com.prime.app.agvirtual.service.RelatorioAutoAtendimentoService;
import com.prime.app.agvirtual.service.exception.NotFoundBusinessException;
import com.prime.app.agvirtual.to.RelatorioAutoAtendimentoAcessadoTO;
import com.prime.infra.util.WrapperUtils;

/**
 * Implementacao do servico de exibicao do relatorio de conclusao do atendimento 
 */
@Service
public class RelatorioAutoAtendimentoServiceImpl implements RelatorioAutoAtendimentoService{
	private static final Logger logger = LoggerFactory.getLogger(RelatorioAutoAtendimentoServiceImpl.class);
	
	@Autowired private AutoAtendimentoAcessadoDao daoAutoAtendimentoAcesso;

	public static final int STATUS_CONCLUIDO = 1;
	public static final int STATUS_AVISO = 2;
	public static final int STATUS_INTERROMPIDO = 3;
	public static final int STATUS_NAO_CONCLUIDO = 4;
	
	@Transactional(readOnly = true)
	public List<RelatorioAutoAtendimentoAcessadoTO> doRelatorio(Long cdAtendimento) throws NotFoundBusinessException{
		// obtem lista rgis com AA aberto para o atendimento
		List<RelatorioAutoAtendimentoAcessadoTO> listaAAAcessados = encontraAAAcessados(cdAtendimento);
		
		if(listaAAAcessados==null || listaAAAcessados.isEmpty()){
			throw new NotFoundBusinessException("");
		}
		
		// obtem lista de AA para cada RGI / seta endereco
		carregaDemaisDados(cdAtendimento, listaAAAcessados);
		
		calculaStatusEObservacao(listaAAAcessados); // logica para PDF, EMAIL, IMPRIMIR, TELA
		
		return listaAAAcessados;
	}
	
	// encontra aaAcessados por rgi
	private List<RelatorioAutoAtendimentoAcessadoTO> encontraAAAcessados(Long cdAtendimento) throws NotFoundBusinessException{
		List<RelatorioAutoAtendimentoAcessadoTO> retorno = new ArrayList<RelatorioAutoAtendimentoAcessadoTO>();
		
		List<Object[]> result = daoAutoAtendimentoAcesso.findAllGroupByRgi(cdAtendimento);
		RelatorioAutoAtendimentoAcessadoTO aaAcessadoTO;
		for(Object[] item : result){
			aaAcessadoTO = new RelatorioAutoAtendimentoAcessadoTO();
			aaAcessadoTO.setCdRgi((Long)item[0]);
			
			if(item[1]!=null){ // TODO
				aaAcessadoTO.setSocilicitante((String)item[1]);
			}
			retorno.add(aaAcessadoTO);
		}
		
		return retorno;		
	}
	
	
	private List<RelatorioAutoAtendimentoAcessadoTO> parseListTO(List<AutoAtendimentoAcessado> listaAAAcessado){
		List<RelatorioAutoAtendimentoAcessadoTO> retorno = new ArrayList<RelatorioAutoAtendimentoAcessadoTO>();
		
		RelatorioAutoAtendimentoAcessadoTO aaAcessadoTO;
		for(AutoAtendimentoAcessado aaa : listaAAAcessado){
			aaAcessadoTO = new RelatorioAutoAtendimentoAcessadoTO();
			aaAcessadoTO.setCdRgi(aaa.getCdRgi());
			aaAcessadoTO.setSocilicitante(aaa.getSocilicitante());
			
			retorno.add(aaAcessadoTO);
		}
		
		return retorno;
	}

	private void carregaDemaisDados(Long cdAtendimento, List<RelatorioAutoAtendimentoAcessadoTO> items){
		Long cdRgi;
		for(RelatorioAutoAtendimentoAcessadoTO aaaTO : items){
			cdRgi = aaaTO.getCdRgi();
			
			if(WrapperUtils.isNull(cdRgi) || "".equals(cdRgi)){
				logger.error("Ignorado AutoAtendimento para atendimento " + cdAtendimento);
				continue;
			}
					
			// carrega AA
			aaaTO.setListaAAAcessado(daoAutoAtendimentoAcesso.findByRgi(aaaTO.getCdRgi(), cdAtendimento));
			// carrega endereco
			aaaTO.setDsEndereco(""); // TODO - chamar DAO
		}
	}
	
	// TODO - implementar regra do caso de uso
	private void calculaStatusEObservacao(List<RelatorioAutoAtendimentoAcessadoTO> items){
		for(RelatorioAutoAtendimentoAcessadoTO aaaTO : items){
			for(AutoAtendimentoAcessado aa : aaaTO.getListaAAAcessado()){
				aa.setObservacao(""); // TODO
				aa.setStatus(STATUS_CONCLUIDO); // TODO
			}
		}
	}
	
	

}
