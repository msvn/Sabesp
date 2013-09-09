package com.prime.infra.config.handler;

import static com.prime.infra.util.WrapperUtils.isNull;

import java.util.HashMap;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prime.app.agvirtual.web.jsf.BasicAutoAtendimentoBBean;
import com.prime.app.agvirtual.web.jsf.util.ReflectionFacade;
import static com.prime.infra.util.WrapperUtils.*;

/**
 * Inicializa Bean.
 * 
 * Obtem Bean associado ao nome da nagegacao. 
 * 
 * Miguel analisar impacto na arquitetura, BEAN ALTAMENTE ACOPLADO A NAVEGACAO
 * 
 * @author felipepm
 */
public class SabespNavigationHandler extends NavigationHandler {
	private static final Logger logger = LoggerFactory.getLogger(SabespNavigationHandler.class);	
	
	private NavigationHandler navitagationBase;
	
	private HashMap<String, String> listaBeans = null;

	
	public SabespNavigationHandler (NavigationHandler base) {
		super();
		navitagationBase = base;
	}


	@SuppressWarnings("deprecation")
	@Override
	public void handleNavigation(FacesContext ctx, String arg1, String outcome) {
		logger.info("Inicio SabespNavigationHandler -> handleNavigation");
		
		if(isNull(outcome) || outcome.isEmpty()){
			logger.info("HandleNavigation nao executado pois outcome esta nulo ou vazio.");
			return;
		}
		
		if (isNull(listaBeans)) {
			getOutcomesBeans();
		}

		if(!listaBeans.containsKey(outcome)){
			logger.info("Não há Bean associado ao Outcome [" + outcome + "].");
			navitagationBase.handleNavigation(ctx, arg1, outcome);
			return;
		}

		// localiza Bean associado ao outcome
		String beanName = listaBeans.get(outcome);

		try{
			FacesContext fc = FacesContext.getCurrentInstance();
			Object bean = fc.getApplication().createValueBinding("#{" + beanName + "}").getValue(fc);
		
			ReflectionFacade instance = ReflectionFacade.instance(bean);
			
			// executa eraseBeanAtributes
			if(isNotNull(instance.getMetodo("eraseBeanAtributes"))){
				logger.info("Invocando metodo eraseBeanAtributes");
				instance.chamaMetodo(instance.getMetodo("eraseBeanAtributes"), new Object[] {});
			}
			if(isNotNull(instance.getMetodo("initialize"))){
				logger.info("Invocando metodo initialize");
				instance.chamaMetodo(instance.getMetodo("initialize"), new Object[] {});
			}					
			
		}catch(Exception e){
			logger.error("HandleNavigation executado com erro.", e);
		}finally{
			navitagationBase.handleNavigation(ctx, arg1, outcome);
		}
	}

	/**
	 * Retorna lista de Beans.
	 * 
	 * Miguel rever por favor
	 */
	private void getOutcomesBeans() {
		listaBeans = new HashMap<String, String>();
		listaBeans.put("CONSERTO_CAVALETE", "consertoCavaleteBBean");
		listaBeans.put("CONSERTO_REGISTRO", "consertoRegistroBBean");
		listaBeans.put("CONSERTO_HIDROMETRO", "consertoHidrometroBBean");
		listaBeans.put("AGENCIASATENDIMENTO", "agenciasAtendimentoBBean");
		listaBeans.put("SUA_CONTA_SEGUNDA_VIA", "contaBBean");
		listaBeans.put("LIGACOES_MUDANCA_LIGACAO_ESGOTO", "ligacaoEsgotoBBean");
		listaBeans.put("EMERGENCIA_VAZAMENTO_REDE", "vazamentoBBean");	
		listaBeans.put("LIGACOES_MUDANCA_LIGACAO_AGUA", "ligacaoAguaBBean");
		listaBeans.put("EMERGENCIA_ESGOTO_ENTUPIDO", "esgotoEntupidoBBean");
		listaBeans.put("SUA_CONTA_CONSULTA_DADOS_LEITURA", "cronogramaLeituraBBean");
		listaBeans.put("SUA_CONTA_CONSULTA_HISTORICO_CONTAS", "historicoContasBBean");
	}
	
}
