$(document ).ready(function(){
    

	var values = JSON.parse(sessionStorage.getItem("Name"));
	
	var urlParams = new URLSearchParams(window.location.search);
	
	var Roomid = urlParams.get('id');
	
	var ennemyId;
	
	var roomBet;
	
	
	$.ajax({
		url: "http://localhost/RoomService/getBet/" + Roomid,
		type:"GET",
		async: false,
		success: function(data){
			
			roomBet = data;
		
		}
	});

	
	
	//Get the ennemy user id
	$.ajax({
		url: "http://localhost/RoomService/joueurs/" + Roomid,
		type:"GET",
		async: false,
		success: function(data){
			var ids = data.split("/");
			
			if(ids[0] == values.id){
				ennemyId = ids[1];
			} else {
				ennemyId = ids[0];
			}
		
		}
	});
	
	$("#playerName").text(values.name);
	
	
	$.ajax({
		url: "http://localhost/UserService/getName/" + ennemyId,
		type:"GET",
		async: false,
		success: function(data){
			
			$("#EnnemyName").text(data);
		
		}
	});
	
	var myCards;

	$.ajax({
		url: "http://localhost/PlayService/GetCards/" + Roomid + "/" + values.id,
		type:"GET",
		async: false,
		success: function(data){
			myCards = JSON.parse(data);
		}
	});
	
	var myCardsResult;
	
	$.ajax({
		url: "http://localhost/PlayService/GetCardsResult/" + Roomid + "/" + values.id,
		type:"GET",
		async: false,
		success: function(data){
			myCardsResult = JSON.parse(data);
		}
	});
	
	
	var ennemyCards;

	$.ajax({
		url: "http://localhost/PlayService/GetCards/" + Roomid + "/" + ennemyId,
		type:"GET",
		async: false,
		success: function(data){
			ennemyCards = JSON.parse(data);
		}
	});
	
	var ennemyCardsResult;

	$.ajax({
		url: "http://localhost/PlayService/GetCardsResult/" + Roomid + "/" + ennemyId,
		type:"GET",
		async: false,
		success: function(data){
			ennemyCardsResult = JSON.parse(data);
		}
	});
	
	var results;
	
	$.ajax({
		url: "http://localhost/PlayService/GetResults/" + Roomid ,
		type:"GET",
		async: false,
		success: function(data){
			results = data.split("/");
		}
	});

	

	
	

	
	function play(id, card, ennemyCards){
		
		var card = myCards[id];
		
		var ennemyCard = ennemyCards[id];
		
		fillMyImage(card.imgUrl, card.name, card.attack, card.defence, card.id);
		
		fillEnnemyImage(ennemyCard.imgUrl, ennemyCard.name, ennemyCard.attack, ennemyCard.defence, ennemyCard.id);
		
	}
	
	function result(id, myCardsResult, ennemyCardsResult){
		
		var card = myCardsResult[id];
		
		var ennemyCard = ennemyCardsResult[id];
		
		replaceMy(card.attack, card.defence);
		
		replaceEnnemy(ennemyCard.attack, ennemyCard.defence);
		
	}
	
	
	function replaceMy(attack, defence){
		 $('#cardAttack').text(attack);
		 $('#cardDefence').text(defence);
	}
	
	function replaceEnnemy (attack, defence){
		
		 $('#cardAttackEnnemy').text(attack);
		 $('#cardDefenceEnnemy').text(defence);
		
	}
	
	function setResultsColor(idResult){
		 if(results[idResult] == values.id.toString()){
			 $('#MyCard').css("background-color", "green");
			 $('#EnnemyCard').css("background-color", "red");
		 } else {
			 $('#EnnemyCard').css("background-color", "green");
			 $('#MyCard').css("background-color", "red");
		 }
	}
	
	
	
	
	function fillMyImage(imgUrl, name, attack, defence, id){
		 $('#MyCard').css("background-color", "white");
		$('#cardImgId').attr("src", imgUrl);
	    $('#Name').text(name);
	    $('#cardAttack').text(attack);
	    $('#cardDefence').text(defence);
	    $('#cardId').text(id);
	}
	
	function fillEnnemyImage(imgUrl, name, attack, defence, id){
		 $('#EnnemyCard').css("background-color", "white");
		$('#cardImgIdEnnemy').attr("src", imgUrl);
	    $('#NameEnnemy').text(name);
	    $('#cardAttackEnnemy').text(attack);
	    $('#cardDefenceEnnemy').text(defence);
	    $('#cardIdEnnemy').text(id);
	}
	
	var MyPoints = 0;
	
	var EnnemyPoints = 0;
	
	function addMePoint(){
		MyPoints++;
		$("#MyPoints").text(MyPoints);
		
	}
	
	function addEnnemyPoint(){
		EnnemyPoints++;
		$("#EnnemyPoints").text(EnnemyPoints);
		
	}
	
	var id = 0;
	var idPlay = 0;
	
	var idResult = 0;
	

	var intervalId = null;
	
	var  interval = function(){
		   
		   if(id > 9){
			   
			   if(results[5] == values.id.toString()){

				   //Add money
				   var bet = roomBet * 2;
				   $.ajax({
		    			  url:"http://localhost/UserService/addMoney/"+ values.id + "/" + bet,
		    			  type:"GET",
		    			  async: false,
		    			  success : function(){
		    				  return;
		    			  }
					  });
				  
				   
					window.location.href = "/win.html?id=" + Roomid + "&bet=" + roomBet;
				} else {
					window.location.href = "/loose.html?id=" + Roomid + "&bet=" + roomBet;
				}
			   
			   clearInterval(intervalId);
			   
		   }
		   if(id%2 == 0){
			   play(idPlay, myCards, ennemyCards);
			   idPlay++;
		   } else {
			   result(idResult,myCardsResult, ennemyCardsResult);
			   setResultsColor(idResult);
			   if(results[idResult] == values.id.toString()){
				   addMePoint();
			   } else {
				   addEnnemyPoint();
			   }
			   idResult++;
		   }
		   id++
		}
	
	intervalId = setInterval(interval, 3000);
	
	

});




