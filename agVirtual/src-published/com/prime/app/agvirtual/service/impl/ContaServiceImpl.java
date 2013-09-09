package com.prime.app.agvirtual.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.prime.app.agvirtual.dao.AcatamentoContaCSIDao;
import com.prime.app.agvirtual.dao.AgenciaDao;
import com.prime.app.agvirtual.dao.BairroDao;
import com.prime.app.agvirtual.dao.CategoriaDao;
import com.prime.app.agvirtual.dao.ClienteDao;
import com.prime.app.agvirtual.dao.ContaDao;
import com.prime.app.agvirtual.dao.EnderecoDao;
import com.prime.app.agvirtual.dao.ImovelDao;
import com.prime.app.agvirtual.dao.MunicipioDao;
import com.prime.app.agvirtual.dao.OrcamentoDao;
import com.prime.app.agvirtual.dao.TarifaDao;
import com.prime.app.agvirtual.entity.Categoria;
import com.prime.app.agvirtual.entity.Cliente;
import com.prime.app.agvirtual.entity.Conta;
import com.prime.app.agvirtual.entity.Endereco;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.service.BloqueioService;
import com.prime.app.agvirtual.service.ContaService;
import com.prime.app.agvirtual.to.Acatamento2ViaTO;
import com.prime.app.agvirtual.to.AgenciaTO;
import com.prime.app.agvirtual.to.DadosImoveisTO;
import com.prime.app.agvirtual.util.BoletoUtil;
import com.prime.infra.BloqueioException;
import com.prime.infra.BusinessException;
import com.prime.infra.util.WrapperUtils;
import com.prime.infra.web.jsf.util.FacesBundleUtil;

@Service
public class ContaServiceImpl implements ContaService {
	
	@Autowired
	private ImovelDao imovelDAO;
	
	@Autowired
	private ContaDao contaDAO;
	
	@Autowired
	private AcatamentoContaCSIDao contaCsiDAO;
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private BairroDao bairroDao;
	
	@Autowired
	private EnderecoDao enderecoDao;
	
	@Autowired
	private MunicipioDao municipioDao;
	
	@Autowired
	private CategoriaDao categoriaDao;
	
	@Autowired
	private TarifaDao tarifaDAO;
	
	@Autowired
	private AgenciaDao agenciaDao;
	
	@Autowired
	private OrcamentoDao orcamentoDao;
	
	@Autowired
	private BloqueioService bloqueioService;
	

	@Transactional(readOnly = true)
	public DadosImoveisTO identificarImovel(Imovel imovel , String cdItemMenu ,String jsession) throws Exception{
		
		DadosImoveisTO dadosImoveisTO = new DadosImoveisTO();
		dadosImoveisTO.setJSessionId(jsession);
		
		Imovel imovelCarregado = imovelDAO.findByImovel(imovel);
		
		if(imovelCarregado.getDsRgi() == null)
			throw new BusinessException(FacesBundleUtil.getInstance().getString("erro.pesquisa.rgi.invalido"));
		
		Cliente clientePesquisa = new Cliente();
		clientePesquisa.setCdMunicipio(imovelCarregado.getCdMunicipio());
		clientePesquisa.setCdCliente(imovelCarregado.getCdCliente());
		
		if(imovelCarregado.getCdMunicipio().equals("100")) { //100 =  Sao Paulo
			imovelCarregado.setDsDiretoria("M"); 
		}else{
			imovelCarregado.setDsDiretoria("R");
		}
		
		Cliente clienteCarregado = clienteDao.findByCliente(clientePesquisa);
		
		Endereco enderecoPesquisa = new Endereco();
		enderecoPesquisa.setImovel(imovelCarregado);
		
		Endereco enderecoCarregado = enderecoDao.findByEndereco(enderecoPesquisa);
		
		dadosImoveisTO.setEndereco(enderecoCarregado);
		dadosImoveisTO.setCliente(clienteCarregado);

		/**
		 * Verifica se o imovel é de rol especial e seta sua vicepresidencia 
		 */
		imovelDAO.verificaRgiRolEspecial(imovelCarregado);
		
		dadosImoveisTO.setImovel(imovelCarregado);
		
		clienteDao.pesquisaNumeroCpfCnpjCliente(dadosImoveisTO);
		
		AgenciaTO agencia  = null;
		if(imovelCarregado.isRolEspecial()){
			agencia = agenciaDao.findAgenciaMaisProximaByRgiRolEspecial(dadosImoveisTO);
		}else{
			agencia = agenciaDao.findAgenciaMaisProximaByRgiRolComum(dadosImoveisTO);
		}
		
		List<Integer> vicePresidenciaUnidadeNegocio = orcamentoDao.getVicePresidenciaUnidadeNegocio(Long.valueOf(dadosImoveisTO.getImovel().getDsRgi()));
		if(vicePresidenciaUnidadeNegocio.size() > 0) {
			dadosImoveisTO.getImovel().setCodigoVicePresidencia(vicePresidenciaUnidadeNegocio.get(0).toString());
			dadosImoveisTO.getImovel().setCodigoAtendimentoComercial(vicePresidenciaUnidadeNegocio.get(1).toString());
		}
		
		dadosImoveisTO.setAgenciaTO(agencia);
		
		/**
		 * Funcionalidade de validar bloqueio
		 */
		if(WrapperUtils.isNotNull(cdItemMenu) && (!cdItemMenu.equals("") && Boolean.FALSE)){ // TODO - Habilitar bloqueio (remover false)
			boolean bloqueado = bloqueioService.isItemMenuBloqueado(cdItemMenu,imovelCarregado.getCdMunicipio());
			if(bloqueado){
				throw new BloqueioException(FacesBundleUtil.getInstance().getString("rgi.bloqueado.item.menu"));
			}
		}
		if(clienteCarregado != null)
		clienteCarregado.setNumeroInscricaoEstadual(clienteDao.pesquisaNumeroInscricaoEstadual(dadosImoveisTO));
		
		dadosImoveisTO.setImovel(imovelCarregado);
		
		return dadosImoveisTO;
	}
	

