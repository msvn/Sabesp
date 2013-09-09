package com.prime.app.agvirtual.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibm.icu.math.BigDecimal;
import com.prime.app.agvirtual.dao.ContaDao;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.enums.AgvStatusConta;
import com.prime.app.agvirtual.enums.AgvStatusPagamento;
import com.prime.app.agvirtual.enums.TipoConsumo;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.util.ContaUtil;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

/**
 * Classe responsavel pela pesquisa de contas
 * @author gustavons
 *
 */
@Repository
public class ContaDaoImpl extends GenericRDMSJpaDao<Imovel, Long> implements ContaDao {
	
	
	private static final Logger agvlogger = LoggerFactory.getLogger(ContaDaoImpl.class);
	
	/**
	 * Pesquisa Inicial de contas em aberto , chamando após obter todas as contas , subpesquisas para 
	 * tratamento de exibição
	 */
	public ArrayList<Conta> pesquisaContasEmAberto(DadosImoveisTO dadosImoveisTO) {
		
		ArrayList<Conta> listaContas = new ArrayList<Conta>();
		
		
		/**
		 * Operação Dona Assunta
		 * 
		 * O RGI é 0067582150.

			As contas onde a mensagem deverá estar vinculada/exibida:
			   setembro a deszembro/98
			   janeiro a fevereiro/99
			   maio a agosto/99
			   outubro a dezembro/99
			   janeiro a fevereiro/2000
			   junho a dezembro/2000
			   janeiro/2001
			   novembro a dezembro/2004
			   janeiro a dezembro/2005
			   janeiro a dezembro/2006
			   janeiro a novembro/2007,
	     *		   
		 */
		
		//FIXME Gustavo implementar regra dona sunta
		
		agvlogger.info("#ContaDaoImpl# -> Inicializa #pesquisaContasEmAberto# \n");
		
		try {

			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement("");
			/**
			 * Importante caso seja rol especial , seta o rgiMaster e guarda o rgi do filho em outra variável 
			 */
			Imovel imovelTemp = dadosImoveisTO.getImovel();
				if(dadosImoveisTO.getCliente().getDsRgiImovelMaster() != null){
					
					Imovel imovelMaster = (Imovel) BeanUtils.cloneBean(dadosImoveisTO.getImovel());
					imovelMaster.setDsRgi(dadosImoveisTO.getCliente().getDsRgiImovelMaster());
					imovelMaster.setCodigoControleFaturamento(dadosImoveisTO.getImovel().getCodigoControleFaturamento());
					imovelMaster.setCdMunicipio(dadosImoveisTO.getImovel().getCdMunicipio());
					imovelMaster.setCdCliente(dadosImoveisTO.getImovel().getCdCliente());
					dadosImoveisTO.setImovel(imovelMaster);
					listaContas = findContasEmAberto(imovelMaster);
				}else{
					listaContas = findContasEmAberto(imovelTemp);
				}
			
			/**
			 * 
			 */
			
			
			agvlogger.info("#ContaDaoImpl# -> Loop de Contas # \n");
			for (Iterator<Conta> iterator = listaContas.iterator(); iterator.hasNext();) {
				Conta element = (Conta) iterator.next();
				
				element = pesquisaSituacaoConta(element,pstmt); //Pesquisa Ok
				element = pesquisaDebitoAutomatico(element);// Pesquisa Ok
				element = pesquisaContaPertenceExtrato(element);//Pesquisa campos Ok
				
				//Se for grande consumidor
				if(element.getTpConta().equals("6")){
					element = pesquisaNrCicloNrUnidade(element); //Pesquisa campos ok
					element = pesquisaContaEmitidaTace(element); //falta testar
				}
			}
			
			
			pesquisaSuperIntendenciaRgi(dadosImoveisTO);
			imovelTemp.setCodigoSuperIntendencia(dadosImoveisTO.getImovel().getCodigoSuperIntendencia());
			/**
			 * Aplica regras de frontEnd nos combos de imprimir , 2º via
			 * Caso de Uso RN2
			 */
			validaRegrasSituacaoConta(listaContas,dadosImoveisTO);
			
			/**
			 * remove as contas que não foram validadas
			 */
			removerContasNaoValidas(listaContas);
			
			
			/**
			 * Volta o RGI filho para dadosImoveis
			 */
//			dadosImoveisTO.setImovel(imovelTemp);
		
			agvlogger.info("#ContaDaoImpl# -> Fim Loop de Contas # \n");
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return (listaContas);
	}

	
	private void removerContasNaoValidas(ArrayList<Conta> listaContas) {
		ArrayList<Conta> remover =  new ArrayList<Conta>();
		for (Conta element : listaContas) {
			if(element.isRemoverListaTela()){ 
				remover.add(element);
			}
		}
		listaContas.removeAll(remover);
	}

	/**
	 * Fatura liberada (verificar event.cdhistfat)	(sem mensagem)	69			
	 * @param listaContas
	 * @param dadosImoveisTO
	 */
	private void validaRegrasSituacaoConta(ArrayList<Conta> listaContas,
			DadosImoveisTO dadosImoveisTO) {
		//Para cada conta passa pelas regras
		for (Conta conta : listaContas) {
			
			//Verifica se é grande consumidor / rol especial
			if(conta.getTpConta().equals("6")){
				
				//Carrega Codigo Historico de Faturamento utilizado para validação
 				pesquisaCodigoHistoricoFaturamento(conta,dadosImoveisTO);
 				pesquisaLigacaoSuprimidaTipoHidrometro(conta);
				if(conta.getCodHistorico() !=  null && conta.getCodHistorico().equals("69")){
					pesquisaLigacaoSuprimidaTipoHidrometro(conta);//TODO deixar so essa chamada
					pesquisaRetencaoConta(conta);
					/**
					 * Para clientes listados no rol de grandes consumidores, não serão exibidas as contas que
					 *  não tenham sido emitidas pelo TACE, quando ainda não tiver ocorrido o evento 1X do cronograma comercial 
					 *  referente ao seu grupo e mês de emissão.
					 */
					if(!conta.getEmitidoTace()){
						conta.bloquear(true,true,true,true);
					}
					
					/**
					 * Conta sem retenção de impostos cujo RGI é de estatal federal ou entidade pública federal
					 *	CCG50.TPRETENC = 0 (não tem retenção) 
					 *  E (CCG03 - CADASTRO E EVENT - DÉBITO).CDCOBR=7 (ROL ESPECIAL)				 
					 */
					if(conta.getTipoRetencao() != null && Integer.valueOf(conta.getTipoRetencao()).intValue() == 0  &&
							conta.getCodCobrancaDebito().equals("7") &&
							conta.getCodCobrancaCadastro().equals("7")){
						conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao9"));
						conta.bloquear(true,true,true);
					}
					/**
					 * Conta com retenção de impostos cujo RGI não é de estatal federal ou entidade pública federal
					 * CCG50.TPRETENC>0 É ENTIDADE PÚLBICA FED OU ESTATAL FEDERAL 
					 * E (CCG03 - CADASTRO E EVENT - DÉBITO).CDCOBR != 7 (ROL ESPECIAL)
					 */
					else if(conta.getTipoRetencao() != null && Integer.valueOf(conta.getTipoRetencao()).intValue() > 0  &&
							!conta.getCodCobrancaDebito().equals("7") &&
							!conta.getCodCobrancaCadastro().equals("7")){
						
						conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao10"));
						conta.bloquear(true,true,true);
					
					//validação alterada para rol especial.
					}else if(conta.getIspec().equals("CFRAE") || 
							conta.getIspec().equals("CFRAF") ||
							conta.getIspec().equals("CFRAG") ){
						conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao5"));
						conta.bloquear(false,true,false);
					}
					else{
						conta.bloquear(false,false,false); //deixa todos as funcionalidades liberadas	
					}
					
				}else{
					setaMotivoBloqueio(conta);
					conta.bloquear(true,true,true); //bloqueia todas as funcionalidades
				}
				
			//Rol Comum
			}else{ 
				/**
				 * Conta em processo de análise
				 */
				if(conta.getSituacaoPagamento().getCodigo().intValue() ==  AgvStatusPagamento.SUSPENSO.getCodigo().intValue()){ //SUSPENSO = 4
					conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao1"));
					conta.bloquear(true,true,true);
				}
				/**
				 * Conta relativa a parcela vencida
				 */
				else if(conta.getIspec().equals("CFRAB") && conta.getDtVencimento().before(new Date())){
					conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao2"));
					conta.bloquear(true,true,true);
				}
				/**
				 * Conta é relativa a uma parcela
				 */
				else if(conta.getIspec().equals("CFRAB")){
					conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao3"));
					conta.bloquear(false,true,false);
				}
				/**
				 * Conta em cobrança jurídica
				 */
				else if(conta.getSituacaoCorSup().equals("A") || conta.getSituacaoCorSup().equals("J")){
					conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao4"));
					conta.bloquear(true,true,true);
				}
				/**
				 * Conta relativa a débito pendente
				 * EVENT.ISPEC = CFRAE, CFRAF OU CFRAG
				 */
				else if(conta.getIspec().equals("CFRAE") || 
						conta.getIspec().equals("CFRAF") ||
						conta.getIspec().equals("CFRAG") ){
					conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao5"));
					conta.bloquear(true,true,true);
				}
				/**
				 * Conta com débito em conta corrente
				 */
				else if(conta.getDebitoAutomatico()){
					conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao7"));
					conta.bloquear(false,true,false);
				}
				
				/**
				 * Contas cujas solicitações de emissão via correio excederam o limite de cinco
				 */
				else if(false){ //FIXME Gustavo Corrigir regra
					conta.bloquear(false,true,false);
				}else{
					pesquisaRetencaoConta(conta);
					pesquisaLigacaoSuprimidaTipoHidrometro(conta);
					
					/**
					 * Conta de Entidade Publica
					 * CCG50.CDORGPUB != 0
					 */
					if(conta.getCodigoOrgaoPublico() != null && !conta.getCodigoOrgaoPublico().equals("0")){
						conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao6"));
						conta.bloquear(false,true,false);
					}
					
					/**
					 * RGI está suprimido
					 * CCG01.STLIGACAO != 0, R OU C
					 */
					if(conta.getSituacaoLigacao() != null &&
							!conta.getSituacaoLigacao().equals("0") &&
							!conta.getSituacaoLigacao().equals("R") &&
							!conta.getSituacaoLigacao().equals("C") ){
						conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao8"));
						conta.bloquear(true,true,false);
					}
					/**
					 * Conta sem retenção de impostos cujo RGI é de estatal federal ou entidade pública federal
					 *	CCG50.TPRETENC = 0 (não tem retenção) 
					 *  E CCG50.CDCOBR =9 (ROL COMUM)
					 */
					else if(conta.getTipoRetencao() != null &&	conta.getTipoRetencao().equals("0") && 
							conta.getCodCobrancaCadastro().equals("9")){ 
						conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao9"));
						conta.bloquear(true,true,true);
					}
					/**
					 * Conta com retenção de impostos cujo RGI não é de estatal federal ou entidade pública federal
					 * CCG50.TPRETENC>0 É ENTIDADE PÚLBICA FED OU ESTATAL FEDERAL 
					 * E (CCG03 E EVENT).CDCOBR != 9 (ROL COMUM) 
					 */
					else if(conta.getTipoRetencao() != null && Integer.valueOf(conta.getTipoRetencao()).intValue() > 0  &&
								!conta.getCodCobrancaDebito().equals("9") &&
								!conta.getCodCobrancaCadastro().equals("9")){
						conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao10"));
						conta.bloquear(true,true,true);
					}else{
						//Default
						conta.setSituacaoConta("Em Atraso");
						conta.bloquear(false,false,false);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param dadosImoveisTO
	 */
	public void pesquisaSuperIntendenciaRgi(DadosImoveisTO dadosImoveisTO) {
		
		String sqlQuery =
		"select b.nrrgilig,b.cdunidco1,c.cdunidco2,c.nrrgilig,c.amjrefer,c.tpconta,c.nrseqcta2, c.nrseqcta,c.cdhistfat, "+ 
		"c.djatualiz from pccg0305 a, ccg03 b, cfe06 c where  a.nrrgilig= ?  and a.nrrgilig=b.nrrgilig and a.nrgrupfat=b.nrgrupfat "+
		"and b.cdunidco1=c.cdunidco2 and a.nrrgilig=c.nrrgilig ";
		
		PreparedStatement pstmt = null;
		ResultSet rs =  null;
		
		agvlogger.info("#ContaDaoImpl# -> Método pesquisaSuperIntendenciaRgi  # \n");
		agvlogger.info("Query ==> \n" + sqlQuery);
		try{
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1, dadosImoveisTO.getImovel().getDsRgi());
			
			agvlogger.info("Query param 1 ==> "+ dadosImoveisTO.getImovel().getDsRgi());
			
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				dadosImoveisTO.getImovel().setCodigoSuperIntendencia(rs.getString(3));
			}else{
				agvlogger.error("Erro consulta retenção - não encontrado:");
				throw new Exception("");
			}

			agvlogger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();

		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar NR Ciclo da conta, pesquisa de contas em aberto");
		}
		
	}


	/**
	 * Seta mensagens para as situações de rol especial
	 * @param conta
	 */
	private void setaMotivoBloqueio(Conta conta) {
		
		if(conta.getCodHistorico() != null){
			int temp =  Integer.valueOf(conta.getCodHistorico()).intValue();
			switch(temp){
				case 50: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao50"));
				case 51: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao51"));
				case 52: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao52"));
				case 53: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao53"));
				case 54: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao54"));
				case 55: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao55"));
				case 56: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao56"));
				case 99: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao99"));
				case 300: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao300"));
				case 387: conta.setSituacaoConta(FacesBundleUtil.getInstance().getString("segundavia.situacao387"));
				default:	
			}
		}
	}

	/**
	 * 
	 * @param conta
	 * @return
	 */
	private Conta pesquisaLigacaoSuprimidaTipoHidrometro(Conta conta){
		String sqlQuery ="SELECT b.STLIGACAO,b.CDCOBR , b.cdunidcom , b.tphidro " +
						"FROM PCCG0303 a , CCG03 b " +
						"WHERE a.NRGRUPFAT=b.NRGRUPFAT " +
						"AND a.CDMUNICIP=? " +
						"AND a.NRRGILIG = ?"+
						"AND a.NRRGILIG=b.NRRGILIG ";
		PreparedStatement pstmt = null;
		ResultSet rs =  null;
		
		agvlogger.info("#ContaDaoImpl# -> Método pesquisaLigacaoSuprimida  # \n");
		agvlogger.info("Query ==> \n" + sqlQuery);
		try{
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1, conta.getImovel().getCdMunicipio());
			pstmt.setString(2, conta.getImovel().getDsRgi());
			
			agvlogger.info("Query param 1 ==> "+ conta.getImovel().getCdMunicipio());
			agvlogger.info("Query param 2 ==> "+ conta.getImovel().getDsRgi());
			
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				conta.setSituacaoLigacao(rs.getString(1));
				conta.setCodCobrancaCadastro(rs.getString(2)); // Se CCG03.CDCOBR=9 è ENTIDADE PUBLICA 
				conta.setCdunidcom(rs.getString(3));
				conta.setTipoHidro(rs.getString(4));
			}else{
				agvlogger.error("Erro consulta retenção - não encontrado:");
				throw new Exception("");
			}

			agvlogger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();

		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar NR Ciclo da conta, pesquisa de contas em aberto");
		}
		
		return conta;
	}
	
	/**
	 * 
	 * @param conta
	 * @return 
	 */
	private Conta pesquisaRetencaoConta(Conta conta) {
		String sqlQuery ="select TPRETENC , CDORGPUB  from ccg50 where NRRGILIG = ? AND CDMUNICIP = ?";
		PreparedStatement pstmt = null;
		ResultSet rs =  null;
		
		agvlogger.info("#ContaDaoImpl# -> Método pesquisaRetencaoConta  # \n");
		agvlogger.info("Query ==> \n" + sqlQuery);
		try{
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
		
			pstmt.setString(1, conta.getImovel().getDsRgi());
			pstmt.setString(2, conta.getImovel().getCdMunicipio());
			agvlogger.info("Query param 1 ==> "+ conta.getImovel().getDsRgi());
			agvlogger.info("Query param 2 ==> "+ conta.getImovel().getCdMunicipio());
			
			rs = pstmt.executeQuery();
			if(rs.next()){ 
				conta.setTipoRetencao(rs.getString(1));
				conta.setCodigoOrgaoPublico(rs.getString(2));
			}else{
				agvlogger.error("Erro consulta retenção - não encontrado:");
				throw new Exception("");
			}

			agvlogger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();

		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar NR Ciclo da conta, pesquisa de contas em aberto");
		}
		
		return conta;
	}
	
	/**
	 * Pesquisa o campo codHistFat para fazer validações de regra de bloqueio no frontend 
	 * @param conta
	 * @param dadosImoveisTO 
	 * @return
	 */
	private Conta pesquisaCodigoHistoricoFaturamento(Conta conta, DadosImoveisTO dadosImoveisTO) {
		
		PreparedStatement pstmt = null;
		ResultSet rs =  null;
		
		String sqlQuery = "Select cdhistfat,djatualiz from cfe06 where "+
	    " nrrgilig = ? and amjrefer = ? "+
	    " and tpconta = ? and nrseqcta2 =  ? "+
	    " and cdhistfat in (50,51,52,53,54,55,56,99,69,300,387) and nrseqcta = ?  and  cdunidco2 = ?" +
	    " order by djatualiz desc";
		
		try{
			
			agvlogger.info("#ContaDaoImpl# -> Método pesquisaCodigoHistoricoFaturamento  # \n");
			agvlogger.info("Query ==> \n" + sqlQuery);
		
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
		
			pstmt.setString(1, conta.getImovel().getDsRgi());
			pstmt.setString(2, conta.getDataReferenciaAnoJuliano());
			pstmt.setString(3, conta.getTpConta());
			pstmt.setString(4, conta.getNrSequenciaConta2());
			pstmt.setString(5, conta.getNrSequenciaConta1());
			pstmt.setString(6, dadosImoveisTO.getImovel().getCodigoSuperIntendencia());
			
			agvlogger.info("Query param 1 ==> "+ conta.getImovel().getDsRgi());
			agvlogger.info("Query param 2 ==> "+ conta.getDataReferenciaAnoJuliano());
			agvlogger.info("Query param 3 ==> " +conta.getTpConta());
			agvlogger.info("Query param 4 ==> "+conta.getNrSequenciaConta2());
			agvlogger.info("Query param 5 ==> "+conta.getNrSequenciaConta1());
			agvlogger.info("Query param 6 ==> "+ conta.getCdunidcom());
			
			rs = pstmt.executeQuery();
			if(rs.next()){ 
				conta.setCodHistorico(rs.getString(1));
			}else{
				agvlogger.error("Erro consulta cod historico - não encontrado:");
				throw new Exception("Erro consulta cod historico - não encontrado");
			}

			agvlogger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();

		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar NR Ciclo da conta, pesquisa de contas em aberto");
		}
		return conta;
	}
	
	

	/**
	 * Pesquisa nr ciclo e superintendência responsável pelo RGI
	 * @param element
	 * @return
	 */
	private Conta pesquisaNrCicloNrUnidade(Conta element) {
		
		String sqlQuery ="select nrciclo , cdunidcom , nrrgilig from " +
		"cfe01 where " +
		"nrrgilig = ? ";
		PreparedStatement pstmt = null;
		ResultSet rs =  null;
		
		agvlogger.info("#ContaDaoImpl# -> Método pesquisaNrCicloNrUnidade  # \n");
		agvlogger.info("Query ==> \n" + sqlQuery);
		try{
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
		
			pstmt.setString(1, element.getImovel().getDsRgi());
			agvlogger.info("Query param 1 ==> "+ element.getImovel().getDsRgi());
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				element.setNrCiclo(rs.getString(1));
				element.setCdunidcom(rs.getString(2));
			}else{
				agvlogger.error("Erro consulta ciclo - não encontrado:");
				throw new Exception("");
			}

			agvlogger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();

		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar NR Ciclo da conta, pesquisa de contas em aberto");
		}
		
		
		return element;
	}

	/**
	 * Pesquisa se a conta foi emitida por um TACE
	 * Regra Asp
	 * 	  'Denis Tonon - 27/02/2007 - Ativ. GM 293
	 *    If (IsNull(Retorno) Or Retorno >= DataParaDJ(Date)) Then
     *       PodeExibirFatura = False
	 * 
	 * @param element
	 * @return
	 */
	private Conta pesquisaContaEmitidaTace(Conta element) {
		String sqlQuery ="select djproces from " +
				"ctf11 where cdunidcom = ? " +
				"and amjrefer = ? " +
				"and nrciclo = ? " +
				"and tpevento=33";
		
		agvlogger.info("#ContaDaoImpl# -> Método pesquisaContaEmitidaTace  # \n");
		agvlogger.info("Query ==> \n" + sqlQuery);
	
			
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1,element.getCdunidcom());
			pstmt.setString(2,element.getDataReferenciaAnoJuliano());
			pstmt.setString(2,element.getNrCiclo());
			
			agvlogger.info("Query param 1 ==> "+ element.getCdunidcom());
			agvlogger.info("Query param 2 ==> "+ element.getDataReferenciaAnoJuliano());
			agvlogger.info("Query param 3 ==> "+ element.getNrCiclo());
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.first()){
				while (rs.next()) {
					Date dateTemp = (rs.getDate(1));
					if(dateTemp.after(new Date())){ //TODO GUSTAVO validar com regra DataParaDJ no ASP
						element.setEmitidoTace(false);		 
					}else{
						element.setEmitidoTace(true);
					}
				}
			}else{
				element.setEmitidoTace(false);
			}
			
