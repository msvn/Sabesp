package com.prime.app.agvirtual.web.jsf;

import static com.prime.app.agvirtual.web.jsf.util.Utils.parseDouble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.icesoft.faces.component.ext.HtmlDataTable;
import com.icesoft.faces.component.ext.HtmlSelectBooleanCheckbox;
import com.prime.app.agvirtual.entity.BancoConveniadoPagEletronico;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.service.BancoConveniadoPgtEletronicoService;
import com.prime.app.agvirtual.to.Acatamento2ViaTO;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * Bean responsavel pela tela de segunda via / contas em aberto
 * @author gustavons
 *
 */
@Component
@Scope(value="session")
public class ContaBBean extends BasicPesquisaBBean {
	private static final Logger agvlogger = LoggerFactory.getLogger(ContaBBean.class);
	
	private static final long serialVersionUID = 6897044564463030350L;
	
	private ArrayList<Conta> listaContas;
	
	// Key =  Ano mes referencia , e Conta como objeto
	private HashMap<String,Conta> listaContasImpressaoSegundaVia;
	
	private ArrayList<Conta> listaImpressao;
	
	private Conta currentBean;
	
	//Atributo utilizado para controle do botao de prosseguir	
	private boolean renderedImprimir;
	
	//Atributo para controle dos check box da listagem, para marcar e desmarcar o botao de selectAll
	private boolean check2via;
	
	//Atributo utilizado para controle do botao de prosseguir
	private boolean renderedCorreio;

	//Atributo para controle dos check box da listagem, para marcar e desmarcar o botao de selectAll
	private boolean checkCorreio;
	
	//atributo utilizado para controle do render do botao de prosseguri ,
	//caso venha lista vazia ele é setado para falso
	private boolean renderedBotao;
	
//	private boolean firstCheck;
	
	private int numeroContasSelecionadas;
	
	//Objeto utilizado para pagamento da conta
	private Conta contaPagamentoEletronico;
	
	//Utilizado na tela de endereço de correspondecia para envio da 2 via
	private String flagEndereco2Via = "true";
	
	@Autowired
	private  BancoConveniadoPgtEletronicoService eletronicoService;
	/**
	 * Atributo de controle do modal.
	 */
	private boolean modalRendered = false;
	
	  // tabela contendo beans (binding)
    private HtmlDataTable componentDataTable;
	
	 // checkbox para selecao de todos os registros (binding)
    private HtmlSelectBooleanCheckbox componentCheckBox2Via = null;
    
    private HtmlSelectBooleanCheckbox componentCheckBoxCorreio = null;
    
    // define se varios beans podem ser selecionados
    private boolean isMulpitleSelection = true;
    
    // total de registros selecionados
    private int totalBeansSelected = 0;
    
    // total de registros selecionados
    private int totalBeansSelectedCorreio = 0;   
    
    //Valor utilizado para efetuar o valor do envio da conta pelo correio.
    private double valorEnvio2Via = 0;
    
    private Endereco endereco2Via = new Endereco();
    
    private ArrayList<SelectItem> listaBancosConveniados;
    
    private String linkBancoConveniado;
    
    private String codBancoConveniado;

	private boolean renderedRolComum;

	private boolean renderedRolEspecial;

	private int requestNum = 0;
	private int requestNumLog = 0;
	
	private String checkPrimeiro = "0";
    
	public ContaBBean() {}
	
	/**
	 * Metodo responsavel por inicializar o Bean.
	 * 
	 * Escopo de Sessao.
	 */
	public void initialize(){
		inicializar(null);
	}
	
