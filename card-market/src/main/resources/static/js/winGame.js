  $(document ).ready(function(){
	  
		$("#home").click(function(){
			
			
			window.location.href = "/card.html";
		});
	  
	  var urlParams = new URLSearchParams(window.location.search);
		
	  var id = urlParams.get('id');
	  
	  var bet = urlParams.get('bet');
	  
	  bet = bet * 2;
	  
	  $("#moneyWon").text(bet);
	  
	  
	  
	  
	  
	  $.ajax({
		  url:"http://localhost:8084/RoomService/deleteRoom/" + id,
		  type:"DELETE"
	  });
	  
	  
	  var values = JSON.parse(sessionStorage.getItem("Name"));
		
		$.ajax({
			  url:"http://localhost:8081/UserService/money/" + values.id,
			  type:"GET",
			  success: function( data ){
					  $("#money").text(data);
					  $("#userNameId").text(values.name);
			  }
		  });

	  
    
      
      
});
