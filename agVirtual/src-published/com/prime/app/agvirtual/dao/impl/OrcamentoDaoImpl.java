package com.prime.app.agvirtual.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.OrcamentoDao;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.OrcamentoOferecidoTO;
import com.prime.infra.BusinessException;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;
import com.prime.infra.util.WrapperUtils;

@Repository
public class OrcamentoDaoImpl extends GenericRDMSJpaDao<Imovel, Long> implements OrcamentoDao {
	
	private static final Logger logger = LoggerFactory.getLogger(OrcamentoDaoImpl.class);
	public static final String PAGAMENTO_A_VISTA = "V";
	public static final String SERVICO_GRATUITO = "G";
	public static final String SERVICO_COM_PRECO_FIXO = "S";
	public static final String SERVICO_PARCELADO = "S";
	public static final String SERVICO_VARIADO = "N";
	public static final String LBL_SERVICO_GRATUITO = "Gratuito";
	public static final String LBL_SERVICO_VARIADO = "Variável";
	public static final int SERVEXE_DEFAULT = 1;
	
	public List<Integer> getVicePresidenciaUnidadeNegocio(Long rgi) {
		
		ArrayList<Integer> vicePresidenciaUnidadeNegocio = new ArrayList<Integer>();
		
		try {
	        StringBuilder query = new StringBuilder();
	        query.append("select c.cdunidco3, cdunidco1 ");
	        query.append("from pccg0305 a, ccg03 b, ctb23 c ");
	        query.append("where a.nrrgilig = ? ");
	        query.append("and a.nrrgilig=b.nrrgilig ");
	        query.append("and a.nrgrupfat=b.nrgrupfat ");
	        query.append("and c.cdatcom=b.cdunidcom");
	        
	        PreparedStatement pstm = getHibernateSession().connection().prepareStatement(query.toString());

			pstm.setLong(1, rgi);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()) {
				vicePresidenciaUnidadeNegocio.add(rs.getInt("cdunidco3")); //Vice Presidência
				vicePresidenciaUnidadeNegocio.add(rs.getInt("cdunidco1")); //Unidade de Negócio
			}

			fecharRecurso(pstm, null, rs);
		}catch (Exception e) {
			logger.error("Erro ao acessar o DB.");
		}
		
