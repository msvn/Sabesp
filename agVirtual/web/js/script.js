/* validacao campos calendarios */
function somente_numero(campo){  
	 var digits="0123456789";
	 var campo_temp;   

	 for (var i=0;i<campo.value.length;i++){  
         campo_temp=campo.value.substring(i,i+1);   
         if (digits.indexOf(campo_temp)==-1){  
             campo.value = campo.value.substring(0,i);  
         }  
	 }
}  



// verifica a altura para que o bg do menu pcupe toda a tela
function altura(){
	var totalTela = document.documentElement.clientHeight;
	var totalPagina = document.body.clientHeight;
	var menu = document.getElementById('menu');
	
	if(document.body.clientHeight > document.documentElement.clientHeight)
		menu.style.height = totalPagina + "px";
	else{
		if(document.documentElement.clientHeight < menu.clientHeight){
			menu.style.height = menu.clientHeight + "px";
		}
		else{
			menu.style.height = (totalTela - 28) + "px";
		}
	}
	
	document.getElementById('listaMenu').style.display = "none";
}

//menu Lateral

var contador = 0;

var sinal = "sobe"

function mostraMenu(){
	if((contador <= 160) && (contador >= 0)){
		if(sinal == "sobe"){
			document.getElementById('listaMenu').style.display = "block";
			document.getElementById('seta').style.left = (contador - 18) + "px";
			contador+= 4;
			document.getElementById('menu').style.width = contador - 4 + "px";
		}
		else if(sinal == "desce"){
			document.getElementById('seta').style.left = contador + "px";
			contador-= 4;
			document.getElementById('menu').style.width = contador + 22 + "px";
		}
		
		setTimeout(mostraMenu, 1);
	}
	else{
		if(sinal == "sobe"){
			document.getElementById('seta').className = "setaEsquerda";
			contador-= 4;
			sinal = "desce"
		}
		else if(sinal == "desce"){
			document.getElementById('listaMenu').style.display = "none";
			document.getElementById('seta').className = "seta";
			contador+= 4;
			sinal = "sobe"
		}
	}
}

// CONTROLA AS CORES DA TABELA 
var guardaTr
var guardaTable

function cores(nomeTabela){
	guardaTable = document.getElementById(nomeTabela).getElementsByTagName('table');
	
	for(var i=0; i<guardaTable.length; i++){
		guardaTr = guardaTable[i].getElementsByTagName('tr');
		
		for(var a=1; a<guardaTr.length; a++){
			if ((a % 2) == 0){
				guardaTr[a].className = "claro";
			}
			else{
				guardaTr[a].className = "escuro";
			}	
		}	
	}
}

// CONTROLA AS CORES DA TABELA 
var guardaTrPaginacao
var guardaTablePaginacao

function coresPaginacao(nomeTabela){
	guardaTablePaginacao = document.getElementById(nomeTabela).getElementsByTagName('table');
	
	for(var i=0; i<guardaTablePaginacao.length; i++){
		guardaTrPaginacao = guardaTablePaginacao[i].getElementsByTagName('tr');
		
		for(var a=1; a<guardaTrPaginacao.length; a++){
			if ((a % 2) == 0){
				guardaTrPaginacao[a].className = "claro";
			}
			else{
				guardaTrPaginacao[a].className = "escuro";
			}	
		}	
	}
}

//Troca a seta da Tabela
function trocaSeta(nome){
	if(nome.className == "setaBaixo")
		nome.className = "setaCima";
	else if(nome.className == "setaCima")
		nome.className = "setaBaixo";
}

//muda campo input
function focaliza(nome){
	nome.className = "focaliza"
}

function desfocaliza(nome){
	nome.className = "desfocaliza"
}


//CALENDARIO

//TotalDias(): retorna o total de dias de um mês
//	- mes: mes a verificar
//	- ano: ano [para verificação de ano bissexto]
function TotalDias(mes, ano)
{
	var arrMeses = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
	
	if(ano % 4 == 0)
		arrMeses[1] = 29;
	
	return arrMeses[mes];
}

