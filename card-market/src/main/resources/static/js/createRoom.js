if(sessionStorage.getItem("Name") == null){
	window.location.href = "/login.html";
}


$(document).ready(function(){


	
	$("#home").click(function(){
		
		
		window.location.href = "/card.html";
	});
	
	var values = JSON.parse(sessionStorage.getItem("Name"));
	
	$("#menubar").load("../menu.html");
	
	updateMoney();
	
		$("#waitingPage").hide();

	    $("#cancelButtonId").click(function(){
	    	
	    	window.location.href = "/card.html";
	        
	    });  
	    
	    $("#form").submit(function(event){
	    	
	    	
	    	event.preventDefault();
	    	
	    	var values = JSON.parse(sessionStorage.getItem("Name"));
	    	
	    	var roomName = $("#roomNameId").val();
	    	
	    	var roomBet = $("#roomBetId").val();
	    	
	    	if(values.money < roomBet){
	    		toast("Not enough Money", "Error", "error");
	    		return;
	    		
	    	}
	    	
	    	var json = {
	        		"id_joueur1": values.id,
	        		"name": roomName,
	        		"mise": roomBet
	        	};
			
	        $.ajax({
	        	url:"http://localhost:8084/RoomService/addRoom",
	        	type:"POST",
	        	data: JSON.stringify(json),
	        	contentType:"application/json; charset=utf-8",
	        	success: function( data ){
	        		
	        		checkRoom(data, roomBet);
	        	}
	        });
	        
	        
	        $("#createForm").hide();
	        $("#waitingPage").show();
	        
	        
	        
	    }); 
	    
	   
	    function checkRoom(id, roomBet) {
	    	
	    
	    	
	    	setInterval(function() {
	    		$.ajax({
	    			  url:"http://localhost:8084/RoomService/isRoomOk/" + id,
	    			  type:"GET",
	    			  contentType:"application/json; charset=utf-8",
	    			  success: function( data ){
	    				  if(data == true){
	    					  //Withdraw money
	    					  $.ajax({
	    		    			  url:"http://localhost:8081/UserService/removeMoney/"+ values.id + "/" + roomBet,
	    		    			  type:"GET"
	    					  });
	    					 
	    					  //launch game
	    					  window.location.href = "/playRoom.html?id=" + id;
	    				  }
	    			  }
	    			})
	        }, 1000);
	    }
	    
	    
	    
		function toast(msg, header, type){
			toastr.options = {
					  "closeButton": false,
					  "debug": false,
					  "newestOnTop": false,
					  "progressBar": false,
					  "positionClass": "toast-top-center",
					  "preventDuplicates": true,
					  "onclick": null,
					  "showDuration": "300",
					  "hideDuration": "500",
					  "timeOut": "1000",
					  "extendedTimeOut": "500",
					  "showEasing": "swing",
					  "hideEasing": "linear",
					  "showMethod": "fadeIn",
					  "hideMethod": "fadeOut"
					}
			toastr[type](msg, header);
		}
		
		/**
		 * Update the money of the user in the header banner
		 */
		function updateMoney(){
			$.ajax({
				  url:"http://localhost:8081/UserService/money/" + values.id,
				  type:"GET",
				  success: function( data ){
						  $("#money").text(data);
						  $("#userNameId").text(values.name);
				  }
			  });
			
		}
	    
	    
});

