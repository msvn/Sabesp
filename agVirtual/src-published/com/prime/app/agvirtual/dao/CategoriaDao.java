package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.Categoria;
import com.prime.app.agvirtual.entity.Imovel;

public interface CategoriaDao { 
	
	 List<Categoria> carregarCategoria(Imovel imovel);
	

}
