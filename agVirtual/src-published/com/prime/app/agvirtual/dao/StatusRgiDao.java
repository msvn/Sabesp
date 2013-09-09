package com.prime.app.agvirtual.dao;

public interface StatusRgiDao {

	boolean isLigacaoAtiva(Long rgi);

	boolean rgiPossuiSupressao(Long rgi);
	
	boolean isRgiCortado(Long rgi);

}
