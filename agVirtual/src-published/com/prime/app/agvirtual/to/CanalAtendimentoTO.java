package com.prime.app.agvirtual.to;

import com.prime.app.agvirtual.entity.AgvTabCanalAtend;
import com.prime.app.agvirtual.enums.Diretoria;

/**
 * 
 * @author gustavons
 *
 */
public class CanalAtendimentoTO implements BasicTO<AgvTabCanalAtend>{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7957747778700774977L;
	
	/**
	 * Atributos
	 */
		private Long   cdCanalAtend;
		private String dsInfoCanalAtend;
		private String dsLink;
		private String dsLinkUrl;
		private String dsTelefone;
		private String nmCanalAtend;
		private String dsDiaHorarioAtend;
		private String codDiretoria;
		private String dsDiretoria;
		
		public String getDsDiretoria() {
			return Diretoria.getString(Integer.valueOf(codDiretoria).intValue());
		}
		public void setDsDiretoria(String dsDiretoria) {
			this.dsDiretoria = dsDiretoria;
		}
		public Long getCdCanalAtend() {
			return cdCanalAtend;
		}
		public void setCdCanalAtend(Long cdCanalAtend) {
			this.cdCanalAtend = cdCanalAtend;
		}
		public String getDsInfoCanalAtend() {
			return dsInfoCanalAtend;
		}
		public void setDsInfoCanalAtend(String dsInfoCanalAtend) {
			this.dsInfoCanalAtend = dsInfoCanalAtend;
		}
		public String getDsLink() {
			return dsLink;
		}
		public void setDsLink(String dsLink) {
			this.dsLink = dsLink;
		}
		public String getDsLinkUrl() {
			return dsLinkUrl;
		}
		public void setDsLinkUrl(String dsLinkUrl) {
			this.dsLinkUrl = dsLinkUrl;
		}
		public String getDsTelefone() {
			return dsTelefone;
		}
		public void setDsTelefone(String dsTelefone) {
			this.dsTelefone = dsTelefone;
		}
		public String getNmCanalAtend() {
			return nmCanalAtend;
		}
		public void setNmCanalAtend(String nmCanalAtend) {
			this.nmCanalAtend = nmCanalAtend;
		}
		public String getDsDiaHorarioAtend() {
			return dsDiaHorarioAtend;
		}
		public void setDsDiaHorarioAtend(String dsDiaHorarioAtend) {
			this.dsDiaHorarioAtend = dsDiaHorarioAtend;
		}
		
		public AgvTabCanalAtend toEntity() {
			AgvTabCanalAtend entity =  new AgvTabCanalAtend();
			entity.setCdCanalAtend(cdCanalAtend);
			entity.setDsDiaHorarioAtend(dsDiaHorarioAtend);
			entity.setDsInfoCanalAtend(dsInfoCanalAtend);
			entity.setDsLink(dsLink);
			entity.setDsLinkUrl(dsLinkUrl);
			entity.setDsTelefone(dsTelefone);
			entity.setNmCanalAtend(nmCanalAtend);
			if(codDiretoria != null)
				entity.setCodDiretoria(Integer.valueOf(codDiretoria));
			return entity;
		}
		public String getCodDiretoria() {
			return codDiretoria;
		}
		public void setCodDiretoria(String codDiretoria) {
			this.codDiretoria = codDiretoria;
		}
		
		
}