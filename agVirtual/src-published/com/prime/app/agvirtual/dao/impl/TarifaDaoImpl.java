package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.TarifaDao;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.app.agvirtual.to.CategoriaTO;
import com.prime.app.agvirtual.to.MunicipioTO;
import com.prime.app.agvirtual.to.TarifaTO;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;
import com.prime.infra.util.WrapperUtils;

@Repository
public class TarifaDaoImpl extends GenericRDMSJpaDao<Imovel, Long> implements TarifaDao {

	public static final String FETCH_FIRST_10 = " FETCH FIRST 10 ROW ONLY ";
	public static final String FETCH_FIRST_5 = " FETCH FIRST 5 ROW ONLY ";

	private static final Logger logger = LoggerFactory.getLogger(AgenciaDaoImpl.class);

	public List<CategoriaTO> findCategoria() {
		
		List<CategoriaTO> listaCategoria = new ArrayList<CategoriaTO>();
		CategoriaTO categoria;
		
		String sqlQuery = "SELECT * FROM CTB14";

		try{
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			
			ResultSet rs = pstmt.executeQuery();
			
			while( rs.next() ){
				categoria = new CategoriaTO();
				categoria.setCodigo(rs.getLong("CDCATCON"));
				categoria.setNome(rs.getString("DSCATCON").trim());
				listaCategoria.add(categoria);
			}
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		return listaCategoria;
	}
	
	public List<TarifaTO> findTipoTarifaByMunicipio(MunicipioTO municipio) {
		List<TarifaTO> listaTarifa = new ArrayList<TarifaTO>();
		TarifaTO tarifa = new TarifaTO();
		
		if (municipio == null ) {
			listaTarifa = new ArrayList<TarifaTO>();
		} else if (municipio.getIdMun() < 200) {
			tarifa = new TarifaTO();
			tarifa.setCdTipoTarifa(1L);
			tarifa.setNmTipoTarifa("Tarifas atuais");
			listaTarifa.add(tarifa);
			
			tarifa = new TarifaTO();
			tarifa.setCdTipoTarifa(2L);
			tarifa.setNmTipoTarifa("Tarifas anteriores");
			listaTarifa.add(tarifa);
			
			tarifa = new TarifaTO();
			tarifa.setCdTipoTarifa(3L);
			tarifa.setNmTipoTarifa("Próximas tarifas");
			listaTarifa.add(tarifa);
		} else if (municipio.getIdMun() > 200 && municipio.getIdMun() < 400) {
			tarifa = new TarifaTO();
			tarifa.setCdTipoTarifa(1L);
			tarifa.setNmTipoTarifa("Tarifas atuais");
			listaTarifa.add(tarifa);
			
			tarifa = new TarifaTO();
			tarifa.setCdTipoTarifa(2L);
			tarifa.setNmTipoTarifa("Tarifas anteriores");
			listaTarifa.add(tarifa);
		} else {
			tarifa = new TarifaTO();
			tarifa.setCdTipoTarifa(1L);
			tarifa.setNmTipoTarifa("Tarifas atuais");
			listaTarifa.add(tarifa);
		}

		return listaTarifa;
	}
	
	public List<List<TarifaTO>> findTarifaByParams(String cdMunicipio, String cdCategoria, String cdTipoTarifa) {
		List<List<TarifaTO>> retorno = new ArrayList<List<TarifaTO>>();
		List<TarifaTO> listaTarifas = new ArrayList<TarifaTO>();
		TarifaTO tarifa;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> sqlQuery = new ArrayList<String>(); 
			
		//Verifica se a categoria é igual a 0, caso seja pesquisa todas as categorias
		if ( cdCategoria.equals(""+TarifaTO.RESIDENCIAL) || cdCategoria.equals(""+TarifaTO.TODAS)) {
			sqlQuery.add ( getSQLByCategoria(TarifaTO.RESIDENCIAL, TarifaTO.COMUM, cdTipoTarifa, cdMunicipio) );
			sqlQuery.add ( getSQLByCategoria(TarifaTO.RESIDENCIAL, TarifaTO.SOCIAL, cdTipoTarifa, cdMunicipio) );	
			sqlQuery.add ( getSQLByCategoria(TarifaTO.RESIDENCIAL, TarifaTO.FAVELA, cdTipoTarifa, cdMunicipio) );
		}
		
		if ( cdCategoria.equals(""+TarifaTO.COMERCIAL) || cdCategoria.equals(""+TarifaTO.TODAS)) {
			sqlQuery.add ( getSQLByCategoria(TarifaTO.COMERCIAL, TarifaTO.COMUM, cdTipoTarifa, cdMunicipio) );
			sqlQuery.add ( getSQLByCategoria(TarifaTO.COMERCIAL, TarifaTO.ASSISTENCIAL, cdTipoTarifa, cdMunicipio) );
		}
		
		if ( cdCategoria.equals(""+TarifaTO.INDUSTRIAL) || cdCategoria.equals(""+TarifaTO.TODAS)) {
			sqlQuery.add ( getSQLByCategoria(TarifaTO.INDUSTRIAL, TarifaTO.COMUM, cdTipoTarifa, cdMunicipio) );
		}
		
		if ( cdCategoria.equals(""+TarifaTO.PUBLICA) || cdCategoria.equals(""+TarifaTO.TODAS)) {			
			sqlQuery.add ( getSQLByCategoria(TarifaTO.PUBLICA, TarifaTO.COMUM, cdTipoTarifa, cdMunicipio) );
			sqlQuery.add ( getSQLByCategoria(TarifaTO.PUBLICA, TarifaTO.COM_CONTRATO, cdTipoTarifa, cdMunicipio) );
			sqlQuery.add ( getSQLByCategoria(TarifaTO.PUBLICA, TarifaTO.CONTRATO_DE_PROGRAMA, cdTipoTarifa, cdMunicipio) );
		}
				
		for(String sql : sqlQuery){
			// contador de Tarifas anteriores, onde por regra deveremos desprezar os 5 primeiros
			int tarifasAnterioresContador=0;
			int i=1;			
			// limpa array de tarifas a cada iteração.
			listaTarifas = new ArrayList<TarifaTO>();			
			for(ArrayList<String> rows: findQBE(sql)){				
				tarifasAnterioresContador++;
				// Tarifas anteriores, onde por regra deveremos desprezar os 5 primeiros
				if(sql.indexOf(FETCH_FIRST_10.trim())>0 && tarifasAnterioresContador<=5 ) continue;
				tarifa = new TarifaTO();
				tarifa.setNmTipoTarifa(
						getLabelSubCatagoria(rows.get(7)).trim().length()==0 ? 
						getLabelCatagoria(rows.get(2)) : getLabelCatagoria(rows.get(2)) + "|"+ getLabelSubCatagoria(rows.get(7) ) );
				tarifa.setClasseConsumo(getLabelFaixaConsumo(rows.get(3)));
				tarifa.setTarifaAgua(WrapperUtils.parseMoney(rows.get(5)));
				tarifa.setTarifaEsgoto(WrapperUtils.parseMoney(rows.get(6)));			
				tarifa.setCount(i++);
				listaTarifas.add(tarifa);
			}
			// prepara matriz de retorno, contendo todos painel final
			if(!listaTarifas.isEmpty()) retorno.add(listaTarifas);
		}
		return retorno;
	}

	String getSQLByCategoria(int cdCat, int cdSubCat, String tpTar, String cdMunicipio){
		return  " SELECT CDMUNICIP,CDTARIFA,CDCATCON,QTFAIXATE,DJVIGENC,VLAGUA,VLESGOTO,CDTCOBR " +
				" FROM CTF01 WHERE STREGIST=0 AND STBLOQUE=0 AND CDMUNICIP="+cdMunicipio+" AND CDCATCON="+cdCat+" AND CDTCOBR='"+cdSubCat+"' " +
				getTarifaByMunicipio(cdMunicipio)+ " " +getOperatorAndDataVigenc(tpTar) +
				" ORDER BY DJVIGENC DESC, CDMUNICIP, CDCATCON, CDTARIFA, QTFAIXATE "+getRowNum(tpTar);		
	}
	
	public String getTarifaByMunicipio(String cdMunicipio) {
		String retorno = findQBEOneTuple("SELECT CDTARIFA FROM CTF06 WHERE CDMUNICIP=?",cdMunicipio).get(0);
		return WrapperUtils.isNull(retorno) ? "" : " AND CDTARIFA= "+retorno; 

	}
	
	private String getRowNum(String cdTipoTarifa) {
		String retorno = "";
		if(WrapperUtils.isNull(cdTipoTarifa)){
			return "";
		}
		if(cdTipoTarifa.equalsIgnoreCase(TarifaTO.TARIFAS_ATUAIS)){
			retorno = FETCH_FIRST_5;
		}else if(cdTipoTarifa.equalsIgnoreCase(TarifaTO.TARIFAS_ANTERIORES)){
			retorno = FETCH_FIRST_10;
		}
		return retorno;
	}

	/**
	 * 
	 * @param string 
	 * @return ex: < 19631
	 */
	private String getOperatorAndDataVigenc(String cdTipoTarifa) {
		if(WrapperUtils.isNull(cdTipoTarifa)){
			return "";
		}
		String retorno = " < "+WrapperUtils.getDaysFromDate(WrapperUtils.getFirstDayFromNextMonth());
		if(cdTipoTarifa.equalsIgnoreCase(TarifaTO.PROXIMAS_TARIFAS)){
			retorno = " >= "+WrapperUtils.getDaysFromDate(WrapperUtils.getFirstDayFromNextMonth());
		}
		return " AND DJVIGENC "+retorno; 
	}
	

	public String getCatagoriaByRGI(String rgi) {
		String retorno =null;
		String categoria = findQBEOneTuple("SELECT CDCATCON FROM PCCG0403 WHERE STREGIST=2 AND NRRGILIG=?",rgi).get(0);
		return getLabelCatagoria(categoria);
	}
	
	public String getLabelCatagoria(String categoria) {
		String retorno =null;
		if(!WrapperUtils.isNull(categoria)){
			switch (Integer.parseInt(categoria)) {
			case TarifaTO.RESIDENCIAL:
				retorno = TarifaTO.RESIDENCIAL_LBL;
				break;
			case TarifaTO.COMERCIAL:
				retorno = TarifaTO.COMERCIAL_LBL;
				break;
			case TarifaTO.INDUSTRIAL:
				retorno = TarifaTO.INDUSTRIAL_LBL;
				break;
			case TarifaTO.PUBLICA:
				retorno = TarifaTO.PUBLICA_LBL;
				break;
			default:
				break;
			}			
		}	
		return retorno;
	}
	
	public String getLabelSubCatagoria(String categoria) {
		String retorno =null;
		if(!WrapperUtils.isNull(categoria)){
			switch (Integer.parseInt(categoria)) {
			case TarifaTO.COMUM:
				retorno = TarifaTO.COMUM_LBL;
				break;
			case TarifaTO.SOCIAL:
				retorno = TarifaTO.SOCIAL_LBL;
				break;
			case TarifaTO.FAVELA:
				retorno = TarifaTO.FAVELA_LBL;
				break;
			case TarifaTO.ASSISTENCIAL:
				retorno = TarifaTO.ASSISTENCIAL_LBL;
				break;
			case TarifaTO.COM_CONTRATO:
				retorno = TarifaTO.COM_CONTRATO_LBL;
				break;
			case TarifaTO.CONTRATO_DE_PROGRAMA:
				retorno = TarifaTO.CONTRATO_DE_PROGRAMA_LBL;
				break;
			default:
				break;
			}			
		}	
		return retorno;
	}
	
	private String getLabelFaixaConsumo(String consumo) {
		String retorno =null;
		if(!WrapperUtils.isNull(consumo)){
			switch (Integer.parseInt(consumo)) {
			case 10:
				retorno = TarifaTO.CONSUMO_10;
				break;
			case 20:
				retorno = TarifaTO.CONSUMO_20;
				break;
			case 30:
				retorno = TarifaTO.CONSUMO_30;
				break;
			case 50:
				retorno = TarifaTO.CONSUMO_50;
				break;
			case 999999:
				retorno = TarifaTO.CONSUMO_999999;
				break;
			default:
				break;
			}			
		}	
		return retorno;
	}
}
