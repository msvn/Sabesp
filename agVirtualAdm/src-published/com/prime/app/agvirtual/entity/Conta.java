package com.prime.app.agvirtual.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.prime.app.agvirtual.enums.AgvStatusPagamento;
import com.prime.infra.util.WrapperUtils;

/**
 * 
 * @author gustavons
 *
 */
public class Conta implements Serializable {
	
	SimpleDateFormat dateFormat =  new SimpleDateFormat("MMyyyy");
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -679585384022182971L;
	
	public Conta(){}
	
	private Imovel imovel;
	
	private String cdBarras;
	private String cdConta;
	private String cdSabesp;
	private String dscategoria;
	private String dshidrometro;	
	private Integer dsperiodoMedicao;
	
	private Date dtEmissao;
	private String dataEmissaoJuliano;
	private Date dtLeitura;
	private String dataLeituraAnoJuliano;
	private Date dtReferencia;
	private Integer dtReferenciaMMAAAA;
	private String dataReferenciaAnoJuliano;
	private Date dtVencimento;
	private String dataVencimentoAnoJuliano;
	
	private Integer economiaComercial;
	private Integer economiaIndustrial;
	private Integer economiaPublica;
	private Integer economiaResidencial;
	private Integer economiaValvula;
	private Integer idConsumoAgua;
	private Integer idConsumoEsgoto;
	private Integer idConta;
	private Integer idMedia;
	private Double vlAgua;
	private Double vlAguaHidroComposto; //Somente Rol especial;
	private Double vlAtMonetaria;
	private Double vlCreditos;
	private Double vlDebitos;
	private Double vlEsgoto;
	private Double vlEsgotoNaoDom;
	private Double vlJuros;
	private Double vlMulta;
	private Double vlParcelamento;
	private Double vlRetencao;
	private Double vlTotal;
	private Double vlConsumo;
	private Double volumeAgua;
	private Double volumeAguaHidroComposto;
	private Double volumeEsgoto;
	private Double volumeEsgotoNaoDomes;
	private boolean change;
    /*
     * TODO:
     * No futuro será definido onde logar este código
     * */
    private String codigoTransacao;
	
	/**
	 * NRSEQCTA2
	 */
	private String nrSequenciaConta2;
	
	/**
	 * NRSEQCTA1
	 */
	private String nrSequenciaConta1;
	private String nrCiclo;
	private String cdunidcom; //Unidade Comercial
	private String cdunidco2; //SuperIntendêcia
	private String ispec;
	private String codHistorico;
	/**
	 * Atributos de controle - FrontEnd
	 */
	private boolean bloqueioImprimir;
	private boolean bloqueioPagEletronico;
	private boolean bloqueioCorreio;
	private Boolean debitoAutomatico = false;
	
	private Boolean imprimirSegundaVia;
	private Boolean receberPeloCorreio;
	
	// atributo que indica se conta pertence a algum extrato
	private Boolean extrato =  false;
	
	private String situacaoConta; //StConta
	private String situacaoConsumo; //stconsumo
	
	private String situacaoConsumoHidroComposto; //stconsumo
	
	private String quantidadeConsumo; //qtconsumo
	private String vlleitura; //vlleitura
	
	private String quantidadeConsumoFaturada; //qtconsfat
	
	private String codigoBarBinario;
	
	private String codigoBarraDigitosControle;
	
	private String numeroIPTEBoleto;
	
	private AgvStatusPagamento situacaoPagamento;
	// atributo que idenfica se a conta foi emitida por um TACE
	private Boolean emitidoTace;
	/**
	 * Se TPCONTA  =  6 é do Faturamento Especial, se diferente de 6 é do Rol Comum.
	 */
	private String tpConta; //rolComum ou rolEspecial
	
	//Se CCG03.CDCOBR=9 è ENTIDADE PUBLICA 
	private String codCobrancaCadastro; //CCG03; cod cobrança existente no cadastro.
	private String codCobrancaDebito; //Event cod cobrança gerado no débito
	private String tipoRetencao; //TPRETENC
	private String codigoOrgaoPublico; //CDORGPUB
	private String situacaoLigacao; //StLigacao
	
	private String observacaoConta;
	private String statusReclamacao; //streclam
	
	//atributo que indica se a conta deve ser retirada para exibição
	private boolean removerListaTela = false;
	
	//STCORTSUP
	private String situacaoCorSup;
	
	private String tipoHidro; //tphidro
	