			agvlogger.info("Query Finalizada com Sucesso\n");
			
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar Conta emitida TACE, pesquisa de contas em aberto");
		}
		return element;
	}

	/**
	 *   Verificar se a conta pertence a um extrato de contas
		 pesquisar o mês e ano do extrato AMJREFEXT, mês e ano da conta AMJREFER e status do extrato STEXTRATO com TPCTAEXT sempre "7"
		 o campo AMJREFEXT dirá o mes/ano do extrato
		 o campo AMJREFER dirá o mes/ano da conta
		 o status do extrato STEXTRATO dirá 0 (ZERO) = ABERTO; 1 = QUITADO (INFOR=CLIENTE); 3 = PAGO (INFOR=BANCO)
	 * @param element
	 * @return
	 */
	private Conta pesquisaContaPertenceExtrato(Conta element) {
		
//		String sqlQuery ="SELECT A.AMJREFEXT,A.AMJREFER ,B.STEXTRATO FROM PCEB3602 A ,CEB3602 B " +
//		"WHERE A.TPCTAEXT = 7 AND A.NRRGILIG =? AND B.NRRGILIG = ?";
		
		/**
		 * 	select passado pelo Dennis
		 * " SELECT B.AMJREFEXT,B.AMJREFER ,B.STEXTRATO " +
				" FROM CEB36 B " +
				" WHERE " +
				" B.TPCTAEXT = 7 " +
				" AND B.NRRGILIG= 912190 " +
				" AND B.AMJREFER = 358" +
				" AND B.NRSEQCTA2 = 1"
				
				
				SELECT B.AMJREFEXT,B.AMJREFER ,B.STEXTRATO  
				FROM CEB36 B  WHERE  B.TPConta =1 AND B.NRRGILIG=191 
				 AND B.AMJREFER =362 AND B.NRSEQCTA2=1

		 */
		
		String sqlQuery = "SELECT B.AMJREFEXT,B.AMJREFER ,B.STEXTRATO " +
							" FROM CEB36 B " +
							" WHERE " +
							" B.TPCTAEXT = 7 " +
							" AND B.TPCONTA =? "+
							" AND B.NRRGILIG= ? " +
							" AND B.AMJREFER = ?" +
							" AND B.NRSEQCTA2 = ?";
		
		agvlogger.info("#ContaDaoImpl# -> Método pesquisaContaPertenceExtrato  # \n");
		agvlogger.info("Query ==> \n" + sqlQuery);
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, element.getTpConta());
			pstmt.setString(2, element.getImovel().getDsRgi());
			pstmt.setString(3, element.getDataReferenciaAnoJuliano());
			pstmt.setString(4, element.getNrSequenciaConta2());
			
			agvlogger.info("Query param 1 ==> "+ element.getTpConta());
			agvlogger.info("Query param 2 ==> "+ element.getImovel().getDsRgi());
			agvlogger.info("Query param 3 ==> "+ element.getDataReferenciaAnoJuliano());
			agvlogger.info("Query param 4 ==> "+ element.getNrSequenciaConta2());
			
			ResultSet rs = pstmt.executeQuery();
			//Se existir extrato seta variavel boolean para true.
			while( rs.next() ){ //TODO GUSTAVO verificar com henrique regra
				/**
				 * 0 (ZERO) = ABERTO; 1 = QUITADO (INFOR=CLIENTE); 3 = PAGO (INFOR=BANCO)
				 */
				String retorno = rs.getString(3);
				element.setExtrato(true);
				break;
			}
			
			agvlogger.info("Query Finalizada com Sucesso\n");
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar debito automatico - Extrato, pesquisa de contas em aberto");
		}
		
		return element;
	}

	/**
	 *	Verifica se debito esta programado para debito automatico e ainda nao houve retorno do banco
	 *	Retorna '0' se ja aconteceu o retorno e '2' se ainda nao aconteceu
	 * @param element
	 * @return
	 */
	private Conta pesquisaDebitoAutomatico(Conta element) {
		
//		String sqlQuery = "SELECT DTINCL,DTEXCL FROM PCCG0901 WHERE" +
//				" NRRGILIG=? ORDER BY DTGRAV DESC, HRTRANS DESC"; 
		
		String sqlQuery ="select dtretorno from cea33 where dtvenc = ? and nrrgilig = ? "+ 
		"and amjrefer= ? and tpconta=? and nrseqcta2=? and stbloque<>2"; 
		
		agvlogger.info("#ContaDaoImpl# -> Método pesquisaDebitoAutomatico  # \n");
		agvlogger.info("Query ==> \n" + sqlQuery);
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1, element.getDataVencimentoAnoJuliano());
			pstmt.setString(2, element.getImovel().getDsRgi());
			pstmt.setString(3, element.getDataReferenciaAnoJuliano());
			pstmt.setString(4, element.getTpConta()); 
			pstmt.setString(5, element.getNrSequenciaConta2()); 
			
			agvlogger.info("Query param 1 ==> "+ element.getDataVencimentoAnoJuliano());
			agvlogger.info("Query param 2 ==> "+ element.getImovel().getDsRgi());
			agvlogger.info("Query param 3 ==> "+ element.getDataReferenciaAnoJuliano());
			agvlogger.info("Query param 4 ==> "+ element.getTpConta()); 
			agvlogger.info("Query param 5 ==> "+ element.getNrSequenciaConta2());
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				String retorno = rs.getString(1);
				if(retorno.equals("0") || retorno.equals("2")){
					element.setDebitoAutomatico(true);
				}
			}
			
			agvlogger.info("Query Finalizada com Sucesso\n");
			
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar debito automatico , pesquisa de contas em aberto");
		}
		
		return element;
	}

	/**
	 * Verificar a situação da conta (código de histórico no CSI)
	 * Observação: STCONTA = 0 com STPAGTO = 1 ou 4 a conta está em aberto
	 * @param element
	 * @param pstmt 
	 * @return
	 */
	private Conta pesquisaSituacaoConta(Conta element, PreparedStatement pstmt) {
		String sqlQuery = "SELECT STCONTA,STPAGTO FROM EVENT " +
				"WHERE NRRGILIG=? AND AMJREFER=?"; 
		
		agvlogger.info("#ContaDaoImpl# -> Método pesquisaSituacaoConta  # \n");
		agvlogger.info("Query ==> \n" + sqlQuery);
		
		try{
			getHibernateSession().reconnect();
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, element.getImovel().getDsRgi());
			pstmt.setString(2, element.getDataReferenciaAnoJuliano());
			
			agvlogger.info("Query param 1 ==> "+ element.getImovel().getDsRgi());
			agvlogger.info("Query param 2 ==> "+ element.getDataReferenciaAnoJuliano());
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				if(rs.getString(1).equals("0")){
					if(rs.getString(2).equals("0") || rs.getString(2).equals("4")){
						String descricaoSituacaoConta = (AgvStatusConta.byValue(new Integer(rs.getString(1))).name());
						String descricaoSituacaoPagamento = (AgvStatusPagamento.byValue(new Integer(rs.getString(2))).name());
						element.setSituacaoConta(descricaoSituacaoConta);
						element.setSituacaoPagamento(AgvStatusPagamento.byValue(new Integer(rs.getString(2))));
					}
				}
				break;//TODO verificar qual a logica para vir mais de uma situacao para a msm conta.
			}
			
			agvlogger.info("Query Finalizada com Sucesso\n");
			
			rs.close();
			pstmt.close();
			
		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro pesquisar situação da conta , pesquisa de contas em aberto");
		}
		
		return element;
	}

	/**
	 * Busca na tabela event as contas em aberto (0  ou  4)
	 * Se TPCONTA  = 6 é do Faturamento Especial, se diferente de 6 é do Rol Comum.
	 * @param imovel
	 * @param dadosImoveisTO 
	 * @param pstmt2 
	 * @return
	 */
	protected ArrayList<Conta> findContasEmAberto(Imovel imovel) {
		
		ArrayList<Conta> temp = new ArrayList<Conta>();
		
		agvlogger.info("#ContaDaoImpl# -> Método  #findContasEmAberto# \n");
		String sqlQuery = "SELECT amjrefer,djvenc,vlcontag ,TPCONTA ,NRSEQCTA2 ,ISPEC ,STPAGTO , STCORTSUP " +
				",CDCOBR, VLAGUA, VLESGOTO, VLDESCONT , nrseqcta  " +
				"FROM EVENT WHERE NRRGILIG=? " +
				"AND STCONTA=0 AND STPAGTO IN (0,4) order by amjrefer desc ";  
		
		agvlogger.info("Query ==> \n" + sqlQuery); 
		
		try{
			PreparedStatement pstmt  = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, imovel.getDsRgi());
			agvlogger.info("Query param 1 ==> "+ imovel.getDsRgi());
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				Conta contaCarregado = new Conta();
				contaCarregado.setDtReferencia(WrapperUtils.addMonths((rs.getInt((1))))); 
				contaCarregado.setDtVencimento(WrapperUtils.addDays(rs.getInt(2)));
				contaCarregado.setDataReferenciaAnoJuliano(rs.getString(1));
				contaCarregado.setDataVencimentoAnoJuliano(rs.getString(2));
				contaCarregado.setVlTotal(new Double(rs.getString(3)));
				contaCarregado.setTpConta((rs.getString(4)));
				contaCarregado.setNrSequenciaConta2(rs.getString(5)); //Setado o valor de NRSEQCTA2
				contaCarregado.setIspec(rs.getString(6));
				contaCarregado.setSituacaoPagamento(AgvStatusPagamento.byValue(new Integer(rs.getString(7))));
				contaCarregado.setSituacaoCorSup(rs.getString(8));
				contaCarregado.setCodCobrancaDebito(rs.getString(9)); 
				contaCarregado.setImovel(imovel);
				contaCarregado.setVlAgua( rs.getDouble(10) );
				contaCarregado.setVlEsgoto( rs.getDouble(11) );
				contaCarregado.setVlDesconto( rs.getDouble(12) );
				contaCarregado.setNrSequenciaConta1((rs.getString(13)));
//				contaCarregado.setCodHistorico(rs.getString(13)) ;
				temp.add(contaCarregado);
				
			}
			
			agvlogger.info("Query Finalizada com Sucesso para o RGI ==> "+ imovel.getDsRgi());
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		return temp;
	}
	
	protected ArrayList<Conta> pesquisaHistoricoContasRolEspecial(DadosImoveisTO dadosImoveisTO) {
		ArrayList<Conta> lista = new ArrayList<Conta>();
		PreparedStatement ps = null;		
		String sqlQuery = " SELECT  * " +
  						  " FROM pccg0311 a, ccg03 b " +
						  " WHERE a.cdmunicip=? and a.cdcliente=? and a.nrgrupfat=b.nrgrupfat and a.nrrgilig=b.nrrgilIG " +
						  " AND b.CDCOBR in ('6','7')";

		try{
		
			ps = getHibernateSession().connection().prepareStatement(sqlQuery);
			ps.setString(1,dadosImoveisTO.getImovel().getCdMunicipio()); //cdmunicip
			ps.setString(2,dadosImoveisTO.getImovel().getCdCliente()); //	cdcliente
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("--> "+rs.getString(1)+" "+rs.getString(2));
			}
	
			sqlQuery = " SELECT NRRGILIG,STREGIST,CDMUNICIP,CDCLIENTE,CDCONTFAT " +
					   " FROM cfe01 " +
					   " WHERE stregist=0 and cdmunicip=? and cdcliente=?";
			
			ps = getHibernateSession().connection().prepareStatement(sqlQuery);
			ps.setString(1,dadosImoveisTO.getImovel().getCdMunicipio()); //cdmunicip
			ps.setString(2,dadosImoveisTO.getImovel().getCdCliente()); //	cdcliente

			String nrRGIFilhos = "";
			rs = ps.executeQuery();
			while (rs.next()) {
				if(WrapperUtils.isNull(rs.getString(1))) continue;
				nrRGIFilhos = nrRGIFilhos.concat(rs.getString(1)).concat(",");			
			}
			nrRGIFilhos = WrapperUtils.removeLastCaracter(nrRGIFilhos);
			
			sqlQuery = "SELECT * FROM cfe03 where nrrgilig in ("+nrRGIFilhos+") and amjrefer>?";
			ps = getHibernateSession().connection().prepareStatement(sqlQuery);
			ps.setString(1,	""+ WrapperUtils.getMonthsFromDate( WrapperUtils.removeMonthsFromNow(14)));	
			rs = ps.executeQuery();
			while (rs.next()) {
//				System.out.println("--> "+rs.getString(1)+" "+rs.getString(2));
				Conta conta = new Conta();
				conta.setCdConta(rs.getString(1));
				lista.add(conta);
			}
			
			rs.close();
			ps.close();
		}catch (Exception e) {
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			e.printStackTrace();
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		return lista;
	}
	
	/**
	 * Pesquisa historico de contas para todos os imoveis pertencentes ao Cliente
	 * 
	 * @param Cliente
	 * @param dadosImoveisTO
	 * @return
	 */
	public Cliente carregaHistoricoContasTodosImoveis(Cliente cliente, DadosImoveisTO dadosImoveisTO) { // DadosImoveisTO dadosImoveisTO
		agvlogger.info("Start ContaDaoImpl -> carregaHistoricoContasTodosImoveis");
		List<Imovel> imoveis = cliente.getImoveis();
		
		if(cliente.getImoveis().size()==0) {
			// TODO - throw business exception
			return null;
		}

		agvlogger.info("Start call carregaContasComConsumo"); long start = (new Date()).getTime();
		agvlogger.info("Numero de imoveis (rgis) : ", imoveis.size());
		
		for(Imovel imovel : imoveis){
			agvlogger.info("Carregando contas para rgi " + imovel.getDsRgi());
			List<Conta> contas = carregaContasComConsumo(dadosImoveisTO , imovel.getDsRgi()); // Exec Query 1
			imovel.setContaList(contas);
		}
		
		// REGRAS realizadas pelo carregaContasComConsumo
		// verifica se pode exibir
		// carrega demais dados
		
		agvlogger.info("End call carregaContasComConsumo, time spent: " + ((new Date().getTime()) - start));
		
		agvlogger.info("End ContaDaoImpl -> carregaHistoricoContasTodosImoveis");
		
		return cliente;
	}

	
	/**
	 * nAmjLimite = RGI.CSI.AAAAMMtoAMJ(Year(Date) & Month(Date)) - 14
    
    'Denis tonon - 05/02/2009 - Sol. 1872/2009 - considerando hidro composto
    'sSQL = "select amjrefer,qtconsfat,stconsumo from cea15 where nrrgilig=" & RGI.NRRGILIG & " and stregist=1 order by amjrefer desc"
    'Denis Tonon - 31/07/2006 - Ativ G.M. 11
    'sSQL = "select amjrefer,qtconsfat,stconsumo,qtconsumo,vlleitura from cea15 where nrrgilig=" & RGI.NRRGILIG & " and stregist=1 order by amjrefer desc"
    sSQL = "select a.amjrefer,a.tpconta,a.nrseqcta2,a.nrseqcta,a.qtconsfat,a.stconsumo,a.qtconsumo,a.vlleitura," _
    & "b.qtconsumo as qtconscomp,b.vlleitura as vlleitcomp from cea15 a left outer join cfe46 b" _
    & " on a.nrrgilig=b.nrrgilig and a.amjrefer=b.amjrefer and a.tpconta=b.tpconta and " _
    & "a.nrseqcta2=b.nrseqcta2 and a.nrseqcta=b.nrseqcta where a.nrrgilig=" & RGI.NRRGILIG _
    & " and a.stregist=1 and a.amjrefer > " & nAmjLimite & " order by a.amjrefer desc"
	 * @param rgi 
    
	 */
	private ArrayList<Conta> carregaContasComConsumo(DadosImoveisTO dadosImoveisTO, String rgi) {
		
		ArrayList<Conta> temp = new ArrayList<Conta>();
		PreparedStatement pstmt = null;
		agvlogger.info("#ContaDaoImpl# -> Método  #carregaContasComConsumo# \n");
		
		// retorna consumos
		// por: 14 meses e rgis
		String sqlQuery = " SELECT a.amjrefer,a.tpconta,a.nrseqcta2,a.nrseqcta,a.qtconsfat,a.stconsumo, " +
				" a.qtconsumo,a.vlleitura,b.stconsumo , b.qtconsumo as qtconscomp,b.vlleitura as vlleitcomp " +
				" FROM cea15 a LEFT OUTER JOIN cfe46 b " +
				" ON a.nrrgilig=b.nrrgilig and a.amjrefer=b.amjrefer and a.tpconta=b.tpconta and " +
				" a.nrseqcta2=b.nrseqcta2 and a.nrseqcta=b.nrseqcta where a.nrrgilig= ? " +
				" and a.stregist=1 and a.amjrefer > ? " +
				" ORDER BY a.amjrefer desc";
		
		agvlogger.info("Query ==> \n" + sqlQuery);
		
		try{
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
//			RGI.CSI.AAAAMMtoAMJ(Year(Date) & Month(Date)) - 14
			Long nAmjLimite =  WrapperUtils.getMonthsFromDate( WrapperUtils.removeMonthsFromNow(14) );
//			int nAmjLimite  = 340;
			
			pstmt.setString(1, rgi);
			pstmt.setString(2, nAmjLimite.toString());
			
			agvlogger.info("Param 1==> \n" + rgi);
			agvlogger.info("Param 2==> \n" + nAmjLimite);
			
			ResultSet rs = pstmt.executeQuery();
			
			int i = 0;
			agvlogger.info("Carregando contas de consumo para rgi " + dadosImoveisTO.getImovel().getDsRgi());
			while( rs.next() ){
				i++;
				Conta contaCarregado = new Conta();
				String observacao = "";
				contaCarregado.setDtReferencia(WrapperUtils.addMonths((rs.getInt((1)))));
				contaCarregado.setDataReferenciaAnoJuliano(rs.getString((1)));
				contaCarregado.setTpConta((rs.getString(2)));
				contaCarregado.setNrSequenciaConta2(rs.getString(3));
				contaCarregado.setNrSequenciaConta1(rs.getString(4));
				contaCarregado.setQuantidadeConsumoFaturada(rs.getString(5));
				contaCarregado.setQuantidadeConsumo(rs.getString(7));
				contaCarregado.setVlleitura(rs.getString(8));
				
				contaCarregado.setSituacaoConsumo(rs.getString(6));
				// preenche campo observacao - RN1 - faturamento consumo
				if(contaCarregado.getSituacaoConsumo().equals("M")){
					observacao = observacao +" , "+ "Faturado pela média de consumo";
					contaCarregado.setObservacaoConta(observacao);
				}
				
				//  regra para exibir leitura
//				if(pesquisaHistoricoContasVerificaPodeExibirLeitura(dadosImoveisTO, contaCarregado)){
					// preenche campo observacao - RN1 - conta reformada
					pesquisaDadosContaReformada(rgi, contaCarregado);
					// adicionada dados da conta / preenche campo observacao
					pesquisaDadosComplementaresConta(rgi, contaCarregado);
					temp.add(contaCarregado);
//				}
			}
			
			agvlogger.info("Numero de contas encontradas " + i);
			
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		return temp;
	}
	
	
	/**
	 * Verifica se pode exibir Leitura
	 * A leitura somente sera exibida se:
	   	   a)Foi impressa pelo TACE
		   b)Nao foi impressa pelo tace mas ja aconteceu o evento 1x do cronograma (1x=leitura)
	 * @param dadosImoveisTO
	 * @param contaCarregado 
	 * @return
	 */
	public Boolean pesquisaHistoricoContasVerificaPodeExibirLeitura(DadosImoveisTO dadosImoveisTO, Conta contaCarregado) {
		
		boolean podeExibir = true;
	
		String sqlQuery = 
			"Select b.stgerala, b.stimpress, c.djproces from cfe01 a, cfe02 b, ctf11 c where " +
		    "a.stregist=0 and a.cdmunicip= ? and a.cdcliente= ? " +
		    " and a.cdcontfat= ? and a.cdunidco2=b.cdunidco2 and a.nrrgilig=b.nrrgilig" +
		    " and b.amjrefer = ? and b.tpconta= ? and b.nrseqcta2= ?" +
		    " and b.nrseqcta= ? and b.cdunidco2=c.cdunidcom and " +
		    "b.amjrefer=c.amjrefer and a.nrciclo=c.nrciclo and c.tpevento=33 fetch first 1 row only";
		
		agvlogger.info("Query ==> \n" + sqlQuery);
    
		PreparedStatement pstmt;
		try{
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			pstmt.setString(1, dadosImoveisTO.getImovel().getCdMunicipio());
			pstmt.setString(2, dadosImoveisTO.getCliente().getCdCliente());
			pstmt.setString(3, dadosImoveisTO.getImovel().getCodigoControleFaturamento()); //Codigo Conta faturada
			pstmt.setString(4, contaCarregado.getDataReferenciaAnoJuliano());
			pstmt.setString(5, contaCarregado.getTpConta());
			pstmt.setString(6, contaCarregado.getNrSequenciaConta2());
			pstmt.setString(7, contaCarregado.getNrSequenciaConta1());
			
			agvlogger.info("ContaDaoImpl -> pesquisaHistoricoContasVerificaPodeExibirLeitura");
			agvlogger.info("Parameters: \n");
			agvlogger.info("1 - cdMunicipio=" + dadosImoveisTO.getImovel().getCdMunicipio());
			agvlogger.info("2 - cdCliente=" + dadosImoveisTO.getCliente().getCdCliente());
			agvlogger.info("3 - cdControleFaturamento=" + dadosImoveisTO.getImovel().getCodigoControleFaturamento());
			agvlogger.info("4 - anoJuliano=" + contaCarregado.getDataReferenciaAnoJuliano());
			agvlogger.info("5 - tpConta=" + contaCarregado.getTpConta());
			agvlogger.info("6 - nrSequenciaConta=" + contaCarregado.getNrSequenciaConta2());
			agvlogger.info("7 - nrSequenciaConta1=" + contaCarregado.getNrSequenciaConta1());
			
			ResultSet rs = pstmt.executeQuery();
			
			String stGerala = "";
			String stImpress = "";
			int djProcess = 0;
	
			while( rs.next() ){
				stGerala = rs.getString(1);
				stImpress = rs.getString(2);
				djProcess = rs.getInt(3);
				break;
			}
	//		Regra retirada do ASP
	//	    If oTb("stgerala") <> "T" Or oTb("stimpress") = "A" Then  'se nao foi emitida pelo Tace - FE
	//		
	//		        If oTb("djproces") >= DataParaDJ(Date) Then
	//		            PodeExibirLeitura = False
	//		        End If
	//		    End If
			if( stGerala.equals("T") || stImpress.equals("A")){
				
				if( (WrapperUtils.addDays(djProcess)).before(new Date())) {
					podeExibir = false;
				}
			}
		
	    }catch (Exception e) {
	    	e.printStackTrace();
	    	podeExibir = true;
		}
	    
	    return podeExibir;
	}
	
	
	/**
	 * Pesquisa os dados complementares das contas , caso não tenha a conta é atribuido o valor para observação
	 * 
	 * Nome inicial: findContasByAmjRefer
	 * 
	 * @param imovel
	 * @param contaCarregado2 
	 * @return
	 */
	private void pesquisaDadosComplementaresConta(String dsRgi, Conta contaCarregado) {
		
		agvlogger.info("#ContaDaoImpl# -> Método  #pesquisaDadosComplementaresConta# \n");
		
		String sqlQuery = "SELECT amjrefer,djvenc,vlcontag ,TPCONTA ,NRSEQCTA2 ,ISPEC ,STPAGTO , STCORTSUP ,CDCOBR  " +
						  "FROM EVENT WHERE NRRGILIG=? " +
						  "AND STCONTA=0 " +
						  "AND amjrefer=?"; 
		
		agvlogger.info("Query ==> \n" + sqlQuery);
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, dsRgi);
			pstmt.setString(2, contaCarregado.getDataReferenciaAnoJuliano() );
			agvlogger.info("Query param 1 ==> "+ dsRgi);
			agvlogger.info("Query param 2 ==> "+ contaCarregado.getDataReferenciaAnoJuliano());
			
			ResultSet rs = pstmt.executeQuery();
			
			int i = 0;
			if( rs.next()){
				contaCarregado.setDtReferencia(WrapperUtils.addMonths((rs.getInt((1))))); 
				contaCarregado.setDtVencimento(WrapperUtils.addDays(rs.getInt(2)));
				contaCarregado.setDataReferenciaAnoJuliano(rs.getString(1));
				contaCarregado.setDataVencimentoAnoJuliano(rs.getString(2));
				contaCarregado.setVlTotal(new Double(rs.getString(3)));
				contaCarregado.setTpConta((rs.getString(4)));
				contaCarregado.setNrSequenciaConta2(rs.getString(5)); //Setado o valor de NRSEQCTA2
				contaCarregado.setIspec(rs.getString(6));
				contaCarregado.setSituacaoPagamento(AgvStatusPagamento.byValue(new Integer(rs.getString(7))));
				contaCarregado.setSituacaoCorSup(rs.getString(8));
				contaCarregado.setCodCobrancaDebito(rs.getString(9)); 
			}else{
				contaCarregado.setObservacaoConta("Dados da conta não disponíveis");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * '**
		'Denis Tonon - 18/03/2008 - Ativ. GM 608
		'Retorna TRUE se a conta referente a esta leitura foi reformada
		    If (Not IsNull(Retorno) And Retorno = "R") Then
		        ContaReformada = True
		        Exit Property
		    End If
		/**
		 * Pesquisa se a conta foi reformada
		 * stteclam = status da reclamação
		 * cea44 - Processo análise de conta
		 */
	private void pesquisaDadosContaReformada(String dsRgi, Conta contaCarregado) {
		
		agvlogger.info("#ContaDaoImpl# -> Método  #pesquisaContaReformada# \n");
		String sqlQuery = "Select streclam from cea44 where nrrgilig = ? "+
		    " and amjrefer = ? and nrseqcta1 = ? ";
		
		agvlogger.info("Query ==> \n" + sqlQuery);
		
		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, dsRgi);
			pstmt.setString(2, contaCarregado.getDataReferenciaAnoJuliano() );
			pstmt.setString(3, contaCarregado.getNrSequenciaConta1() );
			
			agvlogger.info("Query param 1 ==> "+ dsRgi);
			agvlogger.info("Query param 2 ==> "+ contaCarregado.getDataReferenciaAnoJuliano());
			agvlogger.info("Query param 3 ==> "+ contaCarregado.getNrSequenciaConta1());
			
			ResultSet rs = pstmt.executeQuery();
			
			int i = 0;
			while( rs.next() ){
				contaCarregado.setStatusReclamacao(((rs.getString((1))))); 
				if(contaCarregado.getObservacaoConta() != null && contaCarregado.getStatusReclamacao().equals("R")){
					String temp = contaCarregado.getObservacaoConta();
					contaCarregado.setObservacaoConta(temp + "," + "Conta Reformada");
				}else if(contaCarregado.getStatusReclamacao().equals("R")){
					contaCarregado.setObservacaoConta("Conta Reformada");
				}
				break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Carrega dados básicos para criação do boleto de segunda via para rol especial
	 * @param contaPesquisa
	 * @return
	 */
	public Conta carregarDadosBoletoRolEspecial(Conta contaPesquisa,DadosImoveisTO dadosImoveis){
		
		try{			
			long amjReferencia =  WrapperUtils.toLong(contaPesquisa.getDataReferenciaAnoJuliano()).longValue();
			
			
			//Verifica se a conta é parcelada / Se sim carrega os dados da conta.
			if(contaPesquisa.getIspec().equals("CFRAB")){
				
				pesquisaParcela(contaPesquisa);
				
			}else{
			//Carrega Multas 2 via
			pesquisaMulta2via(contaPesquisa);
	
			// Carrega Atualizacao Monetaria
			pesquisaAtualizacaoMonetaria2via(contaPesquisa);
			
			//Carrega Juros Mora da conta
			pesquisaJurosMora2via(contaPesquisa);
			
			//Carrega Debitos da conta
			pesquisaDebito2via(contaPesquisa);
			
			//Carrega Creditos da conta
			pesquisaCredito2via(contaPesquisa);
			
			}
			
			//Carrega data de leitura da conta
			pesquisaDataLeituraConta(contaPesquisa); 
			
			//Carrega volume faturado no mês e economias faturadas da conta / carrega tambêm a data de emissão e tpContrato
			pesquisaVolumeFaturadoEconomiasConta(contaPesquisa , dadosImoveis); 
			
			//Carrega Período Medicão Conta
			pesquisaPeriodoMedicaoConta(contaPesquisa);
			
			//Verifica se é entidade pública , caso seja carrega os descontos na conta , caso existam.
			pesquisaDescontosImovelEntidadePublico(contaPesquisa,dadosImoveis);
			
			//Carrega instruçoes de Pagamento do Boleto
			contaPesquisa.setIntrucoesBoleto( carregaComentariosConta(contaPesquisa, amjReferencia, contaPesquisa.getTipoContrato())); 
			
		}catch (Exception e) {
			agvlogger.error("Erro carregarDadosBoletoRolEspecial" +e.getMessage());
			e.printStackTrace();
		}
		
		return contaPesquisa;
		
	}
	

	/**
	 * Pesquisa se a conta é parcelada , se sim carrega os dados da parcela.
	 * Válido para mais de uma parcela ou parcela única
	 * @param contaPesquisa
	 * @throws HibernateException
	 * @throws SQLException
	 */
	private void pesquisaParcela(Conta contaPesquisa) throws HibernateException, SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String qtdParcela = null;
		String nrAcordo = null;

		
		String sqlQtdParcelas =
			"SELECT " +
			"QTPARCELA, " +
			"NRACORDO " +
			"FROM CPU01 tbl " +
			"WHERE tbl.NRRGIPAR = " + contaPesquisa.getImovel().getDsRgi() +
			" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
			" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
			" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();				
		
		stmt = getHibernateSession().connection().createStatement();
		
		rs = stmt.executeQuery(sqlQtdParcelas);	

					
		while( rs.next() ){
			qtdParcela = rs.getString(1);
			nrAcordo = rs.getString(2);
		}
		
		rs.close();
		rs = null;
		pstmt = null;
		
		int parcela = Integer.valueOf(qtdParcela);
		int numeroArcodo = Integer.valueOf(nrAcordo); 
		
		/* Numero da parcela está na própria event (objeto conta) */
		contaPesquisa.getContaParcelada().setAcordo(numeroArcodo);
		
		if( parcela > 1 ) {
			/* parcela */
			contaPesquisa.getContaParcelada().setExibir(true);
			String valorParcela = null;
			
			String sqlValorParcelas =
				"SELECT " +
				"VLPARCELA " +
				"FROM CPU03 tbl " +
				"WHERE tbl.NRRGIPAR = " + contaPesquisa.getImovel().getDsRgi() +
				" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
				" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
				" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();	
			
			stmt = getHibernateSession().connection().createStatement();
			
			rs = stmt.executeQuery(sqlValorParcelas);	
			
			while( rs.next() ) {
				valorParcela = rs.getString(1);
			}
			
			contaPesquisa.setVlTotal(Double.valueOf(valorParcela));
			contaPesquisa.setVlParcelamento(Double.valueOf(valorParcela));
			contaPesquisa.setQuantidadeParcelas(WrapperUtils.toInt(qtdParcela));
			
			rs.close();
			rs = null;
			pstmt = null;
			
		} else if( parcela == 1 ) {
			/* parcela unica */
			contaPesquisa.getContaParcelaUnica().setExibir(true);
			String valorParcela = null;
			
			contaPesquisa.getContaParcelaUnica().setAcordo(numeroArcodo);
			
			String sqlValorParcelas =
				"SELECT " +
				"VLPARCELA " +
				"FROM CPU03 tbl " +
				"WHERE tbl.NRRGIPAR = " + contaPesquisa.getImovel().getDsRgi() +
				" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
				" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
				" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();	
			
			stmt = getHibernateSession().connection().createStatement();
			
			rs = stmt.executeQuery(sqlValorParcelas);	
			
			while( rs.next() ) {
				valorParcela = rs.getString(1);
			}
			
			contaPesquisa.setVlTotal(Double.valueOf(valorParcela));
			contaPesquisa.setVlParcelamento(Double.valueOf(valorParcela));
			contaPesquisa.setQuantidadeParcelas(WrapperUtils.toInt(qtdParcela));
			
			rs.close();
			rs = null;
			pstmt = null;				
		}
		
		
	}


	/**
	 * 
	 * @param contaPesquisa
	 */
	private void pesquisaPeriodoMedicaoConta(Conta contaPesquisa) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
			try{
			
			String sqlQueryMediaEConsumo =
			"SELECT NRRGILIG, AMJREFER, TPCONTA, NRSEQCTA2, NRSEQCTA, QTDDPLEIT FROM CEA15 WHERE NRRGILIG= ? AND AMJREFER= ? ";
			
			pstmt = getHibernateSession().connection().prepareStatement(sqlQueryMediaEConsumo);
			
			pstmt.setString(1, contaPesquisa.getImovel().getDsRgi() );
			pstmt.setString(2, contaPesquisa.getDataReferenciaAnoJuliano() );
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				contaPesquisa.setDsperiodoMedicao( Integer.valueOf(rs.getString(6)) );
			}
				
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		
	}


	/**
	 * 
	 * @param contaPesquisa
	 * @param dadosImoveis 
	 */
	private void pesquisaVolumeFaturadoEconomiasConta(Conta contaPesquisa, DadosImoveisTO dadosImoveis) {
		
		String sqlQuery = "Select b.qtcons04, b.qtcons05, b.qtcons06 , b.qtres , b.qtcom , b.qtind , b.qtpub , b.cdentidad , b.djemisfat  from  cfe02 b where " +
	    " b.cdunidco2=? and b.nrrgilig=? " +
	    " and b.amjrefer = ? and b.tpconta= ? and b.nrseqcta2= ?" +
	    " and b.nrseqcta= ? ";
		
//		"Select cdentidad,djemisfat,djleit,qtres,qtcom,qtind,qtpub,qtcons04,qtcons05,qtcons06,qtddpleit" _
//		& ",stgerala,stimpress from cfe02 where cdunidco2 = " & RGI.SUPERINTENDENCIA & " and "nrrgilig=" " +
//		"& RGI.NRRGILIG & " and amjrefer=" & nAmjRefer & _
//		" and tpconta=" & nTpConta & " and nrseqcta2=" & nNRSEQCTA2 & " and nrseqcta=" & nNRSEQCTA

		try{
		
		ArrayList<String> result = findQBE(sqlQuery,
				 dadosImoveis.getImovel().getCodigoSuperIntendencia(),
				 dadosImoveis.getImovel().getDsRgi(),
				 contaPesquisa.getDataReferenciaAnoJuliano(),
				 contaPesquisa.getTpConta(),
				 contaPesquisa.getNrSequenciaConta2(),
				 contaPesquisa.getNrSequenciaConta1()).get(0);
		
		contaPesquisa.setVolumeAgua(Double.valueOf(result.get(0)));
		contaPesquisa.setVolumeEsgoto(Double.valueOf(result.get(1)));
		contaPesquisa.setVolumeEsgotoNaoDomes(Double.valueOf(result.get(2)));
		contaPesquisa.setEconomiaResidencial(Integer.valueOf(result.get(3)));
		contaPesquisa.setEconomiaComercial(Integer.valueOf(result.get(4)));
		contaPesquisa.setEconomiaIndustrial(Integer.valueOf(result.get(5)));
		contaPesquisa.setEconomiaPublica(Integer.valueOf(result.get(6)));
		contaPesquisa.setEconomiaValvula(Integer.valueOf(0));
		contaPesquisa.setTipoContrato((result.get(7)));
		contaPesquisa.setDtEmissao(WrapperUtils.addDays(Integer.valueOf(result.get(8))));
		
		if(contaPesquisa.getVolumeEsgotoNaoDomes() != null){
			pesquisaValorEsgotoNaoDomest(contaPesquisa,dadosImoveis.getImovel());
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Recupera o ValorEsgoto Nao Doméstico
	 * @param contaPesquisa
	 * @param imovel 
	 * @param dadosImoveis 
	 */
	private void pesquisaValorEsgotoNaoDomest(Conta contaPesquisa, Imovel imovel) {
		
		String sqlQuery = "Select b.VLEFLUENT from CEA12 b where " +
	    " b.nrrgilig=? " +
	    " and b.amjrefer = ? and b.tpconta= ? and b.nrseqcta2= ?" +
	    " and b.nrseqcta= ? ";
		
//		CEA12 e tem as mesmas chaves de acesso que as demais: RGI, AMJREFER, TPCONTA, NRSEQCTA2 e NRSEQCTA. O campo com valor é VLEFLUENT.

		try{
		
		ArrayList<String> result = findQBE(sqlQuery,
				 imovel.getDsRgi(),
				 contaPesquisa.getDataReferenciaAnoJuliano(),
				 contaPesquisa.getTpConta(),
				 contaPesquisa.getNrSequenciaConta2(),
				 contaPesquisa.getNrSequenciaConta1()).get(0);
		
		contaPesquisa.setVlEsgotoNaoDom(Double.valueOf(result.get(0)));
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Pesquisa Consumo do HidroComposto
	 */
	public Double pesquisaConsumoHidroComposto(Conta contaPesquisa, DadosImoveisTO dadosImoveis) {
		
		String sqlHidroComposto = "Select A.STCONSUMO,A.QTCONSUMO,a.vlleitura, B.QTCONSUMO FROM CEA15 A,CFE27 B " +
				"WHERE A.NRRGILIG=? AND A.AMJREFER=? AND a.stregist=1 and A.NRRGILIG=B.NRRGILIG AND A.AMJREFER=B.AMJREFER ";
		
		double value= 0d;
		try{
			
			ArrayList<String> result = findQBE(sqlHidroComposto,
					 dadosImoveis.getImovel().getDsRgi(),
					 contaPesquisa.getDataReferenciaAnoJuliano()).get(0);
			
			if(result != null){
				value = Double.valueOf(result.get(1)); //QTCONSUMO
				contaPesquisa.setVlAguaHidroComposto(value);
				contaPesquisa.setSituacaoConsumoHidroComposto(TipoConsumo.byValue(result.get(0)).getRealName()); //STCONSUMO
				contaPesquisa.setVolumeAguaHidroComposto(result.get(2) != null ? Double.valueOf(result.get(2)): 0d);
			}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		return value;
	}


	/**
	 * Carrega os comentários da conta / multas / observações
	 * @param contaPesquisa
	 * @param instrucoes
	 * @param amjReferencia
	 * @param tipoContrato
	 */
	private ArrayList<String> carregaComentariosConta(Conta contaPesquisa,
			long amjReferencia, String tipoContrato) {
			ArrayList<String> instrucoes = new ArrayList<String>();
		//		If (conta.amjrefer > 278 Or (conta.amjrefer  = 278 And orgi.nrciclo > 04)) Then ' D.D.005/2003
		//		instrucoes = "Pagamento em atraso terá Multa de 2%, mais Atualização Monetária com base na  variação do IPC/FIPE do mês anterior, mais Juros de Mora de 1% ao mês, pró-rata-die SERÃO ACRESCIDAS NA PRÓXIMA CONTA."
				if( amjReferencia > 278 ||  ( amjReferencia == 278 &&  
							Integer.valueOf(contaPesquisa.getNrCiclo()).intValue() > 4 )){
					instrucoes.add("Pagamento em atraso terá Multa de 2%, mais Atualização Monetária com base na  variação do IPC/FIPE do mês anterior, mais Juros de Mora de 1% ao mês, pró-rata-die SERÃO ACRESCIDAS NA PRÓXIMA CONTA.");
					
		//			If conta.TpContrato <> (76008) And conta.TpContrato <> (76031) And conta.TpContrato <> (76033) And conta.TpContrato <> (76020) then
		//			instrucoes = instrucoes & "<BR><BR> O não pagamento até a data de vencimento sujeita o imóvel ao corte do fornecimento de água. "
		//			End If
					if((!tipoContrato.equals("76008")) && (!tipoContrato.equals("76031"))  && 
							(!tipoContrato.equals("76033")) && (!tipoContrato.equals("76020"))){
						instrucoes.add("\n O não pagamento até a data de vencimento sujeita o imóvel ao corte do fornecimento de água. ");
					}
					
					//(conta.amjrefer > 249 Or (conta.amjrefer  = 249 And orgi.nrciclo > 05
				}else if( amjReferencia > 249 ||  ( amjReferencia == 249 &&  
						Integer.valueOf(contaPesquisa.getNrCiclo()).intValue() > 5 )){
					
					//If conta.TpContrato <> (76008) And conta.TpContrato <> (76031) And conta.TpContrato <> (76033) And conta.TpContrato <> (76020) then
					if((!tipoContrato.equals("76008")) && (!tipoContrato.equals("76031"))  && 
							(!tipoContrato.equals("76033")) && (!tipoContrato.equals("76020"))){
						instrucoes.add("CONTA EM ATRASO \n Sujeita o imóvel ao corte do fornecimento de água.\n");
					}
					instrucoes.add(" Multa, atualização monetária (Variação IPC/FIPE do mês anterior) e juros de mora SERÃO ACRESCIDOS NA PRÓXIMA CONTA. \n - " +
							"Multas: de 1 a 10 dias: 2%. De 11 a 30 dias: 6%. Mais de 30");
				}else{
					
		//			If conta.TpContrato <> (76008) And conta.TpContrato <> (76031) And conta.TpContrato <> (76033) And conta.TpContrato <> (76020) then
					if((!tipoContrato.equals("76008")) && (!tipoContrato.equals("76031"))  && 
							(!tipoContrato.equals("76033")) && (!tipoContrato.equals("76020"))){
						instrucoes.add("A conta não paga até a data de vencimento sujeita seu imóvel ao corte do fornecimento de água. \n ");	
					}
					instrucoes.add("Multa e atualização monetária por pagamento após o vencimento serão incluídas na próxima conta. \n VALORES EXPRESSOS EM REAIS");
				}
				
				if(contaPesquisa.getIspec().equals("CFRAB")){
					if(contaPesquisa.getQuantidadeParcelas() != 1){ //FIXME gustavo , verificar onde pegar a qt parcelas da conta
						instrucoes.add("ATENÇÃO BANCO --> NÃO RECEBER O PÓS VENCIMENTO.");
					}
				}
				return instrucoes;
	}

    /**
	 *	Carrega valores nao contabilizados do parcelamento de Entidade Publica
	 * 
	 * @param codigoMunicipio
	 * @param codigoCliente
	 * @return
	 */
	public void pesquisaDescontosImovelEntidadePublico(Conta contaPesquisa, DadosImoveisTO dadosImoveis) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			
			//cpu14 tabela com os descontos que a sabesp deve colocar para imoveis da entidade publica
			String sqlQuery = "select vlcmonet,vljurmora,vlmulta,vldescont from cpu14 where "+
							 " nrrgipar=? and amjrefer= ? " +
							 " and tpconta= ? and nrseqcta2= ? "+
			           		 " and nrseqcta= ? ";
			
			agvlogger.info("Query pesquisaDescontaImovelEntidadePublico ==> \n" + sqlQuery);
		
			ps = getHibernateSession().connection().prepareStatement(sqlQuery);
			ps.setString(1,  dadosImoveis.getImovel().getDsRgi()); 							
			ps.setString(2,  contaPesquisa.getDataReferenciaAnoJuliano());
			ps.setString(3,  contaPesquisa.getTpConta());
			ps.setString(4,  contaPesquisa.getNrSequenciaConta2());
			ps.setString(5,  contaPesquisa.getNrSequenciaConta1());
			
			agvlogger.info("Query param 1 ==> "+ dadosImoveis.getImovel().getDsRgi()); 				
			agvlogger.info("Query param 2 ==> "+ contaPesquisa.getDataReferenciaAnoJuliano());
			agvlogger.info("Query param 3 ==> "+ contaPesquisa.getTpConta());
			agvlogger.info("Query param 4 ==> "+  contaPesquisa.getNrSequenciaConta2());
			agvlogger.info("Query param 5 ==> "+ contaPesquisa.getNrSequenciaConta1());
			
			rs = ps.executeQuery();			
			while (rs.next()) {
				System.out.println("--> " + rs.getString(1));
				contaPesquisa.setVlDesconto(rs.getDouble(4));
				contaPesquisa.setVlMulta(rs.getDouble(3));
				contaPesquisa.setVlJuros(rs.getDouble(2));
				contaPesquisa.setVlConsumoMonetario(rs.getDouble(1));
				contaPesquisa.setVlRetencao(contaPesquisa.getVlDesconto());
				
				/**
				 * 	Regra Original Asp -  Denis
				 *  Set oTb = RGI.CSI.SQLExecute(sSQL)
					    If Not oTb.EOF Then
					        nVLCONTA = nVLCONTA + oTb("vlcmonet") + oTb("vljurmora") + oTb
					("vlmulta")
					        nVLRETENCAO = oTb("vldescont")
					    End If
				 */
				double vlTotal = contaPesquisa.getVlTotal().doubleValue();
				contaPesquisa.setVlTotal(vlTotal + contaPesquisa.getVlConsumoMonetario()+ contaPesquisa.getVlJuros() +  contaPesquisa.getVlMulta());
				
				break; // so passa uma vez por essa regra
			}
			
		}catch(Exception e){
			e.printStackTrace();
			agvlogger.error("Erro ao executar pesquisaDescontaImovelEntidadePublico", e);
		}finally{
			fecharRecurso(ps, null, rs);
		}
		
	}
	    
	    

	/**
	 * Busca condicao de faturamento por codigo cliente e municipio
	 * 
	 * @param codigoMunicipio
	 * @param codigoCliente
	 * @return
	 */
	public Cliente carregarDadosCondicaoFaturamento(Cliente cliente){
		String codCliente = cliente.getCdCliente();
		String codMunicipio = cliente.getCdMunicipio();
		String cdContFat = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			/**
			 *  busca controle faturamento
			 */
			
			// TABELAS
			// ccg03    -> cadastro
			// pccg0311 -> profile  			
			String sqlQuery = " SELECT  b.cdcontfat " +
	  						  " FROM pccg0311 a, ccg03 b " +
							  " WHERE a.cdmunicip=? " +
							  " and a.cdcliente=? and a.nrgrupfat=b.nrgrupfat and a.nrrgilig=b.nrrgilig " +
							  " AND b.CDCOBR in ('6','7') fetch first 1 row only ";  // 6, 7 rol especial
			
			agvlogger.info("Query ==> \n" + sqlQuery);
		
			ps = getHibernateSession().connection().prepareStatement(sqlQuery);
			ps.setString(1, codMunicipio); 							
			ps.setString(2, codCliente); 							
			
			agvlogger.info("Query param 1 ==> "+ codMunicipio); 		
			agvlogger.info("Query param 2 ==> "+ codCliente); 		
			
			rs = ps.executeQuery();			
			while (rs.next()) {
				System.out.println("--> " + rs.getString(1));
				cdContFat =  rs.getString(1);
				cliente.setCdContFat(cdContFat);
				agvlogger.info("Cliente codCliente=" + codCliente + " é Rol Especial.");
				break;
			}
			
			agvlogger.info("Condicao faturamento para cdCliente " + cliente.getCdCliente() + " codMunicipio= " + cliente.getCdMunicipio() + " = " + cdContFat);
			
		}catch(Exception e){
			// TODO - throw exception
			e.printStackTrace();
			agvlogger.error("Erro ao executar pesquisaCondicaoFaturamento", e);
		}finally{
			fecharRecurso(ps, null, rs);
		}
		
		return cliente;
	}
	
	
	/**
	 * Carrega Dados básicos para criação do boleto
	 */
	public Conta carregarDadosBoletoRolComum(Conta contaPesquisa) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try{
			
			String sqlQueryMediaEConsumo =
				"SELECT " +
				"QTCONSUMO, " +
				"QTCONSMED " +
				"FROM CEA15 tbl " +
				"WHERE tbl.NRRGILIG = ? " +
				"AND tbl.AMJREFER = ?";
			
			pstmt = getHibernateSession().connection().prepareStatement(sqlQueryMediaEConsumo);
			
			pstmt.setString(1, contaPesquisa.getImovel().getDsRgi() );
			pstmt.setString(2, contaPesquisa.getDataReferenciaAnoJuliano() );
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				contaPesquisa.setQuantidadeConsumo( rs.getString(1) );
				contaPesquisa.setQtMedia( rs.getString(2) );
			}
			
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			
			String sqlQueryHidrometro =
				"SELECT " +
				"CDHIDRO " +
				"FROM CEA07 tbl " +
				"WHERE tbl.STREGIST = 1 " +
				"AND tbl.NRRGILIG = ?";
			
			pstmt = getHibernateSession().connection().prepareStatement(sqlQueryHidrometro);
			pstmt.setString(1, contaPesquisa.getImovel().getDsRgi() );
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ){
				contaPesquisa.setCodigoHidrometro( rs.getString(1) );
			}
			
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			
/*			
			pesquisaMulta2via(contaPesquisa);

			pesquisaAtualizacaoMonetaria2via(contaPesquisa);
			
			pesquisaJurosMora2via(contaPesquisa);
			
			pesquisaDebito2via(contaPesquisa);
			
			pesquisaCredito2via(contaPesquisa);
*/
			
			/*
			 * Regra UC. atualizada
			 * Se ispec == CFRAB
			 * exibir no boleto o vlRetencao da conta
			 * senão exibir vlDescont da Event (objeto Conta)
			 * */
			if( contaPesquisa.getIspec().equals("CFRAB") ) {
				
				/**/
				String sqlRetencao =
					"SELECT " +
					"VLCONTAG2 " +
					"FROM CPU03 tbl " +
					"WHERE tbl.NRRGIPAR = " + contaPesquisa.getImovel().getDsRgi() +
					" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
					" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
					" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();
					
				if( contaPesquisa.getNrSequenciaConta1() != null )
					sqlRetencao +=	" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();
				
				stmt = getHibernateSession().connection().createStatement();
				
				rs = stmt.executeQuery(sqlRetencao);
				
				while(rs.next()){
					contaPesquisa.setVlRetencao( rs.getDouble(1) );
				}
				
				rs.close();
				rs = null;
				stmt.close();
				stmt  = null;			
				/**/
				
			}
			
			
			String sqlServicos =
				"SELECT " +
				"VLSERVCOM " +
				"FROM CEA11 tbl " +
				"WHERE tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi() +
				" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
				" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
				" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();
				
			if( contaPesquisa.getNrSequenciaConta1() != null )
				sqlServicos +=	" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();
			
			stmt = getHibernateSession().connection().createStatement();
			
			rs = stmt.executeQuery(sqlServicos);
			
			while(rs.next()){
				contaPesquisa.setVlServicos( rs.getDouble(1) );
			}
			
			rs.close();
			rs = null;
			stmt.close();
			stmt  = null;	
			
			/*
			 * montagem codificação sabesp 
			 * CCG03.TPCODIFIC = 1 (Metropolitana)
			 * CCG03.TPCODIFIC = 2 (Litoral)
			 * CCG03.TPCODIFIC = 3 (Interior)
			 */
			String sqlCodificaoSabesp =
				"SELECT " +
				
				/* TPCODIFIC=1 (Metropolitana), TPCODIFIC=2 (Litoral), TPCODIFIC=3 (Interior) */
				"TPCODIFIC, " +
				
				"NRGRUPFAT, " +
				"CDSETFATU, " +
				"CDROTAFAT, " +
				"CDQUADRA, " +
				"CDLOCFAT, " +
				"CDVILAFAT, " +
				"CDSLOCFAT, " +
				"CDUNIDCO1, " +
				"CDMUNICIP " +				
				"FROM CCG03 tbl " +
				"WHERE tbl.NRGRUPFAT = " + contaPesquisa.getImovel().getNrGrupoFaturamento().trim() +
				" AND tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi();
			
			stmt = getHibernateSession().connection().createStatement();
			
			rs = stmt.executeQuery(sqlCodificaoSabesp);			
			
			while(rs.next()){
				
				String TPCODIFIC = rs.getString(1);
				
				/* Metropolitana */
				String NRGRUPFAT = rs.getString(2);
				String CDSETFATU = rs.getString(3);
				String CDROTAFAT = rs.getString(4);
				String CDQUADRA = rs.getString(5);
				String CDLOCFAT = rs.getString(6);
				String CDVILAFAT = rs.getString(7);
				String CDSLOCFAT = rs.getString(8);
				
				/* Litoral */
				String CDUNIDCO1 = rs.getString(9);
				String CDMUNICIP = rs.getString(10);

				/* Metropolitana */
				Integer caseTipoCodific = Integer.valueOf( TPCODIFIC );
				String codificacaoSabesp = null;
				
				switch (caseTipoCodific) {
					
					case 1:
						codificacaoSabesp = 
							ContaUtil.tratarCampoCodificacaoSabesp( NRGRUPFAT.trim(), 2 ) + " " +
							ContaUtil.tratarCampoCodificacaoSabesp( CDSETFATU.trim(), 3  )+ " " +
							ContaUtil.tratarCampoCodificacaoSabesp( CDROTAFAT.trim(), 3 ) + " " +
							ContaUtil.tratarCampoCodificacaoSabesp( CDQUADRA.trim(), 4 ) + " " +
							ContaUtil.tratarCampoCodificacaoSabesp( CDLOCFAT.trim(), 4 ) + " " +
							ContaUtil.tratarCampoCodificacaoSabesp( CDVILAFAT.trim(), 4 ) + " " +
							ContaUtil.tratarCampoCodificacaoSabesp( CDSLOCFAT.trim(), 4 );		
						break;
	
					case 2:		
						codificacaoSabesp = CDUNIDCO1.trim() + CDMUNICIP.trim() + CDSETFATU.trim() + CDQUADRA.trim() + CDLOCFAT.trim() + CDSLOCFAT.trim();
						break;
						
					case 3:					
						codificacaoSabesp = CDUNIDCO1.trim() + CDMUNICIP.trim() + CDSETFATU.trim() + CDROTAFAT.trim() + CDLOCFAT.trim() + CDVILAFAT.trim() + CDSLOCFAT.trim() + CDQUADRA.trim();				
						break;					
						
					default:
						break;
				}
				
				contaPesquisa.setCdSabesp( codificacaoSabesp );
			}
			
			
		/* Identifica qual o tipo de boleto 2 via da conta
		 * - acordo rompido
		 * - normal rol comum
		 * - parcelada
		 * - reformada rol comum  
		 *  */
			
/*		ContaParcelaValueType contaParcelaVT = new ContaParcelaValueType();
		ContaParcelaUnicaValueType contaParcelaUnicaVT = new ContaParcelaUnicaValueType();
		ContaReformadaValueType contaReformada = new ContaReformadaValueType();
		ContaNormalValueType contaNormal = new ContaNormalValueType();
		ContaRompidoValueType contaRompido = new ContaRompidoValueType();
		
		contaPesquisa.setContaRompido(contaRompido);
		contaPesquisa.setContaParcelada(contaParcelaVT);
		contaPesquisa.setContaParcelaUnica(contaParcelaUnicaVT);		
		contaPesquisa.setContaReformada(contaReformada);*/
		
		/* parcela ou parcela unica rol comum */	
		if( contaPesquisa.getIspec().equals("CFRAB") ){
			
			

			
			String qtdParcela = null;
			String nrAcordo = null;

			
			String sqlQtdParcelas =
				"SELECT " +
				"QTPARCELA, " +
				"NRACORDO " +
				"FROM CPU01 tbl " +
				"WHERE tbl.NRRGIPAR = " + contaPesquisa.getImovel().getDsRgi() +
				" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
				" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
				" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();				
			
			stmt = getHibernateSession().connection().createStatement();
			
			rs = stmt.executeQuery(sqlQtdParcelas);	

						
			while( rs.next() ){
				qtdParcela = rs.getString(1);
				nrAcordo = rs.getString(2);
			}
			
			rs.close();
			rs = null;
			pstmt = null;
			
			int parcela = Integer.valueOf(qtdParcela);
			int numeroArcodo = Integer.valueOf(nrAcordo); 
			
			/* Numero da parcela está na própria event (objeto conta) */
			contaPesquisa.getContaParcelada().setAcordo(numeroArcodo);
			contaPesquisa.getContaParcelada().setAcordo(numeroArcodo);
			
			if( parcela > 1 ) {
				/* parcela */
				contaPesquisa.getContaParcelada().setExibir(true);
				String valorParcela = null;
				
				String sqlValorParcelas =
					"SELECT " +
					"VLPARCELA " +
					"FROM CPU03 tbl " +
					"WHERE tbl.NRRGIPAR = " + contaPesquisa.getImovel().getDsRgi() +
					" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
					" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
					" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();	
				
				stmt = getHibernateSession().connection().createStatement();
				
				rs = stmt.executeQuery(sqlValorParcelas);	
				
				while( rs.next() ) {
					valorParcela = rs.getString(1);
				}
				
				contaPesquisa.setVlTotal(Double.valueOf(valorParcela));
				
				
				
				rs.close();
				rs = null;
				pstmt = null;
				
			} else if( parcela == 1 ) {
				/* parcela unica */
				contaPesquisa.getContaParcelaUnica().setExibir(true);
				String valorParcela = null;
				
				String sqlValorParcelas =
					"SELECT " +
					"VLPARCELA " +
					"FROM CPU03 tbl " +
					"WHERE tbl.NRRGIPAR = " + contaPesquisa.getImovel().getDsRgi() +
					" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
					" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
					" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();	
				
				stmt = getHibernateSession().connection().createStatement();
				
				rs = stmt.executeQuery(sqlValorParcelas);	
				
				while( rs.next() ) {
					valorParcela = rs.getString(1);
				}
				
				contaPesquisa.setVlTotal(Double.valueOf(valorParcela));
				

				
				rs.close();
				rs = null;
				pstmt = null;				
			}
			
			
			
			
		}
		
		/* normal rol comum */
		if( contaPesquisa.getIspec().equals("CFRAA") ){			

			contaPesquisa.getContaNormal().setExibir(true);
			
			pesquisaMulta2via(contaPesquisa);

			pesquisaAtualizacaoMonetaria2via(contaPesquisa);
			
			pesquisaJurosMora2via(contaPesquisa);
			
			pesquisaDebito2via(contaPesquisa);
			
			pesquisaCredito2via(contaPesquisa);
			
			pesquisaRetencaoRolComum(contaPesquisa, "contanormal");
			
			pesquisaServicoRolComum(contaPesquisa);
			
		}
		
		/* normal reformada rol comum*/
		if( contaPesquisa.getIspec().equals("CFRAC") ){
			
			contaPesquisa.getContaReformada().setExibir(true);
			pesquisaMulta2via(contaPesquisa);

			pesquisaAtualizacaoMonetaria2via(contaPesquisa);
			
			pesquisaJurosMora2via(contaPesquisa);
			
			pesquisaDebito2via(contaPesquisa);
			
			pesquisaCredito2via(contaPesquisa);
			
			pesquisaRetencaoRolComum(contaPesquisa, "contanormal");
			
			pesquisaServicoRolComum(contaPesquisa);
			
		}
		
		/* rompido rol comum */
		if( contaPesquisa.getIspec().equals("CFRAP") ) {

			String qtdParcela = null;
			String nrAcordo = null;

			
			String sqlQtdParcelas =
				"SELECT " +
				"NRACORDO " +
				"FROM CPU01 tbl " +
				"WHERE tbl.NRRGIPAR = " + contaPesquisa.getImovel().getDsRgi() +
				" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
				" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
				" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();				
			
			stmt = getHibernateSession().connection().createStatement();
			
			rs = stmt.executeQuery(sqlQtdParcelas);	

						
			while( rs.next() ){
				nrAcordo = rs.getString(2);
			}
			
			rs.close();
			rs = null;
			pstmt = null;
			
		}
			
			
		}catch (Exception e) {			
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro ao acessar o DB.");
		}finally {
			try {
				if( rs != null )
					rs.close();
				
				if( pstmt != null )
					pstmt.close();
				
				if( stmt != null )
					stmt.close();
				
			}catch (SQLException e) {
				agvlogger.error("Erro consulta Media :"+ e.getMessage());
				new IllegalArgumentException("Erro ao acessar o DB.");

			}
		}
		
		return contaPesquisa;
	}
	
	private void pesquisaServicoRolComum(Conta contaPesquisa){
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
		
			String sqlServicos =
				"SELECT " +
				"VLSERVCOM " +
				"FROM CEA11 tbl " +
				"WHERE tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi() +
				" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
				" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
				" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2() +
				" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();
			
			stmt = getHibernateSession().connection().createStatement();
			
			rs = stmt.executeQuery(sqlServicos);	

						
			while( rs.next() ){
				contaPesquisa.getContaNormal().setVlServico( rs.getString(1) );				
			}
			
		}catch (Exception e) {			
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro ao acessar o DB.");
		}finally {
			try {
				if( rs != null )
					rs.close();
				
				if( pstmt != null )
					pstmt.close();
				
				if( stmt != null )
					stmt.close();
				
			}catch (SQLException e) {
				agvlogger.error("Erro consulta Media :"+ e.getMessage());
				new IllegalArgumentException("Erro ao acessar o DB.");

			}
		}
		
	}
	
	private void pesquisaRetencaoRolComum(Conta contaPesquisa, String tipoDeConta) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		if( tipoDeConta.equals("contanormal") ){			
			
			String vlRetencao = String.valueOf( contaPesquisa.getVlRetencao() );
			
			vlRetencao = (vlRetencao.equals("null")) ? null : vlRetencao; 
			
			contaPesquisa.getContaNormal().setVlRetencao( vlRetencao );			
			
		}
		
		if(  tipoDeConta.equals("contaparcelada") ){
			
			String sqlCredito = 
				"SELECT " +
				"VLCONTAG2 " +
				"FROM CEA22 tbl " +
				"WHERE tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi() +
				" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
				" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
				" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();
				
			if( contaPesquisa.getNrSequenciaConta1() != null )
				sqlCredito +=	" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();
			
			sqlCredito += 
				" AND tbl.STREGIST=1 " + 
				" AND tbl.TPREGISTR IN (2,4)";
			
			stmt = getHibernateSession().connection().createStatement();
			
			rs = stmt.executeQuery(sqlCredito);
			
			BigDecimal soma = new BigDecimal(0L);

			while( rs.next() ) {
				
				BigDecimal vlCredito = new BigDecimal( String.valueOf(rs.getDouble(1))  );
				vlCredito.setScale(2, BigDecimal.ROUND_HALF_UP);
				soma = soma.add( vlCredito);

			}
			
			rs.close();
			rs = null;
			stmt.close();
			stmt  = null;
			
			contaPesquisa.getContaNormal().setVlCreditos(soma.toString());
			
			
		}
		

		

		
	
	}
	



	/**
	 * @param contaPesquisa
	 * @throws SQLException
	 */
	private void pesquisaCredito2via(Conta contaPesquisa) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sqlCredito = 
			"SELECT " +
			"VLCONTAG2 " +
			"FROM CEA22 tbl " +
			"WHERE tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi() +
			" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
			" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
			" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();
			
		if( contaPesquisa.getNrSequenciaConta1() != null )
			sqlCredito +=	" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();
		
		sqlCredito += 
			" AND tbl.STREGIST=1 " + 
			" AND tbl.TPREGISTR IN (2,4)";
		
		stmt = getHibernateSession().connection().createStatement();
		
		rs = stmt.executeQuery(sqlCredito);
		
		BigDecimal soma = new BigDecimal(0L);

		while( rs.next() ) {
			
			BigDecimal vlCredito = new BigDecimal( String.valueOf(rs.getDouble(1))  );
			vlCredito.setScale(2, BigDecimal.ROUND_HALF_UP);
			soma = soma.add( vlCredito);

		}
		contaPesquisa.setVlCreditos(soma.doubleValue());
		contaPesquisa.getContaNormal().setVlCreditos(soma.toString());
		
		rs.close();
		rs = null;
		stmt.close();
		stmt  = null;
	}


	/**
	 * @param contaPesquisa
	 * @throws SQLException
	 */
	private void pesquisaDebito2via(Conta contaPesquisa) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sqlDebito = 
			"SELECT " +
			"VLCONTAG2 " +
			"FROM CEA22 tbl " +
			"WHERE tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi() +
			" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
			" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
			" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();
			
		if( contaPesquisa.getNrSequenciaConta1() != null )
			sqlDebito +=	" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();
		
		sqlDebito += 
			" AND tbl.STREGIST=1 " + 
			" AND tbl.TPREGISTR IN (1,3)";
		
		agvlogger.info("QUERY DEBITO===>"+sqlDebito);
		
		stmt = getHibernateSession().connection().createStatement();
		
		rs = stmt.executeQuery(sqlDebito);
		
		BigDecimal soma = new BigDecimal(0L);

		while( rs.next() ) {
			
			BigDecimal vlDebito = new BigDecimal( String.valueOf(rs.getDouble(1))  );
			vlDebito.setScale(2, BigDecimal.ROUND_HALF_UP);
			soma = soma.add( vlDebito);

		}
		
		contaPesquisa.setVlDebitos(soma.doubleValue());
		contaPesquisa.getContaNormal().setVlDebitos(soma.toString());
		
		rs.close();
		rs = null;
		stmt.close();
		stmt  = null;
	}


	/**
	 * @param contaPesquisa
	 * @throws SQLException
	 */
	private void pesquisaJurosMora2via(Conta contaPesquisa) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sqlJurosMora = 
			"SELECT " +
			"VLCONTAG " +
			"FROM CEA04 tbl " +
			"WHERE tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi() +
			" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
			" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
			" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();
			
			if( contaPesquisa.getNrSequenciaConta1() != null )
				sqlJurosMora +=	" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();

		stmt = getHibernateSession().connection().createStatement();	
			
		rs = stmt.executeQuery(sqlJurosMora);
		
		BigDecimal soma = new BigDecimal(0L);

		while( rs.next() ) {
			
			BigDecimal vlJuros = new BigDecimal( String.valueOf(rs.getDouble(1))  );
			vlJuros.setScale(2, BigDecimal.ROUND_HALF_UP);
			soma = soma.add( vlJuros);

		}
		
		contaPesquisa.setVlJuros(soma.doubleValue());
		contaPesquisa.getContaNormal().setVlJurosMora(soma.toString());
		
		rs.close();
		rs = null;
		stmt.close();
		stmt  = null;
	}


	/**
	 * @param contaPesquisa
	 * @throws SQLException
	 */
	private void pesquisaAtualizacaoMonetaria2via(Conta contaPesquisa)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String sqlAtMonetaria = 
			"SELECT " +
			"VLCONTAG " +
			"FROM CEA10 tbl " +
			"WHERE tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi() +
			" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
			" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
			" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();
			
			if( contaPesquisa.getNrSequenciaConta1() != null )
				sqlAtMonetaria +=	" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();
			
		stmt = getHibernateSession().connection().createStatement();	
			
		rs = stmt.executeQuery(sqlAtMonetaria);
		
		BigDecimal soma = new BigDecimal(0L);
		
		 
		while( rs.next() ) {
			
			BigDecimal vlAM = new BigDecimal( String.valueOf(rs.getDouble(1))  );
			vlAM.setScale(2, BigDecimal.ROUND_HALF_UP);
			soma = soma.add( vlAM);

		}
		contaPesquisa.setVlAtMonetaria(soma.doubleValue());
		contaPesquisa.getContaNormal().setVlAtualizacaoMonetaria(soma.toString());
		
		rs.close();
		rs = null;
		stmt.close();
		stmt  = null;
	}


	/**
	 * @param contaPesquisa
	 * @return
	 * @throws SQLException
	 */
	private void pesquisaMulta2via(Conta contaPesquisa) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		String queryMulta = 
			"SELECT " +
			"VLCONTAG " +
			"FROM CEA03 tbl " +
			"WHERE tbl.NRRGILIG = " + contaPesquisa.getImovel().getDsRgi() +
			" AND tbl.AMJREFER = " + contaPesquisa.getDataReferenciaAnoJuliano() +
			" AND tbl.TPCONTA = "  + contaPesquisa.getTpConta() +
			" AND tbl.NRSEQCTA2 = " + contaPesquisa.getNrSequenciaConta2();
			
			if( contaPesquisa.getNrSequenciaConta1() != null )
				queryMulta +=	" AND tbl.NRSEQCTA = " + contaPesquisa.getNrSequenciaConta1();
			
		stmt = getHibernateSession().connection().createStatement();

		rs = stmt.executeQuery(queryMulta);
		
		
		BigDecimal soma = new BigDecimal(0L);

		while( rs.next() ) {
			
			BigDecimal multa = new BigDecimal( String.valueOf(rs.getDouble(1))  );
			multa.setScale(2, BigDecimal.ROUND_HALF_UP);
			soma = soma.add( multa);

		}

		contaPesquisa.setVlMulta(soma.doubleValue());
		contaPesquisa.getContaNormal().setVlMulta(soma.toString());
		
		rs.close();
		rs = null;
		stmt.close();
		stmt = null;
	}
	
	
	/**
	 * Pesquisa A data de leitura para a conta de Rol especial
	 * tpEvento = (1=leitura intermediaria, 8=leitura mensal e 16=vencimento)
	 * @param contaPesquisa
	 * @throws SQLException
	 */
	private void pesquisaDataLeituraConta(Conta contaPesquisa) throws SQLException {
		PreparedStatement pstmt = null;
		
		String queryDataLeitura = 
			" SELECT " +
			" a.DJLEIT " +
			" from CTF11 a , cfe01 b where " +
			" a.amjrefer = ? " +
			" and a.cdunidcom=b.cdunidco2 " +
			" and a.nrciclo=b.nrciclo" +
			" and a.tpevento = 8 "; // 8=leitura mensal
		
		agvlogger.info("Query ==> " + queryDataLeitura);
			
		pstmt = getHibernateSession().connection().prepareStatement(queryDataLeitura);
		pstmt.setString(1,contaPesquisa.getDataReferenciaAnoJuliano());
//		pstmt.setString(2,contaPesquisa.getImovel().getDsRgi());	
		
		agvlogger.info("Query param 1 ==> "+contaPesquisa.getDataReferenciaAnoJuliano());
//		agvlogger.info("Query param 2 ==> "+contaPesquisa.getImovel().getDsRgi());	
		
		ResultSet rs = pstmt.executeQuery();
		
		while( rs.next() ) {
			contaPesquisa.setDtLeitura(WrapperUtils.addDays(Integer.valueOf(rs.getString(1))));
			break;
		}
		
		rs.close();
		rs = null;
	}
	
	
	public List<Conta> findByImovel(Imovel imovel) {
		return null;
	}


	
	/**
	 * Pesquisa codigo do hidrometro para cada Rgi
	 * @param rgi
	 * @param string
	 */
	public String pesquisaDadosRgiFilhosConta(String rgi, String amjRefer) {
//		String sql = "select cdhidro,amjrefer from cfe44 where nrrgilig= ? and amjrefer = ? ";
		String sql = "SELECT cdhidro FROM CEA07 WHERE STREGIST=1 AND NRRGILIG=? ORDER BY AMJREFER DESC fetch first 1 row only";
		String cdHidro = "";
		ArrayList<String> result = null; 
		try{
			ArrayList<ArrayList<String>> resultado = findQBE(sql,rgi);
			result = (ArrayList<String>) resultado.get(0);
			cdHidro = result.get(0); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cdHidro;	
	}
	
	/**
	 * pesquisaCdHidroComposto
	 */
	public String pesquisaCdHidroComposto(String rgi, String dataReferenciaAnoJuliano) {
		String sql = "select cdhidro,amjrefer from cfe44 where nrrgilig= ? and amjrefer = ? ";
		String cdHidro = "";
		ArrayList<String> result = null; 
		try{
			ArrayList<ArrayList<String>> resultado = findQBE(sql,rgi,dataReferenciaAnoJuliano);
			result = (ArrayList<String>) resultado.get(0);
			cdHidro = result.get(0); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cdHidro;	
	}
	
	/**
	 * Pesquisa consumo das contas filhas de uma conta do rol especial
	 * @param dadosImoveisTO
	 * @param rgi
	 * @return
	 */
	public void carregaDadosConsumoRgisFilhoContaRolEspecial(ArrayList listaResultado, String rgi, String amjRefer) {
		
		ArrayList<Conta> temp = new ArrayList<Conta>();
		PreparedStatement pstmt = null;
		agvlogger.info("#ContaDaoImpl# -> Método  #carregaDadosConsumoRgisFilhoContaRolEspecial# \n");
		
		String sqlQuery = " SELECT a.vlleitura, b.qtconsumo, a.stconsumo , b.vlleitura as vlleitcomp " +
				" FROM cea15 a LEFT OUTER JOIN cfe46 b " +
				" ON a.nrrgilig=b.nrrgilig and a.amjrefer=b.amjrefer and a.tpconta=b.tpconta and " +
				" a.nrseqcta2=b.nrseqcta2 and a.nrseqcta=b.nrseqcta where a.nrrgilig= ? " +
				" and a.stregist=1 and a.amjrefer = ? ";
				
		
		agvlogger.info("Query ==> \n" + sqlQuery);
		
		try{
			pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, rgi);
			pstmt.setString(2, amjRefer);
			agvlogger.info("Param 1==> \n" + rgi);
			agvlogger.info("Param 2==> \n" + amjRefer);
			ResultSet rs = pstmt.executeQuery();
			while( rs.next() ){
				listaResultado.add(rs.getString(1) == null ? "" : rs.getString(1)); //3 leituraAtual
				listaResultado.add(rs.getString(2) == null ? "" : rs.getString(2)); //4 consumo
				listaResultado.add(TipoConsumo.byValue(rs.getString(3)).getRealName()); //5 codConsumo
				
				break;
			}
			rs.close();
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
			agvlogger.error("Erro consulta RGI:"+ e.getMessage());
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
	}


	
	
	
	

	
}
