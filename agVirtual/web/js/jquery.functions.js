//****
// apply slide down
//****
function applySlideDownEffect(obj){
	var $tbody = jQuery(obj).parents("tbody"),
	$box = $tbody.find(".box-expansivel"),
	$btnCollapse = $tbody.find("a");
	
	if ($box.is(":hidden")) {
		jQuery(".tabela-expansivel .box-expansivel").hide();
		jQuery(".tabela-expansivel tbody").removeClass("ativo hover").find(".expansivel").hide();
		jQuery(".tabela-expansivel tbody").removeClass("ativo hover").find("a").removeClass("btn_collapse_on"); // btn fecha
		jQuery(".tabela-expansivel tbody").removeClass("ativo hover").find("a").addClass("btn_collapse"); // btn abrir
		
		$tbody.addClass("ativo").find(".expansivel").show();
		//$tbody.find(".expansivel").find(".box-expansivel").show();
		
		$btnCollapse.addClass("btn_collapse_on");
		$btnCollapse.removeClass("btn_collapse");
		$box.slideDown(function(){
			// nothing here
		});
	} else {
		$box.slideUp(function (){
			$tbody.removeClass("ativo").find(".expansivel").hide();	
			$btnCollapse.removeClass("btn_collapse_on");
			$btnCollapse.addClass("btn_collapse");
			//ajustaAltura(document.getElementById('wrapper').offsetHeight);
		});
	}
	
}

//function imprimir(){
//	if(!window.print){
//		return;
//	}
//	alert('Print now!');
//	window.print();
//}
//
//function imprimirRelatorioAtendimento(){
//	alert('before expand');
//	jQuery(".tabela-expansivel .box-expansivel").show();
//	//print();
//}

// ****
// NOT USED ANY MORE
// apply slide down
//****
function applySlideDownEffect2(){
	jQuery(".tabela-expansivel .expansor td").click(function(){	
		
		var $tbody = jQuery(this).parents("tbody"),
			$box = $tbody.find(".box-expansivel"),
			$btnCollapse = $tbody.find("a");
			
		if ($box.is(":hidden")) {
			alert("IS hidden");
			jQuery(".tabela-expansivel .box-expansivel").hide();
			jQuery(".tabela-expansivel tbody").removeClass("ativo hover").find(".expansivel").hide();
			jQuery(".tabela-expansivel tbody").removeClass("ativo hover").find("a").removeClass("btn_collapse_on"); // btn fecha
			jQuery(".tabela-expansivel tbody").removeClass("ativo hover").find("a").addClass("btn_collapse"); // btn abrir
			
			alert($tbody.addClass("ativo").find(".expansivel"));
			$tbody.addClass("ativo").find(".expansivel").show();
			//$tbody.find(".expansivel").find(".box-expansivel").show();
			
			$btnCollapse.addClass("btn_collapse_on");
			$btnCollapse.removeClass("btn_collapse");
			$box.slideDown(function(){
				alert(slideDown);
				// nothing here
			});
		} else {
			alert("NOT hidden");
			$box.slideUp(function (){
				$tbody.removeClass("ativo").find(".expansivel").hide();	
				$btnCollapse.removeClass("btn_collapse_on");
				$btnCollapse.addClass("btn_collapse");
				//ajustaAltura(document.getElementById('wrapper').offsetHeight);
			});
		}
		
	});	
}