	private String tipoContrato; //TPCONTRATO
	
	private int quantidadeParcelas;
	
	private String numeroConta;
	
	private String qtMedia;
	
	private String codigoHidrometro;
	
	private Double vlServicos;
	
	private Double vlDesconto;
	
	private Double vlConsumoMonetario;
	
	// atributos usados para o boleto
	
	private ArrayList<String> intrucoesBoleto;
	
	//Atributo utilizado no rol especial onde é setado o cliente atrelado a conta
	private Cliente clienteConta;
	
	private Date novaData;
	
	//Codigo carregado na geração do boleto
	private String codigoOCR;
	
	private List listaRgisFilhosContaRolEspecial;
	
	public List getListaRgisFilhosContaRolEspecial() {
		return listaRgisFilhosContaRolEspecial;
	}

	public void setListaRgisFilhosContaRolEspecial(
			List listaRgisFilhosContaRolEspecial) {
		this.listaRgisFilhosContaRolEspecial = listaRgisFilhosContaRolEspecial;
	}

	private ContaParcelaValueType contaParcelada =  new ContaParcelaValueType();
	private ContaNormalValueType contaNormal = new ContaNormalValueType();
	private ContaReformadaValueType contaReformada =  new ContaReformadaValueType();
	private ContaParcelaUnicaValueType contaParcelaUnica =  new ContaParcelaUnicaValueType();
	private ContaRompidoValueType contaRompido = new ContaRompidoValueType();
	
	public Double getVlAguaHidroComposto() {
		return vlAguaHidroComposto;
	}

	public void setVlAguaHidroComposto(Double vlAguaHidroComposto) {
		this.vlAguaHidroComposto = vlAguaHidroComposto;
	}
	
	public String getSituacaoConsumoHidroComposto() {
		return situacaoConsumoHidroComposto;
	}

	public void setSituacaoConsumoHidroComposto(String situacaoConsumoHidroComposto) {
		this.situacaoConsumoHidroComposto = situacaoConsumoHidroComposto;
	}
	
	public Double getVolumeAguaHidroComposto() {
		return volumeAguaHidroComposto;
	}

