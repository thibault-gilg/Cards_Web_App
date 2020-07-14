  $(document ).ready(function(){
	  
		$("#home").click(function(){
			
			
			window.location.href = "/card.html";
		});
	  
	  var urlParams = new URLSearchParams(window.location.search);
		
	  
	  
	  var bet = urlParams.get('bet');
	  
	  
	  $("#moneyLost").text(bet);
	  
	 
	  
	  var values = JSON.parse(sessionStorage.getItem("Name"));
		
		$.ajax({
			  url:"http://localhost/UserService/money/" + values.id,
			  type:"GET",
			  success: function( data ){
					  $("#money").text(data);
					  $("#userNameId").text(values.name);
			  }
		  });

	  
    
      
      
});