		return vicePresidenciaUnidadeNegocio;
	}
	
	
	/**
	 * Carrega a vide presidencia e Unidade de negocio apartir de um codigo de municipio
	 */
	public List<Integer> getVicePresidenciaUnidadeNegocio(String cdMunicip) {
		
		ArrayList<Integer> vicePresidenciaUnidadeNegocio = new ArrayList<Integer>();
		
		try {
	        StringBuilder query = new StringBuilder();
	        
	        if(!cdMunicip.equals("100")){
	        	query.append("SELECT B.CDUNIDCO3,B.CDUNIDCO2 FROM PCTB2304 A, CTB23 B WHERE A.CDATCOM=B.CDATCOM AND A.CDMUNICIP=?");
	        }else{
	        	// Caso seja de Sao paulo CDMUNICIPIO =  100 , é passado o cdAtcom 912 que representa 
	        	// os serviços da reão central.
	        	query.append("select CDUNIDCO3, CDUNIDCO2 from CTB23 where CDATCOM=912");
	        }
	        PreparedStatement pstm = getHibernateSession().connection().prepareStatement(query.toString());
	        pstm.setString(1, cdMunicip);
	       
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()) {
				vicePresidenciaUnidadeNegocio.add(rs.getInt("CDUNIDCO3")); //Vice Presidência
				vicePresidenciaUnidadeNegocio.add(rs.getInt("CDUNIDCO2")); //Unidade de Negócio
			}

			fecharRecurso(pstm, null, rs);
		}catch (Exception e) {
			logger.error("Erro ao acessar o DB.");
		}
		
		return vicePresidenciaUnidadeNegocio;
	}
	
	
	
	public OrcamentoOferecidoTO getTipoOrcamento(Long rgi, Integer cdGrupoServico, Integer cdSubServico , String cdMunicipio) {
		logger.info("Inicio metodo OrcamentoDAOImpl -> getTipoOrcamento");
		
		OrcamentoOferecidoTO orcamento = new OrcamentoOferecidoTO();
		Integer cdVicePresidencia = null;
		Integer cdUnidadeNegocio = null;
		
		//Caso não seja nulo realiza a pesquisa
		if (WrapperUtils.isNotNull(rgi)) {
			List<Integer> vicePresidenciaUnidadeNegocio = getVicePresidenciaUnidadeNegocio(rgi);
			if(vicePresidenciaUnidadeNegocio.size() > 0) {
				cdVicePresidencia = vicePresidenciaUnidadeNegocio.get(0);
				cdUnidadeNegocio = vicePresidenciaUnidadeNegocio.get(1);
			} else {
				return null;
			}
		}else if(WrapperUtils.isNotNull(cdMunicipio)){
			List<Integer> vicePresidenciaUnidadeNegocio = getVicePresidenciaUnidadeNegocio(cdMunicipio);
			if(vicePresidenciaUnidadeNegocio.size() > 0) {
				cdVicePresidencia = vicePresidenciaUnidadeNegocio.get(0);
				cdUnidadeNegocio = vicePresidenciaUnidadeNegocio.get(1);
			} else {
				return null;
			}
		}else {
			//Caso seja nulo o auto atendimento é gratuito e passará osa dados fíxos, segundo verificada com o Henrique
			cdVicePresidencia = 1;
			cdUnidadeNegocio = 51;
			
			logger.info("Imovel nao identificado, assumindo cdVicePresidencia=" + cdVicePresidencia + ", cdUnidadeNegocio=" +cdUnidadeNegocio);
		}

		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//DSUNIMED = D = Dias / H = horas
	        StringBuilder query = new StringBuilder();
	        query.append("SELECT QTPARCEL1, SNPRECOFIX, STGERAL1 , QTHRPRAZO , DSUNIMED ");
	        query.append("FROM CTB01 ");
	        query.append("WHERE CDUNIDCO3 = ? ");
	        query.append("AND CDUNIDCO2 = ? ");
	        query.append("AND CDGRPSERV = ? ");
	        query.append("AND CDSERVCOM > 0 ");
	        query.append("AND CDSERVEXE > 0 ");
	        query.append("FETCH FIRST 1 ROW ONLY");

	        logger.info("Parametros:");
	        logger.info("cdVicePresidencia=" + cdVicePresidencia);
	        logger.info("cdUnidadeNegocio=" + cdUnidadeNegocio);
	        logger.info("cdGrupoServico=" + cdGrupoServico);
	        
	        pstm = getHibernateSession().connection().prepareStatement(query.toString());
			
			pstm.setInt(1, cdVicePresidencia);
			pstm.setInt(2, cdUnidadeNegocio);
			pstm.setInt(3, cdGrupoServico);
			
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				orcamento.setPrazoAtendimento(tratamentoHoras(rs.getString("QTHRPRAZO")));
				if(rs.getString("SNPRECOFIX").equalsIgnoreCase(SERVICO_GRATUITO)) {
					orcamento.setPago(false);
				} else {
					orcamento.setPago(true);
					
					if(rs.getString("SNPRECOFIX").equalsIgnoreCase(PAGAMENTO_A_VISTA)) {
						orcamento.setPagamentoAvista(true);
						orcamento.setPrecoFixo(true);
					}
					
					if(rs.getString("SNPRECOFIX").equalsIgnoreCase(SERVICO_COM_PRECO_FIXO)) {
						orcamento.setPrecoFixo(true);
					}
					
					if(rs.getString("STGERAL1").equalsIgnoreCase(SERVICO_PARCELADO)) {
						orcamento.setPagamentoParcelado(true);
					}
					
					orcamento.setMaxParcelas(rs.getInt("QTPARCEL1"));
				}
			}

		}catch (Exception e) {
//			e.printStackTrace();
			logger.error("Erro ao acessar o DB.");
			throw new RuntimeException("Erro ao obter tipo orcamento.", e.getCause());
		}finally{
			fecharRecurso(pstm, null, rs);
		}
		
		return orcamento;
	}
	
	/**
	 * 
	 * @param qtHoras
	 * @return
	 */
	private String tratamentoHoras(String qtHoras) {
		if(qtHoras != null){
			Integer value = WrapperUtils.toInt(qtHoras);
			if(value.intValue() > 72){
				String dias =  String.valueOf(value/24) + " dias";
				return dias;
			}else{
				return qtHoras + " horas";
			}
		}
		return qtHoras;
	}

	public BigDecimal getValorServico(Long rgi, Integer cdGrupoServico, Integer cdSubServico, Integer cdServicoExecutado , String cdMunicipio) {
		logger.info("Inicio OrcamentoDaoImpl -> getValorServico");
		
		BigDecimal valorServico = null;
		List<Integer> vicePresidenciaUnidadeNegocio =  null;
		//Caso não seja nulo realiza a pesquisa
		if (WrapperUtils.isNotNull(rgi)) {
			vicePresidenciaUnidadeNegocio = getVicePresidenciaUnidadeNegocio(rgi);
		}else if(WrapperUtils.isNotNull(cdMunicipio)){
			vicePresidenciaUnidadeNegocio = getVicePresidenciaUnidadeNegocio(cdMunicipio);
		}
		
		Integer cdVicePresidencia = vicePresidenciaUnidadeNegocio.get(0);
		Integer cdUnidadeNegocio = vicePresidenciaUnidadeNegocio.get(1);
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
	        StringBuilder query = new StringBuilder();
	        query.append("SELECT VLSERVCOM, CDSERVEXE, DTVIGENC ");
	        query.append("FROM CTB81 ");
	        query.append("WHERE CDUNIDCO3 = ? ");
	        query.append("AND CDUNIDCO2 = ? ");
	        query.append("AND CDGRPSERV = ? ");
	        query.append("AND CDSERVCOM = ? ");
	        query.append("AND CDSERVEXE = ? ");
	        query.append("ORDER BY CDSERVEXE, DTVIGENC DESC ");
	        query.append("FETCH FIRST 1 ROW ONLY");

	        pstm = getHibernateSession().connection().prepareStatement(query.toString());
			
			pstm.setInt(1, cdVicePresidencia);
			pstm.setInt(2, cdUnidadeNegocio);
			pstm.setInt(3, cdGrupoServico);
			pstm.setInt(4, cdSubServico);
//			pstm.setInt(5, SERVEXE_DEFAULT);
			pstm.setInt(5, cdServicoExecutado);

			rs = pstm.executeQuery();
			
			if(rs.next()) {
				valorServico = rs.getBigDecimal("VLSERVCOM");
			}

		}catch (Exception e) {
//			e.printStackTrace();
			logger.error("Erro ao acessar o DB.");
			throw new RuntimeException("Erro ao obter valor do serviço.", e.getCause());
		}finally{
			fecharRecurso(pstm, null, rs);
		}
		
		return valorServico;
	}

}