 // seleciona o bean quando o checkbox correpondente é clicado
    public void selectOne2Via(ValueChangeEvent event) {
    	agvlogger.info("Inicio -> selectOne2Via");
    	agvlogger.info("Request" + ++requestNumLog);	
        
        // bloqueia request invalida
        if(!Boolean.valueOf(event.getNewValue().toString()).booleanValue()){
    		check2via = false;
	        if(isInvalidRequest(++requestNum)){
	        	return;
	        }
    	}
       if (!queue(event)) {
          boolean checked = Boolean.valueOf(event.getNewValue().toString())
                .booleanValue();
          
          totalBeansSelectedCorreio = 0;
          // atualiza a quantidade de registros selecionados
          totalBeansSelected = checked? ++totalBeansSelected: --totalBeansSelected;
          checkCorreio = false;
          // se for selecao unica deseleciona tudo
          if (!isMulpitleSelection()) {
        	  selectAll2Via(false);
          }
          selectAllCorreioLimpar(false);
 
          // seta o bean selecionado como corrente
          currentBean = (Conta) componentDataTable.getRowData();
          currentBean.setImprimirSegundaVia(checked);
          
          renderedImprimir = true;
          renderedCorreio = false;
          
          verificaSelecionaTodosImprimir();
          
          if(totalBeansSelected == 0)
        	  renderedImprimir = false;
       }
    }

	/**
	 * 
	 */
	private void verificaSelecionaTodosImprimir() {
		// atualiza o check global
          int contadorContas = 0; 
    	  for (Conta element : listaContas) {
        	  if(!element.isBloqueioImprimir())
        		  ++contadorContas;
    	  }
          check2via = totalBeansSelected == contadorContas;
	}
    
    
    public void selectOneCorreio(ValueChangeEvent event) {
       agvlogger.info("Inicio -> selectOneCorreio");
       agvlogger.info("Req" + ++requestNumLog);
       
       // bloqueia request invalida
   		if(!Boolean.valueOf(event.getNewValue().toString()).booleanValue()){
	       if(isInvalidRequest(++requestNum)){
	       	return;
	       }
        }
       
       if (!queue(event)) {
          boolean checked = Boolean.valueOf(event.getNewValue().toString())
                .booleanValue();
          
          totalBeansSelected = 0;
          // atualiza a quantidade de registros selecionados
          totalBeansSelectedCorreio = checked? ++totalBeansSelectedCorreio: --totalBeansSelectedCorreio;
          check2via = false;
          // se for selecao unica deseleciona tudo
          if (!isMulpitleSelection()) {
        	  selectAllCorreio(false);
          }
          selectAllLimpar(false);
           
          // seta o bean selecionado como corrente
          currentBean = (Conta) componentDataTable.getRowData();
          currentBean.setReceberPeloCorreio(checked);
          
          renderedCorreio = true;
          renderedImprimir = false;
          
          verificaSelecionaTodosCorreio();
          
          if(totalBeansSelectedCorreio == 0)
        	  renderedCorreio = false;
       }
    }

	/**
	 * 
	 */
	private void verificaSelecionaTodosCorreio() {
		// atualiza o check global
          int contadorContas = 0; 
    	  for (Conta element : listaContas) {
        	  if(!element.isBloqueioCorreio())
        		  ++contadorContas;
    	  }
       	  checkCorreio = totalBeansSelectedCorreio == contadorContas;
	}
    
    
    // (de)seleciona todos os registros quando o checkbox do cabecalho é clicado
    public void selectAll2Via(ValueChangeEvent event) {
    	agvlogger.info("Inicio -> selectAll2Via");	
    	agvlogger.info("Req" + ++requestNumLog);  	
         
    	    	
       if (!queue(event)) {
    	   // atualiza a quantidade de registros selecionados
          int contadorContas = 0; 
     	  for (Conta element : listaContas) {
         	  if(!element.isBloqueioImprimir())
         		  ++contadorContas;
     	  }
    	  
     	  if(contadorContas != 0){
	          boolean checked = ((Boolean) event.getNewValue()).booleanValue();
	          selectAll2Via(checked);
	          if(!checked){
	        	  renderedImprimir = false;
	              renderedCorreio = false;  
	          }else{
	        	  renderedImprimir = true;
	        	  renderedCorreio = false;  
	          }
	    	  
	          totalBeansSelected = checked? contadorContas : 0;
	          checkCorreio = false;
     	  }
       }
    }
    
