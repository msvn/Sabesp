package com.prime.app.agvirtual.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prime.app.agvirtual.dao.CategoriaDao;
import com.prime.app.agvirtual.entity.Categoria;
import com.prime.app.agvirtual.entity.Imovel;
import com.prime.infra.dao.jpa.GenericRDMSJpaDao;

@Repository
public class CategoriaDaoImpl extends GenericRDMSJpaDao<Categoria, Long> implements CategoriaDao {
	
	public List<Categoria> carregarCategoria(Imovel imovel){
		
		List<Categoria> categoriaList = new ArrayList<Categoria>(); 
		
        String sSQL = 
        	"SELECT " +
        	"CDCATCON " +
        	"FROM " +
        	"CCG04 " +
        	"WHERE " +
        	"NRRGILIG =? " + 
        	"AND STREGIST = 1 ";
        
        try {
        	PreparedStatement ps = getHibernateSession().connection().prepareStatement(sSQL);
        	ps.setString(1, imovel.getDsRgi().trim() );
        	
    		ResultSet rs = ps.executeQuery();
    		
    		while (rs.next()) {
    			
    			Categoria categoria = new Categoria();
    			
    			int value = Integer.valueOf(rs.getString(1).trim()); 
    			
    			if( value > 0 && value < 5 ){
    				
    				categoria.setCdCategoriaConsumo( String.valueOf( (value -1) + 1)  );
    				
    			}    			
    			
    			categoriaList.add( categoria );
    		}
        	
        	
        }catch (Exception e) {
			new IllegalArgumentException("Erro ao acessar o DB.");
		}
        
        return categoriaList;
	}

}
