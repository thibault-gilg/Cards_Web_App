$(document ).ready(function(){
	
	$("#home").click(function(){
		window.location.href = "/card.html";
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

	
    
    $.ajax({
    	url:"http://localhost:8084/RoomService/rooms",
    	type:"GET",
    	success: function(data) {
        	var i;
        	var json;
    		for(i=0;i<data.length;i++) {
    			console.log(data[i]);
        		json = data[i];
        		if (json.id_joueur1 != null) {
	        		$.ajax({
	        			url:"http://localhost:8081/UserService/getName/" +json.id_joueur1,
	        			type:"GET",
	        			success: function ( data ) {
	                		addRoomToList(json.id,json.name,data,json.mise);
	        			}
	        		});
        		}
        		else {
            		addRoomToList(json.id,json.name,"Inconnu",json.mise);
        		}
       		}
    	}
    });
    

    
     $("#createRoomButtonId").click(function(){
        window.location.href = "/createRoom.html"
    }); 
    
});


function addRoomToList(id,name, user, bet){
    
    content="<td> "+name+" </td> \
                            <td> "+user+" </td> \
                            <td> "+bet+" $</td> \
                            <td> \
                                <div class='center aligned'> \
                                    <div class='ui  vertical animated button' tabindex='0' onClick='onRoomSelected("+id+")'> \
                                        <div class='hidden content'>Play</div> \
                                        <div class='visible content'> \
                                            <i class='play circle icon'></i> \
                                        </div> \
                                    </div> \
                                </div> \
                            </td>";
    
    $('#roomListId tr:last').after('<tr>'+content+'</tr>');
    
    
};

function onRoomSelected(id){
	
	var values = JSON.parse(sessionStorage.getItem("Name"));
	
	var roomBet;
	
	updateMoney();
	
	$.ajax({
		url: "http://localhost:8084/RoomService/getBet/" + id,
		type:"GET",
		async: false,
		success: function(data){
			roomBet = data;
			if(values.money < data){
				toast("Not enough Money", "Error", "error");
				return;
			}
			$.ajax({
				  url:"http://localhost:8084/RoomService/addPlayer/"+ id.toString() + "/" + values.id,
				  type:"GET",
				  async: false,
				  success: function(){
					  //Withdraw money
					  $.ajax({
		    			  url:"http://localhost:8081/UserService/removeMoney/"+ values.id + "/" + roomBet,
		    			  type:"GET"
					  });
					  //Redirecting to the game
					  window.location.href = "/playRoom.html?id=" + id;
				  }
				});
		}
	})
	
	
	/**
	 * Update the money of the user in the header banner
	 */
	function updateMoney(){
		$.ajax({
			  url:"http://localhost:8081/UserService/money/" + values.id,
			  type:"GET",
			  success: function( data ){
					  $("#money").text(data);
			  }
		  });
		
	}
	
    

	
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