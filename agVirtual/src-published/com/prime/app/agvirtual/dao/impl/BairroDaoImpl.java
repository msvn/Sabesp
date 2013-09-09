package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.BairroDao;
import com.prime.app.agvirtual.entity.Bairro;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;

@Repository
public class BairroDaoImpl extends GenericRDMSJpaDao<Imovel, Long> implements BairroDao {

	public Bairro findByBairro(Bairro bairro) {
		
		Bairro bairroCarregado = new Bairro();
		
		String sqlQuery = 
			"SELECT " +
			"NMBAIRRO " +
			"FROM PCTB3002 " +
			"WHERE " +
			"CDMUNICIP=?" +
			"AND CDBAIRRO=?";
		
		try {
			
			PreparedStatement pstmt = getHibernateSession().connection().prepareStatement(sqlQuery);
			pstmt.setString(1, bairro.getCdMunicipio());
			pstmt.setString(2, bairro.getCdBairro());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				bairroCarregado.setNmBairro(rs.getString("NMBAIRRO"));
			}
			
		}catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
		
		return bairroCarregado;
	}

	
	
}