	@Transactional(readOnly = true)
	public List<Imovel> localizarImovel(Imovel imovel) {
		return this.imovelDAO.findByName(imovel);
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public ArrayList pesquisaContasEmAberto(DadosImoveisTO dadosImoveisTO) {
		/**
		 * Carrega Rgi master antes de fazer a pesquisa por contas em aberto
		 */
		Cliente cliente = dadosImoveisTO.getCliente();
		
		//pesquisa codContFat
		contaDAO.carregarDadosCondicaoFaturamento(cliente);
		ArrayList listaContasAbertos = new ArrayList();
		if(cliente.isRolEspecial()){
			List<String> listaRgis = imovelDAO.findAllRGIs(dadosImoveisTO.getImovel());
			/**
			 * seta lista de rgis no cliente para ser utilizado na segunda via rol especial.
			 */
			dadosImoveisTO.getCliente().setListaRgis(listaRgis);
			/**
			 * Pesquisa Rgi master e seta dentro da variavel de cliente.
			 */
			String rgiMaster = clienteDao.pesquisaRgiMaster(listaRgis);
			cliente.setDsRgiImovelMaster(rgiMaster);
			/**
			 * Pesquisa Contas em Aberto com RGI Master.
			 */
			listaContasAbertos = this.contaDAO.pesquisaContasEmAberto(dadosImoveisTO);
		}else{
			/**
			 * Pesquisa Contas em Aberto com RGI Rol Comum.
			 */
			listaContasAbertos =this.contaDAO.pesquisaContasEmAberto(dadosImoveisTO);
		}
		
		return listaContasAbertos;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Endereco> localizarEnderecos(Endereco enderecoPesquisa) {
		return enderecoDao.getEnderecosByNameAndMunicipio(enderecoPesquisa);		
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Endereco> localizarNumerosEndereco(Endereco endereco){
		return enderecoDao.carregarNumeroEnderecoRGI(endereco);
	}

	@Transactional
	public void solicitaAcatamento2ViaCorreio(Acatamento2ViaTO acatamentoTemp) throws Exception {
		contaCsiDAO.acata2Via(acatamentoTemp);
	}


	@Transactional
	public double pesquisaTarifaSegundaViaConta(DadosImoveisTO dadosImoveisTO) {
		
		int codMunicipioc3 = municipioDao.pesquisaCodMunicipio(dadosImoveisTO.getImovel().getCdMunicipio());
		String nrVP = "";
		String nUn = "";
		
		ArrayList<String> retorno = municipioDao.pesquisaNrVpUn(dadosImoveisTO.getImovel().getCodigoSuperIntendencia(), nrVP , nUn);
		if(retorno  != null){
			nrVP = (String) retorno.get(0);
			nUn = (String) retorno.get(1);
		}
		
		if(codMunicipioc3 == 99999){
			/**
			 * 	If nVp <> 1 Then
			 *  nVp = 1
            	nUn = 51
			 */
			if(nrVP.equals("1")){
				nUn = "51";
			}
			
		}else if(codMunicipioc3 == 99998){
			/**
			 *  If nVp <> 3 Then
            	nVp = 3
            	nUn = 2
			 */
			if(nrVP.equals("3")){
				nUn = "2";
			}
		}else if(codMunicipioc3 == 99996){
			/**
			 *  If nVp <> 2 Then
            	nVp = 2
            	nUn = 6
			 */
			if(nrVP.equals("2")){
				nUn = "6";
			}
		}
		
		double valor = municipioDao.pesquisaTarifaSegundaViaConta(nrVP , nUn);
		
		return valor;
	}


	@Transactional(propagation=Propagation.SUPPORTS)
	public Cliente pesquisaHistoricoContas(DadosImoveisTO dadosImoveisTO) throws Exception {
		Cliente cliente = dadosImoveisTO.getCliente();
		Imovel imovel = dadosImoveisTO.getImovel();
		
		contaDAO.carregarDadosCondicaoFaturamento(cliente);

		if(cliente.isRolEspecial()){
			List<String> listaRgis = imovelDAO.findAllRGIs(imovel);
			String rgiMaster = clienteDao.pesquisaRgiMaster(listaRgis);
			cliente.setDsRgiImovelMaster(rgiMaster);
			
			for(String rgi : listaRgis){
				cliente.addImovel(imovelDAO.findByRgi(rgi));
			}
		}else{
			cliente.addImovel(dadosImoveisTO.getImovel());
		}
		
		// pesquisa historico contas
		contaDAO.carregaHistoricoContasTodosImoveis(cliente, dadosImoveisTO);
		
		// carrega dados de endereco
		long start = System.currentTimeMillis();
		for (Imovel imovelTmp : cliente.getImoveis()) {
			Endereco enderecoPesquisa = new Endereco();
			enderecoPesquisa.setImovel(imovel);
			Endereco enderecoCarregado = enderecoDao.findByEndereco(enderecoPesquisa);
			imovelTmp.setEndereco(enderecoCarregado);
		}
		
		// TODO - REGRAS sendo realizadas no DAO no momento
		// verifica se pode exibir
		// carrega demais dados
		
		return cliente;
	}
	
	/**
	 * Gera o código de barra utilizado na tela de pagamento eletrônico.
	 */
	@Transactional
	public void gerarCodigoBarras(Conta conta) {
		conta.setDtEmissao(WrapperUtils.getToday());
		conta = this.contaDAO.carregarDadosBoletoRolComum(conta);
		String[] numeroCodBar = carregarCodigoBarras(conta);
		String codBarComDigitoVerificador = numeroCodBar[1];
		conta.setCodigoBarraDigitosControle(codBarComDigitoVerificador);
	}
	
	public String carregarFDIC(Conta conta){
		
		int sfidc = -1;
		
		String tpConta = conta.getTpConta().trim();
		
		List<Categoria> categoriaList = new ArrayList<Categoria>(); //categoriaDao.carregarCategoria( conta.getImovel() );
		Categoria categoria = new Categoria();
		categoria.setCdCategoriaConsumo("1");
		categoriaList.add(categoria);
		
		int CDTCOBR = 0; // TODO vide uso no asp
		
		if( !tpConta.equals("6") ){ // rol comum
			
			int nCategoria = -1;
			int nCategoriaMista = -1;
			
			for (int i=0; i < categoriaList.size(); i++ ) {
				
				nCategoria = i + 1;
				nCategoriaMista += 1; 
				
			}
			
			if( nCategoriaMista > 1 ) 
				sfidc = 9; // Economia mista
			else {
				switch (nCategoria) {
				 
				case 1:
						sfidc = 1;
/*						if(  ){
							
						}else if(){
							
						}
						break;
*/				case 2:
						sfidc = 3;
						break;
				case 3:
						sfidc = 4;
						break;
				case 4:						
						break;
				default:	
						sfidc = 0; // Outros

				}
			}
			
		}else {
			sfidc = 0; // Rol especial
		}
		
		sfidc = 0;
		return String.valueOf(sfidc);
	}

	private String[] carregarOCR(Conta conta) {
		
		final String primeiraPosicao = "2 ";
		final String tipoEmisao = "2";
		
		String nrConta = conta.getNumeroConta();
		
		String digitoOCR1 = BoletoUtil.getInstance().dacMod11( nrConta );
	
		String digitoOCR2 = BoletoUtil.getInstance().dacMod11( nrConta + digitoOCR1 + tipoEmisao + conta.getValorContaValueType() );
		
		String nrOCR = primeiraPosicao + nrConta + digitoOCR1 + tipoEmisao + conta.getValorContaValueType() + digitoOCR2;
		
		String ocrs[] = { digitoOCR1, digitoOCR2 , nrOCR };
		
		return ocrs;
	}
	
	private String[] carregarIPTE(Conta conta) {
		
		final String codigoServico = "232";
		final String codigoEmpresa = "0097";
		
		String tipoConta = conta.getTpConta().trim();
		String amJulianoRef = conta.getDataReferenciaAnoJuliano();
		String versaoIPTEOCR = "2";
		String vlConta = conta.getNumeroConta();
		String rgi = conta.getImovel().getNumeroRGIBoletoCodBar();
		String sequencia = conta.getNrSequenciaConta2(); // verificar é sequencia2 gustavo/henrique
		
		String ocrs[ ] = carregarOCR(conta); 
		
		String digOCR1 = ocrs[0];
		String digOCR2 = ocrs[1];
		
		final String tipEmissao = "2";
		final String posicaoLivre = "0";
		
		String temp = codigoServico + codigoEmpresa + tipoConta + amJulianoRef + versaoIPTEOCR + rgi + sequencia + digOCR1 + tipEmissao + digOCR2 + posicaoLivre;
		
		String soma1 = temp + vlConta;
		
		// campos 1 a 5
		/*
		String campos1a5 = codigoServico + codigoEmpresa + tipoConta + amJulianoRef + versaoIPTEOCR;
		
		// campos 6 à B
		String campos6aB = rgi + sequencia + digOCR1 + tipEmissao + digOCR2 + posicaoLivre;
		
		// valorconta;
		String soma = campos1a5 + campos6aB + vlConta;
		*/
		
		String digitoIPTE1 = BoletoUtil.getInstance().dacMod11(soma1);
		
		soma1 += digitoIPTE1;
		
		String digitoIPTE2 = BoletoUtil.getInstance().dacMod11(soma1);
		
		String ipteFinal = temp + digitoIPTE1 + digitoIPTE2;
		
		String ipteFrmt = 
			ipteFinal.substring(0, 3) + 
			" " + 
			ipteFinal.substring(3, 7) +
			" " +
			ipteFinal.substring(7, 11) +
			" " +
			ipteFinal.substring(11, 15) +
			" " +
			ipteFinal.substring(15, 19) +
			" " +
			ipteFinal.substring(19, 23) +
			" " +
			ipteFinal.substring(23, 27);
		
		String[] iptes = {ipteFrmt, digitoIPTE1, digitoIPTE2};
		
	 
		return iptes;
	}
	
	public String[] carregarCodigoBarras(Conta conta){
		
		final String identificacao = "826";
		final String codigoEmpresa = "0097";
		final String versaoIPTE = "2";		
		final String tipoEmissao = "2";
		final String sFIDC = "0";
		final String versaoBarra = "2";
				
		

		
		String sTPACORDO = "J"; // verificar de ondem vem sTPACORDO = "J" Or sTPACORDO = "K"

		String rgi = conta.getImovel().getNumeroRGIBoletoCodBar();
		String tpConta = conta.getTpConta().trim();		
		String vlConta =  conta.getValorContaValueType();
		String dtReferenciaJuliano = conta.getDataReferenciaAnoJuliano();
		String nrSequencia2 = conta.getNrSequenciaConta2(); // verificar é sequencia2 gustavo/henrique
		
		String dtaamm = WrapperUtils.parseDate(conta.getDtReferencia(), "yyMM");
		
		
		String ocrs[ ] = carregarOCR(conta); 
		
		String digOCR1 = ocrs[0];
		String digOCR2 = ocrs[1];
		String codigoOCR = ocrs[2];
		
		conta.setCodigoOCR(codigoOCR);

		
		String codigoBarra = null;
		
		codigoBarra = vlConta + codigoEmpresa + tpConta + dtReferenciaJuliano + versaoIPTE + rgi + nrSequencia2 + digOCR1 +
		tipoEmissao + digOCR2 + sFIDC + "00" + dtaamm + versaoBarra; 
		
/*		
		if( conta.getIspec().trim().equalsIgnoreCase("CFRAB") && ( sTPACORDO.equalsIgnoreCase("J") || sTPACORDO.equalsIgnoreCase("K") )  ) {
			tipoEmissao = "4";
			
			codigoBarra = vlConta + codigoEmpresa + tpConta + dtReferenciaJuliano + versaoIPTE + rgi + nrSequencia2 +
				digOCR1 + tipoEmissao + digOCR2 + sFIDC + dtaamm + versaoBarra;			
			
		}else if( tpConta.equalsIgnoreCase("6") ) {
			
			codigoBarra = vlConta + codigoEmpresa + tpConta + dtReferenciaJuliano + versaoIPTE + rgi + nrSequencia2 + digOCR1 +
				tipoEmissao + digOCR2 + sFIDC + "00" + dtaamm + versaoBarra; 
			
		}else {

			codigoBarra = vlConta + codigoEmpresa + tpConta + dtReferenciaJuliano + versaoIPTE + nrSequencia2 + digOCR1 +
				tipoEmissao + digOCR2 + sFIDC + digIPTE1 + digIPTE2 + dtaamm + versaoBarra;
			
		}
*/		
		
		String digitoVerificador = BoletoUtil.getInstance().dacMod10(identificacao + codigoBarra);
		
		String barraSemDigito = identificacao + digitoVerificador + codigoBarra;
		
		String[] partes = { 
				barraSemDigito.substring(0, 11),
				barraSemDigito.substring(11, 22),
				barraSemDigito.substring(22, 33),
				barraSemDigito.substring(33, 44)
		};
		
		int sizePartes = ( barraSemDigito.length() - 4 );
		
		String novaCodBarComDigitoIntermediarios = "";
		
		for(int i=0; i <4; i++ ){
			
			
			String digitoControleIntermediario = BoletoUtil.getInstance().dacMod10( partes[i] );
			
			if( novaCodBarComDigitoIntermediarios.equals("") ) {
				novaCodBarComDigitoIntermediarios += partes[i] + " " + digitoControleIntermediario;
			}else{
				novaCodBarComDigitoIntermediarios += " " + partes[i] + " " + digitoControleIntermediario;
			}
			
		}
		
		String[] codigos = {barraSemDigito, novaCodBarComDigitoIntermediarios};
		
		return codigos;		
		
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public Conta complementarDadosBoleto(Conta contaPesquisa , DadosImoveisTO dadosImoveisTo) throws Exception{
		
		/*	
		 * se rol comum então montar a discriminação do boleto como :
		 * conta normal ou reformada			
		*/
			contaPesquisa.setDtEmissao(WrapperUtils.getToday());
				
			contaPesquisa = this.contaDAO.carregarDadosBoletoRolComum(contaPesquisa);
			Endereco enderecoPesquisa = new Endereco();
			enderecoPesquisa.setImovel( contaPesquisa.getImovel() );
			
			Endereco enderecoConta = this.enderecoDao.findByEndereco( enderecoPesquisa );
			
			contaPesquisa.getImovel().setEndereco(enderecoConta);
			
			AgenciaTO agencia = new AgenciaTO();
			contaPesquisa.getImovel().setAgencia(agencia);
			
			agencia.setEndereco( this.enderecoDao.findByEnderecoAgenciaAtendimento(enderecoPesquisa) );
			
			String[] numeroCodBar = carregarCodigoBarras(contaPesquisa);
			
			String codBarSemDigito = numeroCodBar[0];
			String codBarComDigitoVerificador = numeroCodBar[1];
			
			String codigoBinario = BoletoUtil.getInstance().imprimeBarra(codBarSemDigito);
			
			contaPesquisa.setCodigoBarBinario(codigoBinario);
			contaPesquisa.setCodigoBarraDigitosControle(codBarComDigitoVerificador);
			
			contaPesquisa.setNumeroIPTEBoleto( carregarIPTE(contaPesquisa)[0] );

			
		
		return contaPesquisa;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public Conta complementarDadosBoletoRolEspecial(Conta contaPesquisa,
			DadosImoveisTO dadosImoveisTO) throws Exception {
		
			/**
			 * Carrega Dados Boleto Rol Especial.
			 */
			contaPesquisa = this.contaDAO.carregarDadosBoletoRolEspecial(contaPesquisa, dadosImoveisTO);
			
			/**
			 * Recupera qual a categoria do imovel
			 */
			try{
			contaPesquisa.setDscategoria(this.tarifaDAO.getCatagoriaByRGI(dadosImoveisTO.getImovel().getDsRgi()));
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			Endereco enderecoPesquisa = new Endereco();
			enderecoPesquisa.setImovel( contaPesquisa.getImovel() );
			
			String[] ocr= carregarOCR(contaPesquisa);
			if(ocr.length > 0){
				//Codigo OCr Completo posicao 2
				contaPesquisa.setCodigoOCR(ocr[2]);
				String[] numeroCodBar = carregarCodigoBarras(contaPesquisa);
				
				String codBarSemDigito = numeroCodBar[0];
				String codBarComDigitoVerificador = numeroCodBar[1];
				
				//Cria o codigo binário
				String codigoBinario = BoletoUtil.getInstance().imprimeBarra(codBarSemDigito);
				contaPesquisa.setCodigoBarBinario(codigoBinario);
				contaPesquisa.setCodigoBarraDigitosControle(codBarComDigitoVerificador);
			
				//Verifica se é economia mista
				validaCategoriaMista(contaPesquisa);
				
				//Soma os Créditos / Débitos / Multas ao total da conta.
				somaValoresAConta(contaPesquisa);
				
				/**
				 * Adiciona o endereço da agencia , cep , telefone
				 */
				AgenciaTO agencia = new AgenciaTO();
				agencia.setEndereco( this.enderecoDao.findByEnderecoAgenciaAtendimento(enderecoPesquisa) );
				contaPesquisa.getImovel().setAgencia(agencia);
				
				//Pesquisa Cpf e Cnpj do cliente
				clienteDao.pesquisaNumeroCpfCnpjCliente(dadosImoveisTO);
				contaPesquisa.setClienteConta(dadosImoveisTO.getCliente());
				
				//Pesquisa dados dos rgis filhos
				ArrayList<String> listaRgis = (ArrayList) dadosImoveisTO.getCliente().getListaRgis();
				if(listaRgis != null){
				ArrayList listaRgisTela = new ArrayList<String>();
				
				/**
				 * Carrega dados dos Rgis filhos , Hidro Composto , Consumo , Endereço
				 */
				carregaCadastroLigacoesRgisFilhosConta2Via(contaPesquisa,dadosImoveisTO, enderecoPesquisa, listaRgis,listaRgisTela);
			
			}else{
				throw new BusinessException("");
			}
		}
			
			/**
			 * Caso seja conta parcelada , limpa os dados que não devem ser mostrados
			 */
			if(contaPesquisa.getIspec().equals("CFRAB")){
				limpasDadosContaParcelada(contaPesquisa);
			}
			
		return contaPesquisa;
	}


	private void limpasDadosContaParcelada(Conta contaPesquisa) {
		contaPesquisa.setVlAgua(null);
		contaPesquisa.setVlEsgoto(null);
		contaPesquisa.setVlEsgotoNaoDom(null);
		contaPesquisa.setVlServicos(null);
		contaPesquisa.setVlDebitos(null);
		contaPesquisa.setVlCreditos(null);
		contaPesquisa.setVlRetencao(null);
		contaPesquisa.setVlMulta(null);
	}


	/**
	 * @param contaPesquisa
	 * @param dadosImoveisTO
	 * @param enderecoPesquisa
	 * @param listaRgis
	 * @param listaRgisTela
	 * @throws Exception
	 */
	private void carregaCadastroLigacoesRgisFilhosConta2Via( Conta contaPesquisa, DadosImoveisTO dadosImoveisTO, 
			Endereco enderecoPesquisa, ArrayList<String> listaRgis,
			ArrayList listaRgisTela) throws Exception {
		
		/**
		 * Itera os flhos
		 */
		for (String rgi : listaRgis) {
			double valorHidroComposto = 0d;
			
			Imovel imovelCarregado = imovelDAO.findByImovel(new Imovel(rgi));
			Cliente clientePesquisa = new Cliente();
			clientePesquisa.setCdMunicipio(imovelCarregado.getCdMunicipio());
			clientePesquisa.setCdCliente(imovelCarregado.getCdCliente());
			Endereco enderecoCarregado = enderecoDao.findByEndereco(enderecoPesquisa);
			ArrayList<String> filhoComposto = null;
			/**
			 * Verifica se é RGI master e se for se tem a Flag TpHidro == T (Se sim é hidro composto)
			 */
			if(rgi.equals(dadosImoveisTO.getCliente().getDsRgiImovelMaster())){ 
				if(contaPesquisa.getTipoHidro() != null && contaPesquisa.getTipoHidro().equals("T") ){
					valorHidroComposto = this.contaDAO.pesquisaConsumoHidroComposto(contaPesquisa,dadosImoveisTO);
					String cdHidrometroComposto = contaDAO.pesquisaCdHidroComposto(rgi,contaPesquisa.getDataReferenciaAnoJuliano());
				
					filhoComposto = new ArrayList<String>();
					filhoComposto.add(rgi); //0
					filhoComposto.add(enderecoCarregado.getEnderecoCompleto()); //1
					filhoComposto.add(cdHidrometroComposto); //2
					filhoComposto.add(contaPesquisa.getVolumeAguaHidroComposto().toString()); //3 leituraAtual 
					filhoComposto.add(Double.valueOf(valorHidroComposto).toString()); //4 consumo
					filhoComposto.add(contaPesquisa.getSituacaoConsumoHidroComposto()); //5 codConsumo 
				}
			}
			
			ArrayList filho = new ArrayList<String>();
			String cdHidrometro = contaDAO.pesquisaDadosRgiFilhosConta(rgi,contaPesquisa.getDataReferenciaAnoJuliano());
			
			filho.add(rgi); //0
			filho.add(enderecoCarregado.getEnderecoCompleto()); //1
			filho.add(cdHidrometro); //2
			//Pesquisa os valores de leitura e consumo para o rgi filho
			// Adiciona os resultados na sequencia no arraylist
			this.contaDAO.carregaDadosConsumoRgisFilhoContaRolEspecial(filho,rgi,contaPesquisa.getDataReferenciaAnoJuliano());
			listaRgisTela.add(filho);
			if(filhoComposto != null){
				listaRgisTela.add(filhoComposto);
			}
		}
			contaPesquisa.setListaRgisFilhosContaRolEspecial(listaRgisTela);
	}



	/**
	 * Soma todos valores de debito , credito , serviços
	 * @param contaPesquisa
	 */
	private void somaValoresAConta(Conta contaPesquisa) {
		double vlTotal = contaPesquisa.getVlTotal(); 
		double vlDebitos = ( contaPesquisa.getVlDebitos() != null ? contaPesquisa.getVlDebitos() : 0d); //(-)  
		double vlCreditos = ( contaPesquisa.getVlCreditos() != null ? contaPesquisa.getVlCreditos() : 0d); //(+)
		double vlServicos =  ( contaPesquisa.getVlServicos() != null ? contaPesquisa.getVlServicos() : 0d); //(-)
		
		/**
		 * conta feita no DAO
		 * contaPesquisa.setVlTotal(vlTotal + contaPesquisa.getVlConsumoMonetario()+ contaPesquisa.getVlJuros() +  contaPesquisa.getVlMulta());
		 */
		
		vlTotal = vlTotal - (vlDebitos) - vlServicos + vlCreditos;
		contaPesquisa.setVlTotal(vlTotal);
	}


	/**
	 * Se mais que uma economia for 1 então a categoria é Mista
	 * @param contaPesquisa
	 */
	private void validaCategoriaMista(Conta contaPesquisa) {
		int cont = 0;
		if(!WrapperUtils.isNull(contaPesquisa.getEconomiaResidencial())){
			if(	contaPesquisa.getEconomiaResidencial() == 1){
				cont++;
			}
			if(	contaPesquisa.getEconomiaComercial() == 1){
				cont++;
			}
			if(	contaPesquisa.getEconomiaIndustrial() == 1){
				cont++;
			}
			if(	contaPesquisa.getEconomiaPublica() == 1){
				cont++;
			}
			if(cont > 1){
				contaPesquisa.setDscategoria("Mista");
			}
		}
	}
	
}