	public void setVolumeAguaHidroComposto(Double volumeAguaHidroComposto) {
		this.volumeAguaHidroComposto = volumeAguaHidroComposto;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public Double getVlConsumoMonetario() {
		return vlConsumoMonetario;
	}

	public void setVlConsumoMonetario(Double vlConsumoMonetario) {
		this.vlConsumoMonetario = vlConsumoMonetario;
	}	
	
	
	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public String getDataEmissaoJuliano() {
		return dataEmissaoJuliano;
	}

	public void setDataEmissaoJuliano(String dataEmissaoJuliano) {
		this.dataEmissaoJuliano = dataEmissaoJuliano;
	}

	public Double getVolumeEsgotoNaoDomes() {
		return volumeEsgotoNaoDomes;
	}

	public void setVolumeEsgotoNaoDomes(Double volumeEsgotoNaoDomes) {
		this.volumeEsgotoNaoDomes = volumeEsgotoNaoDomes;
	}

	public Double getVlEsgotoNaoDom() {
		return vlEsgotoNaoDom;
	}

	public void setVlEsgotoNaoDom(Double vlEsgotoNaoDom) {
		this.vlEsgotoNaoDom = vlEsgotoNaoDom;
	}

	public String getCodigoOCR() {
		return codigoOCR;
	}

	public void setCodigoOCR(String codigoOCR) {
		this.codigoOCR = codigoOCR;
	}

	public String getTipoHidro() {
		return tipoHidro;
	}

	public void setTipoHidro(String tipoHidro) {
		this.tipoHidro = tipoHidro;
	}

	public Date getNovaData() {
		return new Date();
	}

	public void setNovaData(Date novaData) {
		this.novaData = novaData;
	}

	public Cliente getClienteConta() {
		return clienteConta;
	}

	public void setClienteConta(Cliente clienteConta) {
		this.clienteConta = clienteConta;
	}

	public ArrayList<String> getIntrucoesBoleto() {
		return intrucoesBoleto;
	}

	public void setIntrucoesBoleto(ArrayList<String> intrucoesBoleto) {
		this.intrucoesBoleto = intrucoesBoleto;
	}

	public void bloquear(boolean bloqueioImprimir , boolean bloqueioCorreio , boolean bloqueioPagEletronico){
		this.bloqueioImprimir = bloqueioImprimir;
		this.bloqueioPagEletronico = bloqueioPagEletronico ;
		this.bloqueioCorreio = bloqueioCorreio;
	}
	
	public void bloquear(boolean bloqueioImprimir , boolean bloqueioCorreio , boolean bloqueioPagEletronico , boolean removerListaTela){
		this.bloqueioImprimir = bloqueioImprimir;
		this.bloqueioPagEletronico = bloqueioPagEletronico ;
		this.bloqueioCorreio = bloqueioCorreio;
		this.removerListaTela = removerListaTela;
	}
	
	
	
	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public boolean isRemoverListaTela() {
		return removerListaTela;
	}

	public void setRemoverListaTela(boolean removerListaTela) {
		this.removerListaTela = removerListaTela;
	}

	public String getSituacaoLigacao() {
		return situacaoLigacao;
	}

	public void setSituacaoLigacao(String situacaoLigacao) {
		this.situacaoLigacao = situacaoLigacao;
	}

	public String getCodigoOrgaoPublico() {
		return codigoOrgaoPublico;
	}
	
	public void setCodigoOrgaoPublico(String codigoOrgaoPublico) {
		this.codigoOrgaoPublico = codigoOrgaoPublico;
	}
	
	public String getCodCobrancaCadastro() {
		return codCobrancaCadastro;
	}

	public void setCodCobrancaCadastro(String codCobrancaCadastro) {
		this.codCobrancaCadastro = codCobrancaCadastro;
	}

	public String getCodCobrancaDebito() {
		return codCobrancaDebito;
	}

	public void setCodCobrancaDebito(String codCobrancaDebito) {
		this.codCobrancaDebito = codCobrancaDebito;
	}

	public String getTipoRetencao() {
		return tipoRetencao;
	}

	public void setTipoRetencao(String tipoRetencao) {
		this.tipoRetencao = tipoRetencao;
	}

	public String getSituacaoCorSup() {
		return situacaoCorSup;
	}
	public void setSituacaoCorSup(String situacaoCorSup) {
		this.situacaoCorSup = situacaoCorSup;
	}
	public boolean isBloqueioImprimir() {
		return bloqueioImprimir;
	}
	public void setBloqueioImprimir(boolean bloqueioImprimir) {
		this.bloqueioImprimir = bloqueioImprimir;
	}
	public boolean isBloqueioPagEletronico() {
		return bloqueioPagEletronico;
	}
	public void setBloqueioPagEletronico(boolean bloqueioPagEletronico) {
		this.bloqueioPagEletronico = bloqueioPagEletronico;
	}
	public boolean isBloqueioCorreio() {
		return bloqueioCorreio;
	}
	public void setBloqueioCorreio(boolean bloqueioCorreio) {
		this.bloqueioCorreio = bloqueioCorreio;
	}
	public String getCodHistorico() {
		return codHistorico;
	}
	public void setCodHistorico(String codHistorico) {
		this.codHistorico = codHistorico;
	}
	public String getIspec() {
		return ispec;
	}
	public void setIspec(String ispec) {
		this.ispec = ispec;
	}
	public Boolean getExtrato() {
		return extrato;
	}
	public void setExtrato(Boolean extrato) {
		this.extrato = extrato;
	}
	public String getNrCiclo() {
		return nrCiclo;
	}
	public void setNrCiclo(String nrCiclo) {
		this.nrCiclo = nrCiclo;
	}
	public String getCdunidcom() {
		return cdunidcom;
	}
	public void setCdunidcom(String cdunidcom) {
		this.cdunidcom = cdunidcom;
	}
	public String getNrSequenciaConta2() {
		return nrSequenciaConta2;
	}
	public void setNrSequenciaConta2(String nrSequenciaConta) {
		this.nrSequenciaConta2 = nrSequenciaConta;
	}
	public Boolean getEmitidoTace() {
		return emitidoTace;
	}
	public void setEmitidoTace(Boolean emitidoTace) {
		this.emitidoTace = emitidoTace;
	}
	public Boolean getDebitoAutomatico() {
		return debitoAutomatico;
	}
	public void setDebitoAutomatico(Boolean debitoAutomatico) {
		this.debitoAutomatico = debitoAutomatico;
	}
	public String getCdBarras() {
		return cdBarras;
	}
	public void setCdBarras(String cdBarras) {
		this.cdBarras = cdBarras;
	}
	public String getCdConta() {
		return cdConta;
	}
	public void setCdConta(String cdConta) {
		this.cdConta = cdConta;
	}
	public String getCdSabesp() {
		return cdSabesp;
	}
	public void setCdSabesp(String cdSabesp) {
		this.cdSabesp = cdSabesp;
	}
	public String getDscategoria() {
		return dscategoria;
	}
	public void setDscategoria(String dscategoria) {
		this.dscategoria = dscategoria;
	}
	public String getDshidrometro() {
		return dshidrometro;
	}
	public void setDshidrometro(String dshidrometro) {
		this.dshidrometro = dshidrometro;
	}
	public Integer getDsperiodoMedicao() {
		return dsperiodoMedicao;
	}
	public void setDsperiodoMedicao(Integer dsperiodoMedicao) {
		this.dsperiodoMedicao = dsperiodoMedicao;
	}
	public Date getDtLeitura() {
		return dtLeitura;
	}
	public void setDtLeitura(Date dtLeitura) {
		this.dtLeitura = dtLeitura;
	}
	public Date getDtReferencia() {
		return dtReferencia;
	}
	public void setDtReferencia(Date dtReferencia) {
		this.dtReferencia = dtReferencia;
		setDtReferenciaMMAAAA(dtReferencia);
	}
	public Date getDtVencimento() {
		return dtVencimento;
	}
	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	public Integer getEconomiaComercial() {
		return economiaComercial;
	}
	public void setEconomiaComercial(Integer economiaComercial) {
		this.economiaComercial = economiaComercial;
	}
	public Integer getEconomiaIndustrial() {
		return economiaIndustrial;
	}
	public void setEconomiaIndustrial(Integer economiaIndustrial) {
		this.economiaIndustrial = economiaIndustrial;
	}
	public Integer getEconomiaPublica() {
		return economiaPublica;
	}
	public void setEconomiaPublica(Integer economiaPublica) {
		this.economiaPublica = economiaPublica;
	}
	public Integer getEconomiaResidencial() {
		return economiaResidencial;
	}
	public void setEconomiaResidencial(Integer economiaResidencial) {
		this.economiaResidencial = economiaResidencial;
	}
	public Integer getIdConsumoAgua() {
		return idConsumoAgua;
	}
	public void setIdConsumoAgua(Integer idConsumoAgua) {
		this.idConsumoAgua = idConsumoAgua;
	}
	public Integer getIdConsumoEsgoto() {
		return idConsumoEsgoto;
	}
	public void setIdConsumoEsgoto(Integer idConsumoEsgoto) {
		this.idConsumoEsgoto = idConsumoEsgoto;
	}
	public Integer getIdConta() {
		return idConta;
	}
	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}
	public Integer getIdMedia() {
		return idMedia;
	}
	public void setIdMedia(Integer idMedia) {
		this.idMedia = idMedia;
	}
	public Double getVlAgua() {
		return vlAgua;
	}
	public void setVlAgua(Double vlAgua) {
		this.vlAgua = vlAgua;
	}
	public Double getVlAtMonetaria() {
		return vlAtMonetaria;
	}
	public void setVlAtMonetaria(Double vlAtMonetaria) {
		this.vlAtMonetaria = vlAtMonetaria;
	}
	public Double getVlCreditos() {
		return vlCreditos;
	}
	public void setVlCreditos(Double vlCreditos) {
		this.vlCreditos = vlCreditos;
	}
	public Double getVlDebitos() {
		return vlDebitos;
	}
	public void setVlDebitos(Double vlDebitos) {
		this.vlDebitos = vlDebitos;
	}
	public Double getVlEsgoto() {
		return vlEsgoto;
	}
	public void setVlEsgoto(Double vlEsgoto) {
		this.vlEsgoto = vlEsgoto;
	}
	public Double getVlJuros() {
		return vlJuros;
	}
	public void setVlJuros(Double vlJuros) {
		this.vlJuros = vlJuros;
	}
	public Double getVlMulta() {
		return vlMulta;
	}
	public void setVlMulta(Double vlMulta) {
		this.vlMulta = vlMulta;
	}
	public Double getVlParcelamento() {
		return vlParcelamento;
	}
	public void setVlParcelamento(Double vlParcelamento) {
		this.vlParcelamento = vlParcelamento;
	}
	public Double getVlRetencao() {
		return vlRetencao;
	}
	public void setVlRetencao(Double vlRetencao) {
		this.vlRetencao = vlRetencao;
	}
	public Double getVlTotal() {
		return vlTotal;
	}
	public void setVlTotal(Double vlTotal) {
		this.vlTotal = vlTotal;
	}
	public Double getVolumeAgua() {
		return volumeAgua;
	}
	public void setVolumeAgua(Double volumeAgua) {
		this.volumeAgua = volumeAgua;
	}
	public Double getVolumeEsgoto() {
		return volumeEsgoto;
	}
	public void setVolumeEsgoto(Double volumeEsgoto) {
		this.volumeEsgoto = volumeEsgoto;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Boolean getImprimirSegundaVia() {
		return imprimirSegundaVia;
	}
	public void setImprimirSegundaVia(Boolean imprimirSegundaVia) {
		this.imprimirSegundaVia = imprimirSegundaVia;
	}
	public Boolean getReceberPeloCorreio() {
		return receberPeloCorreio;
	}
	public void setReceberPeloCorreio(Boolean receberPeloCorreio) {
		this.receberPeloCorreio = receberPeloCorreio;
	}
	
	public String getTpConta() {
		return tpConta;
	}
	public void setTpConta(String tpConta) {
		this.tpConta = tpConta;
	}
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this);
	}
	public Imovel getImovel() {
		return imovel;
	}
	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}
	public String getDataLeituraAnoJuliano() {
		return dataLeituraAnoJuliano;
	}
	public void setDataLeituraAnoJuliano(String dataLeituraAnoJuliano) {
		this.dataLeituraAnoJuliano = dataLeituraAnoJuliano;
	}
	public String getDataReferenciaAnoJuliano() {
		return dataReferenciaAnoJuliano;
	}
	public void setDataReferenciaAnoJuliano(String dataReferenciaAnoJuliano) {
		this.dataReferenciaAnoJuliano = dataReferenciaAnoJuliano;
	}
	public String getDataVencimentoAnoJuliano() {
		return dataVencimentoAnoJuliano;
	}
	public void setDataVencimentoAnoJuliano(String dataVencimentoAnoJuliano) {
		this.dataVencimentoAnoJuliano = dataVencimentoAnoJuliano;
	}
	public String getSituacaoConta() {
		return situacaoConta;
	}
	public void setSituacaoConta(String situacaoConta) {
		this.situacaoConta = situacaoConta;
	}
	public AgvStatusPagamento getSituacaoPagamento() {
		return situacaoPagamento;
	}
	public void setSituacaoPagamento(AgvStatusPagamento situacaoPagamento) {
		this.situacaoPagamento = situacaoPagamento;
	}

	public Integer getDtReferenciaMMAAAA() {
		return dtReferenciaMMAAAA;
	}

	public void setDtReferenciaMMAAAA(Date dtReferenciaMMAAAA) {
		this.dtReferenciaMMAAAA = Integer.valueOf(dateFormat.format(dtReferenciaMMAAAA));
	}

	public Double getVlConsumo() {
		return vlConsumo;
	}

	public void setVlConsumo(Double vlConsumo) {
		this.vlConsumo = vlConsumo;
	}

	public String getNrSequenciaConta1() {
		return nrSequenciaConta1;
	}

	public void setNrSequenciaConta1(String nrSequenciaConta1) {
		this.nrSequenciaConta1 = nrSequenciaConta1;
	}

	public void setDtReferenciaMMAAAA(Integer dtReferenciaMMAAAA) {
		this.dtReferenciaMMAAAA = dtReferenciaMMAAAA;
	}
	
	public String getNumeroConta(){
		
		String tpConta = this.tpConta.trim();
		String mesRef = this.dataReferenciaAnoJuliano;//conta.getDataReferenciaAnoJuliano();
		String nrRGI = this.imovel.getDsRgi().trim();
		String sqContaMes = this.nrSequenciaConta2.trim(); // getNrSequenciaConta() seguencia de Conta no mês ?
		
		String nrConta = tpConta + mesRef + nrRGI + sqContaMes;
		
		return nrConta;
	}
	
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getValorContaValueType(){
		String vlType =  WrapperUtils.parseDouble(this.vlTotal);
		
		int tamanhoTotal = 11;
		String zeros = "";
		
		int zerosCompletar = (tamanhoTotal - vlType.length());
		
		for(int i=0; i < zerosCompletar; i++){
			zeros += "0";
		}
		
		String valueTOreturn = zeros + vlType;
		
		return valueTOreturn;
	}

	public String getQuantidadeConsumoFaturada() {
		return quantidadeConsumoFaturada;
	}

	public void setQuantidadeConsumoFaturada(String quantidadeConsumoFaturada) {
		this.quantidadeConsumoFaturada = quantidadeConsumoFaturada;
	}

	public String getSituacaoConsumo() {
		return situacaoConsumo;
	}

	public void setSituacaoConsumo(String situacaoConsumo) {
		this.situacaoConsumo = situacaoConsumo;
	}

	public String getQuantidadeConsumo() {
		return quantidadeConsumo;
	}

	public void setQuantidadeConsumo(String quantidadeConsumo) {
		this.quantidadeConsumo = quantidadeConsumo;
	}

	public String getVlleitura() {
		return vlleitura;
	}

	public void setVlleitura(String vlleitura) {
		this.vlleitura = vlleitura;
	}

	public String getObservacaoConta() {
		return observacaoConta;
	}

	public void setObservacaoConta(String observacaoConta) {
		this.observacaoConta = observacaoConta;
	}

	public String getStatusReclamacao() {
		return statusReclamacao;
	}

	public void setStatusReclamacao(String statusReclamacao) {
		this.statusReclamacao = statusReclamacao;
	}

	public String getQtMedia() {
		return qtMedia;
	}

	public void setQtMedia(String qtMedia) {
		this.qtMedia = qtMedia;
	}

	public String getCodigoHidrometro() {
		return codigoHidrometro;
	}

	public void setCodigoHidrometro(String codigoHidrometro) {
		this.codigoHidrometro = codigoHidrometro;
	}

	public Double getVlServicos() {
		return vlServicos;
	}

	public void setVlServicos(Double vlServicos) {
		this.vlServicos = vlServicos;
	}

	public Double getVlDesconto() {
		return vlDesconto;
	}

	public void setVlDesconto(Double vlDesconto) {
		this.vlDesconto = vlDesconto;
	}

	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}


	public String getCodigoBarraDigitosControle() {
		return codigoBarraDigitosControle;
	}

	public void setCodigoBarraDigitosControle(String codigoBarraDigitosControle) {
		this.codigoBarraDigitosControle = codigoBarraDigitosControle;
	}

	public String getCodigoBarBinario() {
		return codigoBarBinario;
	}

	public void setCodigoBarBinario(String codigoBarBinario) {
		this.codigoBarBinario = codigoBarBinario;
	}

	public String getNumeroIPTEBoleto() {
		return numeroIPTEBoleto;
	}

	public void setNumeroIPTEBoleto(String numeroIPTEBoleto) {
		this.numeroIPTEBoleto = numeroIPTEBoleto;
	}

	public String getCodigoTransacao() {
		return codigoTransacao;
	}

	public void setCodigoTransacao(String codigoTransacao) {
		this.codigoTransacao = codigoTransacao;
	}

	public String getCdunidco2() {
		return cdunidco2;
	}

	public void setCdunidco2(String cdunidco2) {
		this.cdunidco2 = cdunidco2;
	}

	public Integer getEconomiaValvula() {
		return economiaValvula;
	}

	public void setEconomiaValvula(Integer economiaValvula) {
		this.economiaValvula = economiaValvula;
	}

	public ContaParcelaValueType getContaParcelada() {
		return contaParcelada;
	}

	public void setContaParcelada(ContaParcelaValueType contaParcelada) {
		this.contaParcelada = contaParcelada;
	}

	public ContaNormalValueType getContaNormal() {
		return contaNormal;
	}

	public void setContaNormal(ContaNormalValueType contaNormal) {
		this.contaNormal = contaNormal;
	}

	public ContaReformadaValueType getContaReformada() {
		return contaReformada;
	}

	public void setContaReformada(ContaReformadaValueType contaReformada) {
		this.contaReformada = contaReformada;
	}

	public ContaParcelaUnicaValueType getContaParcelaUnica() {
		return contaParcelaUnica;
	}

	public void setContaParcelaUnica(ContaParcelaUnicaValueType contaParcelaUnica) {
		this.contaParcelaUnica = contaParcelaUnica;
	}

	public ContaRompidoValueType getContaRompido() {
		return contaRompido;
	}

	public void setContaRompido(ContaRompidoValueType contaRompido) {
		this.contaRompido = contaRompido;
	}
	
	
	
	
}