    // (de)seleciona todos os registros quando o checkbox do cabecalho é clicado
    public void selectAllCorreio(ValueChangeEvent event) {
    	agvlogger.info("Inicio -> selectAllCorreio");
    	agvlogger.info("Req" + ++requestNumLog);
   
    	
    	
    	 if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
             event.setPhaseId(PhaseId.INVOKE_APPLICATION);
             event.queue();
             return;
    	 }
    	  int contadorContas = 0; 
	   	  for (Conta element : listaContas) {
	       	  if(!element.isBloqueioCorreio())
	       		  ++contadorContas;
	   	  }
    	 
	   	  if(contadorContas != 0){
	        boolean checked = ((Boolean) event.getNewValue()).booleanValue();
			selectAllCorreio(checked);
			if (!checked) {
				renderedImprimir = false;
				renderedCorreio = false;
			} else {
				renderedCorreio = true;
				renderedImprimir = false;
				checkCorreio = checked;
			}
			// atualiza a quantidade de registros selecionados

			totalBeansSelectedCorreio = checked ? contadorContas : 0;
			check2via = false;
	   	  }
    }    
    
    /**
     * Verifica se uma request é invalida.
     * 
     * Requests invalidas sao enviadas pela utilizacao do atributo valueChangeListener.
     * O controle da quantidade de requests invalidas e feito pelo numero check box em tela.
     * 
     * numero check box em tela = getNumLinhasPorPagina() * 2
     */
    private boolean isInvalidRequest(int requestNum){
    	// encontra numero max de requests a serem bloqueadas
    	int maxRequestsBlock = Integer.parseInt(getNumLinhasPorPagina());
    	maxRequestsBlock = (listaContas.size() < maxRequestsBlock) ? listaContas.size() : maxRequestsBlock;
    	
    	if(requestNum <= maxRequestsBlock * 2){
    		agvlogger.info("Request " + requestNum + " bloqueada!");
    		return Boolean.TRUE;
    	}
    	return Boolean.FALSE;
    }    

    
    public String selecionarContaErro(){
    	FacesContext.getCurrentInstance().addMessage(null,
	    new FacesMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("segundavia.seleciona.conta"), null));
    	return "ERRO";
    }
    
    // (de)seleciona todos os beans
    private void selectAll2Via(boolean checked) {
    	agvlogger.info("Inicio -> selectAll -> setImprimirSegundaVia");
    	
    	for (Iterator iterator = listaContas.iterator(); iterator.hasNext();) {
			Conta element = (Conta) iterator.next();
			if(!element.isBloqueioImprimir()){
				element.setImprimirSegundaVia(checked);
			}
		}
    	selectAllCorreioLimpar(false);
    }
    
    // (de)seleciona todos os beans
    private void selectAllLimpar(boolean checked) {
    	agvlogger.info("Inicio -> selectAllLimpar -> setImprimirSegundaVia");
    	
    	for (Iterator iterator = listaContas.iterator(); iterator.hasNext();) {
			Conta element = (Conta) iterator.next();
			element.setImprimirSegundaVia(checked);
		}
    }

    
   /* // (de)seleciona todos os registros quando o checkbox do cabecalho é clicado
    public void selectAllCorreio(ValueChangeEvent event) {
    	 if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
             event.setPhaseId(PhaseId.INVOKE_APPLICATION);
             event.queue();
             return;
    	 }
          boolean checked = ((Boolean) event.getNewValue()).booleanValue();
          selectAllCorreio(checked);
          if(!checked){
        	  renderedImprimir = false;
              renderedCorreio = false;  
          }else{
        	  renderedCorreio = true;
        	  renderedImprimir = false;
          }
          // atualiza a quantidade de registros selecionados
          totalBeansSelectedCorreio = checked? listaContas.size() : 0;
          check2via = false;
    }*/
    
    // (de)seleciona todos os beans
    private void selectAllCorreio(boolean checked) {
    	agvlogger.info("Inicio -> selectAllCorreio -> setReceberPeloCorreio");
    	
    	for (Iterator iterator = listaContas.iterator(); iterator.hasNext();) {
			Conta element = (Conta) iterator.next();
			if(!element.isBloqueioCorreio()){
				element.setReceberPeloCorreio(checked);
			}
		}
    	selectAllLimpar(false);
    }
    
 // (de)seleciona todos os beans
    private void selectAllCorreioLimpar(boolean checked) {
    	agvlogger.info("Inicio -> selectAllCorreioLimpar -> setReceberPeloCorreio");
    	
    	for (Iterator iterator = listaContas.iterator(); iterator.hasNext();) {
			Conta element = (Conta) iterator.next();
			element.setReceberPeloCorreio(checked);
		}
    }
    
 
    private boolean queue(FacesEvent event) {
       boolean queue = false;
       if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
          event.setPhaseId(PhaseId.INVOKE_APPLICATION);
          event.queue();
          queue = true;
       }
       return queue;
    }
    
    /**
     * Solicita Acatamento  de 1 ou N contas
     * @return
     */
    public String solicitaAcatementoCorreio2Via(){
    	
    	try{
    		ArrayList<Conta> listaContasPeloCorreio = new ArrayList<Conta>();

    		for (Iterator iterator = listaContas.iterator(); iterator.hasNext();) {
				Conta element = (Conta) iterator.next();
				
				if(element.getReceberPeloCorreio()){
					listaContasPeloCorreio.add(element);
				}
    		}	

			Acatamento2ViaTO acatamentoTemp = new Acatamento2ViaTO();
			
			if(flagEndereco2Via.equals("true")){
				acatamentoTemp.setEndereco(getDadosImoveisTO().getEndereco());
			}else{
				if(!validaCamposEndereco(getEndereco2Via())){
					return "CONTA_ENDERECO_2VIA_CORREIO";
				}else{
					acatamentoTemp.setEndereco(getEndereco2Via());
				}
			}

			acatamentoTemp.setRgi(WrapperUtils.toLong(getDadosImoveisTO().getImovel().getDsRgi()));
			acatamentoTemp.setContas(listaContasPeloCorreio);
			
			contaService.solicitaAcatamento2ViaCorreio(acatamentoTemp);
    	}catch (Exception e) {
    		agvlogger.error("ERRO ACATAMENTO"+ e.getMessage());
    		return tratarErroSistemico(e); 
		}
    	
    	return "SOLICITACAO_SUCESSO";
    }
    
   

	/**
     * Valida campos da tela de 2 via de conta via correio. 
     * @param endereco2Via2
     * @return
     */
    private boolean validaCamposEndereco(Endereco endereco2Via2) {
    	if ("".equals(endereco2Via2.getDsEndereco().trim())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR,  FacesBundleUtil.getInstance().getString("segundavia.endereco.dsendereco"), null));
			return false;
		}
    	if ("".equals(endereco2Via2.getNrEndereco())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("segundavia.endereco.numero"), null));
    		return false;
		}
    	if ("".equals(endereco2Via2.getCdCep().toString())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("segundavia.endereco.cep"), null));
    		return false;
		}
    	if ("".equals(endereco2Via2.getNmMunicipio().toString())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("segundavia.endereco.municipio"), null));
    		return false;
		}
    	if ("".equals(endereco2Via2.getDsUF().toString())) {
    		FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, FacesBundleUtil.getInstance().getString("segundavia.endereco.uf"), null));
    		return false;
		}
		return true;
	}

	/**
     * Valida regras de pagamento eletronico para um conta 
     * @return
     */
    public String carregaPagamentoEletronico(){
    	String contaSelecionada = (String)FacesContext.getCurrentInstance().getExternalContext()
		.getRequestParameterMap().get("contaParam");
    	
    	for (Conta element : listaContas) {
    		if(element.getNumeroConta().equals(contaSelecionada)){
    			contaPagamentoEletronico = element;
    			/**
    			 * Para clientes que são entidades públicas (grupo 41) 
    			 * o mês de referência deve ser igualado ao mês do vencimento.
    			 */
    			if(contaPagamentoEletronico.getCodCobrancaCadastro() != null && contaPagamentoEletronico.getCodCobrancaCadastro().equals("9")){
    				contaPagamentoEletronico.setDtReferencia(contaPagamentoEletronico.getDtVencimento());
    			}
    			break;
    		}
		}
    	contaService.gerarCodigoBarras(contaPagamentoEletronico);
    	listaBancosConveniados =  eletronicoService.buscaBancoConveniado();
    	return "CONTA_PAGAMENTO_ELETRONICO";
    }
    
    /**
     * Altera valor do link do banco conveniado
     * @param e
     */
    public void changeValueLinkBanco(ValueChangeEvent e){
    	BancoConveniadoPagEletronico bancoPagEletronico = eletronicoService.findById(WrapperUtils.toLong((String) e.getNewValue()));
    	linkBancoConveniado = bancoPagEletronico.getDsLink();
    }
    
    /**
     * Gera a solicitacao do Pag Eletrônio e redireciona o usuário a pagina do banco
     * @return
     */
    public String solicitaPagamentoEletronico(){
    	
    	if(codBancoConveniado == null || codBancoConveniado.equals("")){
    		FacesContext.getCurrentInstance().addMessage(null,
    	    new FacesMessage(FacesMessage.SEVERITY_ERROR,FacesBundleUtil.getInstance().getString("segundavia.pagamento.eletronico.banco"), null));
    		return "ERRO";
    	}
    	if(contaPagamentoEletronico.getExtrato()){
    		FacesContext.getCurrentInstance().addMessage(null,
    	    new FacesMessage(FacesMessage.SEVERITY_ERROR,FacesBundleUtil.getInstance().getString("segundavia.pagamento.eletronico.extrato"), null));
    		return "ERRO";
    	}
    	if(contaPagamentoEletronico.getDebitoAutomatico()){
    		FacesContext.getCurrentInstance().addMessage(null,
    		new FacesMessage(FacesMessage.SEVERITY_ERROR,FacesBundleUtil.getInstance().getString("segundavia.pagamento.eletronico.debito.automatico"), null));
    		return "ERRO";
    	}
    	
//    	this.listaCorrelacao = correlacaoService.findByAtendimento(autoAtendimento.getCodigo(), false);
    	
    	return "CONCLUIR";
    }
    
	public boolean isMulpitleSelection() {
		return isMulpitleSelection;
	}

	public void setMulpitleSelection(boolean isMulpitleSelection) {
		this.isMulpitleSelection = isMulpitleSelection;
	}

	public int getTotalBeansSelected() {
		return totalBeansSelected;
	}

	public void setTotalBeansSelected(int totalBeansSelected) {
		this.totalBeansSelected = totalBeansSelected;
	}

	public ArrayList<Conta> getListaContas() {
		return listaContas;
	}

	public void setListaContas(ArrayList<Conta> listaContas) {
		this.listaContas = listaContas;
	}
	
	public void inicializar(ActionEvent e){
		initBasicPesquisaBBean();
		requestNum = 0;
		checkPrimeiro = "0"; //variavel para evitar bug do icefaces
		this.listaContas = contaService.pesquisaContasEmAberto(getDadosImoveisTO());
		renderedCorreio = false;
		renderedImprimir = false;
		
		if(getDadosImoveisTO().getImovel().isRolEspecial()){
			this.renderedRolEspecial = true;
			this.renderedRolComum = false;
		}else{
			this.renderedRolComum = true;
			this.renderedRolEspecial = false;
		}
		if(listaContas.isEmpty()){
			renderedBotao = false;
		}else{
			renderedBotao = true;
		}
		
		agvlogger.info("Metodo initialize executado!");
	}
	
	public void prosseguirListarContaPorEndereco(ActionEvent e){
		getDadosImoveisTO().setImovel(getImovelSelecionadoView());
		this.listaContas = contaService.pesquisaContasEmAberto( getDadosImoveisTO() );
	}
	
	/**
	 * Controle da alteração de check Imprimir
	 * @param e
	 */
	public void changeImprimir(ValueChangeEvent e){
		for (Iterator iterator = listaContas.iterator(); iterator.hasNext();) {
			Conta element = (Conta) iterator.next();
			element.setReceberPeloCorreio(false);
		}
	}
	
	/**
	 * Controle da alteração de check Correio
	 * @param e
	 */
	public void changePeloCorreio(ValueChangeEvent e){
		for (Iterator iterator = listaContas.iterator(); iterator.hasNext();) {
			Conta element = (Conta) iterator.next();
			element.setImprimirSegundaVia(false);
		}
	}
	
	
	/**
	 * Verifica qual ação tomar , podendo emitir segunda via pelo correio , imprimir boleto ou concluir o atendimento
	 * @return
	 */
	public String criarAcatamentoEnvioEndereco(){
		
		String outcome = null;	
		
		try{
			/*// verifica se existe algum check de contas 
			for( Conta conta : listaContas ) {
				
				if( conta.getReceberPeloCorreio() ) {
					enviarCorrerio = true;
					break;
				}
			}*/
			//Se esitver selecionado algum check de Envio de segunda via pelo correio
			//carrega tela de endereço para acatamento
			outcome = criaAcatamentoEndereco();	
			//Se não existir seleção de contas na tela , direciona para tela de conclusão do atendimento
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(FacesMessage.SEVERITY_ERROR,FacesBundleUtil.getInstance().getString("segundavia.emitir.boleto"), null));
			e.printStackTrace();
		}
		
		return outcome;
	}

	/**
	 * @param outcome
	 * @return
	 * @throws Exception
	 */
	private void carregarContasImpressao() throws Exception {
		for( Conta conta : listaContas ) {
			
			if( conta.getImprimirSegundaVia() && !listaContasImpressaoSegundaVia.containsKey(conta.getDataReferenciaAnoJuliano()) ) {
				
				//Se for Rol Comum !+ 6
				if( !conta.getTpConta().equals("6") ) {
					renderedRolComum = true;
					renderedRolEspecial = false;
					conta = contaService.complementarDadosBoleto(conta ,getDadosImoveisTO());
					verificaValoresContaRolComum(conta);
					conta.setCodigoTransacao( gerarCodigoTransacao() );
					listaContasImpressaoSegundaVia.put(conta.getDataReferenciaAnoJuliano(),conta);
					
				 //Se for Rol Especial = 6
				}else if( conta.getTpConta().equals("6") ) { 
					try{
					renderedRolEspecial = true;
					renderedRolComum = false;
					conta = contaService.complementarDadosBoletoRolEspecial(conta ,getDadosImoveisTO());
					conta.setCodigoTransacao( gerarCodigoTransacao() );
					listaContasImpressaoSegundaVia.put(conta.getDataReferenciaAnoJuliano(),conta);
//					
					}catch (Exception e) {
						 FacesContext.getCurrentInstance().addMessage(null,
				         new FacesMessage(FacesMessage.SEVERITY_ERROR,FacesBundleUtil.getInstance().getString("segundavia.emitir.boleto"), null));
					}
				}
			}
		}
	}

	/**
	 * @param conta
	 */
	private void verificaValoresContaRolComum(Conta conta) {
		if( conta.getContaNormal().isExibir() ) {
			
			Double vlMulta = parseDouble(conta.getContaNormal().getVlMulta());
			Double vlAM = parseDouble(conta.getContaNormal().getVlAtualizacaoMonetaria());
			Double vlJuros = parseDouble(conta.getContaNormal().getVlJurosMora());
			Double vlCredito = parseDouble(conta.getContaNormal().getVlCreditos());
			Double vlDebito = parseDouble(conta.getContaNormal().getVlDebitos());
			Double vlRetencao = parseDouble(conta.getContaNormal().getVlRetencao());
			Double vlServico = parseDouble(conta.getContaNormal().getVlServico());
			
			if( vlMulta != null && vlMulta > 0.0 )
				conta.setRenderedMulta(true);
			if( vlAM != null && vlAM > 0.0 )
				conta.setRenderedAtualizacaoMonetaria(true);
			if( vlJuros != null && vlJuros > 0.0 )
				conta.setRenderedJurosMora(true);
			if( vlCredito != null && vlCredito > 0.0 )
				conta.setRenderedCredito(true);
			if( vlDebito != null && vlDebito > 0.0 )
				conta.setRenderedDebito(true);
			if( vlRetencao != null && vlRetencao > 0.0 )
				conta.setRenderedRetencao(true);
			if( vlServico != null && vlServico > 0.0 )
				conta.setRenderedServico(true);
		}
	}
	
	/**
	 * carrega dados para escolha do endereço de envio
	 */
	public String criaAcatamentoEndereco(){
		numeroContasSelecionadas = 0;
		for (Conta element : listaContas) {
			if(element.getReceberPeloCorreio()){
				getDadosImoveisTO().getImovel().setCodigoSuperIntendencia(element.getCdunidcom());
				numeroContasSelecionadas++;
			}
		}
		endereco2Via =  new Endereco();
		valorEnvio2Via = contaService.pesquisaTarifaSegundaViaConta(getDadosImoveisTO());
		valorEnvio2Via = valorEnvio2Via*numeroContasSelecionadas;
		return "CONTA_ENDERECO_2VIA_CORREIO";
	}

	public String paginaInicial(){
		return "PAGINAINICIAL";
	}

	public Conta getCurrentBean() {
		return currentBean;
	}

	public void setCurrentBean(Conta currentBean) {
		this.currentBean = currentBean;
	}

	public HtmlDataTable getComponentDataTable() {
		return componentDataTable;
	}

	public void setComponentDataTable(HtmlDataTable componentDataTable) {
		this.componentDataTable = componentDataTable;
	}

	public HtmlSelectBooleanCheckbox getComponentCheckBox2Via() {
		return componentCheckBox2Via;
	}

	public void setComponentCheckBox2Via(
			HtmlSelectBooleanCheckbox componentCheckBox2Via) {
		this.componentCheckBox2Via = componentCheckBox2Via;
	}

	public HtmlSelectBooleanCheckbox getComponentCheckBoxCorreio() {
		return componentCheckBoxCorreio;
	}

	public void setComponentCheckBoxCorreio(
			HtmlSelectBooleanCheckbox componentCheckBoxCorreio) {
		this.componentCheckBoxCorreio = componentCheckBoxCorreio;
	}

	public int getTotalBeansSelectedCorreio() {
		return totalBeansSelectedCorreio;
	}

	public void setTotalBeansSelectedCorreio(int totalBeansSelectedCorreio) {
		this.totalBeansSelectedCorreio = totalBeansSelectedCorreio;
	}

	public boolean isCheck2via() {
		return check2via;
	}

	public void setCheck2via(boolean check2via) {
		this.check2via = check2via;
	}

	public boolean isCheckCorreio() {
		return checkCorreio;
	}

	public void setCheckCorreio(boolean checkCorreio) {
		this.checkCorreio = checkCorreio;
	}
	
	public void atualizarModal(ActionEvent e) {
		this.modalRendered = !modalRendered;
	}
	
	public boolean isModalRendered() {
		return modalRendered;
	}

	public void setModalRendered(boolean modalRendered) {
		this.modalRendered = modalRendered;
	}

	public String getFlagEndereco2Via() {
		return flagEndereco2Via;
	}

	public void setFlagEndereco2Via(String flagEndereco2Via) {
		this.flagEndereco2Via = flagEndereco2Via;
	}
	
	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}

	public int getNumeroContasSelecionadas() {
		return numeroContasSelecionadas;
	}

	public void setNumeroContasSelecionadas(int numeroContasSelecionadas) {
		this.numeroContasSelecionadas = numeroContasSelecionadas;
	}

	public double getValorEnvio2Via() {
		return valorEnvio2Via;
	}

	public void setValorEnvio2Via(double valorEnvio2Via) {
		this.valorEnvio2Via = valorEnvio2Via;
	}

	public Endereco getEndereco2Via() {
		return endereco2Via;
	}

	public void setEndereco2Via(Endereco endereco2Via) {
		this.endereco2Via = endereco2Via;
	}

	public Conta getContaPagamentoEletronico() {
		return contaPagamentoEletronico;
	}

	public void setContaPagamentoEletronico(Conta contaPagamentoEletronico) {
		this.contaPagamentoEletronico = contaPagamentoEletronico;
	}

	public BancoConveniadoPgtEletronicoService getEletronicoService() {
		return eletronicoService;
	}

	public void setEletronicoService(
			BancoConveniadoPgtEletronicoService eletronicoService) {
		this.eletronicoService = eletronicoService;
	}

	public ArrayList<SelectItem> getListaBancosConveniados() {
		return listaBancosConveniados;
	}

	public void setListaBancosConveniados(
			ArrayList<SelectItem> listaBancosConveniados) {
		this.listaBancosConveniados = listaBancosConveniados;
	}

	public String getLinkBancoConveniado() {
		return linkBancoConveniado;
	}

	public void setLinkBancoConveniado(String linkBancoConveniado) {
		this.linkBancoConveniado = linkBancoConveniado;
	}

	public String getCodBancoConveniado() {
		return codBancoConveniado;
	}

	public void setCodBancoConveniado(String codBancoConveniado) {
		this.codBancoConveniado = codBancoConveniado;
	}

	public HashMap<String,Conta> getListaContasImpressaoSegundaVia() {
		//Verifica se lista para impressao não é vazia
//		listaContasImpressaoSegundaVia =  new HashMap<String, Conta>();
		try {
			if(listaContasImpressaoSegundaVia != null){
				carregarContasImpressao();
			}else{
				listaContasImpressaoSegundaVia =  new HashMap<String, Conta>();
				carregarContasImpressao();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		listaImpressao = new ArrayList<Conta>();
		listaImpressao.addAll(listaContasImpressaoSegundaVia.values());
		
		for( Conta conta : listaContas ) {
			if(!conta.getImprimirSegundaVia()){
				listaImpressao.remove(conta);
			}
		}
					
		return listaContasImpressaoSegundaVia;
	}

	
	private String gerarCodigoTransacao(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		String codigoTransacao = String.valueOf( session.getId() + System.currentTimeMillis() );
		return codigoTransacao;
	}

	public boolean isRenderedBotao() {
		return renderedBotao;
	}

	public void setRenderedBotao(boolean renderedBotao) {
		this.renderedBotao = renderedBotao;
	}

	public boolean isRenderedImprimir() {
		return renderedImprimir;
	}

	public void setRenderedImprimir(boolean renderedImprimir) {
		this.renderedImprimir = renderedImprimir;
	}

	public boolean isRenderedCorreio() {
		return renderedCorreio;
	}

	public void setRenderedCorreio(boolean renderedCorreio) {
		this.renderedCorreio = renderedCorreio;
	}

	public boolean isRenderedRolComum() {
		return renderedRolComum;
	}

	public void setRenderedRolComum(boolean renderedRolComum) {
		this.renderedRolComum = renderedRolComum;
	}

	public boolean isRenderedRolEspecial() {
		return renderedRolEspecial;
	}

	public void setRenderedRolEspecial(boolean renderedRolEspecial) {
		this.renderedRolEspecial = renderedRolEspecial;
	}

	public ArrayList<Conta> getListaImpressao() {
		return listaImpressao;
	}

	public void setListaImpressao(ArrayList<Conta> listaImpressao) {
		this.listaImpressao = listaImpressao;
	}

	public void setListaContasImpressaoSegundaVia(
			HashMap<String, Conta> listaContasImpressaoSegundaVia) {
		this.listaContasImpressaoSegundaVia = listaContasImpressaoSegundaVia;
	}
	

	public String getNumLinhasPorPagina(){
		return LINHAS_PAGINACAO_10;
	}

	public String getCheckPrimeiro() {
		if(checkPrimeiro.equals("0")){
			checkPrimeiro = "1";
			return "0";
		}else{
			return checkPrimeiro;
		}
		
	}

	public void setCheckPrimeiro(String checkPrimeiro) {
		this.checkPrimeiro = checkPrimeiro;
	}
	
	
}
