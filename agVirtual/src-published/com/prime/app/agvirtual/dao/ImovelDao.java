package com.prime.app.agvirtual.dao;

import java.util.List;

import com.prime.app.agvirtual.entity.Categoria;
import com.prime.app.agvirtual.entity.Imovel;

public interface ImovelDao {
	
	String pesquisaUnidadeNegocio(String rgi);

	public Imovel findByRgi(String dsRgi) throws Exception;
	
	Imovel findByImovel(Imovel imovel) throws Exception ;
	
	List<Imovel> findByName(Imovel imovel);
	
	List<Categoria> listaCategoriaByRGI(Imovel imovel);
	
	public List<String> findAllRGIs(Imovel imovel);

	public boolean verificaRgiRolEspecial(Imovel imovel);
	
	void pesquisaCodProcessamentoRgi(Imovel imovelCarregado, Imovel imovel);
}
