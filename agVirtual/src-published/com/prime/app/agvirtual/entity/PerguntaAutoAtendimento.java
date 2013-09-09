package com.prime.app.agvirtual.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@NamedQueries( { @NamedQuery(name = "PerguntaAutoAtendimento.findAll", query = "select o from PerguntaAutoAtendimento o") })
@Table(name = "AGV_TAB_PERG_AUTOATENDIMENTO", schema = Schema.DB_OWNER)
public class PerguntaAutoAtendimento implements BaseEntity {
	@Id
	@Column(name = "CD_PERGUNTA")
	private int cdPergunta;
	@Column(name = "DS_PERGUNTA")
	private String dsPergunta;
	@Column(name = "NR_ORDEM_PERGUNTA")
	private Integer nrOrdemPergunta;
	@Column(name = "DS_DIRETORIA" ,length = 1)
	private String dsDiretoria;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "CD_AUTOATENDIMENTO")
	private AutoAtendimentoPergResp autoAtendimento;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinTable(name = "AGV_TAB_PERG_RESP_AUTOATEND", schema = Schema.DB_OWNER, joinColumns = { @JoinColumn(name = "CD_PERGUNTA") }, inverseJoinColumns = @JoinColumn(name = "CD_RESPOSTA"))
	private List<RespostaAutoAtendimento> listaRespostaAutoAtendimento;
	
	@Transient
	private String dsRespostaSelecionada;
	
	@Transient
	private String codRespostaSelecionado;
	
	@Transient
	private List<SelectItem> listaRespostaAutoAtendimentoSelectItem;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getCodRespostaSelecionado() {
		return codRespostaSelecionado;
	}

	public void setCodRespostaSelecionado(String codRespostaSelecionado) {
		this.codRespostaSelecionado = codRespostaSelecionado;
	}

	public Object parseTO() {
		return null;
	}

	public PerguntaAutoAtendimento() {
	}

	public String getDsPergunta() {
		return dsPergunta;
	}

	public void setDsPergunta(String dsPergunta) {
		this.dsPergunta = dsPergunta;
	}

	public Integer getNrOrdemPergunta() {
		return nrOrdemPergunta;
	}

	public void setNrOrdemPergunta(Integer nrOrdemPergunta) {
		this.nrOrdemPergunta = nrOrdemPergunta;
	}

	public AutoAtendimentoPergResp getAutoAtendimento() {
		return autoAtendimento;
	}

	public void setAutoAtendimento(AutoAtendimentoPergResp autoAtendimento) {
		this.autoAtendimento = autoAtendimento;
	}

	public List<RespostaAutoAtendimento> getListaRespostaAutoAtendimento() {
		return listaRespostaAutoAtendimento;
	}

	public void setListaRespostaAutoAtendimento(
			List<RespostaAutoAtendimento> listaRespostaAutoAtendimento) {
		this.listaRespostaAutoAtendimento = listaRespostaAutoAtendimento;
	}

	public int getCdPergunta() {
		return cdPergunta;
	}

	public void setCdPergunta(int cdPergunta) {
		this.cdPergunta = cdPergunta;
	}
	/**
	 * Transforma a lista de resposta em select item 
	 * @return
	 */
	public List<SelectItem> getListaRespostaAutoAtendimentoSelectItem() {
		ArrayList<SelectItem> retorno = new ArrayList<SelectItem>();
		
		if(listaRespostaAutoAtendimento != null){
			for (RespostaAutoAtendimento resposta : listaRespostaAutoAtendimento) {
				SelectItem item = new SelectItem((resposta.getCdResposta().toString()), resposta.getDsResposta().toString());
				retorno.add(item);
			}
		}
		
		return retorno;
	}


	public void setListaRespostaAutoAtendimentoSelectItem(
			List<SelectItem> listaRespostaAutoAtendimentoSelectItem) {
		this.listaRespostaAutoAtendimentoSelectItem = listaRespostaAutoAtendimentoSelectItem;
	}
	
	/**
	 * Recupera a descrição da resposta
	 * @return
	 */
	public String getDsRespostaSelecionada() {
		if(codRespostaSelecionado!=null){
			List temp = listaRespostaAutoAtendimento;
			if(temp != null){
				for (Iterator iterator = temp.iterator(); iterator.hasNext();) {
					RespostaAutoAtendimento object = (RespostaAutoAtendimento) iterator.next();
					if(object.getCdResposta().toString().equals(codRespostaSelecionado)){
						dsRespostaSelecionada = ( object.getDsResposta() != null ? object.getDsResposta().trim() : "" ); 
						break;
					}
				}
			}
		}
		return dsRespostaSelecionada;
	}

	public void setDsRespostaSelecionada(String dsRespostaSelecionada) {
		this.dsRespostaSelecionada =  dsRespostaSelecionada;
	}

	public String getDsDiretoria() {
		return dsDiretoria;
	}

	public void setDsDiretoria(String dsDiretoria) {
		this.dsDiretoria = dsDiretoria;
	}
	

}