//DiaDaSemana(): retorna o nome do dia da semana
//	- dia: dia da semana, em valor numérico
function DiaDaSemana(dia)
{
	var arrSemana = new Array("D", "S", "T", "Q", "Q", "S", "S", "D");
	
	return arrSemana[dia];
}

//NomeMes(): retorna o nome do mês
//	- mes: mês a retornar o nome, numérico
function NomeMes(mes)
{
	var arrMeses = new Array("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro")
	
	return arrMeses[mes];
}

function MostrarData(ano, mes, dia, alvo)
{
	document.getElementById(diaMarcado).className = '';
	alvo.className = 'marcado';
	
	diaMarcado = alvo.id;
	
	var inputs = document.getElementById('formulario').getElementsByTagName('input');
	document.getElementById('calendario').style.display = 'none';
	
	inputs.dia.value = dia;
	inputs.mes.value = parseInt(mes) + 1;
	inputs.ano.value = ano;
}


//GerarCalendario(): imprime o calendário completo de um ano
//	• _ano: ano a imprimir o calendário
//	• alvo: onde o calendário vai ser inserido

var diaMarcado;
function GerarCalendario(_ano, alvo)
{
	var _data = new Date(_ano, 0, 1);
	var countSemana = _data.getDay() + 1;
	
	var strCalendario = '';
	for(var i = 0; i < 12; i ++)
	{
		strCalendario += '<div class="mes" id="' + (i + 1) + '_' + _ano  + '"><div class="controla"><a href="javascript:TrocarMes(+1);" class="direita"> <img src="images/calendario_avancar.gif" width="19" height="19" alt="" border="0"> </a><a href="javascript:TrocarMes(-1);" class="esquerda"> <img src="images/calendario_voltar.gif" width="19" height="19" alt="" border="0"> </a>' + NomeMes(i) + ' | '  + _ano + '</div><table border="0"><tr>';
		
		for(var k = 0; k < 7; k ++)
			strCalendario += "<th>" + DiaDaSemana(k) + "</th>";
		
		strCalendario += "</tr><tr>";
		
		for(var j = 1; j <= TotalDias(i, _ano); j ++)
		{	
			if(j == 1)
			{
				for(var k = 1; k < countSemana; k ++)
					strCalendario += "<td>&nbsp;</td>";
			}
		
			var data = new Date(_ano, i, j);
			
			var _class = '';
			if(i == dataAtual.getMonth() && j == dataAtual.getDate() && _ano == dataAtual.getFullYear())
			{
				_class = 'marcado';
				diaMarcado = j + '_' + i + '_' + _ano;
			}
			
			
			strCalendario += '<td>';
			strCalendario += '<span id="' + j + '_' + i + '_' + _ano + '" onclick="MostrarData(\'' + _ano + '\', \'' + i + '\', \'' + j + '\', this)" class="' + _class + '">' + (data.getDate()) + '</span>';
			strCalendario += '</td>';
			
			if(countSemana == 7)
			{
				strCalendario += "</tr><tr>";
				countSemana = 1;
			}
			
			else
			{
				countSemana ++;
			}
		}
		
		strCalendario += "</table></div>";
		
	}
	
	document.getElementById(alvo).innerHTML += strCalendario;
}

var mesAtual;
var _mes, _ano;
function TrocarMes(n)
{
	_mes += n;
	
	if(_mes <= 0)
	{
		_mes = 12
		_ano --;
		if(document.getElementById('calendario').innerHTML.indexOf('_' + _ano)  == -1)
			GerarCalendario(_ano, 'calendario');
		
	}
	
	else if(_mes >= 13)
	{
		_mes = 1;
		_ano ++;
		if(document.getElementById('calendario').innerHTML.indexOf(_ano)  == -1)
			GerarCalendario(_ano, 'calendario');
	}
	
	document.getElementById(mesAtual).style.display = "none";
	
	document.getElementById(_mes + '_' + _ano).style.display = "block";
	
	
	mesAtual = (_mes + '_' + _ano);
}

var dataAtual;
//Init(): inicia o calendário
function Init()
{
	dataAtual = new Date();
	_mes = (dataAtual.getMonth() + 1);
	_ano = dataAtual.getFullYear();
	mesAtual = (_mes + '_' + _ano);
	
	GerarCalendario(_ano, 'calendario');
	
	
	document.getElementById(_mes  + '_' + _ano).style.display = 'block';
}

