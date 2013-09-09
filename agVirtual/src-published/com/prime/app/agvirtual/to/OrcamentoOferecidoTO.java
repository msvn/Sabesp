package com.prime.app.agvirtual.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * 
 * @author gsilva
 *
 */
public class OrcamentoOferecidoTO implements Serializable {

	private static final long serialVersionUID = -6324745918196013636L;
	
	private Integer idOrcamento;
	
	private Long idAutoAtendimentoAcessado;
	
	/**
	 * Quantidade de parcelas escolhidas pelo usuario
	 * Passado como parametro para acatamento no CSI
	 */
	private Integer qtParcela;
	
	/**
	 * Quantidade maxima permitida de parcelas
	 */
	private Integer maxParcelas;
	
	private Date dtOrcamento;
	
	private boolean pago;
	
	private boolean pagamentoAvista;
	
	private boolean precoFixo;
	
	private boolean pagamentoParcelado;
	
	private String prazoAtendimento;
	
	private String dsOrcamento;
	
	private String msgCobranca;
	
	
	private List<SelectItem> listaParcelas =  null;
	
	public List<SelectItem> getListaParcelas() {
		listaParcelas = new ArrayList<SelectItem>();
		if(maxParcelas != null){
			for (int i = 1; i <= maxParcelas; i++) {
				if(i == 1){
					listaParcelas.add(new SelectItem(String.valueOf(i),"À Vista"));
				}else{
					listaParcelas.add(new SelectItem(String.valueOf(i),String.valueOf(i)));	
				}
			}
		}
		return listaParcelas;
	}

	public void setListaParcelas(List<SelectItem> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}

	private BigDecimal valorTotal;

	public Integer getIdOrcamento() {
		return idOrcamento;
	}

	public void setIdOrcamento(Integer idOrcamento) {
		this.idOrcamento = idOrcamento;
	}

	public Long getIdAutoAtendimentoAcessado() {
		return idAutoAtendimentoAcessado;
	}

	public void setIdAutoAtendimentoAcessado(Long idAutoAtendimentoAcessado) {
		this.idAutoAtendimentoAcessado = idAutoAtendimentoAcessado;
	}

	public Integer getQtParcela() {
		return qtParcela;
	}

	public void setQtParcela(Integer qtParcela) {
		this.qtParcela = qtParcela;
	}

	public Date getDtOrcamento() {
		return dtOrcamento;
	}

	public void setDtOrcamento(Date dtOrcamento) {
		this.dtOrcamento = dtOrcamento;
	}

	public boolean getPago() {
		return pago;
	}

	public void setPago(boolean isPago) {
		this.pago = isPago;
	}

	public boolean getPagamentoAvista() {
		return pagamentoAvista;
	}


	public boolean getPrecoFixo() {
		return precoFixo;
	}

	public void setPrecoFixo(boolean isPrecoFixo) {
		this.precoFixo = isPrecoFixo;
	}

	public Integer getMaxParcelas() {
		return maxParcelas;
	}

	public void setMaxParcelas(Integer maxParcelas) {
		this.maxParcelas = maxParcelas;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public boolean getPagamentoParcelado() {
		return pagamentoParcelado;
	}

	public void setPagamentoAvista(boolean pagamentoAvista) {
		this.pagamentoAvista = pagamentoAvista;
	}


	public void setPagamentoParcelado(boolean pagamentoParcelado) {
		this.pagamentoParcelado = pagamentoParcelado;
	}

	public String getPrazoAtendimento() {
		return prazoAtendimento;
	}

	public void setPrazoAtendimento(String prazoAtendimento) {
		this.prazoAtendimento = prazoAtendimento;
	}

	public String getDsOrcamento() {
		return dsOrcamento;
	}

	public void setDsOrcamento(String dsOrcamento) {
		this.dsOrcamento = dsOrcamento;
	}

	public String getMsgCobranca() {
		return msgCobranca;
	}

	public void setMsgCobranca(String msgCobranca) {
		this.msgCobranca = msgCobranca;
	}
	
}