function mostraCalendario(){
	var calendar = document.getElementById('calendario')
	if(calendar.style.display == 'block')
		calendar.style.display = 'none';
	else
		calendar.style.display = 'block';
}

function setTitulo(dsTitulo){
	parent.document.getElementById("Titulo").innerHTML=dsTitulo;
}

var menuAnterior;
function abreMenu(idMenu, objMenu){
	if(idMenu==1){
		parent.parent.parent.document.getElementById("frmconteudo").src="CadastroCanalAtendimento.html";
		setTitulo("Cadastro de Canais de Atendimento");
	}else if(idMenu==2){
		parent.parent.parent.document.getElementById("frmconteudo").src="CadastroDocumento.html";
		setTitulo("Cadastro de Documentos");
	}else if(idMenu==3){
		parent.parent.parent.document.getElementById("frmconteudo").src="CadastroNoticia.html";
		setTitulo("Cadastro de Noticias");
	}else if(idMenu==4){
		parent.parent.parent.document.getElementById("frmconteudo").src="BloqueioServicos.html";
		setTitulo("Bloqueio de Serviços");
	}else if(idMenu==5){
		parent.parent.parent.document.getElementById("frmconteudo").src="CadastroPesquisa.html";
		setTitulo("Cadastro de Pesquisas");	
	}else if(idMenu==6){
		parent.parent.parent.document.getElementById("frmconteudo").src="ServicosCorrelatos.html";
		setTitulo("Serviços Correlatos");	
	}else if(idMenu==7){
		parent.parent.parent.document.getElementById("frmconteudo").src="CadastroServico.html";
		setTitulo("Cadastro de Serviços");	
	}else if(idMenu==8){
		parent.parent.parent.document.getElementById("frmconteudo").src="CadastroSubServico.html";
		setTitulo("Cadastro de Subserviços");	
	}else if(idMenu==9){
		parent.parent.parent.document.getElementById("frmconteudo").src="CadastroHome.html";
		setTitulo("Cadastro de Conteúdos da Home");	
	}
	
	objMenu.className="current";
	try{
		menuAnterior.className="normal";
	}catch(e){
		var t="";
	}
	menuAnterior = objMenu;
	
}

function abrePagina(pagina,titulo){
	parent.parent.parent.document.getElementById("frmconteudo").src=pagina;
	setTitulo(titulo);
}

function alturaFrame(){
	var totalTela = document.documentElement.clientHeight - 100;
	document.getElementById("frmconteudo").style.height = totalTela + "px";
}


var cor;
function addSublinhado(tr){
	cor=tr.style.backgroundColor;
	tr.style.backgroundColor='#D7EBF1';
}

function delSublinhado(tr){
	tr.style.backgroundColor=cor;
}

 function queryString(ID){
	var URL = document.location.href;
	
	if(URL.indexOf('?')>-1){
		var qString = URL.split('?');
		var keyVal = qString[1].split('&');
		for(var i=0;i<keyVal.length;i++){
			if(keyVal[i].indexOf(ID + '=')==0){
				var val = keyVal[i].split('=');
				return val[1];
			}
		}
        return "";
	}
    else
	{
        return "";
	}
}

function substituir(palavra,termoProcurar, termoSubstituir){
	while(palavra.indexOf(termoProcurar) > -1){
		palavra=palavra.replace(termoProcurar,termoSubstituir);
	}
	
	return palavra;
}

function exibirMensagem(tipo,msg){
	if(tipo=='1'){
		document.getElementById("imgAviso").src="images/finalizar_acao.gif"
		document.getElementById("textoAviso").innerHTML = substituir(msg,'%20',' ');
		document.getElementById("aviso").style.display="block";			
	}else if(tipo=='2'){
		document.getElementById("imgAviso").src="images/mensagem_erro.gif"
		document.getElementById("textoAviso").innerHTML = substituir(msg,'%20',' ');
		document.getElementById("aviso").style.display="block";
	}
}